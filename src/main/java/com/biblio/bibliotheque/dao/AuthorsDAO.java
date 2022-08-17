package com.biblio.bibliotheque.dao;

import com.biblio.bibliotheque.config.DataBaseConfig;
import com.biblio.bibliotheque.constants.DBConstants;
import com.biblio.bibliotheque.model.Authors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class AuthorsDAO {
    private static final Logger logger = LogManager.getLogger("AuthorsDAO");
    public DataBaseConfig dataBaseConfig = new DataBaseConfig();

    public Authors getAuthors(Authors authors) {
        Connection connection = null;
        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(DBConstants.GET_AUTHORS);
            prepareStatement.setString(1, authors.toString());
            ResultSet resultset = prepareStatement.executeQuery();
            if (resultset.next()){
                authors = new Authors();
                authors.getId();
                authors.getLastName();
                authors.getFirstName();
            }
            dataBaseConfig.closeResultSet(resultset);
            dataBaseConfig.closePreparesStatement(prepareStatement);
        }catch (Exception ex){
            logger.error("Error  fetching autors",ex);
        }finally {
            dataBaseConfig.closeConnection(connection);
            return authors;
        }
    }
}
