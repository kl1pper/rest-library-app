package ru.dmitry.library.controllers;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.dmitry.library.domain.Book;
import ru.dmitry.library.domain.BookListModel;
import ru.dmitry.library.exceptions.ServiceErrorException;
import ru.dmitry.library.services.BookService;

import java.io.IOException;
import java.io.Writer;

@Controller
@RequestMapping(value = "/book")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @ExceptionHandler(ServiceErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public void handleException(final ServiceErrorException e, Writer writer) throws IOException {
        logger.error(e.getMessage(), e);
        writer.write(String.format("{\"error\":\"%s\"}", e.getMessage()));
    }

    @Autowired
    private BookService bookService;
    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.GET)
    public String main() {
        return "book";
    }

    @RequestMapping(params = {"search", "page"}, method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public BookListModel getList(@RequestParam("search") String searchRequest, @RequestParam("page") int page) throws IOException {
        Book book = mapper.readValue(searchRequest, Book.class);
        BookListModel result = new BookListModel();
        result.setPageCount(bookService.getPageCount(book));
        result.setBooks(bookService.getList(book, page-1));
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Book find(@PathVariable("id") int id) {
        return bookService.find(id);
    }

    @RequestMapping(value = "/title/{id}", method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String findTitleById(@PathVariable("id") int id) {
        return bookService.findTitleById(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void insert(@RequestBody Book book) {
        bookService.insert(book);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Book book) {
        bookService.update(book);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") int id) {
        bookService.delete(id);
    }
}
