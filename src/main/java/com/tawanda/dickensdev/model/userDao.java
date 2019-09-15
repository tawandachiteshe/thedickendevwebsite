package com.tawanda.dickensdev.model;

import java.util.List;
import java.util.UUID;

public interface userDao {
    int insertUser(userInfo userInfo);
    int insertUser(UUID id, userInfo userInfo);
    List<userInfo> getAll();
    default int insertPerson( userInfo userInfo){
        UUID id  = UUID.randomUUID();
        return insertUser(id,userInfo);
    }
}
