package com.example.libmanagement.repository;

import com.example.libmanagement.entity.Fines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinesRepository extends JpaRepository<Fines,Long> {
    List<Fines> findAllByBookLoans_BorrowerCardIdAndBookLoans_DateInIsNotNullAndPaidIsFalse(String cardId);

    List<Fines> findAllByBookLoans_LoanIdIn(List<Long> loanIds);
    List<Fines> findFinesByLoanIdIn(List<Long> loanIds);

    @Query(nativeQuery = true, value="update fines set paid=true where loan_id in(:loanIds)")
    int updatePaidStatus(List<Long> loanIds);

    List<Fines> findAllByBookLoans_Borrower_CardIdAndBookLoansDateInIsNotNullAndPaidIsFalse(String cardId);

    int countAllByBookLoans_Borrower_CardIdAndBookLoansDateInIsNotNullAndPaidIsFalse(String cardId);
}
