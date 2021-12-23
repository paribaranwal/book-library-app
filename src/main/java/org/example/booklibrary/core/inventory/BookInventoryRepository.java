package org.example.booklibrary.core.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookInventoryRepository extends JpaRepository<BookInventory, Long> {
    BookInventory findByIdBook(Long bookId);
}