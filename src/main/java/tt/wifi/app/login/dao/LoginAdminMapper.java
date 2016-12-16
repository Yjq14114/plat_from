package tt.wifi.app.login.dao;

import org.springframework.stereotype.Repository;
import tt.wifi.app.login.model.LoginAdmin;

import java.util.List;

@Repository
public interface LoginAdminMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(LoginAdmin record);

    int insertSelective(LoginAdmin record);

    LoginAdmin selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(LoginAdmin record);

    int updateByPrimaryKey(LoginAdmin record);
    List<LoginAdmin> selectByname(String name);
}