package integration;

import com.softwify.libraryapp.config.DataBaseConfig;
import com.softwify.libraryapp.config.DefaultDataBaseConfig;
import com.softwify.libraryapp.dao.TextbookDao;
import com.softwify.libraryapp.model.Textbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.DataBasePrepareServiceTextbook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        assertEquals(6, textbookDao.getAll().size());
        boolean deleted = textbookDao.deleteTextbook(5);
        assertTrue(deleted);

        List<Textbook> textbooks = textbookDao.getAll();
        assertEquals(5, textbooks.size());

        for (Textbook textbook : textbooks) {
            assertNotEquals(5, textbook.getId());
        }
    }

    @Test
    public void deletingNonExistentTextbookShouldNotModifyTheList() {
        assertEquals(6, textbookDao.getAll().size());
        boolean deleted = textbookDao.deleteTextbook(20);
        assertFalse(deleted);

        assertEquals(6, textbookDao.getAll().size());
    }

    @Test
    public void checkIfTheTextbookHasBeenRegistered() throws ParseException {
        String title = "le phenix";
        int isbn = 44;
        int authorId = 12;
        String editor = "Ci";
        String date= "09-06-2022";
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date parseDate = format.parse(date);

        Textbook textbook = new Textbook(1, title, authorId, isbn, editor, parseDate);
        textbookDao.save(textbook);
    }

}
