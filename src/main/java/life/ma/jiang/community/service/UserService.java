package life.ma.jiang.community.service;

import life.ma.jiang.community.mapper.UserMapper;
import life.ma.jiang.community.model.User;
import life.ma.jiang.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdateUser(User user){
        UserExample userExample = new UserExample();
        User dbuser = null;
        if(user.getAccountId() != null && !user.getAccountId().equals("0")){
            userExample.createCriteria().andAccountIdEqualTo(user.getAccountId().toString());
        }else{
            userExample.createCriteria().andPwdEqualTo(user.getPwd()).andNameEqualTo(user.getName());
        }
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() >0)
            dbuser = users.get(0);
        if(dbuser != null){
            user.setId(dbuser.getId());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.updateByPrimaryKey(user);
        }else
            userMapper.insert(user);
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
       userMapper.insert(user);
    }

    public User checkLogin(String name, String pwd) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name).andPwdEqualTo(pwd);
        List<User> users = userMapper.selectByExample(example);
        return  users == null || users.size()==0 ? null : users.get(0);
    }
}
