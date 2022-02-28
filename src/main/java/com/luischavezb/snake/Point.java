package com.luischavezb.snake;

public class Point implements Positionable {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public void x(double x) {
        this.x = x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public void y(double y) {
        this.y = y;
    }
}
