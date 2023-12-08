package com.example.libmanagement.repository;
import java.util.*;


import com.example.libmanagement.entity.BookAuthor;
import com.example.libmanagement.entity.BookAuthorKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorKey> {
    List<BookAuthor>findAllByAuthorNameContainingOrBookTitleContainingOrBookIsbnContaining(String name,String title,String isbn);
}
