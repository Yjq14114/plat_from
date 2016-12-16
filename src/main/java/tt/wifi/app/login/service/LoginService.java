package tt.wifi.app.login.service;

import org.springframework.stereotype.Service;
import tt.wifi.app.login.dao.LoginAdminMapper;
import tt.wifi.app.login.model.LoginAdmin;
import tt.wifi.app.login.service.iface.ILoginService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yangjq on 2016/9/9.
 */
@Service("loginService")
public class LoginService implements ILoginService {
    @Resource
    private LoginAdminMapper loginAdminMapper;
    @Override
    public List<LoginAdmin> getByName(String name) {
        return loginAdminMapper.selectByname(name);
    }
}
