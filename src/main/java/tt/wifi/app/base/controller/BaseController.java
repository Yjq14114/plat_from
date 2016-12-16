package tt.wifi.app.base.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yangjq on 2016/9/29.
 */
@Controller
@RequestMapping("/*")
public class BaseController {
    public HttpServletRequest request;
    public HttpServletResponse response;
    @RequestMapping("/*")
    @ResponseBody
    public void initRequest(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.response = response;
    }
    public void writeJson(Object obj) throws IOException{
        try {
            String json = JSON.toJSONStringWithDateFormat(obj,"yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            response.getWriter().close();
        }
    }
}
