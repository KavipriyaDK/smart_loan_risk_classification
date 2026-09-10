package com.example.loan;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        CreditAssessment assessment =
                new CreditAssessment();

        List<LoanApplication> applications =
                List.of(

                        // LOW RISK
                        new LoanApplication(
                                new Customer(
                                        "Anita",
                                        35,
                                        "GOV123",
                                        100_000,
                                        800
                                ),
                                800_000,
                                20_000
                        ),

                        // MEDIUM RISK
                        new LoanApplication(
                                new Customer(
                                        "Bala",
                                        21,
                                        "GOV124",
                                        50_000,
                                        680
                                ),
                                400_000,
                                18_000
                        ),

                        // HIGH RISK / REJECTED
                        new LoanApplication(
                                new Customer(
                                        "Chitra",
                                        19,
                                        "",
                                        20_000,
                                        600
                                ),
                                300_000,
                                25_000
                        )
                );

        for (LoanApplication application
                : applications) {

            LoanDecision decision =
                    assessment.assess(application);

            System.out.println(
                    "\nCustomer: "
                    + decision.customerName()
            );

            System.out.println(
                    "Risk Level: "
                    + decision.riskLevel()
            );

            System.out.println(
                    "Approved: "
                    + decision.approved()
            );

            System.out.printf(
                    "DTI: %.2f%%%n",
                    decision.dtiPercent()
            );

            System.out.printf(
                    "Maximum Permissible Loan: %.2f%n",
                    decision.maximumPermissibleLoan()
            );

            if (decision.reasons().isEmpty()) {

                System.out.println(
                        "Reasons: None"
                );

            } else {

                System.out.println(
                        "Reasons:"
                );

                for (String reason
                        : decision.reasons()) {

                    System.out.println(
                            " - " + reason
                    );
                }
            }
        }
    }
}
