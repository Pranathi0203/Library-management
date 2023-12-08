package com.example.libmanagement.entity;
import java.util.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="authors")
public class Author {
    @Id
    private Long authorId;
    private String name;
}
