package com.example.libmanagement.controller;

import com.example.libmanagement.entity.Author;
import com.example.libmanagement.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
public class SearchController {
    final AuthorService authorService;

    public SearchController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(path = {"/","/search"})
    public String getAuthor(Author author, Model model, String name) {
        if(name != null) {
            List<Author> authorsList = authorService.getAuthor(name);
            log.info("Inside getAuthors " + authorsList.get(0).getName());
            model.addAttribute("authorsList", authorsList);
        }
        return "searchauthor";
    }

}
