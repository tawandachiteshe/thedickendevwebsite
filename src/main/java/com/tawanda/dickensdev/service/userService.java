package com.tawanda.dickensdev.service;

import com.tawanda.dickensdev.model.userDao;
import com.tawanda.dickensdev.model.userInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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

        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("secret", 10000, 128);
        String sql = "insert into userdb(username,userid,useremail,userpassword) values (?,uuid_generate_v4(),?,?)";
        jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.setString(1,userInfo.getUsername());
                preparedStatement.setString(2,userInfo.getEmail());
                preparedStatement.setString(3,encoder.encode(userInfo.getPassword()));
                return preparedStatement.execute();
            }
        });

        return 1;
    }
}
