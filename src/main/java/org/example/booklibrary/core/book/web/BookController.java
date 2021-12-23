package org.example.booklibrary.core.book.web;

import javassist.NotFoundException;
import org.example.booklibrary.core.book.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService service;
    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public BookView getBook(@PathVariable Long id) throws NotFoundException {
        return service.findBookById(id);
    }

    @GetMapping
    @ResponseBody
    public Page<BookView> getBooks(@RequestParam(required = false) String title,
                                   @RequestParam(required = false) String author,
                                   @RequestParam(required = false) String isbnCode,
                                   @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        BookCriteria criteria = new BookCriteria();
        criteria.setAuthor(author);
        criteria.setTitle(title);
        criteria.setIsbnCode(isbnCode);
        return service.findBookByCriteria(criteria, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookView addBook(@RequestBody @Valid BookRequest req) { return service.create(req); }

    @PutMapping("/{id}")
    @ResponseBody
    public BookView updateBookDetail(@PathVariable Long id, @RequestBody @Valid BookRequest req) throws NotFoundException {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Void removeBook(@PathVariable Long id) throws NotFoundException {
        return service.remove(id);
    }
}
