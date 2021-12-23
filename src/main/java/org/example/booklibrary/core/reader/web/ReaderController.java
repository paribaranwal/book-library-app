package org.example.booklibrary.core.reader.web;

import javassist.NotFoundException;
import org.example.booklibrary.core.reader.ReaderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reader")
public class ReaderController {
    private final ReaderService service;
    public ReaderController(ReaderService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ReaderView getReader(@PathVariable Long id) throws NotFoundException {
        return service.getReaderById(id);
    }

    @GetMapping
    @ResponseBody
    public Page<ReaderView> getReaders(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return service.getAllReaders(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReaderView addReader(@RequestBody ReaderRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ReaderView updateReaderDetail(@PathVariable Long id, @RequestBody ReaderRequest req) throws NotFoundException {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Void removeReader(@PathVariable Long id) throws NotFoundException {
        return service.remove(id);
    }
}
