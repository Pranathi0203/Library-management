package com.example.libmanagement.repository;

import com.example.libmanagement.entity.BookLoans;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepository extends JpaRepository<BookLoans,Long> {
}
