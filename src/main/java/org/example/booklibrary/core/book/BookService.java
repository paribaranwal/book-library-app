package org.example.booklibrary.core.book;

import javassist.NotFoundException;
import org.example.booklibrary.core.book.web.BookCriteria;
import org.example.booklibrary.core.book.web.BookRequest;
import org.example.booklibrary.core.book.web.BookView;
import org.example.booklibrary.util.DateTimeUtility;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public BookView convertToView(Book book) {
        BookView view = new BookView();
        view.setId(book.getId());
        view.setAuthor(book.getAuthor());
        view.setTitle(book.getTitle());
        view.setIsbnCode(book.getIsbnCode());
        view.setCreatedDate(book.getCreatedDate());
        view.setUpdatedDate(book.getUpdatedDate());
        return view;
    }

    public BookView findBookById(Long id) throws NotFoundException {
        Book book = bookRepo.findById(id).orElseThrow(() -> new NotFoundException("No Book Found For given Id"));
        return convertToView(book);
    }

    public Page<BookView> findBookByCriteria(BookCriteria criteria, Pageable pageable) {
        if (criteria.getAuthor() == null &&
                criteria.getTitle() == null &&
                criteria.getIsbnCode() == null){
            Page<Book> books = bookRepo.findAll(pageable);
            List<BookView> bookViews = new ArrayList<>();
            books.forEach(book -> {
                BookView bookView = convertToView(book);
                bookViews.add(bookView);
            });
            return new PageImpl<>(bookViews, pageable, books.getTotalElements());
        }
        Page<Book> books = bookRepo.findByTitleOrAuthorOrIsbnCode(criteria.getTitle(),
                criteria.getAuthor(), criteria.getIsbnCode(), pageable);
        List<BookView> bookViews = new ArrayList<>();
        books.forEach(book -> {
            BookView bookView = convertToView(book);
            bookViews.add(bookView);
        });
        return new PageImpl<>(bookViews, pageable, books.getTotalElements());
    }

    public BookView create(BookRequest req) {
        Book book = new Book();
        book.setAuthor(req.getAuthor());
        book.setTitle(req.getTitle());
        book.setIsbnCode(req.getIsbnCode());
        book.setCreatedDate(DateTimeUtility.getCurrentUTCDateTime());
        book.setUpdatedDate(DateTimeUtility.getCurrentUTCDateTime());
        Book createdBook = bookRepo.save(book);
        return convertToView(createdBook);
    }

    public BookView update(Long id, BookRequest req) throws NotFoundException {
        Book bookToUpdate = bookRepo.findById(id).orElseThrow(() -> new NotFoundException("No Book Found For given Id"));
        bookToUpdate.setAuthor(req.getAuthor());
        bookToUpdate.setTitle(req.getTitle());
        bookToUpdate.setIsbnCode(req.getIsbnCode());
        bookToUpdate.setUpdatedDate(DateTimeUtility.getCurrentUTCDateTime());
        Book updatedBook = bookRepo.save(bookToUpdate);
        return convertToView(updatedBook);
    }

    public Void remove(Long id) throws NotFoundException {
        try {
            bookRepo.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new NotFoundException(e.getMessage());
        }
        return null;
    }
}
