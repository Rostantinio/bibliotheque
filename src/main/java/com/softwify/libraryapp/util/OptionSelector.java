package com.softwify.libraryapp.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;


public class OptionSelector {
    private static Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger(OptionSelector.class.getSimpleName());

    public int readInt() {
        try {
            String option = scanner.nextLine();
            return Integer.parseInt(option);
        } catch (NumberFormatException exception) {
            logger.error("Desole vous n'avez pas entre un entier");
            return -1;
        }
    }

    public String readString() {
        return scanner.nextLine();
    }

    public String readDate() {
        return scanner.nextLine();
    }
}
