package com.luischavezb.snake;

public class Apple implements Capturable {
    private double x;
    private double y;

    @Override
    public long dna() {
        return 0;
    }

    @Override
    public int capture() {
        return 1;
    }

    @Override
    public boolean collides(Collisionable collisionable, Gridable gridable) {
        return true;
    }

    @Override
    public int width() {
        return 20;
    }

    @Override
    public int height() {
        return 20;
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
