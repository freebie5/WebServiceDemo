package com.example.demo.util;

import org.apache.axis.encoding.XMLType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static String METHOD = "getCountryCityByIp";

    private static String RESULT_TAG = "getCountryCityByIpResult";

    private static String NAME_SPACE = "http://WebXml.com.cn/";

    private static String WSDL = "http://www.webxml.com.cn/WebServices/IpAddressSearchWebService.asmx?wsdl";

    public static void main(String[] args) throws Exception {
//        Main.getResultByHttpClient4();
//        Main.getResultByCxf();
//        Main.getResultByXfire();
//        Main.getResultByAxis();
        Main.getResultByAxis2();
    }

    public static void getResultByHttpClient4() throws Exception {
        String url = WSDL.replace("?wsdl","");//不需要?wsdl后缀;
        String method = METHOD;
        String resultTag = RESULT_TAG;
        String namespace = NAME_SPACE;
        List<Map<String,Object>> inputParams = new ArrayList<>();
        //
        Map<String,Object> param0 = new HashMap<>();
        param0.put("paramName", "theIpAddress");
        param0.put("paramValue", "183.54.42.103");
        inputParams.add(param0);
        //
        Object result = HttpClient4Util.getResult(url, method, resultTag, namespace, inputParams);
        System.out.println(result);
    }

    public static void getResultByCxf() throws Exception {
        String url = WSDL;
        String nameSpace = NAME_SPACE;
        String method = METHOD;
        List<Object> inputParams = new ArrayList<>();
        inputParams.add("183.54.42.103");
        Object result = CxfUtil.getResult(url, nameSpace, method, inputParams);
        System.out.println(result);
    }

    public static void getResultByXfire() throws Exception {
        String url = WSDL;
        String method = METHOD;
        List<Object> inputParams = new ArrayList<>();
        inputParams.add("183.54.42.103");
        Object result = XfireUtil.getResult(url, method, inputParams);
        System.out.println(result);
    }

    public static void getResultByAxis() throws Exception {
        String url = WSDL.replace("?wsdl","");//不需要?wsdl后缀
        String method = METHOD;
        String namespace = NAME_SPACE;
        List<Map<String,Object>> inputParams = new ArrayList<>();
        //
        Map<String,Object> inputParam0 = new HashMap<>();
        inputParam0.put("paramName", "theIpAddress");
        inputParam0.put("paramValueType", XMLType.XSD_STRING);
        inputParam0.put("paramValue", "183.54.42.103");
        inputParams.add(inputParam0);
        //
        Object result = AxisUtil.getResult(url, method, namespace, inputParams);
        System.out.println(result);
    }

    public static void getResultByAxis2() throws Exception {
        String url = WSDL.replace("?wsdl","");//不需要?wsdl后缀
        String method = METHOD;
        String namespace = NAME_SPACE;
        List<Map<String,Object>> inputParams = new ArrayList<>();
        //
        Map<String,Object> inputParam0 = new HashMap<>();
        inputParam0.put("paramName", "theIpAddress");
        inputParam0.put("paramValueType", XMLType.XSD_STRING);
        inputParam0.put("paramValue", "183.54.42.103");
        inputParams.add(inputParam0);
        String result = Axis2Util.getResult(url, method, namespace, inputParams);
        System.out.println(result);
    }

}
