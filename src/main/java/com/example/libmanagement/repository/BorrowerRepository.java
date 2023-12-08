package com.example.libmanagement.repository;

import com.example.libmanagement.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, String> {
    Borrower findBorrowerBySsn(String ssn);
    Borrower findBorrowerByCardId(String cardId);
    boolean existsBorrowerBySsn(String ssn);
    Borrower findBorrowerByCardIdOrFnameContainingOrLnameContaining(String cardId,String fname, String lname);

    long count();
}
