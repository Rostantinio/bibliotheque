package com.softwify.libraryapp.services;

import com.softwify.libraryapp.dao.AuthorDao;
import com.softwify.libraryapp.model.Author;
import com.softwify.libraryapp.util.OptionSelector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.util.List;

public class AuthorManager {
    private static final Logger logger = LogManager.getLogger(AuthorManager.class.getSimpleName());
    private final AuthorDao authorDao;
    private final OptionSelector optionSelector;

    public AuthorManager(AuthorDao authorDao, OptionSelector optionSelector) {
        this.authorDao = authorDao;
        this.optionSelector = optionSelector;
    }


    public void manager() throws ParseException {
        displayAuthors();

        boolean continueSelection = true;

        while (continueSelection) {
            System.out.println("\r\n" + "Pour ajouter un nouvel auteur, veuillez saisir : \"add\"\r\n"
                    + "Pour faire une action sur un auteur, veuillez saisir l'action suivit, d'un espace, puis de l'identifiant de l'auteur.\r\n"
                    + "Actions possible : Suppression (delete).\r\n" + "\r\n"
                    + "Ou saisissez \"back\" pour retourner au menu principal \r\n"
                    + "----------------------------------------------");

            String input = optionSelector.readString();
            String[] split = input.trim().split("\\s+");
            String option = split[0];
            switch (option) {
                case "back": {
                    LibraryMenu.loadInterface();
                    continueSelection = false;
                    break;
                }
                case "delete": {
                    try {
                        processDelete(split[1]);
                    } catch (ArrayIndexOutOfBoundsException exception) {
                        logger.error("Veuillez entrer un identifiant s'il vous plait !");
                    }
                    break;
                } case "add": {
                    addNewAuthor();
                    break;
                }
            }
        }
    }

    public void displayAuthors() {
        List<Author> authors = authorDao.getAll();
        System.out.println("Liste des auteurs");
        for (Author author : authors) {
            System.out.println(author.getId() + " - " + author.getFullName());
        }

    }

    public void processDelete(String idInString) {

        try {
            int id = Integer.parseInt(idInString);
            boolean deleted = delete(id);
            if (deleted){
                returnList();
            }
        } catch (NumberFormatException exception) {
            logger.error(idInString + "n'est pas un nombre entrer un nombre");
        }
    }

    public boolean delete(int id) {
        boolean deleted = authorDao.delete(id);

        if (deleted) {
            System.out.println("L'auteur et ses livres ont été supprimés avec succès.");
        } else {
            System.out.println("L'auteur n'apparait pas dans la liste.");
        }
        return deleted;
    }

    private void returnList() {
        System.out.println("Tapez \"ENTER\", pour retourner\r\n" + "--------------------------");
        optionSelector.readString();
        displayAuthors();
    }

    public  void addNewAuthor() {
        System.out.println("Ajout d'un nouvel auteur");
        System.out.println("Entrez le prénom de l'auteur :");
        String firstName = optionSelector.readString();

        while (firstName.isEmpty()) {
            logger.error("inserez un prenom s'il vous plait!");
            System.out.println("Entrez le prénom de l'auteur :");
            firstName = optionSelector.readString();
        }

        System.out.println("Entrez le nom de l'auteur :");
        String lastName = optionSelector.readString();

        while (lastName.isEmpty()) {
            logger.error("inserez un nom s'il vous plait!");
            System.out.println("Entrez le nom de l'auteur :");
            lastName = optionSelector.readString();
        }

        Author existingAuthor = authorDao.getByFirstNameAndLastName(firstName, lastName);

        if (existingAuthor != null) {
            logger.error("L'auteur " + existingAuthor.getFullName() + "  existe déjà. \n Veuillez reprendre s'il vous plaît.\n");
            addNewAuthor();
        } else {
            Author author = new Author(1, firstName, lastName);
            Author newAuthor = authorDao.save(author);
            if (newAuthor != null) {
                System.out.println("L'auteur " + author.getFullName() +" a été rajouté avec succès.");
            } else {
                logger.error("error to insert author");
            }
            returnList();
        }
    }
}
