package ru.dmitry.library.services;

import ru.dmitry.library.domain.Book;

import java.util.List;

public interface BookService {

    Book find(int id);

    String findTitleById(int id);

    List<Book> getList(Book searchParams, int page);

    int getPageCount(Book searchParams);

    void insert(Book book);

    void update(Book book);

    void delete(int id);

}
