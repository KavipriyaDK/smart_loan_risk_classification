package com.example.loan;

import java.util.List;

public record LoanDecision(
        String customerName,
        RiskLevel riskLevel,
        boolean approved,
        double dtiPercent,
        double maximumPermissibleLoan,
        List<String> reasons) {

    public LoanDecision {
        reasons = List.copyOf(reasons);
    }
}
