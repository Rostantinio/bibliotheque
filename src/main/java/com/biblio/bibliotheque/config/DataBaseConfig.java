package com.biblio.bibliotheque.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;


public class DataBaseConfig {

    private static final Logger logger = LogManager.getLogger("DataBaseConfig");

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        logger.info("Create DB connection");
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/bibliotheque?serverTimezone = UTC","root","");
    }

    public void closeConnection(Connection connection) {
        if (connection != null){
            try {
                connection.close();
                logger.info("Closing DB connection");
            } catch (SQLException e) {
                logger.error("Error while closing prepared statement", e);
            }
        }
    }

    public void closePreparesStatement(PreparedStatement preparedStatement){
       if (preparedStatement != null){
           try {
               preparedStatement.close();
               logger.info("Clossing PreparedStatement");
           } catch (SQLException e) {
             logger.error("Error while closing preparedStatement",e);
           }
       }
    }
    public void closeResultSet(ResultSet resultSets) {
        if (resultSets != null) {
            try {
                resultSets.close();
                logger.info("Closing ResultSet");
            } catch (SQLException e) {
                logger.error("Error while closing result set", e);
            }
        }
    }
}
