package com.tobesoft.overseas.nexacrodemo.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NexacroPivotDataController {
    @RequestMapping("/services/pivotData")
    public void getSsvPivotDatar(HttpServletRequest request, HttpServletResponse response)
            throws IOException, PlatformException {

        String strCharset = "utf-8";
        String platformType = PlatformType.CONTENT_TYPE_SSV;

        PlatformRequest platformRequest = new PlatformRequest(request.getInputStream(), null);
        platformRequest.receiveData();

        PlatformData pd = platformRequest.getData();
        VariableList vl = pd.getVariableList();

        String rowCount = vl.getString("rowcount");

        PlatformResponse platformResponse = new PlatformResponse(response.getOutputStream(), platformType, strCharset);
        PlatformData outPD = platformRequest.getData();
        VariableList outVariableList = new VariableList();
        DataSetList outDataSetList = new DataSetList();

        try {

            DataSet outDataSet0 = new DataSet("output");
            outDataSet0.addColumn("col1", DataTypes.STRING, 50);
            outDataSet0.addColumn("col2", DataTypes.STRING, 50);
            outDataSet0.addColumn("col3", DataTypes.STRING, 50);
            outDataSet0.addColumn("col4", DataTypes.STRING, 50);
            outDataSet0.addColumn("col5", DataTypes.STRING, 50);
            outDataSet0.addColumn("col6", DataTypes.STRING, 50);
            outDataSet0.addColumn("col7", DataTypes.STRING, 50);
            outDataSet0.addColumn("col8", DataTypes.STRING, 50);
            outDataSet0.addColumn("col9", DataTypes.STRING, 50);
            outDataSet0.addColumn("col10", DataTypes.STRING, 50);
            outDataSet0.addColumn("col11", DataTypes.STRING, 50);
            outDataSet0.addColumn("col12", DataTypes.INT, 8);
            outDataSet0.addColumn("col13", DataTypes.INT, 8);
            outDataSet0.addColumn("col14", DataTypes.INT, 8);
            outDataSet0.addColumn("col15", DataTypes.INT, 8);
            outDataSet0.addColumn("col16", DataTypes.INT, 8);

            if (rowCount.equals("")) {
                rowCount = "10000";
            }

            int nRow;
            File file = new ClassPathResource("dat/test" + rowCount + ".dat").getFile();
            BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));

            String line;
            char a = (char) 0x1e;
            char b = (char) 0x1f;
            String RS = String.valueOf(a);
            String US = String.valueOf(b);

            while ((line = br.readLine()) != null) {
                String[] values = line.split(RS);
                for (String str : values) {
                    String[] cols = str.split(US);

                    int colCnt = cols.length;
                    nRow = outDataSet0.newRow();
                    for (int i = 1; i < colCnt; i++) {
                        // Temporary change words Korean to English.
                        if(cols[i].equals("???????????????")) cols[i] = "Alpha Department";
                        if(cols[i].equals("???????????????")) cols[i] = "Beta Department";
                        if(cols[i].equals("???????????????")) cols[i] = "Gamma Department";
                        if(cols[i].equals("???????????????")) cols[i] = "Delta Department";
                        if(cols[i].equals("??????1?????????")) cols[i] = "Epsilon Department";
                        if(cols[i].equals("??????2?????????")) cols[i] = "Zeta Department";
                        if(cols[i].equals("???????????????")) cols[i] = "A Team";
                        if(cols[i].equals("???????????????")) cols[i] = "B Team";
                        if(cols[i].equals("???????????????")) cols[i] = "C Team";
                        if(cols[i].equals("???????????????")) cols[i] = "D Team";
                        if(cols[i].equals("???????????????")) cols[i] = "E Team";
                        if(cols[i].equals("???????????????")) cols[i] = "F Team";
                        if(cols[i].equals("???????????????")) cols[i] = "G Team";
                        if(cols[i].equals("???????????????")) cols[i] = "H Team";
                        if(cols[i].equals("???????????????")) cols[i] = "I Team";
                        if(cols[i].equals("???????????????")) cols[i] = "J Team";
                        if(cols[i].equals("???????????????")) cols[i] = "K Team";
                        if(cols[i].equals("???????????????")) cols[i] = "L Team";
                        if(cols[i].equals("???????????????")) cols[i] = "M Team";
                        if(cols[i].equals("???????????????")) cols[i] = "O Team";
                        if(cols[i].equals("???????????????")) cols[i] = "P Team";
                        if(cols[i].equals("???????????????")) cols[i] = "Q Team";
                        if(cols[i].equals("???????????????")) cols[i] = "R Team";
                        if(cols[i].equals("???????????????")) cols[i] = "S Team";
                        if(cols[i].equals("???????????????")) cols[i] = "T Team";
                        if(cols[i].equals("???????????????")) cols[i] = "U Team";
                        if(cols[i].equals("??????DC")) cols[i] = "V Team";
                        if(cols[i].equals("???????????????")) cols[i] = "W Team";
                        if(cols[i].equals("???????????????")) cols[i] = "X Team";
                        if(cols[i].equals("???????????????")) cols[i] = "Y Team";
                        if(cols[i].equals("??????????????? ")) cols[i] = "Z Team";
                        if(cols[i].equals("???????????????")) cols[i] = "AA Team";
                        if(cols[i].equals("??????DC")) cols[i] = "BB Team";
                        if(cols[i].equals("???????????????")) cols[i] = "CC Team";
                        if(cols[i].equals("???????????????")) cols[i] = "DD Team";
                        if(cols[i].equals("???????????????")) cols[i] = "FF Team";
                        if(cols[i].equals("???????????????")) cols[i] = "GG Team";
                        if(cols[i].equals("??????????????????")) cols[i] = "HH Team";
                        if(cols[i].equals("???????????????")) cols[i] = "II Team";
                        if(cols[i].equals("??????????????????")) cols[i] = "JJ Team";
                        if(cols[i].equals("??????????????????")) cols[i] = "KK Team";
                        if(cols[i].equals("???????????????")) cols[i] = "LL Team";
                        if(cols[i].equals("???????????????")) cols[i] = "MM Team";
                        if(cols[i].equals("???????????????")) cols[i] = "NN Team";
                        if(cols[i].equals("?????????")) cols[i] = "OO Team";
                        if(cols[i].equals("?????????")) cols[i] = "PP Team";
                        if(cols[i].equals("??????1???")) cols[i] = "QQ Team";
                        if(cols[i].equals("??????2???")) cols[i] = "RR Team";
                        if(cols[i].equals("??????3???")) cols[i] = "SS Team";
                        if(cols[i].equals("???????????????")) cols[i] = "TT Team";
                        if(cols[i].equals("?????????")) cols[i] = "UU Team";
                        if(cols[i].equals("???????????????")) cols[i] = "VV Team";
                        if(cols[i].equals("?????????")) cols[i] = "WW Team";
                        if(cols[i].equals("E-Commerce???")) cols[i] = "XX Team";
                        if(cols[i].equals("CVS???")) cols[i] = "ZZ Team";
                        if(cols[i].equals("PC?????????")) cols[i] = "Sales classification A";
                        if(cols[i].equals("?????????")) cols[i] = "Sales classification B";
                        if(cols[i].equals("?????????")) cols[i] = "Sales classification C";
                        if(cols[i].equals("?????????????????????")) cols[i] = "Sales classification D";
                        if(cols[i].equals("????????????")) cols[i] = "Sales classification E";
                        if(cols[i].equals("?????????")) cols[i] = "Sales classification F";
                        if(cols[i].equals("?????????")) cols[i] = "Sales classification G";
                        if(cols[i].equals("??????")) cols[i] = "Sales classification H";
                        if(cols[i].equals("??????")) cols[i] = "Sales classification I";
                        if(cols[i].equals("????????????")) cols[i] = "Sales classification J";
                        if(cols[i].equals("?????????")) cols[i] = "Sales classification K";
                        if(cols[i].equals("??????")) cols[i] = "Sales classification L";
                        if(cols[i].equals("????????????")) cols[i] = "Sales classification M";
                        if(cols[i].equals("?????????")) cols[i] = "Sales classification N";
                        if(cols[i].equals("????????????")) cols[i] = "Sales classification O";
                        if(cols[i].equals("?????????")) cols[i] = "Sales classification P";
                        if(cols[i].equals("????????????")) cols[i] = "Sales classification Q";
                        if(cols[i].equals("??????")) cols[i] = "Sales classification R";
                        if(cols[i].equals("???????????????")) cols[i] = "Sales classification S";
                        if(cols[i].equals("?????????")) cols[i] = "Sales classification T";
                        if(cols[i].equals("??????")) cols[i] = "Sales classification U";
                        if(cols[i].equals("?????????")) cols[i] = "Sales classification V";
                        if(cols[i].equals("?????????")) cols[i] = "Sales classification W";
                        if(cols[i].equals("????????????")) cols[i] = "Sales classification X";
                        if(cols[i].equals("????????????")) cols[i] = "Sales classification Y";
                        if(cols[i].equals("?????????")) cols[i] = "Sales classification Z";
                        if(cols[i].equals("?????????")) cols[i] = "Sales classification AA";
                        if(cols[i].equals("?????????")) cols[i] = "Sales classification BB";
                        if(cols[i].equals("??????PX")) cols[i] = "Sales classification CC";
                        if(cols[i].equals("???????????????")) cols[i] = "Sales classification DD";
                        if(cols[i].equals("??????")) cols[i] = "Sales classification EE";
                        if(cols[i].equals("??????")) cols[i] = "Sales classification FF";
                        if(cols[i].equals("???????????????")) cols[i] = "Sales classification GG";
                        if(cols[i].equals("????????????")) cols[i] = "Sales classification HH";
                        if(cols[i].equals("???????????????")) cols[i] = "Sales classification YY";
                        if(cols[i].equals("????????????")) cols[i] = "Sales classification JJ";
                        if(cols[i].equals("?????????")) cols[i] = "Sales classification KK";
                        
                        outDataSet0.set(nRow, i - 1, cols[i]);
                    }
                }
            }
            br.close();

            outDataSetList.add(outDataSet0);

            outVariableList.add("ErrorCode", 0);
            outVariableList.add("ErrorMsg", "Success");
            outVariableList.add("argTest", "variable id");

        } catch (Exception e) {

            outVariableList.add("ErrorMsg", e);
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
