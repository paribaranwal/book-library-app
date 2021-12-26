package org.example.booklibrary.core.inventory.web;

import javassist.NotFoundException;
import org.example.booklibrary.core.inventory.BookReaderMappingActions;
import org.example.booklibrary.core.inventory.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library")
public class InventoryController {
    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping("/books")
    public Page<BookInventoryView> getBookInventory(Pageable pageable) {
        return service.getBookInventory(pageable);
    }

    @PutMapping("/book/{id}")
    public BookInventoryView updateBookInventory(@PathVariable Long id, BookInventoryRequest req)
            throws NotFoundException, RuntimeException {
        return service.updateBookInventory(id, req);
    }

    @PutMapping("book-reader-mapping/{bookId}/{readerId}")
    public BookInventoryView updateBookReaderMapping(@PathVariable Long bookId,
                                                     @PathVariable Long readerId,
                                                     @RequestParam BookReaderMappingActions mappingAction) throws NotFoundException {
        if (mappingAction == BookReaderMappingActions.Allot) {
            return service.allotBookToReader(bookId, readerId);
        } else if (mappingAction == BookReaderMappingActions.Return) {
            return service.returnBookFromReader(bookId, readerId);
        } else {
            throw new NotFoundException(String.format("Invalid Action mapping %s between book and reader", mappingAction));
        }
    }
}
