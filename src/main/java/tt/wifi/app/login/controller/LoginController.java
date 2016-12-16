package tt.wifi.app.login.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import tt.wifi.app.base.controller.BaseController;
import tt.wifi.app.login.model.LoginAdmin;
import tt.wifi.app.login.service.iface.ILoginService;
import tt.wifi.app.utils.MD5Utils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangjq on 2016/9/9.
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{
    @Resource
    private ILoginService loginService;

    private String jsonStr;
    private Integer code;
    @RequestMapping(value = "/validate",produces = {"application/json;charset=UTF-8"},method={RequestMethod.POST})
    @ResponseBody
    public ModelAndView loginAdmin(String data) throws IOException {
        JSONObject jsonObject = JSONObject.parseObject(data);
        String account = jsonObject.get("account").toString();
        String password = jsonObject.get("password").toString();
        List<LoginAdmin> list = loginService.getByName(account);
        Map map = new HashMap();
        try {
            if (list.size()==0){
                jsonStr = "账号不存在";
                code = 3;
            }else{
                String md5 = MD5Utils.GetMD5Code(password);
                String validate = list.get(0).getPassword();

                if (md5.equals(validate)) {
                    jsonStr = "登录成功";
                    code = 1;
                }else{
                    jsonStr = "密码错误";
                    code = 2;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            map.put("result",jsonStr);
            map.put("code",code);
        }
            return new ModelAndView(new MappingJacksonJsonView(),map);

    }
}