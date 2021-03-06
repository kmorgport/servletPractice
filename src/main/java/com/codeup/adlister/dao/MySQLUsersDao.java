package com.codeup.adlister.dao;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.Driver;
import models.User;

public class MySQLUsersDao implements Users{
    private Connection connection = null;
    public MySQLUsersDao(Config config){
        try{
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(
                    config.getUrl(),
                    config.getUser(),
                    config.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database!", e);
        }
    }
    @Override
    public User findByUserName(String username) {
        String query = "SELECT * FROM users where username = ? LIMIT 1";
        try{
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,username);
            stmt.executeQuery();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return new User(
                    rs.getLong("id"),
                    rs.getString("Username"),
                    rs.getString("email"),
                    rs.getString("password")
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    @Override
    public Long insert(User user) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO users(username, email, password) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,user.getUsername());
            stmt.setString(2,user.getEmail());
            stmt.setString(3,user.getPassword());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating a new ad.", e);
        }
    }
}
