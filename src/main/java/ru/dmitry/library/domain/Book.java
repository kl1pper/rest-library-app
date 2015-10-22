package ru.dmitry.library.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Book {

    private Integer id;
    private String author;
    private String title;
    private String description;
    private Integer publishedYear;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Book book = (Book) obj;

        return Objects.equal(id, book.id)
                && Objects.equal(author, book.author)
                && Objects.equal(title, book.title)
                && Objects.equal(description, book.description)
                && Objects.equal(publishedYear, book.publishedYear);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, author, title, description, publishedYear);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("author", author)
                .add("title", title)
                .add("description", description)
                .add("publishedYear", publishedYear)
                .toString();
    }
}
