package org.example.booklibrary.core.inventory.web;

import org.example.booklibrary.core.book.Book;
import org.example.booklibrary.core.book.web.BookView;

import java.util.Date;

public class BookInventoryView extends BookView {
    private Long idBook;
    private Integer available;
    private Integer allotted;
    private Date createdDate;
    private Date updatedDate;

    public Long getIdBook() {
        return idBook;
    }

    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getAllotted() {
        return allotted;
    }

    public void setAllotted(Integer allotted) {
        this.allotted = allotted;
    }

    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setBook(Book book) {
        this.setAuthor(book.getAuthor());
        this.setTitle(book.getTitle());
        this.setIsbnCode(book.getIsbnCode());
    }
}
