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
                        if(cols[i].equals("경인영업부")) cols[i] = "Alpha Department";
                        if(cols[i].equals("동부영업부")) cols[i] = "Beta Department";
                        if(cols[i].equals("중부영업부")) cols[i] = "Gamma Department";
                        if(cols[i].equals("남부영업부")) cols[i] = "Delta Department";
                        if(cols[i].equals("유통1영업부")) cols[i] = "Epsilon Department";
                        if(cols[i].equals("유통2영업부")) cols[i] = "Zeta Department";
                        if(cols[i].equals("강서영업소")) cols[i] = "A Team";
                        if(cols[i].equals("금천영업소")) cols[i] = "B Team";
                        if(cols[i].equals("안산영업소")) cols[i] = "C Team";
                        if(cols[i].equals("제주영업소")) cols[i] = "D Team";
                        if(cols[i].equals("강릉영업소")) cols[i] = "E Team";
                        if(cols[i].equals("도봉영업소")) cols[i] = "F Team";
                        if(cols[i].equals("성북영업소")) cols[i] = "G Team";
                        if(cols[i].equals("용산영업소")) cols[i] = "H Team";
                        if(cols[i].equals("원주영업소")) cols[i] = "I Team";
                        if(cols[i].equals("이천영업소")) cols[i] = "J Team";
                        if(cols[i].equals("춘천영업소")) cols[i] = "K Team";
                        if(cols[i].equals("대전영업소")) cols[i] = "L Team";
                        if(cols[i].equals("천안영업소")) cols[i] = "M Team";
                        if(cols[i].equals("포항영업소")) cols[i] = "O Team";
                        if(cols[i].equals("평택영업소")) cols[i] = "P Team";
                        if(cols[i].equals("청주영업소")) cols[i] = "Q Team";
                        if(cols[i].equals("창원영업소")) cols[i] = "R Team";
                        if(cols[i].equals("진주영업소")) cols[i] = "S Team";
                        if(cols[i].equals("전주영업소")) cols[i] = "T Team";
                        if(cols[i].equals("일산영업소")) cols[i] = "U Team";
                        if(cols[i].equals("인천DC")) cols[i] = "V Team";
                        if(cols[i].equals("익산영업소")) cols[i] = "W Team";
                        if(cols[i].equals("울산영업소")) cols[i] = "X Team";
                        if(cols[i].equals("여수영업소")) cols[i] = "Y Team";
                        if(cols[i].equals("안동영업소 ")) cols[i] = "Z Team";
                        if(cols[i].equals("순천영업소")) cols[i] = "AA Team";
                        if(cols[i].equals("수지DC")) cols[i] = "BB Team";
                        if(cols[i].equals("성남영업소")) cols[i] = "CC Team";
                        if(cols[i].equals("서산영업소")) cols[i] = "DD Team";
                        if(cols[i].equals("부산영업소")) cols[i] = "FF Team";
                        if(cols[i].equals("목포영업소")) cols[i] = "GG Team";
                        if(cols[i].equals("동대구영업소")) cols[i] = "HH Team";
                        if(cols[i].equals("대구영업소")) cols[i] = "II Team";
                        if(cols[i].equals("남인천영업소")) cols[i] = "JJ Team";
                        if(cols[i].equals("남양주영업소")) cols[i] = "KK Team";
                        if(cols[i].equals("구포영업소")) cols[i] = "LL Team";
                        if(cols[i].equals("구미영업소")) cols[i] = "MM Team";
                        if(cols[i].equals("광주영업소")) cols[i] = "NN Team";
                        if(cols[i].equals("경남팀")) cols[i] = "OO Team";
                        if(cols[i].equals("경북팀")) cols[i] = "PP Team";
                        if(cols[i].equals("경인1팀")) cols[i] = "QQ Team";
                        if(cols[i].equals("경인2팀")) cols[i] = "RR Team";
                        if(cols[i].equals("경인3팀")) cols[i] = "SS Team";
                        if(cols[i].equals("김해영업소")) cols[i] = "TT Team";
                        if(cols[i].equals("충청팀")) cols[i] = "UU Team";
                        if(cols[i].equals("통영영업소")) cols[i] = "VV Team";
                        if(cols[i].equals("호남팀")) cols[i] = "WW Team";
                        if(cols[i].equals("E-Commerce팀")) cols[i] = "XX Team";
                        if(cols[i].equals("CVS팀")) cols[i] = "ZZ Team";
                        if(cols[i].equals("PC방도매")) cols[i] = "Sales classification A";
                        if(cols[i].equals("가판대")) cols[i] = "Sales classification B";
                        if(cols[i].equals("계절처")) cols[i] = "Sales classification C";
                        if(cols[i].equals("고속도로휴게소")) cols[i] = "Sales classification D";
                        if(cols[i].equals("교정기관")) cols[i] = "Sales classification E";
                        if(cols[i].equals("납품처")) cols[i] = "Sales classification F";
                        if(cols[i].equals("대리점")) cols[i] = "Sales classification G";
                        if(cols[i].equals("대형")) cols[i] = "Sales classification H";
                        if(cols[i].equals("매점")) cols[i] = "Sales classification I";
                        if(cols[i].equals("문구도매")) cols[i] = "Sales classification J";
                        if(cols[i].equals("백화점")) cols[i] = "Sales classification K";
                        if(cols[i].equals("소형")) cols[i] = "Sales classification L";
                        if(cols[i].equals("유통도매")) cols[i] = "Sales classification M";
                        if(cols[i].equals("인터넷")) cols[i] = "Sales classification N";
                        if(cols[i].equals("일반도매")) cols[i] = "Sales classification O";
                        if(cols[i].equals("자판기")) cols[i] = "Sales classification P";
                        if(cols[i].equals("준하이퍼")) cols[i] = "Sales classification Q";
                        if(cols[i].equals("중형")) cols[i] = "Sales classification R";
                        if(cols[i].equals("체인스토어")) cols[i] = "Sales classification S";
                        if(cols[i].equals("초대형")) cols[i] = "Sales classification T";
                        if(cols[i].equals("콘테")) cols[i] = "Sales classification U";
                        if(cols[i].equals("특수처")) cols[i] = "Sales classification V";
                        if(cols[i].equals("특약점")) cols[i] = "Sales classification W";
                        if(cols[i].equals("팬시문구")) cols[i] = "Sales classification X";
                        if(cols[i].equals("학교도매")) cols[i] = "Sales classification Y";
                        if(cols[i].equals("할인점")) cols[i] = "Sales classification Z";
                        if(cols[i].equals("휴게소")) cols[i] = "Sales classification AA";
                        if(cols[i].equals("골프장")) cols[i] = "Sales classification BB";
                        if(cols[i].equals("국군PX")) cols[i] = "Sales classification CC";
                        if(cols[i].equals("드럭스토어")) cols[i] = "Sales classification DD";
                        if(cols[i].equals("소고")) cols[i] = "Sales classification EE";
                        if(cols[i].equals("수출")) cols[i] = "Sales classification FF";
                        if(cols[i].equals("숙박요식업")) cols[i] = "Sales classification GG";
                        if(cols[i].equals("이커머스")) cols[i] = "Sales classification HH";
                        if(cols[i].equals("천냥하우스")) cols[i] = "Sales classification YY";
                        if(cols[i].equals("철도유통")) cols[i] = "Sales classification JJ";
                        if(cols[i].equals("편의점")) cols[i] = "Sales classification KK";
                        
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
