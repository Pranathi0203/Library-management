package com.example.libmanagement.repository;

import com.example.libmanagement.entity.Author;
import com.example.libmanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,String> {
    public List<Book> findBookByTitleContains(String title);
    List<Book> findAllBookByIsbnIn(List<String> isbns);


}
