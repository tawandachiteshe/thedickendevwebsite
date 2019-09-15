package com.tawanda.dickensdev.service;

import com.tawanda.dickensdev.model.userDao;
import com.tawanda.dickensdev.model.userInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("postgres")
public class userService {
    private userDao userDao;

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public userService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<userInfo> getAllpeople(){
        //        String username, String email, String password, UUID userId

        return jdbcTemplate.query("select * from userdb",(resultSet, i) -> {
            String name = resultSet.getString("username");
            UUID userid =  UUID.fromString(resultSet.getString("userid"));
            String uemail = resultSet.getString("useremail");
            String password = resultSet.getString("userpassword");
            return new userInfo(name,uemail,password,userid);
        });
    }

    public int insertUser(userInfo userInfo){
        return userDao.insertUser(userInfo);
    }
}
