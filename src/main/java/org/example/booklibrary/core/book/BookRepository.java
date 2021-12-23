package org.example.booklibrary.core.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByTitle(String title, Pageable pageable);
    Page<Book> findByAuthor(String author, Pageable pageable);
    Page<Book> findByIsbnCode(String isbnCode, Pageable pageable);

    Page<Book> findByTitleOrAuthorOrIsbnCode(String title, String author, String isbnCode, Pageable pageable);
}