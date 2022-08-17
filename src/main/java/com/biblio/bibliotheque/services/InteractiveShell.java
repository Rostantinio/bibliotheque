    package com.biblio.bibliotheque.services;

    import com.biblio.bibliotheque.dao.AuthorsDAO;
    import com.biblio.bibliotheque.model.Authors;
    import com.biblio.bibliotheque.util.InputReaderUtil;

    import java.util.logging.LogManager;
    import java.util.logging.Logger;

    public class InteractiveShell {
    private static final Logger logger = Logger.getLogger("InteractiveShell");
    public static void loadInterface(){
        System.out.println("Bienvenue à la Bibliothèque");

        boolean continueApp = true;
        InputReaderUtil inputReaderUtil = new InputReaderUtil();
        AuthorsDAO authorsDAO = new AuthorsDAO();
        Authors authors = new Authors();
        AuthorsManager authorsManager = new AuthorsManager(authorsDAO,inputReaderUtil,authors);

        while (continueApp){
            loadMenuu();
            int option = inputReaderUtil.readSelection();
            switch (option){
                case 1: {
                    System.out.println("Liste des auteurs");
                    authorsManager.getAuthors();
                    break;
                }
            }
        }
    }

    private static void loadMenuu(){
        System.out.println("1 - Gestion des auteurs");
        System.out.println("2 - Gestion des livres");
        System.out.println("0 - Quitter la bibliothèque ");
        System.out.println("---------------------------------");
    }

}
