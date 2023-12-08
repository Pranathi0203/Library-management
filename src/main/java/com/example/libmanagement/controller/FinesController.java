package com.example.libmanagement.controller;

import com.example.libmanagement.dto.CheckInOrPayFinesRequest;
import com.example.libmanagement.dto.CheckInResponse;
import com.example.libmanagement.dto.FinesResponse;
import com.example.libmanagement.service.BookLoansService;
import com.example.libmanagement.service.FinesService;
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
public class FinesController {
    final BookLoansService bookLoansService;
    final FinesService finesService;

    @GetMapping(path = {"/finespage"})
    public String addFinesPage() throws Exception {
        System.out.println("inside fines Page");
        return "fines";
    }

    @GetMapping(path = {"/searchfines"})
    public String findBookLoans(FinesResponse response, Model model, String keyword) {
        //System.out.println(keyword);
        FinesResponse finesResponses = finesService.searchFines(keyword);
        //System.out.println(finesResponses);
        model.addAttribute("response", finesResponses);
        return "fines";
    }


    @PostMapping(path="/payfines")
    public String payFines(@ModelAttribute("finesRequest") CheckInOrPayFinesRequest payFinesRequest) {
       log.info("Inside payFines -> "+ payFinesRequest.getCardId());
       String msg=finesService.payFines(payFinesRequest.getCardId());
      log.info(msg);
       return "fines";
    }

    @GetMapping(path = {"/updateFines"})
    public String updateFines(Model model) {
        finesService.calculateFines();
        model.addAttribute("successMsg", "Fines Updated Successfully");
        return "searchauthor";
    }

}
