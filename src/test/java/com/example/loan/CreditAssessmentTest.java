package com.example.loan;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreditAssessmentTest {

    private final CreditAssessment assessment =
            new CreditAssessment();

    @Test
    void normalLowRiskCustomerIsApproved() {

        Customer customer =
                new Customer(
                        "Asha",
                        30,
                        "GOV001",
                        100_000,
                        800
                );

        LoanApplication application =
                new LoanApplication(
                        customer,
                        700_000,
                        20_000
                );

        LoanDecision result =
                assessment.assess(application);

        assertTrue(
                result.approved()
        );

        assertEquals(
                RiskLevel.LOW_RISK,
                result.riskLevel()
        );
    }

    @Test
    void boundaryAgeExactly21IsAccepted() {

        Customer customer =
                new Customer(
                        "Bala",
                        21,
                        "GOV002",
                        50_000,
                        700
                );

        LoanApplication application =
                new LoanApplication(
                        customer,
                        400_000,
                        20_000
                );

        LoanDecision result =
                assessment.assess(application);

        assertTrue(
                result.approved()
        );
    }

    @Test
    void minimumCreditScoreIsAccepted() {

        Customer customer =
                new Customer(
                        "Chitra",
                        25,
                        "GOV003",
                        50_000,
                        650
                );

        LoanApplication application =
                new LoanApplication(
                        customer,
                        300_000,
                        10_000
                );

        LoanDecision result =
                assessment.assess(application);

        assertTrue(
                result.approved()
        );

        assertEquals(
                RiskLevel.MEDIUM_RISK,
                result.riskLevel()
        );
    }

    @Test
    void maximumDtiExactly40PercentIsAccepted() {

        Customer customer =
                new Customer(
                        "Dinesh",
                        25,
                        "GOV004",
                        50_000,
                        700
                );

        LoanApplication application =
                new LoanApplication(
                        customer,
                        300_000,
                        20_000
                );

        LoanDecision result =
                assessment.assess(application);

        assertTrue(
                result.approved()
        );

        assertEquals(
                40.0,
                result.dtiPercent(),
                0.001
        );
    }

    @Test
    void multipleCriticalFailuresAreAllReported() {

        Customer customer =
                new Customer(
                        "Esha",
                        20,
                        "",
                        20_000,
                        600
                );

        LoanApplication application =
                new LoanApplication(
                        customer,
                        1_000_000,
                        25_000
                );

        LoanDecision result =
                assessment.assess(application);

        assertFalse(
                result.approved()
        );

        assertEquals(
                RiskLevel.HIGH_RISK_REJECTED,
                result.riskLevel()
        );

        /*
         * Age
         * Government ID
         * Income
         * Credit score
         * Loan amount
         *
         * Total = 5
         */
        assertEquals(
                5,
                result.reasons().size()
        );
    }

    @Test
    void amountAboveMaximumIsRejected() {

        Customer customer =
                new Customer(
                        "Farah",
                        30,
                        "GOV006",
                        50_000,
                        700
                );

        LoanApplication application =
                new LoanApplication(
                        customer,
                        600_001,
                        10_000
                );

        LoanDecision result =
                assessment.assess(application);

        assertFalse(
                result.approved()
        );

        assertTrue(
                result.reasons()
                        .stream()
                        .anyMatch(
                                reason ->
                                        reason.contains(
                                                "maximum permissible"
                                        )
                        )
        );
    }

    @Test
    void invalidIncomeThrowsException() {

        Customer customer =
                new Customer(
                        "Gita",
                        30,
                        "GOV007",
                        0,
                        700
                );

        LoanApplication application =
                new LoanApplication(
                        customer,
                        100_000,
                        0
                );

        assertThrows(
                ValidationException.class,
                () -> assessment.assess(
                        application
                )
        );
    }

    @Test
    void invalidCreditScoreThrowsException() {

        Customer customer =
                new Customer(
                        "Hari",
                        30,
                        "GOV008",
                        50_000,
                        901
                );

        LoanApplication application =
                new LoanApplication(
                        customer,
                        100_000,
                        0
                );

        assertThrows(
                ValidationException.class,
                () -> assessment.assess(
                        application
                )
        );
    }
}
