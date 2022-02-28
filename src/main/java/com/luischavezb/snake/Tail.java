package com.luischavezb.snake;

import java.util.ArrayDeque;
import java.util.Queue;

public class Tail implements Capturable, Directionable, Tickable {
    private long dna;

    private Direction direction;

    private Queue<Point> points;

    private double x;
    private double y;

    private Entity parent;
    private Entity child;

    public Tail(long dna, Entity parent, Direction direction, Positionable positionable) {
        this.dna = dna;

        this.parent = parent;

        this.direction = direction;

        points = new ArrayDeque<>();

        x = positionable.x();
        y = positionable.y();
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public Entity parent() {
        return parent;
    }

    public Entity child() {
        return child;
    }

    public void child(Entity child) {
        this.child = child;
    }

    @Override
    public long dna() {
        return dna;
    }

    @Override
    public int capture() {
        return 1;
    }

    @Override
    public boolean collides(Collisionable collisionable, Gridable gridable) {
        if (collisionable.dna() == dna) return false;

        return true;
    }

    @Override
    public Direction direction() {
        return direction;
    }

    @Override
    public void direction(Direction direction) {
        this.direction = direction;
    }

    @Override
    public int width() {
        return 10;
    }

    @Override
    public int height() {
        return 10;
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

    @Override
    public void tick(double delta) {

    }
}
