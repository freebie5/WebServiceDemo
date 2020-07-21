package com.example.demo.util;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AxisUtil {

    /**
     * 动态Axis方式
     * @param url
     * @param method
     * @param inputParams
     * @return
     * @throws Exception
     */
    public static Object getResult(String url, String method, String namespace,
                                         List<Map<String,Object>> inputParams) throws Exception {
        Service service = new Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(url);
        call.setOperationName(new QName(namespace, method));
        call.setUseSOAPAction(true);
        call.setSOAPActionURI(namespace+method);
        call.setTimeout(30*1000);
//        call.setReturnType(XMLType.XSD_STRING);
        List<Object> values = new ArrayList<>();
        if(inputParams!=null) {
            for(Map<String,Object> inputParam:inputParams) {
                String paramName = (String)inputParam.get("paramName");
                QName paramValueType = (QName)inputParam.get("paramValueType");
                call.addParameter(paramName, paramValueType, ParameterMode.IN);
                Object paramValue = inputParam.get("paramValue");
                values.add(paramValue);
            }
        }
        // 调用服务
        return call.invoke(values.toArray());
    }

}
