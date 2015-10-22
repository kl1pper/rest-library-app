package ru.dmitry.library.services;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dmitry.library.domain.Book;
import ru.dmitry.library.mappers.BookMapper;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    public static final int PAGE_SIZE = 6;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public Book find(int id) {
        return bookMapper.find(id);
    }

    @Override
    public String findTitleById(int id) {
        return bookMapper.findTitleById(id);
    }

    @Override
    public List<Book> getList(Book searchParams, int page) {
        validateSearchParams(searchParams);
        return bookMapper.findBySearchParams(searchParams, page * PAGE_SIZE, PAGE_SIZE);
    }

    @Override
    public int getPageCount(Book searchParams) {
        validateSearchParams(searchParams);
        int rowCount = bookMapper.countBySearchParams(searchParams);
        int pageCount = rowCount / PAGE_SIZE;
        if (rowCount % PAGE_SIZE > 0 || pageCount == 0) {
            pageCount++;
        }
        return pageCount;
    }

    @Override
    public void insert(Book book) {
        bookMapper.insert(book);
    }

    @Override
    public void update(Book book) {
        bookMapper.update(book);
    }

    @Override
    public void delete(int id) {
        bookMapper.delete(id);
    }

    private void validateSearchParams(Book book) {
        book.setAuthor(validateSearchString(book.getAuthor()));
        book.setTitle(validateSearchString(book.getTitle()));
        book.setDescription(validateSearchString(book.getDescription()));
    }

    private String validateSearchString(String s) {

        s = Strings.emptyToNull(s);

        if (s != null) {
            s = '%' + s.trim().replace(' ', '%') + '%';
        }

        return s;

    }

}
