package com.biblio.bibliotheque.services;

import com.biblio.bibliotheque.dao.AuthorsDAO;
import com.biblio.bibliotheque.model.Authors;
import com.biblio.bibliotheque.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthorsManager {
    private static final Logger logger = LogManager.getLogger("AuthorsManager");

    private final InputReaderUtil inputReaderUtil;
    private final AuthorsDAO authorsDAO;
    private final Authors authors;

    public AuthorsManager(AuthorsDAO authorsDAO, InputReaderUtil inputReaderUtil, Authors authors){
        this.inputReaderUtil = inputReaderUtil;
        this.authorsDAO = authorsDAO;
        this.authors = authors;
    }

    public void getAuthors() {
        try {
            Authors authors1 = authorsDAO.getAuthors(authors);
        } catch (Exception e){
            logger.error("unable to process get authors",e);
        }
    }

}
