import com.softwify.libraryapp.dao.AuthorDao;
import com.softwify.libraryapp.dao.TextbookDao;
import com.softwify.libraryapp.model.Author;
import com.softwify.libraryapp.model.Textbook;
import com.softwify.libraryapp.services.TextbookManager;
import com.softwify.libraryapp.util.OptionSelector;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class TextbookManagerTest {
    TextbookDao textbookDao = Mockito.mock(TextbookDao.class);
    OptionSelector optionSelector = Mockito.mock(OptionSelector.class);
    private AuthorDao authorDao = Mockito.mock(AuthorDao.class);
    TextbookManager textbookManager = new TextbookManager(textbookDao, optionSelector, authorDao);

    @Test
    public void displayTextbookTest() {
        textbookManager.displayTextbook();
        verify(textbookDao,times(1)).getAll();
    }

    @Test
    public void processDeleteMustReturnFalseWhenTheTextbookDoesNotExistInTheDao() {
        when(textbookDao.deleteTextbook(anyInt())).thenReturn(false);

        boolean deleted = textbookManager.delete(20);
        assertFalse(deleted);
        verify(textbookDao,times(1)).deleteTextbook(20);
    }

    @Test
    public void processDeleteMustReturnTrueWhenTheTextbookDoesExistInTheDao() {
        when(textbookDao.deleteTextbook(anyInt())).thenReturn(true);

        boolean deleted = textbookManager.delete(5);
        assertTrue(deleted);
        verify(textbookDao,times(1)).deleteTextbook(5);
    }

    @Test
    public void addTextbookTest() throws ParseException {
        when(optionSelector.readString()).thenReturn("Les fleures de niles");
        when(optionSelector.readString()).thenReturn("Napoleon Hill");
        when(optionSelector.readInt()).thenReturn(4567);
        when(optionSelector.readDate()).thenReturn("09-06-2000");
    }
}
