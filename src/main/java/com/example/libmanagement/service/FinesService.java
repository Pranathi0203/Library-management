package com.example.libmanagement.service;

import com.example.libmanagement.dto.FinesResponse;
import com.example.libmanagement.entity.BookLoans;
import com.example.libmanagement.entity.Borrower;
import com.example.libmanagement.entity.Fines;
import com.example.libmanagement.repository.BookLoansRepository;
import com.example.libmanagement.repository.BookRepository;
import com.example.libmanagement.repository.BorrowerRepository;
import com.example.libmanagement.repository.FinesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FinesService {
    final BookLoansRepository bookLoansRepository;
    final BorrowerRepository borrowerRepository;
    final BookRepository bookRepository;
    final FinesRepository finesRepository;

    private static final double FINEEACHDAY = 0.25;

    public FinesResponse searchFines(String cardId) {
        Borrower borrower = borrowerRepository.findBorrowerByCardId(cardId);
        System.out.println(borrower);
        List<Fines> unpaidBorrowerFinesOfReturnedBooks = finesRepository.findAllByBookLoans_BorrowerCardIdAndBookLoans_DateInIsNotNullAndPaidIsFalse(borrower.getCardId());

        //System.out.println(unpaidBorrowerFinesOfReturnedBooks);
        return createFinesResponse(unpaidBorrowerFinesOfReturnedBooks, borrower);
    }

    public FinesResponse createFinesResponse(List<Fines> activeLoans, Borrower borrower) {
        double unpaidFine =
                activeLoans.stream().mapToDouble(Fines::getFineAmt).sum();
        //System.out.println(unpaidFine);
        FinesResponse res = new FinesResponse();
            res.setBorrowerId(borrower.getCardId());
            res.setBorrowerName(borrower.getFname() + " " + borrower.getLname());
            res.setTotalFine(unpaidFine);

//        return activeLoans.stream().map(e -> {
//            FinesResponse res = new FinesResponse();
//            res.setLoanId(e.getBookLoans().getLoanId());
//            res.setIsbn(e.getBookLoans().getBook().getIsbn());
//            res.setTitle(e.getBookLoans().getBook().getTitle());
//            res.setBorrowerId(e.getBookLoans().getBorrower().getCardId());
//            res.setBorrowerName(e.getBookLoans().getBorrower().getFname() + " " + e.getBookLoans().getBorrower().getLname());
//            res.setBorrowedDate(e.getBookLoans().getDateOut());
//            res.setDueDate(e.getBookLoans().getDueDate());
//            res.setCheckInDate(e.getBookLoans().getDateIn());
//            res.setFineOnOneBook(e.getFineAmt());
//            return res;
//        }).toList();
        return res;
    }

    public void calculateFines() {
        Date currentDate = new Date();
        List<BookLoans> dueBookLoans = bookLoansRepository.findAll();
        List<Fines> existingFines = finesRepository.findAllByBookLoans_LoanIdIn(dueBookLoans.stream().map(BookLoans::getLoanId).toList());

        existingFines.forEach(o -> {
            o.setFineAmt(calculateFineAmount(o.getBookLoans(), currentDate));
        });

        dueBookLoans.removeAll(existingFines
                .stream()
                .map(Fines::getBookLoans)
                .toList());

        List<Fines> newFines = new ArrayList<>();
        dueBookLoans.forEach(o -> {
            Fines fine = new Fines();
            fine.setLoanId(o.getLoanId());
            fine.setBookLoans(o);
            fine.setFineAmt(calculateFineAmount(o, currentDate));
            fine.setPaid(false);
            if (fine.getFineAmt() > 0) {
                newFines.add(fine);
            }
        });

        log.info(" Updating Fines List : " + existingFines.size());
        log.info(("New Fines List : " + newFines.size()));
        finesRepository.saveAll(existingFines);
        finesRepository.saveAll(newFines);

    }

    private Double calculateFineAmount(BookLoans loan, Date currentDate) {
        int daysdiff;
        double fine = 0;
        long diff;
        if (loan.getDateIn() == null) {
            diff = currentDate.getTime() - loan.getDueDate().getTime();

            long diffDays = diff / (24 * 60 * 60 * 1000);
            daysdiff = (int) diffDays;
        } else {
            diff = loan.getDateIn().getTime() - loan.getDueDate().getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            daysdiff = (int) diffDays;
        }
        if (daysdiff > 0) {
            fine = daysdiff * FINEEACHDAY;
        }

        return fine;
    }

    public String payFines(String cardId){
        List<Fines> unpaidFinesOfReturnedBooks=finesRepository.findAllByBookLoans_Borrower_CardIdAndBookLoansDateInIsNotNullAndPaidIsFalse(cardId);

        unpaidFinesOfReturnedBooks.forEach(e->{
            e.setPaid(true);});
        finesRepository.saveAll(unpaidFinesOfReturnedBooks);
        return "Fine Payment Successful";

    }
}
