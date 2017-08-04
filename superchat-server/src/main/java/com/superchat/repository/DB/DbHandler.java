package com.superchat.repository.DB;

import java.sql.*;
import java.util.*;

/**
 * Created by lodo4ka on 20/05/2017.
 */
public class DbHandler {

    Statement statement;
    Connection connection;
    private static DbHandler instance;


    private DbHandler(final String connectionString) {
        try {
            this.connection = DriverManager.getConnection(connectionString);
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            throw new IllegalStateException("Database Exception. Reason: " + e.getMessage());
        }
    }

    public static DbHandler getInstance() {
        if(instance ==null){

            instance =
                    new DbHandler
                            ("jdbc:sqlite:/Users/lodo4ka/Desktop/Github/supercat-server/DB/gadgershoodatabase.sqlite");


            HashMap<String, String> columnMap = new HashMap<>();
            columnMap.put("name", "text");
        }
        return instance;
    }

    public void insertInto(String table, HashMap<String, Object> columnValueMap) {

        StringJoiner columnJoiner = new StringJoiner(",");
        StringJoiner valuesJoiner = new StringJoiner(",");

        Set<Map.Entry<String, Object>> entries = columnValueMap.entrySet();


        for (Map.Entry<String, Object> entry : entries) {
            columnJoiner.add(entry.getKey());
            valuesJoiner.add("'" + entry.getValue() + "'");
        }


        try {
            String sqlQuery = "INSERT INTO " + table + "( " + columnJoiner.toString() + ") VALUES (" + valuesJoiner.toString() + ")";
            System.out.println("querying Db" + sqlQuery);
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to execute inert query. Reason: " + e.getMessage());
        }
    }


    public void createTable(String tableName, Map<String, String> namesTypesMap) {

        StringJoiner stringJoiner = new StringJoiner(",");
        Set<Map.Entry<String, String>> entries = namesTypesMap.entrySet();

        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringJoiner.add(key + " " + value);
        }

        try {
            String sqlQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " ( id INTEGER PRIMARY KEY AUTOINCREMENT, " + stringJoiner.toString() + ")";
            System.out.println(sqlQuery);
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to create table. Reason: " + e.getMessage());
        }
    }

    public void dropTableIfExists(String tableName) {

        try {
            statement.execute("DROP  TABLE IF EXISTS " + tableName);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to drop table. Reason: " + e.getMessage());
        }

    }


    public ResultSet selectWithInnerJoin( List<String> columns ,String tableName1, String tableName2,
                                          String condition1, String condition2,Map<String, String> whereConditionMap) {

        StringJoiner stringJoiner = new StringJoiner(",");
        for (String column : columns) {
            stringJoiner.add(column);
        }


        StringJoiner stringJoiner2 = new StringJoiner(" AND ");
        Set<Map.Entry<String, String>> entries = whereConditionMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            stringJoiner2.add(entry.getKey() + "='" + entry.getValue() + "'");
        }

        try {
            String sqlQuery = "SELECT *"+ " FROM " + tableName1
                    + " INNER JOIN " + tableName2+ " ON " + tableName1 + "." + condition1 + " = " + tableName2 +"." + condition2
                    +  " WHERE " + stringJoiner2.toString();
            System.out.println(sqlQuery);
            return statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new IllegalStateException("Filed to select command. Reason: " + e.getMessage());
        }

    }

    public ResultSet select(String tableName, HashMap<String, String> whereConditionMap) {

        StringJoiner stringJoiner2 = new StringJoiner(" AND ");

        Set<Map.Entry<String, String>> entries = whereConditionMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            stringJoiner2.add(entry.getKey() + "='" + entry.getValue() + "'");
        }


        try {
            String sqlQuery = "SELECT  * FROM " + tableName + " WHERE " + stringJoiner2.toString();
            System.out.println(sqlQuery);
            return statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new IllegalStateException("Filed to select command. Reason: " + e.getMessage());
        }
    }

    public ResultSet selectWithoutCondition(String tableName) {


        try {
            String sqlQuery = "SELECT *" + " FROM " + tableName;
            System.out.println(sqlQuery);
            return statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new IllegalStateException("Filed to select command. Reason: " + e.getMessage());
        }
    }


    public int delete(String tableName, HashMap<String, Object> condition) {

        StringJoiner stringJoiner = new StringJoiner(" , ");

        Set<Map.Entry<String, Object>> entries = condition.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            stringJoiner.add(entry.getKey() + "='" + entry.getValue() + "'");
        }


        try {
            String sqlQuery = "DELETE FROM " + tableName + " WHERE " + stringJoiner.toString();
            System.out.println(sqlQuery);
            return statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            throw new IllegalStateException("Filed to delete command. Reason: " + e.getMessage());
        }
    }
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to close db.");
        }
    }
}
