package com.codeup.adlister.dao;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLDao implements Ads {

    private Connection connection;

    public MySQLDao(Config config){
        try{
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(
                    config.getUrl(),
                    config.getUser(),
                    config.getPassword()
            );
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }

    }
    @Override
    public List<Ad> all() {
        try{
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM ads");
//            while(rs.next()){
//                System.out.println(rs.getLong("user_id"));
//                System.out.println(rs.getString("title"));
//                System.out.println(rs.getString("description"));
//            }
           return generateAds(rs);
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return null;
    }

    private List<Ad> generateAds(ResultSet rs) throws SQLException {
        List<Ad> ads = new ArrayList<>();
        while(rs.next()){
            ads.add(new Ad(
                    rs.getLong("adId"),
                    rs.getLong("user_id"),
                    rs.getString("title"),
                    rs.getString("description")));
        }

        return ads;
    }
    @Override
    public Long insert(Ad ad) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO ads(user_id, title, description) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1,ad.getUserId());
            stmt.setString(2,ad.getTitle());
            stmt.setString(3,ad.getDescription());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating a new ad.", e);
        }
    }
}
