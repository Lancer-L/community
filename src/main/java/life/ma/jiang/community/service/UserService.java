package life.ma.jiang.community.service;

import life.ma.jiang.community.mapper.UserMapper;
import life.ma.jiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdateUser(User user){
        User dbuser = userMapper.findUserByAccountId(user.getAccountId());
        if(dbuser != null){
            user.setId(dbuser.getId());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.updateUserById(user);
        }else
            userMapper.insertUser(user);
    }

    public User findUserByToken(String token){
        return  userMapper.findByToken(token);
    }
    public  User findUserByAccountId(String id){
        return  userMapper.findUserByAccountId(id);
    }


    public void createUser(String name, String pwd, String bio) {
        User user = new User();
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        user.setToken(UUID.randomUUID().toString());
        user.setName(name);
        user.setBio(bio);
        user.setPwd(pwd);
        user.setAvatarUrl("https://avatars2.githubusercontent.com/u/53986967?v=4");
        userMapper.insertUser(user);
    }

    public User checkLogin(String name, String pwd) {
        User user = userMapper.findUserByNameAndPwd(name,pwd);
        return user;
    }
}
