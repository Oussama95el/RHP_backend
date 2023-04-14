package com.simplon.rhp.services;

import com.simplon.rhp.dto.PaySlipResponse;
import com.simplon.rhp.dto.RegisterPaySlip;
import com.simplon.rhp.entities.PaySlip;
import com.simplon.rhp.repositories.PaySlipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<PaySlipResponse> getAllPaySlipsByEmployeeId(Long id) {
        try {
            List<PaySlip> paySlips = paySlipRepository.findAllByEmployeeId(id);
            List<PaySlipResponse> resp = paySlips.stream().map(paySlip -> {
                return PaySlipResponse.builder()
                        .id(paySlip.getId())
                        .date(paySlip.getDate())
                        .month(paySlip.getMonth())
                        .grossSalary(paySlip.getGrossSalary())
                        .benefits(paySlip.getBenefits())
                        .taxRate(paySlip.getTaxRate())
                        .taxAmount(paySlip.getTaxAmount())
                        .deductions(paySlip.getDeductions())
                        .overtimeHours(paySlip.getOvertimeHours())
                        .userEmail(paySlip.getEmployee().getUser().getEmail())
                        .build();
            }).collect(Collectors.toList());
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
