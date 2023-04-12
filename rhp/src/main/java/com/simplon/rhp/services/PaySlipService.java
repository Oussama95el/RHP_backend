package com.simplon.rhp.services;

import com.simplon.rhp.repositories.PaySlipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
