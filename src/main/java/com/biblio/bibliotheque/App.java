package com.biblio.bibliotheque;

import com.biblio.bibliotheque.services.InteractiveShell;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class App {
    private static final Logger logger = Logger.getLogger("App");

    public static void main(String[] args) {
        logger.info("Initializing Bibliotheque");
        InteractiveShell.loadInterface();
    }
}