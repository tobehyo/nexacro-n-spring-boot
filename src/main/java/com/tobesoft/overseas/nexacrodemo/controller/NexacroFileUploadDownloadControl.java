package com.tobesoft.overseas.nexacrodemo.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nexacro.java.xapi.data.ColumnHeader;
import com.nexacro.java.xapi.data.DataSet;
import com.nexacro.java.xapi.data.DataTypes;
import com.nexacro.java.xapi.data.PlatformData;
import com.nexacro.java.xapi.data.VariableList;
import com.nexacro.java.xapi.tx.PlatformException;
import com.nexacro.java.xapi.tx.PlatformRequest;
import com.nexacro.java.xapi.tx.PlatformResponse;
import com.nexacro.java.xapi.tx.PlatformType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class NexacroFileUploadDownloadControl {

    private static final Logger log = LoggerFactory.getLogger(NexacroFileUploadDownloadControl.class);

    private static final String SP = File.separator;  
    private static final String PATH = "upload";
    private static final String CHAR_SET = "UTF-8";


    private String getFilePath(String userFolder) {
        String realPath = new File("src/main/resources/static").getAbsolutePath();
        String uploadPath =  realPath + SP + PATH + SP + userFolder;
        log.debug(uploadPath);
        File extFolder = new File(uploadPath);
        if(!extFolder.exists()) {
            extFolder.mkdirs();
        }
        return uploadPath;
    }

    private void addFiles(List<File> files, File f) {
        if(f.isDirectory()) {
            File[] listFiles = f.listFiles();
            assert listFiles != null;
            for(File file: listFiles) {
                addFiles(files, file);
            }
        } else {
            if (f.isFile()){
                files.add(f);
            }
        }
    }

    private void sendPlatformData(HttpServletResponse response, DataSet ds) throws IOException, PlatformException {        
        String platformType = PlatformType.CONTENT_TYPE_XML;
        PlatformResponse platformResponse = new PlatformResponse(response.getOutputStream(), platformType, CHAR_SET);
        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        resVarList.add("ErrorCode", 0);
        resVarList.add("ErrorMsg", "Success" );
        resData.addDataSet(ds);
        platformResponse.setData(resData);
        platformResponse.sendData();
    }

    @RequestMapping("/services/downloadFileList" )
    public void searchFiles(HttpServletRequest request, HttpServletResponse response) throws IOException, PlatformException {

        PlatformRequest platformRequest = new PlatformRequest(request.getInputStream(), PlatformType.CONTENT_TYPE_XML, CHAR_SET);
        platformRequest.receiveData();
        PlatformData inPD = platformRequest.getData();
        VariableList inVariableList  = inPD.getVariableList();

        String userFileFolder = inVariableList.getString("filefolder");
        String uploadPath = getFilePath(userFileFolder);
        String url = request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/services")) + "/" + PATH + "/" +userFileFolder + "/";

        List<File> fileList = new ArrayList<>();
        File directory = new File(uploadPath);
        addFiles(fileList, directory);

        DataSet ds = new DataSet("dsList");
        ds.addColumn("FILE_NAME", DataTypes.STRING, 255);
        ds.addColumn("FILE_URL", DataTypes.STRING, 255);
        ds.addColumn("FiLE_SIZE", DataTypes.STRING, 255);

        for(File file: fileList) {
            int newRow = ds.newRow();
            ds.set(newRow, "FILE_NAME", file.getName());
            ds.set(newRow, "FILE_URL", url + file.getName());
            ds.set(newRow, "FiLE_SIZE", file.length());

        }

        sendPlatformData(response, ds);
    }

    @RequestMapping("/services/uploadFile")
    public void uploadFiles(@RequestParam("filefolder") String userFileFolder, HttpServletRequest request, HttpServletResponse response) throws Exception {

        if(!(request instanceof MultipartHttpServletRequest)) {

            log.debug("Request is not a MultipartHttpServletRequest");
        }
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

            // parameter and multipart parameter
            //Enumeration<String> parameterNames = multipartRequest.getParameterNames();

            // files..
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            if(fileMap.isEmpty()) {
                String errmsg = "No upload file";
                DataSet dsError = new DataSet("ds_error");
                dsError.addColumn(new ColumnHeader("ErrorMsg", DataTypes.STRING));
                dsError.addColumn(new ColumnHeader("ErrorCode", DataTypes.LONG));

                dsError.set(dsError.newRow(), "ErrorCode", -1);
                dsError.set(dsError.newRow(), "ErrorMsg", errmsg);
                sendPlatformData(response, dsError);
                return;
            }
            String filePath = getFilePath(userFileFolder);
            Set<String> keySet = fileMap.keySet();

            DataSet ds = new DataSet("filefolder");
            ds.addColumn(new ColumnHeader("fileName", DataTypes.STRING));
            ds.addColumn(new ColumnHeader("fileSize", DataTypes.LONG));
            ds.addColumn(new ColumnHeader("fileType", DataTypes.STRING));
            ds.addColumn(new ColumnHeader("savePath", DataTypes.STRING));
            ds.addColumn(new ColumnHeader("orgName", DataTypes.STRING));
            int row;
            for(String name: keySet) {

                MultipartFile multipartFile = fileMap.get(name);
                String originalFilename = multipartFile.getOriginalFilename();
                //String fileName = new Date().getTime() + PREFIX + originalFilename;
                String fileName = originalFilename;
                String type = multipartFile.getContentType();
                //File destination = File.createTempFile(PREFIX, originalFilename, new File(filePath));
                Boolean isExistFile = new File(filePath + SP + fileName).exists();
                if(isExistFile) {
                    fileName = new Date().getTime() + "_" + originalFilename;
                }
                File destination = new File(filePath + SP + fileName);
                multipartFile.transferTo(destination);
                String url = request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/services")) + "/" + PATH + "/" +userFileFolder + "/";
                row = ds.newRow(0);
                ds.set(row, "fileName", fileName);
                ds.set(row, "fileSize", destination.length());
                ds.set(row, "fileType", type);
                ds.set(row, "savePath", url + fileName);
                ds.set(row, "orgName", originalFilename);

                log.debug("uploaded file write success. file="+originalFilename);
            }

            sendPlatformData(response, ds);

        } catch (Exception e) {
            log.debug(e.getMessage());
            DataSet dsError = new DataSet("ds_error");
            dsError.addColumn(new ColumnHeader("ErrorMsg", DataTypes.STRING));
            dsError.addColumn(new ColumnHeader("ErrorCode", DataTypes.LONG));

            dsError.set(dsError.newRow(), "ErrorCode", -1);
            dsError.set(dsError.newRow(), "ErrorMsg", e.getMessage());

            sendPlatformData(response, dsError);
        }

    }

    @RequestMapping("/services/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam("filename") String fileName,
                             @RequestParam("filefolder") String fileFolder) throws Exception {

        String characterEncoding = request.getCharacterEncoding();
        if(characterEncoding == null) {
            characterEncoding = PlatformType.DEFAULT_CHAR_SET;
        }

        fileName = new String(fileName.getBytes("iso8859-1"), characterEncoding);

        fileName = fileName.replace("/", "");
        fileName = fileName.replace("\\", "");
//        fileName = fileName.replace(".", "");
        fileName = fileName.replace("&", "");

        String downloadFilePath = getFilePath(fileFolder);
        String realFileName = downloadFilePath + SP + fileName;
        // already decode..
        // String decodedFileName = URLDecoder.decode(realFileName, "utf-8");

        File file = new File(realFileName);

        // default - application/octet-stream
        // result.setContentType(contentType); // set MIME TYPE

        byte[] buffer = new byte[1024];

        ServletOutputStream out_stream	= null;
        BufferedInputStream in_stream	= null;

        if(file.exists())
        {
            try
            {
                response.setContentType("application/octet;charset=utf-8");
                //response.setHeader("Content-Disposition", "attachment; filename = \"" + filename + "\"");
                response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, CHAR_SET) + ";");

                out_stream = response.getOutputStream();
                in_stream = new BufferedInputStream(new FileInputStream(file));

                int n;
                while ((n = in_stream.read(buffer, 0, 1024)) != -1)
                {
                    out_stream.write(buffer, 0, n);
                }

               log.debug("fileDownload filename==>"+fileName + ", Success. ");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (in_stream != null)
                {
                    try
                    {
                        in_stream.close();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                if (out_stream != null)
                {
                    try
                    {
                        out_stream.close();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        else
        {
            log.error("fileDownload filename==>"+fileName + ", No file name.");
            response.sendRedirect("unknownfile");
        }
    }

    @RequestMapping("/services/downloadFileAll")
    public void downloadFileAll(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam("filenamelist") String fileNameList,
                                @RequestParam("filefolder") String userFolder) {

        String[] arrNameList = fileNameList.split(",");

        ServletOutputStream out_stream	= null;
        BufferedInputStream in_stream	= null;
        ZipOutputStream zout_stream	= null;

        Date curDate = new Date();

        try
        {
            response.setContentType("application/octet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename = \"" + userFolder + curDate.getTime() + ".zip" + "\"");

            out_stream = response.getOutputStream();
            zout_stream = new ZipOutputStream(out_stream);
            zout_stream.setLevel(8);

            String filename = "";
            String filePath = "";

            for( int i = 0; i < arrNameList.length; i++ ){
                filename = new String(arrNameList[i].getBytes("iso8859-1"), "utf-8");
                filePath = getFilePath(userFolder) + SP + filename;
                File fis = new File(filePath);
                in_stream = new BufferedInputStream(new FileInputStream(fis));

                ZipEntry zentry = new ZipEntry(filename);
                zentry.setTime(fis.lastModified());
                zout_stream.putNextEntry(zentry);

                byte[] buffer = new byte[1024];
                int n;
                while ((n = in_stream.read(buffer, 0, 1024)) != -1)
                {
                    zout_stream.write(buffer, 0, n);
                }
                zout_stream.closeEntry();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (zout_stream != null)
            {
                try
                {
                    zout_stream.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            if (out_stream != null)
            {
                try
                {
                    out_stream.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            if (in_stream != null)
            {
                try
                {
                    in_stream.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }

    }

}

