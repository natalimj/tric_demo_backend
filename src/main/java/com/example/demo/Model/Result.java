package com.example.demo.Model;

public class Result {

    private double first;
    private double second;

    public Result(double first, double second) {
        this.first = first;
        this.second = second;
    }
    public Result() {
    }

    public double getFirst() {
        return first;
    }

    public void setFirst(double first) {
        this.first = first;
    }

    public double getSecond() {
        return second;
    }

    public void setSecond(double second) {
        this.second = second;
    }
}
