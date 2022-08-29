import com.softwify.libraryapp.dao.AuthorDao;
import com.softwify.libraryapp.model.Author;
import com.softwify.libraryapp.services.AuthorManager;
import com.softwify.libraryapp.util.OptionSelector;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AuthorManagerTest {
    AuthorDao authorDao = Mockito.mock(AuthorDao.class);
    OptionSelector optionSelector = Mockito.mock(OptionSelector.class);

    AuthorManager authorManager = new AuthorManager(authorDao, optionSelector);

    @Test
    public void displayAuthorTest() {
        authorManager.displayAuthors();
        verify(authorDao,times(1)).getAll();
    }

    @Test
    public void processDeleteMustReturnFalseWhenTheAuthorDoesNotExistInTheDao() {
        when(authorDao.delete(anyInt())).thenReturn(false);

        boolean deleted = authorManager.delete(20);
        assertFalse(deleted);
        verify(authorDao,times(1)).delete(20);
    }

    @Test
    public void processDeleteMustReturnTrueWhenTheAuthorDoesExistInTheDao() {
        when(authorDao.delete(anyInt())).thenReturn(true);

        boolean deleted = authorManager.delete(20);
        assertTrue(deleted);
        verify(authorDao,times(1)).delete(20);
    }

    @Test
    public void addAuthorTest() {
        String author = "cloe";
        when(optionSelector.readString()).thenReturn(author);
        authorManager.addNewAuthor();
        verify(authorDao, times(1)).save(any(Author.class));
    }
}
