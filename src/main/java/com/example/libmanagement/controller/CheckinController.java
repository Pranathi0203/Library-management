package com.example.libmanagement.controller;

import com.example.libmanagement.dto.CheckInOrPayFinesRequest;
import com.example.libmanagement.dto.CheckInResponse;
import com.example.libmanagement.service.BookLoansService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CheckinController {
    final BookLoansService bookLoansService;
    @GetMapping(path={"/checkinpage"})
    @CrossOrigin
    public String checkInPage() throws Exception{
        System.out.println("inside checkinpage");
        return "checkin";
    }
    @GetMapping(path={"/searchcheckin"})
    public String findBookLoans(CheckInResponse response, Model model, String keyword){
        List<CheckInResponse> checkInResponses = bookLoansService.returnbooks(keyword);
        model.addAttribute("responses", checkInResponses);
        return "checkin";
    }

    @PostMapping(path={"/checkin"})
    public String checkInBooks(@ModelAttribute("checkinBookRequest") CheckInOrPayFinesRequest checkInRequest) {
        System.out.println("loanId :: " + checkInRequest.getLoanId());
        for (long i: checkInRequest.getLoanId()) {
            bookLoansService.bookCheckIn(i);
        }
        return "checkinsuccess";
    }
    @GetMapping(path={"/historypage"})
    public String historyPage() throws Exception{
        return "borrowerhistory";
    }
    @GetMapping(path={"borrowerhistory"})
    public String historyOfBorrower(CheckInResponse response, Model model, String keyword){
        List<CheckInResponse> checkInResponses = bookLoansService.borrowerHistory(keyword);
        model.addAttribute("responses", checkInResponses);
        return "borrowerhistory";
    }
}
