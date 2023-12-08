package com.example.libmanagement.service;
import com.example.libmanagement.dto.*;
import com.example.libmanagement.entity.*;
import com.example.libmanagement.exception.*;
import com.example.libmanagement.repository.BookLoansRepository;
import com.example.libmanagement.repository.BookRepository;
import com.example.libmanagement.repository.BorrowerRepository;
import com.example.libmanagement.repository.FinesRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookLoansService {
     final BookLoansRepository bookLoansRepository;
     final BorrowerRepository borrowerRepository;
     final BookRepository bookRepository;
     final FinesRepository finesRepository;
    public void addBookLoans(CheckOutBooksRequest checkOutBooksRequest) throws BookNotAvailableException, BooksCheckOutCapacityReached, BorrowerNotExistsException,FinesExistsException {
        List<Book> books= bookRepository.findAllBookByIsbnIn(checkOutBooksRequest.getIsbns());
        //System.out.println(books);
        Borrower borrower=borrowerRepository.findBorrowerByCardId(checkOutBooksRequest.getCardId());

        //System.out.println(bookLoansRepository.countAllByBorrowerAndDateInIsNull(borrower));
        if(bookLoansRepository.countAllByBorrowerAndDateInIsNull(borrower)>=3){
            throw new BooksCheckOutCapacityReached("You have 3 Book Loans");
        }

        if(bookLoansRepository.countAllByBook_IsbnInAndDateInIsNull(checkOutBooksRequest.getIsbns())>0){
            throw  new BookNotAvailableException("Book is Currently Checked Out");

        }
        if(borrower==null){
            throw new BorrowerNotExistsException("Borrower doesn't exist");
        }
        if(finesRepository.countAllByBookLoans_Borrower_CardIdAndBookLoansDateInIsNotNullAndPaidIsFalse(borrower.getCardId())>0){
            throw new FinesExistsException("Borrower has unpaid fines");

        }
        Date todayDate=new Date();
        long lid=bookLoansRepository.count();
        for(Book i: books){
            System.out.println(lid);
            BookLoans bookLoans = new BookLoans();
            bookLoans.setLoanId(lid+1);
            bookLoans.setBook(i);
            bookLoans.setBorrower(borrower);
            System.out.println(todayDate);
            bookLoans.setDateOut(todayDate);
            bookLoans.setDueDate(calculateDueDate(todayDate));
            bookLoans.setDateIn(null);
            bookLoansRepository.save(bookLoans);
            lid+=1;
        }

    }
    public Date calculateDueDate(Date todayDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(todayDate);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        return calendar.getTime();

    }
public List<CheckInResponse>returnbooks(String searchWord){
    List<BookLoans> checkinInfo=bookLoansRepository.findAllByBorrowerCardIdOrBorrowerFnameContainingOrBorrowerLnameContainingOrBookIsbnAndDateInIsNull(searchWord,searchWord,searchWord,searchWord);
    List<CheckInResponse> checkInResponses= createCheckInResponse(checkinInfo);
    return checkInResponses;
}
public List<CheckInResponse> createCheckInResponse(List<BookLoans> checkinInfo){
        //System.out.println(checkinInfo.getFirst().getBook().getTitle());
        return checkinInfo.stream().filter((e -> e.getDateIn()== null)).map(e -> {
            CheckInResponse res = new CheckInResponse();
            res.setLoanId(e.getLoanId());
            res.setIsbn(e.getBook().getIsbn());
            res.setTitle(e.getBook().getTitle());
            res.setBorrowerId(e.getBorrower().getCardId());
            res.setBorrowerName(e.getBorrower().getFname()+" "+e.getBorrower().getLname());
            res.setBorrowedDate(e.getDateOut());
            res.setDueDate(e.getDueDate());
            //res.setCheckInDate(e.getDateIn());
            return res;
        }).toList();

}
public void bookCheckIn(long loanid){
        BookLoans toBeCheckedIn=bookLoansRepository.findBookLoansByLoanId(loanid);
        toBeCheckedIn.setDateIn(new Date());
        bookLoansRepository.save(toBeCheckedIn);
        System.out.println(toBeCheckedIn);
}

    public List<CheckInResponse> borrowerHistory(String searchWord) {
        List<BookLoans> historyInfo=bookLoansRepository.findAllByBorrowerCardIdOrBorrowerFnameContainingOrBorrowerLnameContainingOrBookIsbn(searchWord,searchWord,searchWord,searchWord);
        return historyInfo.stream().map(e -> {
            CheckInResponse res = new CheckInResponse();
            res.setLoanId(e.getLoanId());
            res.setIsbn(e.getBook().getIsbn());
            res.setTitle(e.getBook().getTitle());
            res.setBorrowerId(e.getBorrower().getCardId());
            res.setBorrowerName(e.getBorrower().getFname()+" "+e.getBorrower().getLname());
            res.setBorrowedDate(e.getDateOut());
            res.setDueDate(e.getDueDate());
            res.setCheckInDate(e.getDateIn());
            return res;
        }).toList();

    }
}

