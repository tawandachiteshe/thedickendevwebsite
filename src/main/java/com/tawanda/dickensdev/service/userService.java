package com.tawanda.dickensdev.service;

import com.tawanda.dickensdev.model.Role;
import com.tawanda.dickensdev.model.userDao;
import com.tawanda.dickensdev.model.userInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository("postgres")
public class userService implements UserDetailsService {
    private userDao userDao;
    private Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("secret", 10000, 128);
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

    public int registerUser(userInfo userInfo){


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

    public boolean validateLogin(userInfo userInfo){
        Boolean isValid = false;
        List<userInfo> validate = getAllpeople();

        for (userInfo user: validate) {
            if(user.getEmail().equals(userInfo.getEmail())&&encoder.matches(userInfo.getPassword(),user.getPassword())){
                isValid = true;
            }
        }
        return isValid;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<userInfo> validate = getAllpeople();

        User user1 = null;
        for (userInfo user: validate) {
           if(user.getEmail().equals(s)){

               user1 = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(Arrays.asList(new Role("ROLE_USER"))));
               return user1;
           }
        }
        return user1;
    }

    private Collection < ? extends GrantedAuthority > mapRolesToAuthorities(Collection <Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
