package com.luischavezb.snake;

import java.util.ArrayList;
import java.util.List;

public final class Collisions {
    public static boolean collision(Collisionable entity, Positionable positionable) {
        double x1 = entity.x();
        double y1 = entity.y();
        int width1 = entity.width();
        int height1 = entity.height();

        double x2 = positionable.x();
        double y2 = positionable.y();

        return ((x2 >= x1 && x2 <= (x1 + width1)) && (y2 >= y1 && y2 <= (y1 + height1)));
    }

    public static boolean collision(Collisionable e1, Collisionable e2) {
        if (e1 == e2) return false;

        double x1 = e1.x();
        double y1 = e1.y();
        int width1 = e1.width();
        int height1 = e1.height();

        double x2 = e2.x();
        double y2 = e2.y();
        int width2 = e2.width();
        int height2 = e2.height();

        return ((x1 >= x2 && x1 <= (x2 + width2)) && (y1 >= y2 && y1 <= (y2 + height2)))
                || ((x2 >= x1 && x2 <= (x1 + width1)) && (y2 >= y1 && y2 <= (y1 + height1)));
    }

    public static List<Collisionable> collision(Collisionable c1, List<Collisionable> collisionables) {
        ArrayList<Collisionable> collisions = new ArrayList<>();

        for (Collisionable c2 : collisionables) {
            if (collision(c1, c2)) {
                collisions.add(c2);
            }
        }

        return collisions;
    }
}
