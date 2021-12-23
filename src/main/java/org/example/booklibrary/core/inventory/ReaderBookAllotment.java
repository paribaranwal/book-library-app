package org.example.booklibrary.core.inventory;

import org.example.booklibrary.core.book.Book;
import org.example.booklibrary.core.reader.Reader;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reader_book_allotment")
public class ReaderBookAllotment {
    @Id
    @Column(name = "id")
    @GenericGenerator(
            name = "book_library_reader_book_allotment_id_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "book_library_reader_book_allotment_id_seq"),
                    @org.hibernate.annotations.Parameter(name= "INCREMENT", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "MINVALUE", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "MAXVALUE", value = "2147483647"),
                    @org.hibernate.annotations.Parameter(name = "CACHE", value = "1")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_library_reader_book_allotment_id_seq")
    private Long id;

    @Column(name="id_book")
    private Long idBook;

    @Column(name="id_reader")
    private Long idReader;

    @ManyToOne
    @JoinColumn(name = "id_reader", insertable = false, updatable = false)
    private Reader reader;

    @ManyToOne
    @JoinColumn(name = "id_book", insertable = false, updatable = false)
    private Book book;

    @Column(name = "allotted")
    private Boolean allotted;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    public ReaderBookAllotment() {
    }

    public ReaderBookAllotment(Book book, Reader readerToAllot) {
        super();
        this.setIdBook(book.getId());
        this.setIdReader(readerToAllot.getId());
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getAllotted() {
        return allotted;
    }

    public void setAllotted(Boolean allotted) {
        this.allotted = allotted;
    }

    public Long getIdBook() {
        return idBook;
    }

    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }

    public Long getIdReader() {
        return idReader;
    }

    public void setIdReader(Long idReader) {
        this.idReader = idReader;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}