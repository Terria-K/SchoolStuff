package org.yourcompany.yourproject;

public class TransactionLog {
    private int id;
    private String type;
    private double money;

    public TransactionLog(int id, String type, double money) {
        this.id = id;
        this.type = type;
        this.money = money;
    }

    public int getID() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getMoney() {
        return money;
    }
}