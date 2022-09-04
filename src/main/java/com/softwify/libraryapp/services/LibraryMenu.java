package com.softwify.libraryapp.services;

import com.softwify.libraryapp.config.DefaultDataBaseConfig;
import com.softwify.libraryapp.config.DataBaseConfig;
import com.softwify.libraryapp.dao.AuthorDao;
import com.softwify.libraryapp.dao.TextbookDao;
import com.softwify.libraryapp.util.OptionSelector;

import java.text.ParseException;

public class LibraryMenu {
    private static DataBaseConfig dataBaseConfig = new DefaultDataBaseConfig();
    private static AuthorDao authorDao = new AuthorDao(dataBaseConfig);
    static OptionSelector optionSelector = new OptionSelector();
    private static AuthorManager authorManager = new AuthorManager(authorDao, optionSelector);
    private static TextbookDao textbookDao = new TextbookDao(dataBaseConfig);

    private static TextbookManager textbookManager = new TextbookManager(textbookDao, optionSelector, authorDao);


    public static void loadInterface() throws ParseException {
        System.out.println("Bienvenue à la Bibliothèque");
        System.out.println("Que voulez-vous faire ?");
        boolean continueApp = true;

        while (continueApp){
            loadMenu();
            int option = optionSelector.readInt();
            switch (option){
                case 1: {
                    authorManager.manager();
                    continueApp = false;
                    break;
                }
                case 2: {
                    textbookManager.manager();
                    continueApp = false;
                    break;
                }
                case 0: {
                    System.exit(option);
                    break;
                }
            }
        }
    }

    private static void loadMenu() {
        System.out.println("1 - Gestion des auteurs");
        System.out.println("2 - Gestion des livres");
        System.out.println("0 - Quitter la bibliothèque ");
        System.out.println("---------------------------------");
    }
}
