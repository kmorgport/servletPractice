package com.codeup.adlister.dao;


public class DaoFactory {
    private static Ads adsDao;
    private static Users usersDao;
    static Config config = new Config();

    public static Ads getAdsDao() {
        if (adsDao == null) {
            adsDao = new MySQLDao(config);
//            adsDao = new com.codeup.adlister.controllers.dao.ListAdsDao();
        }
        return adsDao;
    }

    public static Users getUsersDao() {
        if (usersDao == null) {
            usersDao = new MySQLUsersDao(config);
//            adsDao = new com.codeup.adlister.controllers.dao.ListAdsDao();
        }
        return usersDao;
    }
}
