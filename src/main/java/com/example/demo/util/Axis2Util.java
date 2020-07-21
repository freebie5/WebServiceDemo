package com.example.demo.util;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import java.util.List;
import java.util.Map;

public class Axis2Util {

    public static String getResult(String url, String methodName, String namespaceStr,
                                   List<Map<String,Object>> inputParams) throws Exception {

        EndpointReference endpointReference = new EndpointReference(url);

        //创建一个OMFactory，命名空旧，方法，参数都由它创建
        OMFactory factory = OMAbstractFactory.getOMFactory();

        //创建命名空间，如果你的WebService指定了targetNamespace属性的话，就要用这个
        //对应于@WebService(targetNamespace = "http://WebXml.com.cn/")
        OMNamespace omNamespace = factory.createOMNamespace(namespaceStr, "xsd");

        //创建一个method对象，"qqCheckOnline"为方法名
        OMElement method = factory.createOMElement(methodName, omNamespace);
        for(int i=0;i<inputParams.size();i++) {
            String paramName = (String)inputParams.get(0).get("paramName");
            String paramValue = (String)inputParams.get(0).get("paramValue");
            //创建参数，对应于@WebParam(name = "qqCode")
            //由于@WebParam没有指定targetNamespace，所以下面创建name参数时，用了null，否则你得赋一个namespace给它
            OMElement nameElement = factory.createOMElement(paramName, omNamespace);
            nameElement.addChild(factory.createOMText(nameElement, paramValue));
            //将入参设置到method
            method.addChild(nameElement);
        }

        //设置配置参数
        Options options = new Options();
        //此处对应于@WebMethod(action = "http://WebXml.com.cn/qqCheckOnline")
        options.setAction(namespaceStr+methodName);
        options.setTo(endpointReference);

        //创建客户端，并调用
        ServiceClient client = new ServiceClient();
        client.setOptions(options);
        OMElement result = client.sendReceive(method);
        return result.toString();
    }

}
