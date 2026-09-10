package com.example.loan;

public class Customer {

    private final String name;
    private final int age;
    private final String governmentId;
    private final double monthlyIncome;
    private final int creditScore;

    public Customer(
            String name,
            int age,
            String governmentId,
            double monthlyIncome,
            int creditScore) {

        this.name = name;
        this.age = age;
        this.governmentId = governmentId;
        this.monthlyIncome = monthlyIncome;
        this.creditScore = creditScore;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGovernmentId() {
        return governmentId;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public int getCreditScore() {
        return creditScore;
    }
}
