package com.softwify.libraryapp.services;

import com.softwify.libraryapp.dao.AuthorDao;
import com.softwify.libraryapp.dao.TextbookDao;
import com.softwify.libraryapp.model.Author;
import com.softwify.libraryapp.model.Textbook;
import com.softwify.libraryapp.util.OptionSelector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TextbookManager {
    private static final Logger logger = LogManager.getLogger(TextbookManager.class.getSimpleName());
    private final TextbookDao textbookDao;
    private final OptionSelector optionSelector;
    private  final AuthorDao authorDao;

    public  TextbookManager(TextbookDao textbookDao, OptionSelector optionSelector, AuthorDao authorDao) {
        this.textbookDao = textbookDao;
        this.optionSelector = optionSelector;
        this.authorDao = authorDao;
    }

    public void manager() throws ParseException {
        displayTextbook();

        boolean continueSelection = true;

        while (continueSelection) {
            System.out.println("\r\n" + "Pour ajouter un nouveau livre, veuillez saisir : \"add\"\r\n"
                    + "Pour faire une action sur un livre, veuillez saisir l'action suivit, de l'espace, puis de l'identifiant.\r\n"
                    + "Actions possible : Affichage (read) et Suppression (delete).\r\n" + "\r\n"
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
                }
                case "read": {
                    try {
                        processReading(split[1]);
                    } catch (ArrayIndexOutOfBoundsException exception) {
                        logger.error("euillez entrer un  identifiant s'il vous plait");
                    }
                }
                case "add": {
                    addNewTextbook();
                    break;
                }
                default:
                    logger.error("Choix non disponible, veuillez retenter");
                    break;
            }
        }

    }

    public void displayTextbook() {
        List<Textbook> textbooks = textbookDao.getAll();
        System.out.println("Liste des livres");
        for (Textbook textbook : textbooks) {
            System.out.println(textbook.getId() + " - " + textbook.getTitle() + " - " + textbook.getFullName());
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
        boolean deleted = textbookDao.deleteTextbook(id);

        if (deleted) {
            System.out.println("Le livre a été supprimé avec succès.");
        } else {
            System.out.println("Le livre n'apparait pas dans la liste.");
        }
        return deleted;
    }

    public boolean readTextbook(int id) {
        Textbook textbook = textbookDao.get(id);
        if (textbook == null) {
            return false;
        }
        System.out.println("Titre : " + textbook.getTitle());
        System.out.println("Auteur : " + textbook.getFullName());
        System.out.println("ISBN : " + textbook.getIsbn());
        System.out.println("Editeur : " + textbook.getEditor());
        System.out.println("Année de publication : " + textbook.getPublicationDate());
        return true;
    }

    public void processReading(String idInString){

        try {
            int id = Integer.parseInt(idInString);
            boolean read = readTextbook(id);
            if (read) {
                returnList();
            }
        } catch (NumberFormatException e) {
            logger.error(idInString
                    + "n'est pas un nombre, entrer un nombre représentant l'identifiant du livre!");
        }
    }

    private void returnList() {
        System.out.println("Tapez \"ENTER\", pour retourner\r\n" + "--------------------------");
        optionSelector.readString();
        displayTextbook();
    }

    public Author authorFullName() {
        System.out.print("Veuillez entrer le nom complet de l'auteur : ");
        String firstName = null;
        String lastName = null;
        try {
            String authorFullName = optionSelector.readString();
            authorFullName = authorFullName.trim().replaceAll("\\s+", " ");
            String[] splited = authorFullName.split(" ");
            firstName = splited[0];
            lastName = splited[1];

            while (firstName.isEmpty() || lastName.isEmpty()) {
                authorFullName = optionSelector.readString();
                authorFullName = authorFullName.trim().replaceAll("\\s+", " ");
                splited = authorFullName.split(" ");
                firstName = splited[0];
                lastName = splited[1];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("Nom et prenom requis");
        }
        return new Author(firstName, lastName);
    }

    public void addNewTextbook() throws ParseException {
        System.out.println("\nAjout d'un nouveau livre");

        Author author = authorFullName();
        if(author.getFirstName() != null && author.getLastName() != null){
            Author existingAuthor = authorDao.getByFirstNameAndLastName(author.getFirstName(), author.getLastName());
            if(existingAuthor != null) {
                Textbook textbook = bookInformation(existingAuthor.getId());
                saveBook(textbook);
            } else {
                logger.error("L'auteur " + author.getFullName() + " n'existe pas");
                addNewTextbook();
            }
        } else {
            logger.error("s'il vous plait veuillez entrer le nom et le pronom de l'auteur");
            addNewTextbook();
        }
    }

    public Textbook bookInformation(int authorId) throws ParseException {
        System.out.print("Entrez le titre du livre : ");
        String title = optionSelector.readString();
        while (title.isEmpty()) {
            logger.error("Titre vide, veuillez reessayer");
            System.out.print("Entrez le titre du livre : ");
            title = optionSelector.readString();
        }

        System.out.print("Entrez le numéro ISBN : ");
        int isbnInt = optionSelector.readInt();
        while (isbnInt == 0) {
            logger.error("ISBN vide, veuillez reessayer");
            System.out.print("Entrez le ISBN du livre : ");
            isbnInt = optionSelector.readInt();
        }

        System.out.print("Entrez l'editeur du livre : ");
        String editor = optionSelector.readString();
        while (editor.isEmpty()) {
            logger.error("Editeur vide veuillez reessayer");
            System.out.print("Entrez l'editeur du livre : ");
            editor = optionSelector.readString();
        }
        System.out.print("Entrez l'année de publication : ");
        String date = optionSelector.readDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date convertedDate = dateFormat.parse(date);

        while (date.isEmpty()) {
            logger.error("date vide, veuillez reessayer");
            System.out.print("Entrez l'année de publication  ");
            date = optionSelector.readDate();
            dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            convertedDate = dateFormat.parse(date);
        }

        return new Textbook(1, title, authorId, isbnInt, editor, convertedDate);
    }

    private void saveBook(Textbook textbook){
        Textbook addedTextbook = textbookDao.saveTextbook(textbook);
        if (addedTextbook != null){
            System.out.println("\nLe livre " + textbook.getTitle() + " a été rajouté avec succès.\n");
        } else {
            logger.error("Une erreur est survenue");
        }
        returnList();
    }
}
