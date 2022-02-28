package com.luischavezb.snake;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class World implements Engine, Gridable {
    private int ticksPerSecond;

    private long lastTick;
    private long pendingTicks;

    private List<Entity> entities;

    private WorldThread worldThread;

    private Consumer<Double> onTick;

    private int width;
    private int height;

    public World(int ticksPerSecond, int width, int height, Consumer<Double> onTick) {
        this.ticksPerSecond = ticksPerSecond;

        this.width = width;
        this.height = height;

        this.onTick = onTick;

        lastTick = System.currentTimeMillis();
        pendingTicks = 0;

        entities = new ArrayList<>();

        worldThread = new WorldThread();
        worldThread.start();
    }

    @Override
    public long tickInterval() {
        long interval = 1_000 / ticksPerSecond;

        return interval;
    }

    @Override
    public boolean mustBeTicked() {
        long interval = tickInterval();
        long currentTime = System.currentTimeMillis();

        long diff = currentTime - lastTick;

        if (diff >= interval) {
            pendingTicks += Math.ceil(diff / interval);
        }

        return pendingTicks > 0;
    }

    @Override
    public long waitMillis() {
        long interval = tickInterval();
        long currentTime = System.currentTimeMillis();

        long diff = currentTime - lastTick;

        if (diff <= interval) {
            return diff;
        }

        return 0;
    }

    @Override
    public double delta() {
        long currentTime = System.currentTimeMillis();
        long diff = currentTime - lastTick;

        double delta = diff / 1_000.0;

        return delta;
    }

    @Override
    public long ticks() {
        long ticks = pendingTicks;
        pendingTicks = 0;

        return ticks;
    }

    @Override
    public void tick(double delta) {
        //System.out.printf("Delta: %f\n", delta);

        entities.stream()
                .filter(entity -> entity instanceof Tickable)
                .map(entity -> Tickable.class.cast(entity))
                .forEach(tickable -> tickable.tick(delta()));

        List<Collisionable> collisionables = entities.stream()
                .filter(entity -> entity instanceof Collisionable)
                .map(entity -> Collisionable.class.cast(entity))
                .collect(Collectors.toList());

        collisionables.stream()
                .map(collisionable -> new CollisionEvent(collisionable, Collisions.collision(collisionable, collisionables)))
                .forEach(collisionEvent -> {
                    collisionEvent.targets().forEach(collisionable -> {
                        if (collisionEvent.source().collides(collisionable, this)) {
                            entities.remove(collisionEvent.source());
                        }
                    });
                });

        if (onTick != null) {
            onTick.accept(delta());
        }
    }

    @Override
    public List<Entity> entities() {
        return entities;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public void set(Entity entity, Positionable positionable) throws PositionOutOfBoundsException {
        if (positionable.x() > width() || positionable.x() < 0) throw new PositionOutOfBoundsException(positionable);
        if (positionable.y() > height() || positionable.y() < 0) throw new PositionOutOfBoundsException(positionable);

        entity.x(positionable.x());
        entity.y(positionable.y());

        entities.add(entity);
    }

    @Override
    public void set(Entity entity) throws PositionOutOfBoundsException {
        if (entity.x() > width() || entity.x() < 0) throw new PositionOutOfBoundsException(entity);
        if (entity.y() > height() || entity.y() < 0) throw new PositionOutOfBoundsException(entity);

        entities.add(entity);
    }

    @Override
    public void remove(Entity entity) {
        entities.remove(entity);
    }

    private class WorldThread extends Thread implements Runnable {
        public WorldThread() {
            super("World Thread");
        }

        @Override
        public void run() {
            while (true) {
                long waitMillis = World.this.waitMillis();

                if (waitMillis > 0) {
                    //System.out.printf("Waiting: %d milliseconds\n", waitMillis);
                    try {
                        Thread.sleep(waitMillis);
                    } catch (InterruptedException ex) {
                        // TODO: HANDLE
                    }
                }

                if (!World.this.mustBeTicked()) {
                    //System.out.println("The world must not be ticked");
                    continue;
                }

                long ticks = World.this.ticks();

                //System.out.printf("Executing ticks: %d\n", ticks);

                for (int i = 0; i < ticks; i++) {
                    World.this.tick(World.this.delta());
                    World.this.lastTick = System.currentTimeMillis();
                }
            }
        }
    }
}
