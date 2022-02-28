package com.luischavezb.snake;

public class PositionOutOfBoundsException extends RuntimeException {
    public PositionOutOfBoundsException(Positionable positionable, Throwable cause) {
        super(String.format("Invalid index: %d:%d", positionable.x(), positionable.y()), cause);
    }
    public PositionOutOfBoundsException(Positionable positionable) {
        this(positionable, null);
    }
}
