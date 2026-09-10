package com.example.loan;

import java.util.ArrayList;
import java.util.List;

public class CreditAssessment {

    public static final double MIN_INCOME =
            30_000.0;

    public static final int MIN_CREDIT_SCORE =
            650;

    public static final double MAX_DTI =
            40.0;

    public static final double LOW_RISK_DTI =
            30.0;

    public static final int LOW_RISK_CREDIT_SCORE =
            750;

    public static final double INCOME_MULTIPLIER =
            12.0;

    public LoanDecision assess(
            LoanApplication application) {

        validate(application);

        Customer customer =
                application.getCustomer();

        // Calculate DTI
        double dti =
                calculateDti(
                        customer.getMonthlyIncome(),
                        application
                                .getExistingMonthlyObligations()
                );

        // Calculate maximum permissible loan
        double maximumLoan =
                Math.max(
                        0.0,
                        customer.getMonthlyIncome()
                                * INCOME_MULTIPLIER
                                - application
                                .getExistingMonthlyObligations()
                );

        List<String> reasons =
                new ArrayList<>();

        // Age check
        if (customer.getAge() < 21) {

            reasons.add(
                    "Customer must be at least 21 years old."
            );
        }

        // Government ID check
        if (customer.getGovernmentId() == null
                || customer.getGovernmentId().isBlank()) {

            reasons.add(
                    "A valid government-issued " +
                    "identification number is required."
            );
        }

        // Income check
        if (customer.getMonthlyIncome()
                < MIN_INCOME) {

            reasons.add(
                    "Monthly income is below " +
                    "the minimum threshold."
            );
        }

        // Credit score check
        if (customer.getCreditScore()
                < MIN_CREDIT_SCORE) {

            reasons.add(
                    "Credit score is below " +
                    "the minimum requirement."
            );
        }

        // DTI check
        if (dti > MAX_DTI) {

            reasons.add(
                    "Debt-to-income ratio exceeds " +
                    "the maximum permitted DTI."
            );
        }

        // Maximum loan check
        if (application.getRequestedAmount()
                > maximumLoan) {

            reasons.add(
                    "Requested loan amount exceeds " +
                    "the maximum permissible loan amount."
            );
        }

        /*
         * If there is any critical failure,
         * reject the application.
         */
        if (!reasons.isEmpty()) {

            return new LoanDecision(
                    customer.getName(),
                    RiskLevel.HIGH_RISK_REJECTED,
                    false,
                    dti,
                    maximumLoan,
                    reasons
            );
        }

        /*
         * Low Risk:
         * Credit score >= 750
         * DTI <= 30%
         */
        RiskLevel risk;

        if (customer.getCreditScore()
                    >= LOW_RISK_CREDIT_SCORE
                && dti <= LOW_RISK_DTI) {

            risk = RiskLevel.LOW_RISK;

        } else {

            risk = RiskLevel.MEDIUM_RISK;
        }

        return new LoanDecision(
                customer.getName(),
                risk,
                true,
                dti,
                maximumLoan,
                reasons
        );
    }

    public double calculateDti(
            double monthlyIncome,
            double monthlyObligations) {

        if (monthlyIncome <= 0) {

            throw new ValidationException(
                    "Monthly income must be greater than zero."
            );
        }

        if (monthlyObligations < 0) {

            throw new ValidationException(
                    "Existing monthly obligations " +
                    "cannot be negative."
            );
        }

        return (
                monthlyObligations
                        / monthlyIncome
        ) * 100.0;
    }

    private void validate(
            LoanApplication application) {

        if (application == null) {

            throw new ValidationException(
                    "Loan application cannot be null."
            );
        }

        Customer customer =
                application.getCustomer();

        if (customer == null) {

            throw new ValidationException(
                    "Customer cannot be null."
            );
        }

        if (customer.getName() == null
                || customer.getName().isBlank()) {

            throw new ValidationException(
                    "Customer name cannot be blank."
            );
        }

        if (customer.getAge() < 0
                || customer.getAge() > 120) {

            throw new ValidationException(
                    "Customer age must be between 0 and 120."
            );
        }

        if (customer.getMonthlyIncome() <= 0) {

            throw new ValidationException(
                    "Monthly income must be greater than zero."
            );
        }

        if (customer.getCreditScore() < 0
                || customer.getCreditScore() > 900) {

            throw new ValidationException(
                    "Credit score must be between 0 and 900."
            );
        }

        if (application.getRequestedAmount() <= 0) {

            throw new ValidationException(
                    "Requested loan amount must be " +
                    "greater than zero."
            );
        }

        if (application.getExistingMonthlyObligations()
                < 0) {

            throw new ValidationException(
                    "Existing monthly obligations " +
                    "cannot be negative."
            );
        }
    }
}
