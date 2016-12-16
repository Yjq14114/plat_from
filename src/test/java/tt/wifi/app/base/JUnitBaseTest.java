package tt.wifi.app.base;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import tt.wifi.app.common.HttpTookit;
import tt.wifi.app.utils.AESUtil;
import tt.wifi.app.utils.DateUtil;
import tt.wifi.app.utils.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by yangjq on 2016/9/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:conf/spring-mybatis.xml"})

@TransactionConfiguration(defaultRollback = true)
@Transactional
public class JUnitBaseTest extends TestCase {
    protected final static String HOST=	"http://localhost:8888/wifi-platform";

    private ApplicationContext ac = null;

    @Autowired
    private WebApplicationContext wac;

    public MockMvc mockMvc;
    //  @Before
//  public void before() {
//      ac = new ClassPathXmlApplicationContext("applicationContext.xml");
////      userService = (IUserService) ac.getBean("userService");
//  }
    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }
    public String sendPostReq(String url,String json,Long time){

        String keySeed=getKeySeed(time,json.length());
        //对数据进行des加密
        String data= AESUtil.encode(json, keySeed);
        //生成校验码
        String key = StringUtil.MD5(time+data);
        //对加密后的数据进行url编码，排除传入后台后取得数据的异常性，比如+、空格等会获取时产生差异
        try {
            data= URLEncoder.encode(data,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //String param=String.format("time=%s&data=%s&key=%s&dl=%s", time,data,key,json.length());
        //System.out.println(url+"?"+param);
        Map<String,String> params = new HashMap<String, String>();
        params.put("time", String.valueOf(time));
        params.put("data", data);
        params.put("key", key);
        params.put("dl", String.valueOf(json.length()));

        String result = HttpTookit.doPost(url, params);
        Map<String,Object> resultData = getResult(result);
        result = resultData.toString();
        return result;

    }
    private String getKeySeed(Long time,int len) {
        return String.valueOf(time+len);
    }
    private Date getSysDate(){
        return DateUtil.getDate();
    }
    private Long getServerTimeDiff(){
        Long time = 0l;
        String result = HttpTookit.doGet(HOST+"/app/getsystime", "");
        Map<String,Object> datas = JSON.parseObject(result,Map.class);
        time = StringUtil.getLong(datas.get("time"));
        return time - DateUtil.getDate().getTime();
    }
    @SuppressWarnings("unchecked")
    private Map<String,Object> getResult(String data) {
        Map<String,Object> datas = JSON.parseObject(data,Map.class);
        System.out.println(data);
        Long time = StringUtil.getLong(datas.get("time"));
        String key = (String)datas.get("key");
        String json = (String)datas.get("data");
        Integer dl = StringUtil.getInt(datas.get("dl"));
        //校验时间的差异是否正常
        Date serverDate = new Date(time);
        Date clientDate = new Date(getSysDate().getTime()+getServerTimeDiff());
        //若服务器时间小于客户端时间20秒，则表示请求异常
        if (DateUtil.dateDiff(clientDate,serverDate)>20000){
            System.out.println("请求超时");
            return null;
        }
        //校验数据是否正常
        String validateCode=StringUtil.MD5(time+json);
        if (!validateCode.equals(key)){
            System.out.println("数据校验出错");
            return null;
        }
        //解密json
        String keySeed=String.valueOf(time+dl);
        String result = AESUtil.decode(json, keySeed);
        Map<String,Object> params =(Map<String,Object>)com.alibaba.fastjson.JSON.parse(result);
        return params;
    }
}
