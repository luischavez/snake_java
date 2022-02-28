package com.luischavezb.snake;

import java.util.List;

public class CollisionEvent {
    private Collisionable source;
    private List<Collisionable> targets;

    public CollisionEvent(Collisionable source, List<Collisionable> targets) {
        this.source = source;
        this.targets = targets;
    }

    public Collisionable source() {
        return source;
    }

    public List<Collisionable> targets() {
        return targets;
    }
}
