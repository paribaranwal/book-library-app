package org.example.booklibrary.core.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderBookAllotmentRepository extends JpaRepository<ReaderBookAllotment, Long> {
    ReaderBookAllotment findByIdBookAndIdReader(Long bookId, Long readerId);
}