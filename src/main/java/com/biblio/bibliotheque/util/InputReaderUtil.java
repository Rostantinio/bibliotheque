package com.biblio.bibliotheque.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;


public class InputReaderUtil {
    private static Scanner scan = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger("InputReaderUtil");

    public int readSelection() {
        try {
            int input = Integer.parseInt(scan.nextLine());
            return input;
        }catch (Exception e){
            logger.error("error while reading user input from shell", e);
            System.out.println("Error reading input. please enter valid number for proceeding further");
            return -1;
        }
    }
}
