package com.example.libmanagement.repository;

import com.example.libmanagement.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
    @Query(nativeQuery = true, value="select * from Authors where Author_id= :id")
    public List<Author> getAllAuthorsById(Long id);
    public List<Author> findAuthorsByNameContains(String name);

}
