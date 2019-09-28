package com.tawanda.dickensdev.service;

import com.tawanda.dickensdev.model.Role;
import com.tawanda.dickensdev.model.UserRoles;
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
@Repository
public class userService implements UserDetailsService {
    private userDao userDao;
    private UserRoles userRoles;
    private Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("secret", 10000, 128);
    private final JdbcTemplate jdbcTemplate;
    private List<userInfo> userList;

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
            boolean enabled = resultSet.getBoolean("enabled");
            String role = resultSet.getString("user_role");
            return new userInfo(name,uemail,password,userid,enabled,role);
        });
    }

    public int registerUser(userInfo userInfo){


        String sql = "insert into userdb(username,userid,useremail,userpassword,enabled,user_role) values (?,uuid_generate_v4(),?,?,?,?)";
        jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.setString(1,userInfo.getUsername());
                preparedStatement.setString(2,userInfo.getEmail());
                preparedStatement.setString(3,encoder.encode(userInfo.getPassword()));
                preparedStatement.setBoolean(4,userInfo.getEnabled());
                preparedStatement.setString(5,userInfo.getRoles());
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
       List<userInfo> users = jdbcTemplate.query("select * from userdb where useremail=" + "\'" + s + "\'",(resultSet, i) -> {
            String name = resultSet.getString("username");
            UUID userid =  UUID.fromString(resultSet.getString("userid"));
            String uemail = resultSet.getString("useremail");
            String password = resultSet.getString("userpassword");
            boolean enabled = resultSet.getBoolean("enabled");
            String role = resultSet.getString("user_role");
            return new userInfo(name,uemail,password,userid,enabled,role);
        });
        userInfo user = users.get(0);

        return new User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(Arrays.asList(new Role(user.getRoles()))));
    }
    public userInfo findByEmail(String email){
        userInfo userInfo = null;
        for (userInfo user:userList) {
            if(user.getEmail().equals(email)){
                userInfo = new userInfo(user.getUsername(),user.getEmail(),user.getPassword(),user.getUserId(),user.getEnabled(),userInfo.getRoles());
            }
        }
        return userInfo;
    }
    public void enableUser(userInfo user){
        String sql = "update userdb set enabled="+user.getEnabled()+" where userid="+"\'"+user.getUserId()+"\'";
        jdbcTemplate.execute(sql);

    }
    public List<userInfo> findById(String token){
        String sql = "select * from userdb where userid=" + "\'" + token + "\'";
       return jdbcTemplate.query(sql,(resultSet, i) -> {
            String name = resultSet.getString("username");
            UUID userid =  UUID.fromString(resultSet.getString("userid"));
            String uemail = resultSet.getString("useremail");
            String password = resultSet.getString("userpassword");
            boolean enabled = resultSet.getBoolean("enabled");
            String role = resultSet.getString("user_role");
            return new userInfo(name,uemail,password,userid,enabled,role);
        });
    }

    private Collection < ? extends GrantedAuthority > mapRolesToAuthorities(Collection <Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
