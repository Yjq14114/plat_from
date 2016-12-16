package tt.wifi.app.login;


import org.junit.Test;
import tt.wifi.app.base.JUnitBaseTest;
import tt.wifi.app.utils.DateUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by yangjq on 2016/9/29.
 */

public class TestLogin extends JUnitBaseTest{

    @Test
    public void testLogin() throws Exception{
        String url = "/login/validate";
        mockMvc.perform(post(url).param("data","{account:'xiebin',password:'12345'}"))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    public void testJuint(){
        String url = "http://localhost:8888/wifi-platform/login/validate";
        String param = "{account:'xiebin',password:'12345'}";
        System.out.println(sendPostReq(url,param, DateUtil.getDate().getTime()));;
    }
}
