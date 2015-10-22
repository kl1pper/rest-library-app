package ru.dmitry.library.mappers;

import org.apache.ibatis.annotations.Param;
import ru.dmitry.library.domain.Book;

import java.util.List;

public interface BookMapper {

    List<Book> findBySearchParams(@Param("book") Book searchParams, @Param("offset") int offset, @Param("limit") int limit);

    int countBySearchParams(Book book);

    Book find(int id);

    String findTitleById(int id);

    void insert(Book book);

    void update(Book book);

    void delete(int id);

}
