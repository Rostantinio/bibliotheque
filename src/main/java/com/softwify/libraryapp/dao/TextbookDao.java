package com.softwify.libraryapp.dao;

import com.softwify.libraryapp.config.DataBaseConfig;
import com.softwify.libraryapp.constants.Queries;
import com.softwify.libraryapp.model.Author;
import com.softwify.libraryapp.model.Textbook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TextbookDao {
    public static final Logger logger = LogManager.getLogger(TextbookDao.class.getSimpleName());
    public DataBaseConfig dataBaseConfig;

    public TextbookDao(DataBaseConfig dataBaseConfig){
        this.dataBaseConfig = dataBaseConfig;
    }

    public List<Textbook> getAll(){
        Connection connection = null;
        List<Textbook> textbooks = new ArrayList<>();
        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_TEXTBOOKS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");

                Textbook textbook = new Textbook(id, title, firstname, lastname);
                textbooks.add(textbook);
            }
            dataBaseConfig.closeResultSet(resultSet);
            dataBaseConfig.closePreparesStatement(preparedStatement);
        } catch (SQLException | NumberFormatException exception) {
            logger.error("Error fetching textbook", exception);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            dataBaseConfig.closeConnection(connection);
        }

        return textbooks;
    }

    public boolean deleteTextbook(int id) {
        Connection connection = null;
        boolean deleted = false;

        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Queries.DELETE_TEXTBOOK);
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

    public Textbook get(int id) {
        Connection connection = null;
        Textbook textbook = null;
        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_TEXTBOOK);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String title = resultSet.getString("title");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                int isbn = resultSet.getInt("isbn");
                String editor = resultSet.getString("editor");
                Date publicationDate = resultSet.getDate("publication_date");
                textbook = new Textbook(title, firstname, lastname, isbn, editor, (java.sql.Date) publicationDate);
            }
            dataBaseConfig.closeResultSet(resultSet);
            dataBaseConfig.closePreparesStatement(preparedStatement);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("An error has occurred", e);
        } finally {
            dataBaseConfig.closeConnection(connection);
        }

        return textbook;
    }

    public Textbook save(Textbook textbook){
        Connection connection = null;
        Textbook newTextbook = null;
        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Queries.ADD_TEXTBOOK, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, textbook.getTitle());
            preparedStatement.setInt(2, textbook.getAuthor_id());
            preparedStatement.setInt(3, textbook.getIsbn());
            preparedStatement.setString(4, textbook.getEditor());
            Date publicationDate = textbook.getPublicationDate();
            java.sql.Date sqlPackageDate = new java.sql.Date(publicationDate.getTime());
            preparedStatement.setDate(5, sqlPackageDate);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                int id = resultSet.getInt(1);
                newTextbook = new Textbook(textbook.getId(), textbook.getTitle(),textbook.getAuthor_id(), textbook.getIsbn(), textbook.getEditor(), textbook.getPublicationDate());
            }
            dataBaseConfig.closePreparesStatement(preparedStatement);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("An error has occurred", e);
        } finally {
            dataBaseConfig.closeConnection(connection);
        }
        return newTextbook;
    }

    public boolean getByFirstNameAndLastName(Author author) {
        Connection connection = null;
        boolean textbook = false;
        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_AUTHOR_BY_FIRSTNAME_AND_LASTNAME);
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                textbook = count > 0;
            }
            dataBaseConfig.closeResultSet(resultSet);
            dataBaseConfig.closePreparesStatement(preparedStatement);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("An error has occurred", e);
        } finally {
            dataBaseConfig.closeConnection(connection);
        }

        return textbook;
    }
}
