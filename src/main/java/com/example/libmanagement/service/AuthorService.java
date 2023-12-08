package com.example.libmanagement.service;

import com.example.libmanagement.entity.Author;
import com.example.libmanagement.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {

        this.authorRepository = authorRepository;
    }
    public List<Author> getAuthor(String name){
        List<Author> authorsList=authorRepository.findAuthorsByNameContains(name);
        return authorsList;
    }

}
