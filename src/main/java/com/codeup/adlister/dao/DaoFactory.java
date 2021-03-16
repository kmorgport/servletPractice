package com.codeup.adlister.dao;


public class DaoFactory {
    private static Ads adsDao;
    static Config config = new Config();

    public static Ads getAdsDao() {
        if (adsDao == null) {
            adsDao = new MySQLDao(config);
//            adsDao = new com.codeup.adlister.controllers.dao.ListAdsDao();
        }
        return adsDao;
    }

    public static Users
}
