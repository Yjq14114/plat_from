package tt.wifi.app.login.service.iface;

import org.springframework.stereotype.Repository;
import tt.wifi.app.login.model.LoginAdmin;

import java.util.List;

/**
 * Created by yangjq on 2016/9/9.
 */
@Repository
public interface ILoginService {
    List<LoginAdmin> getByName(String name);

}
