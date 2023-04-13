package com.simplon.rhp.services;

import com.simplon.rhp.entities.PaySlip;
import com.simplon.rhp.repositories.PaySlipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class PaySlipService {


    private final PaySlipRepository paySlipRepository;



    public double calculateNetSalary(double grossSalary, double taxRate, double deductions, double benefits) {
        return grossSalary - (grossSalary * taxRate) - deductions + benefits;
    }


    public double calculateGrossSalary(double netSalary, double taxRate, double deductions, double benefits) {
        return netSalary + (netSalary * taxRate) + benefits - deductions;
    }

    public PaySlip getPaySlipById(Long id) {
        return paySlipRepository.findById(id).orElse(null);
    }
}
