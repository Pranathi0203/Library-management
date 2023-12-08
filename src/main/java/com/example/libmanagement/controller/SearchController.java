package com.example.libmanagement.controller;

import com.example.libmanagement.dto.SearchResponse;
import com.example.libmanagement.entity.Author;
import com.example.libmanagement.service.AuthorService;
import com.example.libmanagement.service.BookService;
import com.example.libmanagement.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Set;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SearchController {
    final AuthorService authorService;
    final BookService bookService;
    final SearchService searchService;

//    @GetMapping(path = {"/","/search"})
//    public String getAuthor(Author author, Model model, String name) {
//        if(name != null) {
//            List<Author> authorsList = authorService.getAuthor(name);
//            log.info("Inside getAuthors " + authorsList.get(0).getName());
//            model.addAttribute("authorsList", authorsList);
//        }
//        return "searchauthor";
//    }

    @GetMapping(path = {"/","/search"})
    public String getSearchInfo(SearchResponse response, Model model, String keyword){
        if(keyword != null && !keyword.isEmpty()) {
            Set<SearchResponse> searchResponses = searchService.getSearchInfo(keyword);
            //System.out.println(searchResponses);
            model.addAttribute("responses", searchResponses);
        }
        return "searchauthor";
    }
}
