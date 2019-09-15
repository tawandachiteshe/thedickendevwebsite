package com.tawanda.dickensdev.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class fuser implements userDao{
    List<userInfo> db = new ArrayList<>();


    @Override
    public int insertUser(userInfo userInfo) {
        return 0;
    }

    @Override
    public int insertUser(UUID id, userInfo userInfo) {
//        String username, String email, String password, UUID userId
        db.add(new userInfo(userInfo.getUsername(),userInfo.getEmail(),userInfo.getPassword(),id));
        return 0;
    }

    @Override
    public List<userInfo> getAll() {
        return db;
    }

    @Override
    public int insertPerson(userInfo userInfo) {
        return 0;
    }
}
