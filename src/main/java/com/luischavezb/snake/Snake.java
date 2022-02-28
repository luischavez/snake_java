package com.luischavezb.snake;

import java.util.ArrayList;
import java.util.List;

public class Snake implements Collisionable, Directionable, Tickable {
    private long dna;

    private Direction direction;

    private double x;
    private double y;

    private Entity child;

    private List<Capturable> capturables;

    private int speed = 40;

    public Snake(Direction direction) {
        dna = System.nanoTime();
        this.direction = direction;

        capturables = new ArrayList<>();
    }

    public Entity child() {
        return child;
    }

    public List<Capturable> capturables() {
        return capturables;
    }

    public int speed() {
        return speed;
    }

    @Override
    public long dna() {
        return dna;
    }

    @Override
    public boolean collides(Collisionable collisionable, Gridable gridable) {
        if (collisionable instanceof Capturable) {
            if (collisionable.dna() == dna) {
                Tail tail = Tail.class.cast(collisionable);
                if ((Direction.RIGHT.equals(tail.direction()) ||Direction.LEFT.equals(tail.direction())) && (Direction.UP.equals(direction) || Direction.DOWN.equals(direction))) {
                    return false;
                }
                if ((Direction.UP.equals(tail.direction()) ||Direction.DOWN.equals(tail.direction())) && (Direction.LEFT.equals(direction) || Direction.RIGHT.equals(direction))) {
                    return false;
                }

                return true;
            }

            capturables.add(Capturable.class.cast(collisionable));

            Point point = null;

            switch (direction) {
                case UP:
                    point = new Point(x, y + height() + 5);
                    break;
                case DOWN:
                    point = new Point(x, y - height() - 5);
                    break;
                case LEFT:
                    point = new Point(x + width() + 5, y);
                    break;
                case RIGHT:
                    point = new Point(x - width() - 5, y);
                    break;
            }

            child = new Tail(dna, this, direction, point);
            gridable.set(child);

            return false;
        }

        return true;
    }

    @Override
    public Direction direction() {
        return direction;
    }

    @Override
    public void direction(Direction direction) {
        if (Direction.UP.equals(direction) && Direction.DOWN.equals(this.direction)) return;
        if (Direction.DOWN.equals(direction) && Direction.UP.equals(this.direction)) return;
        if (Direction.LEFT.equals(direction) && Direction.RIGHT.equals(this.direction)) return;
        if (Direction.RIGHT.equals(direction) && Direction.LEFT.equals(this.direction)) return;

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
        switch (direction) {
            case UP:
                y += (speed * delta) * -1;
                break;
            case DOWN:
                y += speed * delta;
                break;
            case LEFT:
                x += (speed * delta) * -1;
                break;
            case RIGHT:
                x += speed * delta;
                break;
        }

        if (child != null) {
            switch (direction) {
                case UP:
                    child.y(child.y() + (speed * delta) * -1);
                    break;
                case DOWN:
                    child.y(child.y() + (speed * delta));
                    break;
                case LEFT:
                    child.x(child.x() + (speed * delta) * -1);
                    break;
                case RIGHT:
                    child.x(child.x() + (speed * delta));
                    break;
            }
        }
    }
}
