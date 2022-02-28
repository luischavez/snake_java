package com.luischavezb.snake;

public interface Engine extends Tickable {
    long tickInterval();
    boolean mustBeTicked();
    long waitMillis();
    double delta();
    long ticks();
}
