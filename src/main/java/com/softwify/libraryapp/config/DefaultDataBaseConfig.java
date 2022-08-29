package com.softwify.libraryapp.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;


public class DefaultDataBaseConfig extends DataBaseConfig {

    private static final Logger logger = LogManager.getLogger("DataBaseConfig");

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        logger.info("Create DB connection");
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/library?serverTimezone = UTC","root","rootroot");
    }

}
