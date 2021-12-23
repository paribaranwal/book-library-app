package org.example.booklibrary.core.inventory;

import org.example.booklibrary.core.book.Book;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="BookInventory")
public class BookInventory {
    public BookInventory() {
    }

    public BookInventory(Book book) {
        super();
        this.setIdBook(book.getId());
    }
    @Id
    @Column(name = "id")
    @GenericGenerator(
            name = "book_library_book_inventory_id_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "book_library_book_inventory_id_seq"),
                    @org.hibernate.annotations.Parameter(name= "INCREMENT", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "MINVALUE", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "MAXVALUE", value = "2147483647"),
                    @org.hibernate.annotations.Parameter(name = "CACHE", value = "1")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_library_book_inventory_id_seq")
    private Long id;

    @Column(name="id_book")
    private Long idBook;

    @Column(name="available")
    private Integer available;

    @Column(name="allotted")
    private Integer allotted;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "updatedDate")
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "id_book", insertable = false, updatable = false)
    private Book book;

    public Book getBook() {
        return book;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdBook() {
        return idBook;
    }

    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }

    public void setBook(Book book) {
        this.book = book;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
