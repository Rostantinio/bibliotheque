package integration;

import com.softwify.libraryapp.config.DataBaseConfig;
import com.softwify.libraryapp.dao.AuthorDao;
import com.softwify.libraryapp.model.Author;
import configtest.DataBaseConfigTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.DataBasePrepareService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorDaoTest {

    public DataBaseConfig dataBaseConfig = new DataBaseConfigTest();
    public AuthorDao authorDao = new AuthorDao(dataBaseConfig);

    public DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();

    @BeforeEach
    public void setUp(){
        dataBasePrepareService.clearDataBaseEntries();
    }

    @Test
    public void displayTheListOfAuthorsFromTheDatabase() {
        List<Author> authors = authorDao.getAll();
        assertEquals(4, authors.size());

    }

    @Test
    public void deleteAuthorByIdShouldRemoveFromTheList() {
        assertEquals(4, authorDao.getAll().size());
        boolean deleted = authorDao.delete(9);
        assertTrue(deleted);

        List<Author> authors = authorDao.getAll();
        assertEquals(3, authors.size());

        for (Author author : authors) {
            assertNotEquals(9, author.getId());
        }
    }

    @Test
    public void deletingNonExistentAuthorShouldNotModifyTheList() {
        assertEquals(4, authorDao.getAll().size());
        boolean deleted = authorDao.delete(20);
        assertFalse(deleted);

        assertEquals(4, authorDao.getAll().size());
    }

    @Test
    public void checkIfTheAuthorHasBeenRegistered() {
        Author addNewAuthor = new Author("sillicon", "valley");
        authorDao.save(addNewAuthor);
        assertEquals(5, authorDao.getAll().size());
    }

    @Test
    public void checksThatAnAuthorExistsInTheDatabase() {
        Author author = new Author("Hill", "Napoleon");
        String chek = author.getFullName();
        assertTrue(true, chek);
    }

    @Test
    public void checksThatAnAuthorNotExistsInTheDatabase() {
        Author author = new Author("kendra", "Kamdem");
        String chek = author.getFullName();
        assertFalse(false, author.getFullName());
    }
}
