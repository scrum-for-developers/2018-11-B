package de.codecentric.psd.worblehat.web.controller;

import java.util.HashMap;
import java.util.Optional;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.BookDataFormData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InsertBookControllerTest {

    private InsertBookController insertBookController;

    private BookService bookService;

    private BookDataFormData bookDataFormData;

    private BindingResult bindingResult;

    private static final Book TEST_BOOK = new Book("title", "author", "edition", "isbn", null, 2016);

    private static final Book TEST_BOOK_WITH_DESCRIPTION = new Book("title", "author", "edition", "isbn", "description", 2016);

    @Before
    public void setUp() {
        bookService = mock(BookService.class);
        insertBookController = new InsertBookController(bookService);
        bookDataFormData = new BookDataFormData();
        bindingResult = new MapBindingResult(new HashMap<>(), "");
    }

    @Test
    public void shouldSetupForm() {
        ModelMap modelMap = new ModelMap();

        insertBookController.setupForm(modelMap);

        assertThat(modelMap.get("bookDataFormData"), is(not(nullValue())));
    }

    @Test
    public void shouldRejectErrors() {
        bindingResult.addError(new ObjectError("", ""));

        String navigateTo = insertBookController.processSubmit(bookDataFormData, bindingResult);

        assertThat(navigateTo, is("insertBooks"));
    }

    @Test
    public void shouldCreateNewCopyOfExistingBook() {
        setupFormData();
        when(bookService.bookExists(TEST_BOOK.getIsbn())).thenReturn(true);
        when(bookService.createBook(any(), any(), any(), any(), any(), anyInt())).thenReturn(Optional.of(TEST_BOOK));

        String navigateTo = insertBookController.processSubmit(bookDataFormData, bindingResult);

        verifyBookIsCreated();
        assertThat(navigateTo, is("redirect:bookList"));
    }

    @Test
    public void shouldCreateBookAndNavigateToBookList() {
        setupFormData();
        when(bookService.bookExists(TEST_BOOK.getIsbn())).thenReturn(false);
        when(bookService.createBook(any(), any(), any(), any(), any(), anyInt())).thenReturn(Optional.of(TEST_BOOK));

        String navigateTo = insertBookController.processSubmit(bookDataFormData, bindingResult);

        verifyBookIsCreated();
        assertThat(navigateTo, is("redirect:bookList"));
    }

    @Test
    public void shouldCreateBookWithoutDescriptionAndNavigateToBookList(){
        setupFormDataWithDescription();
        when(bookService.bookExists(TEST_BOOK_WITH_DESCRIPTION.getIsbn())).thenReturn(false);
        when(bookService.createBook(any(), any(), any(), any(), any(), anyInt())).thenReturn(Optional.of(TEST_BOOK_WITH_DESCRIPTION));

        String navigateTo = insertBookController.processSubmit(bookDataFormData, bindingResult);

        verifyBookIsCreatedWithDescription();
        assertThat(navigateTo, is("redirect:bookList"));
    }

    private void verifyBookIsCreated() {
        verify(bookService).createBook(TEST_BOOK.getTitle(), TEST_BOOK.getAuthor(),
                TEST_BOOK.getEdition(), TEST_BOOK.getIsbn(), TEST_BOOK.getDescription(), TEST_BOOK.getYearOfPublication());
    }

    private void verifyBookIsCreatedWithDescription(){
        verify(bookService).createBook(TEST_BOOK_WITH_DESCRIPTION.getTitle(), TEST_BOOK_WITH_DESCRIPTION.getAuthor(),
                TEST_BOOK_WITH_DESCRIPTION.getEdition(), TEST_BOOK_WITH_DESCRIPTION.getIsbn(),
                TEST_BOOK_WITH_DESCRIPTION.getDescription(), TEST_BOOK_WITH_DESCRIPTION.getYearOfPublication());
    }

    private void setupFormData() {
        bookDataFormData.setTitle(TEST_BOOK.getTitle());
        bookDataFormData.setAuthor(TEST_BOOK.getAuthor());
        bookDataFormData.setEdition(TEST_BOOK.getEdition());
        bookDataFormData.setIsbn(TEST_BOOK.getIsbn());
        bookDataFormData.setYearOfPublication(String.valueOf(TEST_BOOK.getYearOfPublication()));
    }

    private void setupFormDataWithDescription() {
        bookDataFormData.setTitle(TEST_BOOK_WITH_DESCRIPTION.getTitle());
        bookDataFormData.setAuthor(TEST_BOOK_WITH_DESCRIPTION.getAuthor());
        bookDataFormData.setEdition(TEST_BOOK_WITH_DESCRIPTION.getEdition());
        bookDataFormData.setIsbn(TEST_BOOK_WITH_DESCRIPTION.getIsbn());
        bookDataFormData.setDescription(TEST_BOOK_WITH_DESCRIPTION.getDescription());
        bookDataFormData.setYearOfPublication(String.valueOf(TEST_BOOK_WITH_DESCRIPTION.getYearOfPublication()));
    }
}
