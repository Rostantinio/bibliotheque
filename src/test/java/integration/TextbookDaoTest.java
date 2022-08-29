package integration;

import com.softwify.libraryapp.config.DataBaseConfig;
import com.softwify.libraryapp.config.DefaultDataBaseConfig;
import com.softwify.libraryapp.dao.TextbookDao;
import com.softwify.libraryapp.model.Author;
import com.softwify.libraryapp.model.Textbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.DataBasePrepareService;
import services.DataBasePrepareServiceTextbook;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TextbookDaoTest {
    public DataBaseConfig dataBaseConfig = new DefaultDataBaseConfig();
    public TextbookDao textbookDao = new TextbookDao(dataBaseConfig);
    public DataBasePrepareServiceTextbook dataBasePrepareServiceTextbook = new DataBasePrepareServiceTextbook();


    @BeforeEach
    public void setUp() {
        dataBasePrepareServiceTextbook.clearDataBaseEntries();
    }

    @Test
    public void displayTheListOfTextbooksFromTheDatabase() {
        List<Textbook> texbooks = textbookDao.getAll();
        assertEquals(1, texbooks.size());
    }

    @Test
    public void deleteTextbookByIdShouldRemoveFromTheList() {
        assertEquals(1, textbookDao.getAll().size());
        boolean deleted = textbookDao.deleteTextbook(21);
        assertTrue(deleted);

        List<Textbook> textbooks = textbookDao.getAll();
        assertEquals(0, textbooks.size());

        for (Textbook textbook : textbooks) {
            assertNotEquals(21, textbook.getId());
        }
    }

    @Test
    public void deletingNonExistentTextbookShouldNotModifyTheList() {
        assertEquals(1, textbookDao.getAll().size());
        boolean deleted = textbookDao.deleteTextbook(20);
        assertFalse(deleted);

        assertEquals(1, textbookDao.getAll().size());
    }
}
