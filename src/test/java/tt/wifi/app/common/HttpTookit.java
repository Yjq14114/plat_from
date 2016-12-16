package tt.wifi.app.common;


import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tt.wifi.app.utils.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yangjq on 2016/9/30.
 */
public final class HttpTookit {

    private static Log log = LogFactory.getLog(HttpTookit.class);
    public static String sessionId;
    public static String doGet(String url,String queryString){
        String response = null;
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        method.setRequestHeader("Content-type" , "text/html; charset=utf-8");
        if(!StringUtil.isEmpty(sessionId)){
            method.setRequestHeader("Cookie", "JSESSIONID="+sessionId);
        }
        try {
            if (StringUtils.isNotBlank(queryString))
                method.setQueryString(URIUtil.encodeQuery(queryString));
            client.executeMethod(method);
            //System.out.println(method.getStatusCode());
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                setSessionId(method);
                InputStream inputStream = method.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String str= "";
                while((str = br.readLine()) != null){
                    stringBuffer .append(str );
                }
                response = stringBuffer.toString();
            }
        } catch (URIException e) {
            log.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e);
        } catch (IOException e) {
            log.error("执行HTTP Get请求" + url + "时，发生异常！", e);
        } finally {
            method.releaseConnection();
        }
        return response;
    }

    /**
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map<String, String> params) {
        HttpClient client = new HttpClient();
        //设置Http Post数据
        PostMethod postMethod = new PostMethod(url);
        List<NameValuePair> data =new ArrayList<NameValuePair>();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                NameValuePair nvp = new NameValuePair(key, value);
                data.add(nvp);
            }
        }
        postMethod.setRequestBody(data.toArray(new NameValuePair[0]));
        if (!StringUtil.isEmpty(sessionId)) {
            postMethod.setRequestHeader("Cookie", "JSESSIONID="+sessionId);
        }
        int statusCode;
        try {
            statusCode = client.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                setSessionId(postMethod);
                String sms = postMethod.getResponseBodyAsString();
                System.out.println("result:" + sms);
                return sms;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            postMethod.releaseConnection();
        }
        return null;
    }
    private static void setSessionId(HttpMethod m) {
        Header header = m.getResponseHeader("Set-Cookie");
        if (header==null) {
            return;
        }
        HeaderElement[] es = header.getElements();
        for (HeaderElement e : es) {
            if (e.getName().equals("JSESSIONID")) {
                if(StringUtil.isEmpty(sessionId)){
                    sessionId=e.getValue();
                    break;
                }
            }
        }

    }
}
