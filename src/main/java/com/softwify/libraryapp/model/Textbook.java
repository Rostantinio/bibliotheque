package com.softwify.libraryapp.model;

import java.util.Date;

public class Textbook {
    private int id;
    private String title;
    private int author_id;
    private int isbn;
    private String editor;
    private Date publicationDate;
    private String authorFirstName;
    private String authorLastName;

    public Textbook(int id, String title, int author_id, int isbn, String editor, Date publicationDate) {
        this.id = id;
        this.author_id = author_id;
        this.title = title;
        this.isbn = isbn;
        this.editor = editor;
        this.publicationDate = publicationDate;
    }

    public Textbook(int id, String title, String firstName, String lastName) {
        this.id = id;
        this.title = title;
        this.authorFirstName = firstName;
        this.authorLastName = lastName;
    }

    public Textbook(String title, String authorFirstname, String authorLastname, int isbn, String editor, java.sql.Date publicationDate) {
        this.title = title;
        this.authorFirstName = authorFirstname;
        this.authorLastName = authorLastname;
        this.isbn = isbn;
        this.editor = editor;
        this.publicationDate = publicationDate;
    }


    public Textbook(int id, String title, int author_id) {
        this.id = id;
        this.title = title;
        this.author_id = author_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getFullName() {
        return authorFirstName + " " + authorLastName;
    }
}

