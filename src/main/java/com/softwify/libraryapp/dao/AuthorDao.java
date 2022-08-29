package com.softwify.libraryapp.dao;

import com.softwify.libraryapp.config.DataBaseConfig;
import com.softwify.libraryapp.constants.Queries;
import com.softwify.libraryapp.model.Author;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AuthorDao {
    private static final Logger logger = LogManager.getLogger(AuthorDao.class.getSimpleName());
    public DataBaseConfig dataBaseConfig;
    public AuthorDao(DataBaseConfig dataBaseConfig) {
        this.dataBaseConfig = dataBaseConfig;
    }
    public List<Author> getAll() {
        Connection connection = null;
        List<Author> authors = new ArrayList<>();
        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(Queries.GET_AUTHORS);
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
               int id = resultSet.getInt("id");
               String firstName = resultSet.getString("firstName");
               String lastName = resultSet.getString("lastName");

               Author author = new Author(id, firstName, lastName);
               authors.add(author);
            }
            dataBaseConfig.closeResultSet(resultSet);
            dataBaseConfig.closePreparesStatement(prepareStatement);
        } catch (ClassNotFoundException | SQLException exception) {
            logger.error("Error fetching authors", exception);
        } finally {
            dataBaseConfig.closeConnection(connection);
        }

        return authors;
    }

    public boolean delete(int id) {
        Connection connection = null;
        boolean deleted = false;
        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Queries.DELETE_AUTHOR);
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            deleted = affectedRows == 1;
            dataBaseConfig.closePreparesStatement(preparedStatement);
        } catch (SQLException | ClassNotFoundException exception) {
            logger.error("error occurrence not exist", exception);
        } finally {
            dataBaseConfig.closeConnection(connection);
        }

        return deleted;
    }

    public Author save(Author author){
        Connection connection = null;
        Author newAuthor = null;
       try {
           connection = dataBaseConfig.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(Queries.ADD_AUTHOR, Statement.RETURN_GENERATED_KEYS);
           preparedStatement.setString(1, author.getFirstName());
           preparedStatement.setString(2, author.getLastName());
           preparedStatement.executeUpdate();
           ResultSet resultSet = preparedStatement.getGeneratedKeys();
           if (resultSet.next()) {
               int id = resultSet.getInt(1);
               newAuthor = new Author(id, author.getFirstName(), author.getLastName());
           }
       } catch (SQLException | ClassNotFoundException exception) {
           logger.error("An error occurred", exception);
       } finally {
           dataBaseConfig.closeConnection(connection);
       }

       return newAuthor;
    }

    public Author getByFirstNameAndLastName(String firstName, String lastName) {
        Connection connection = null;
        Author author = null;
        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_AUTHOR_BY_FIRSTNAME_AND_LASTNAME);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                author = new Author(id, firstName, lastName);
            }
            dataBaseConfig.closeResultSet(resultSet);
            dataBaseConfig.closePreparesStatement(preparedStatement);
        } catch (SQLException | ClassNotFoundException exception) {
            logger.error("An error occurred", exception);

        } finally {
            dataBaseConfig.closeConnection(connection);
        }
        return author;
    }
}
