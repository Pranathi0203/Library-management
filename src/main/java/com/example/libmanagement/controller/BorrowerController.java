package com.example.libmanagement.controller;

import com.example.libmanagement.entity.Borrower;
import com.example.libmanagement.exception.SsnAlreadyExistsException;
import com.example.libmanagement.service.BorrowerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BorrowerController {
    final BorrowerService borrowerService;
    @PostMapping(path={"/addborrower"})
    @CrossOrigin
    public String addBorrower(@ModelAttribute("borrower") Borrower borrower,Model model) throws SsnAlreadyExistsException{
        try{
            long count=borrowerService.getCount();
            //System.out.println("count:" + count);
            //System.out.println(borrower.getFname());
            int length = (int) (Math.log10(count) + 1);
            count++;
            String cardId="ID";
            for(int i=0;i<6-length;i++){
                cardId += "0";
            }
            cardId+=Long.toString(count);
            borrower.setCardId(cardId);

            borrower = borrowerService.addBorrower(borrower);
            System.out.println(borrower.getCardId());
            model.addAttribute("cardId", borrower.getCardId());
        }
        catch(SsnAlreadyExistsException e){
           return "borrowerexception";
        }
        return "addborrowersuccess";
    }
    @GetMapping(path={"/addborrowerpage"})
    @CrossOrigin
    public String addBorrowerPage() throws Exception{
        System.out.println("inside addBorrowerpage");
        return "addborrower";
    }

}
