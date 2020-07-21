package com.example.demo.util;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import javax.xml.namespace.QName;
import java.util.List;

public class CxfUtil {

    /**
     * 动态Cxf方式
     * @param url
     * @param nameSpace
     * @param method
     * @param inputParams
     * @return
     * @throws Exception
     */
    public static Object getResult(String url, String nameSpace, String method,
                                        List<Object> inputParams) throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(url);
        // 设置超时单位为毫秒
        HTTPConduit conduit = (HTTPConduit)client.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(30*1000);
        policy.setAllowChunking(false);
        policy.setReceiveTimeout(30*1000);
        conduit.setClient(policy);
        //
        QName name = new QName(nameSpace, method);
        Object[] object = client.invoke(name, inputParams.toArray());
        return object[0];
    }

}
