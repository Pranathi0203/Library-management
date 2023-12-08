package com.example.libmanagement.repository;

import com.example.libmanagement.dto.CheckOutBooksRequest;
import com.example.libmanagement.entity.Book;
import com.example.libmanagement.entity.BookLoans;
import com.example.libmanagement.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BookLoansRepository extends JpaRepository<BookLoans,Long> {
    List<BookLoans> findAllByBookInAndDateInIsNullAndDueDateIsNotNull(List<Book> book);
    List<BookLoans>findAllByBorrowerCardIdOrBorrowerFnameContainingOrBorrowerLnameContainingOrBookIsbn(String cardId,String fname,String lname,String isbn);
//    boolean existsBookLoansByIsbn(List<String> isbn);
    List<BookLoans>findAllByBorrowerCardIdOrBorrowerFnameContainingOrBorrowerLnameContainingOrBookIsbnAndDateInIsNull(String cardId,String fname,String lname,String isbn);

    long count();
    int countAllByBorrowerAndDateInIsNull(Borrower borrower);
    long countAllByBook_IsbnInAndDateInIsNull(List<String> isbns);
    BookLoans findBookLoansByLoanId(long loanId);

    List<BookLoans> findAllBookLoansByDateInIsNull();
    List<BookLoans>findAllByBorrowerCardIdOrBorrowerFnameContainingOrBorrowerLnameContainingAndDateInIsNull(String cardId,String fname, String lname);

}
