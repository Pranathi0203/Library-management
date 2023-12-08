package com.example.libmanagement.service;

import java.util.*;
import java.util.stream.Collectors;

import com.example.libmanagement.dto.BookAuthorDTO;
import com.example.libmanagement.dto.SearchResponse;
import com.example.libmanagement.entity.Book;
import com.example.libmanagement.entity.BookAuthor;
import com.example.libmanagement.entity.Author;
import com.example.libmanagement.entity.BookLoans;
import com.example.libmanagement.repository.BookAuthorRepository;
import com.example.libmanagement.repository.BookLoansRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {
    public final BookAuthorRepository bookAuthorRepository;
    public final BookLoansRepository bookLoansRepository;

    public Set<SearchResponse> getSearchInfo(String searchTerm) {
        List<BookAuthor> listOfBookAuthor = bookAuthorRepository.findAllByAuthorNameContainingOrBookTitleContainingOrBookIsbnContaining(searchTerm, searchTerm, searchTerm);
        Set<SearchResponse> searchResponseList = createSearchResponse(listOfBookAuthor);
        return searchResponseList;
    }

    public Set<SearchResponse> createSearchResponse(List<BookAuthor> bookAuthors) {

        List<Book> books = bookAuthors.stream().map(BookAuthor::getBook).toList();
        List<BookLoans> unreturnedLoans = bookLoansRepository.findAllByBookInAndDateInIsNullAndDueDateIsNotNull(books);


        List<BookAuthorDTO> dtos = bookAuthors.stream().map(e -> {
            BookAuthorDTO res = new BookAuthorDTO();
            Book book = e.getBook();
            res.setIsbn(book.getIsbn());
            res.setTitle(book.getTitle());
            res.setAuthorName(e.getAuthor().getName());
            res.setUnavailable(unreturnedLoans.stream().anyMatch(u -> u.getBook().getIsbn().equals(book.getIsbn())));
            return res;
        }).toList();

        Map<String, List<String>> isbnAuthorMap = new HashMap<>();
        for (BookAuthorDTO res : dtos) {
            List<String> authorsList = isbnAuthorMap.get(res.getIsbn());
            if (authorsList != null && !authorsList.isEmpty()) {
                authorsList.add(res.getAuthorName());
            } else {
                authorsList = new ArrayList<>();
                authorsList.add(res.getAuthorName());
            }
            isbnAuthorMap.put(res.getIsbn(), authorsList);
        }

        return dtos.stream().map(o -> {
            SearchResponse res = new SearchResponse();
            res.setIsbn(o.getIsbn());
            res.setTitle(o.getTitle());
            res.setAuthorNames(String.join(", ", isbnAuthorMap.get(o.getIsbn())));
            res.setUnavailable(o.isUnavailable());
            return res;
        }).collect(Collectors.toSet());
    }

}
