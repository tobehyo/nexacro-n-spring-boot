package com.tobesoft.overseas.nexacrodemo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nexacro.java.xapi.data.DataSet;
import com.nexacro.java.xapi.data.DataSetList;
import com.nexacro.java.xapi.data.DataTypes;
import com.nexacro.java.xapi.data.PlatformData;
import com.nexacro.java.xapi.data.VariableList;
import com.nexacro.java.xapi.tx.PlatformException;
import com.nexacro.java.xapi.tx.PlatformRequest;
import com.nexacro.java.xapi.tx.PlatformResponse;
import com.nexacro.java.xapi.tx.PlatformType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/services/nexacroapi")
@RestController
public class NexacroApiSampleController {

    private static final Logger log = LoggerFactory.getLogger(NexacroApiSampleController.class);

    @GetMapping
    public void getDataNexacroApiSampleController(HttpServletRequest request, HttpServletResponse response) throws IOException, PlatformException {

        String strCharset = "utf-8";
        //String platformType = PlatformType.CONTENT_TYPE_SSV;
        String platformType = PlatformType.CONTENT_TYPE_XML;

        PlatformRequest platformRequest = new PlatformRequest(request.getInputStream(), platformType, strCharset);
        platformRequest.receiveData();
        PlatformData pd = platformRequest.getData();
        log.debug("GET dataset count {} variable count {}", pd.getDataSetCount(), pd.getVariableCount());

        PlatformResponse platformResponse = new PlatformResponse(response.getOutputStream(), platformType, strCharset);
        PlatformData outPD = platformRequest.getData();
        VariableList outVariableList = new VariableList();
        DataSetList outDataSetList = new DataSetList();

        try {

            DataSet outDataSet0  = new DataSet("output");
            outDataSet0.addColumn("Column0", DataTypes.STRING, 20);
            outDataSet0.addColumn("Column1", DataTypes.STRING, 256);
            outDataSet0.addColumn("Column2", DataTypes.STRING, 256);
            outDataSet0.addColumn("Column3", DataTypes.STRING, 256);
            outDataSet0.addColumn("Column4", DataTypes.STRING, 256);

            int nRow = outDataSet0.newRow();
            outDataSet0.set(nRow, "Column0", "테스트");
            nRow = outDataSet0.newRow();
            outDataSet0.set(nRow, "Column0", "테스트");


            outDataSetList.add(outDataSet0);

            outVariableList.add("ErrorCode", 0);
            outVariableList.add("ErrorMsg",  "Success");
            outVariableList.add("argTest",  "variable id");


        } catch(Exception e) {

            outVariableList.add("ErrorMsg",  e);
            outVariableList.add("ErrorCode", -1);
            e.printStackTrace();

        } finally {

            outPD.setDataSetList(outDataSetList);
            outPD.setVariableList(outVariableList);
            platformResponse.setData(outPD);
            platformResponse.sendData();
        }
    }
    
    @PostMapping
    public void postDataNexacroApiSampleController(HttpServletRequest request, HttpServletResponse response) throws IOException, PlatformException {

        String strCharset = "utf-8";
        //String platformType = PlatformType.CONTENT_TYPE_SSV;
        String platformType = PlatformType.CONTENT_TYPE_XML;

        PlatformRequest platformRequest = new PlatformRequest(request.getInputStream(), platformType, strCharset);
        platformRequest.receiveData();
        PlatformData inPD = platformRequest.getData();
        log.info("POST dataset count {} variable count {}", inPD.getDataSetCount(), inPD.getVariableCount());

        DataSetList inDatdasetList = null;
        VariableList inVariableList = null;

        if(inPD.getDataSetCount() > 0) inDatdasetList = inPD.getDataSetList();
        if(inPD.getVariableCount() > 0) inVariableList = inPD.getVariableList();

        log.info("Datast List {} Variable List {}", inDatdasetList, inVariableList);
        
        PlatformResponse platformResponse = new PlatformResponse(response.getOutputStream(), platformType, strCharset);
        PlatformData outPD = new PlatformData();
        VariableList outVariableList  = outPD.getVariableList();
        DataSetList outDataSetList   = outPD.getDataSetList();

        try {

            DataSet outDataSet0  = new DataSet("output");
            outDataSet0.addColumn("Column0", DataTypes.STRING, 20);
            outDataSet0.addColumn("Column1", DataTypes.STRING, 256);
            outDataSet0.addColumn("Column2", DataTypes.STRING, 256);
            outDataSet0.addColumn("Column3", DataTypes.STRING, 256);
            outDataSet0.addColumn("Column4", DataTypes.STRING, 256);

            int nRow = outDataSet0.newRow();
            outDataSet0.set(nRow, "Column0", "테스트");
            nRow = outDataSet0.newRow();
            outDataSet0.set(nRow, "Column0", "테스트");


            outDataSetList.add(outDataSet0);

            outVariableList.add("ErrorCode", 0);
            outVariableList.add("ErrorMsg",  "Success");
            outVariableList.add("argTest",  "variable id");


        } catch(Exception e) {

            outVariableList.add("ErrorMsg",  e);
            outVariableList.add("ErrorCode", -1);
            e.printStackTrace();

        } finally {

            outPD.setDataSetList(outDataSetList);
            outPD.setVariableList(outVariableList);
            platformResponse.setData(outPD);
            platformResponse.sendData();            
        }
    }
}
