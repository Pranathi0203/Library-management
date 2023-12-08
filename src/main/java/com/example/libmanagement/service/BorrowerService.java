package com.example.libmanagement.service;

import com.example.libmanagement.entity.Borrower;
import com.example.libmanagement.exception.SsnAlreadyExistsException;
import com.example.libmanagement.repository.BorrowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowerService {
    public final BorrowerRepository borrowerRepository;
    public Borrower addBorrower(Borrower borrower) throws SsnAlreadyExistsException{
       // Borrower borrowerSsn=borrowerRepository.findBorrowerBySsn(borrower.getSsn());
        if(borrowerRepository.existsBorrowerBySsn(borrower.getSsn())){
            throw new SsnAlreadyExistsException("Ssn already exists");
        }
        borrowerRepository.save(borrower);
        return borrower;

    }
public long getCount(){
        return borrowerRepository.count();
}
}
