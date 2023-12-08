package com.example.libmanagement.controller;

import com.example.libmanagement.dto.CheckOutBooksRequest;
import com.example.libmanagement.entity.BookLoans;
import com.example.libmanagement.entity.Borrower;
import com.example.libmanagement.exception.BookNotAvailableException;
import com.example.libmanagement.exception.BooksCheckOutCapacityReached;
import com.example.libmanagement.exception.BorrowerNotExistsException;
import com.example.libmanagement.service.BookLoansService;
import com.example.libmanagement.service.BookService;
import com.example.libmanagement.service.BorrowerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CheckoutController {
    final BorrowerService borrowerService;
    final BookService bookService;
    final BookLoansService bookLoansService;
    @PostMapping(path={"/checkout"})
    public String checkOutBooks(@ModelAttribute("checkoutBookRequest") CheckOutBooksRequest checkoutBookRequest,Model model) throws BookNotAvailableException, BooksCheckOutCapacityReached, BorrowerNotExistsException {
        try {
            //System.out.println("BorrowerId :: " + checkoutBookRequest.getCardId());
            //System.out.println("List of ISBN :" + checkoutBookRequest.getIsbns());
            bookLoansService.addBookLoans(checkoutBookRequest);
        }
        catch(BookNotAvailableException e){
            return "BookNotAvailableException";
        }
        catch(BooksCheckOutCapacityReached e){
            return "BooksCheckOutCapacityReached";
        }
        catch(BorrowerNotExistsException e){
            return "BorrowerNotExists";
        }
        catch (Exception e){
            return "checkout";
        }

        return "checkoutsuccess";
    }
    @GetMapping(path={"/checkoutpage"})
    @CrossOrigin
    public String checkOutPage() throws Exception {
        System.out.println("inside checkoutpage");
        return "checkout";
    }

}
