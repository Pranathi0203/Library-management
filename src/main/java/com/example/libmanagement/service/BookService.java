package com.example.libmanagement.service;

import com.example.libmanagement.entity.Author;
import com.example.libmanagement.entity.Book;
import com.example.libmanagement.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBook(String title){
        List<Book> bookList=bookRepository.findBookByTitleContains(title);
        return bookList;
    }
}
