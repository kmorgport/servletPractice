package com.codeup.adlister.dao;

import models.User;

public interface Users {
    User findByUserName(String username);
    Long insert(User user);
}
