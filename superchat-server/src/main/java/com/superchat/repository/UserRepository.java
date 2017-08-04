package com.superchat.repository;

import com.superchat.Entity.User;
import com.superchat.repository.DB.DbHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class UserRepository {

    private DbHandler dbHandler = DbHandler.getInstance();

    public void create(final User user){


        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", user.getName());
        dbHandler.insertInto("user", columnMap);
    }

    public int exists(final User user){
        HashMap<String, String> conditionMap = new HashMap<>();
        conditionMap.put("name", user.getName());

        ResultSet resultSet = dbHandler.select("user", conditionMap);


        try {
            return resultSet.getInt("id");
        } catch (SQLException e) {
            return -1;
        }
    }
}
