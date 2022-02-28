package com.luischavezb.snake;

public interface Collisionable extends Entity {
    boolean collides(Collisionable collisionable, Gridable gridable);
}
