package com.example.loan;

public class LoanApplication {

    private final Customer customer;
    private final double requestedAmount;
    private final double existingMonthlyObligations;

    public LoanApplication(
            Customer customer,
            double requestedAmount,
            double existingMonthlyObligations) {

        this.customer = customer;
        this.requestedAmount = requestedAmount;
        this.existingMonthlyObligations =
                existingMonthlyObligations;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public double getExistingMonthlyObligations() {
        return existingMonthlyObligations;
    }
}
