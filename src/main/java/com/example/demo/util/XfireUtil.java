package com.example.demo.util;

import org.codehaus.xfire.client.Client;
import java.net.URL;
import java.util.List;

public class XfireUtil {

    /**
     * 动态Xfire方式
     * @param url
     * @param method
     * @param inputParams
     * @return
     * @throws Exception
     */
    public static Object getResult(String url,String method,
                                          List<Object> inputParams) throws Exception {
        Client client = null;
        try {
            client =  new Client(new URL(url));
            client.setTimeout(30*1000);
            Object[] resultArr = client.invoke(method, inputParams.toArray());
            return resultArr[0];
        } finally {
            if(client!=null) {
                client.close();
            }
        }
    }

}
