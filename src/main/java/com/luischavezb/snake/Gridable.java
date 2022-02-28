package com.luischavezb.snake;

import java.util.List;

public interface Gridable extends Dimensionable {
    List<Entity> entities();
    void set(Entity entity, Positionable positionable) throws PositionOutOfBoundsException;
    void set(Entity entity) throws PositionOutOfBoundsException;
    void remove(Entity entity);
}
