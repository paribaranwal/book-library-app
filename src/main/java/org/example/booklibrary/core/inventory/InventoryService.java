package org.example.booklibrary.core.inventory;

import javassist.NotFoundException;
import org.example.booklibrary.core.book.BookRepository;
import org.example.booklibrary.core.inventory.web.BookInventoryRequest;
import org.example.booklibrary.core.inventory.web.BookInventoryView;
import org.example.booklibrary.core.inventory.web.InventoryAction;
import org.example.booklibrary.core.reader.Reader;
import org.example.booklibrary.core.reader.ReaderRepository;
import org.example.booklibrary.util.DateTimeUtility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {
    private final BookInventoryRepository bookInventoryRepository;
    private final BookRepository bookRepo;
    private final ReaderRepository readerRepository;
    private final ReaderBookAllotmentRepository readerBookAllotmentRepository;

    public InventoryService(BookInventoryRepository bookInventoryRepository,
                            BookRepository bookRepo,
                            ReaderRepository readerRepository,
                            ReaderBookAllotmentRepository readerBookAllotmentRepository) {
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookRepo = bookRepo;
        this.readerRepository = readerRepository;
        this.readerBookAllotmentRepository = readerBookAllotmentRepository;
    }

    public BookInventoryView convertBookInventoryToView(BookInventory bookInventory) {
        BookInventoryView view = new BookInventoryView();
        Integer allotted = 0, available = 0;
        if (bookInventory.getAllotted() != null) {
            allotted = bookInventory.getAllotted();
        }
        if (bookInventory.getAvailable() != null) {
            available = bookInventory.getAvailable();
        }
        view.setId(bookInventory.getId());
        view.setIdBook(bookInventory.getIdBook());
        view.setBook(bookInventory.getBook());
        view.setAllotted(allotted);
        view.setAvailable(available);
        view.setCreatedDate(bookInventory.getCreatedDate());
        view.setUpdatedDate(bookInventory.getUpdatedDate());
        return view;
    }

    public Page<BookInventoryView> getBookInventory(Pageable pageable) {
        Page<BookInventory> booksInventory;
        booksInventory = bookInventoryRepository.findAll(pageable);
        List<BookInventoryView> booksInventoryViews = new ArrayList<>();
        booksInventory.forEach(bookInventory -> {
            BookInventoryView bookInventoryView = convertBookInventoryToView(bookInventory);
            booksInventoryViews.add(bookInventoryView);
        });
        return new PageImpl<>(booksInventoryViews, pageable, booksInventory.getTotalElements());
    }

    public BookInventoryView updateBookInventory(Long id, BookInventoryRequest req) throws NotFoundException, RuntimeException {
        BookInventory bookInventory;
        Integer available = 0;
        bookInventory = bookInventoryRepository.findByIdBook(id);
        if (bookInventory == null ) {
            bookInventory = new BookInventory(bookRepo.findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format("No Book Found For Id %s", id))));
        }

        if (bookInventory.getAvailable() != null) {
            available = bookInventory.getAvailable();
        }
        if (req.getAction() == InventoryAction.Add) {
            bookInventory.setAvailable(available + req.getQuantity());
        } else if( req.getAction() == InventoryAction.Remove &&
                available > req.getQuantity()) {
            bookInventory.setAvailable(available - req.getQuantity());
        } else {
            throw new RuntimeException(String.format("Unable to do this inventory operation on book '%s' ", bookInventory.getBook().getTitle()));
        }
        if (bookInventory.getId() == null) {
            bookInventory.setCreatedDate(DateTimeUtility.getCurrentUTCDateTime());
        }
        bookInventory.setUpdatedDate(DateTimeUtility.getCurrentUTCDateTime());
        BookInventory updatedInventory = bookInventoryRepository.save(bookInventory);
        return convertBookInventoryToView(updatedInventory);
    }

    public BookInventoryView allotBookToReader(Long id, Long readerId) throws NotFoundException, RuntimeException {
        BookInventory bookInventory;
        ReaderBookAllotment readerBookAllotment;
        bookInventory = bookInventoryRepository.findByIdBook(id);
        if (bookInventory == null ) {
            throw new NotFoundException(String.format("No Book Found For Id %s", id));
        }
        Reader readerToAllot = readerRepository.findById(readerId)
                .orElseThrow(() -> new NotFoundException(String.format("No Reader Found For Id %s", readerId)));
        Integer available = bookInventory.getAvailable();
        if (available > 0) {
            bookInventory.setAvailable(available - 1);
            bookInventory.setAllotted(1);
        } else {
            throw new RuntimeException(String.format("%s book not available to allot", bookInventory.getBook().getTitle()));
        }
        readerBookAllotment = readerBookAllotmentRepository.findByIdBookAndIdReader(id, readerId);
        if (readerBookAllotment == null ) {
            readerBookAllotment = new ReaderBookAllotment(bookInventory.getBook(), readerToAllot);
            readerBookAllotment.setCreatedDate(DateTimeUtility.getCurrentUTCDateTime());
        } else if(readerBookAllotment.getAllotted()) {
            throw new RuntimeException(String.format("%s already has '%s' book", readerToAllot.getName(), bookInventory.getBook().getTitle()));
        }
        readerBookAllotment.setUpdatedDate(DateTimeUtility.getCurrentUTCDateTime());
        readerBookAllotment.setAllotted(true);
        BookInventory updatedInventory = bookInventoryRepository.save(bookInventory);
        readerBookAllotmentRepository.save(readerBookAllotment);
        return convertBookInventoryToView(updatedInventory);
    }

}
