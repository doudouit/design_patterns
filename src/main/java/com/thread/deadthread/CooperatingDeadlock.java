package com.thread.deadthread;

import javax.annotation.concurrent.GuardedBy;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 上面的getImage()和setLocation(Point location)都需要获取两个锁的
 *
 * 并且在操作途中是没有释放锁的
 * 这就是隐式获取两个锁(对象之间协作)..
 *
 * 这种方式也很容易就造成死锁.....
 *
 */
public class CooperatingDeadlock {
    // Warning: deadlock-prone!
    class Taxi {
        @GuardedBy("this") private Point location, destination;
        private final Dispatcher dispatcher;

        public Taxi(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        public synchronized Point getLocation() {
            return location;
        }

        // setLocation 需要Taxi内置锁
        public synchronized void setLocation(Point location) {
            this.location = location;
            if (location.equals(destination))
                // 调用notifyAvailable()需要Dispatcher内置锁
                dispatcher.notifyAvailable(this);
        }

        public synchronized Point getDestination() {
            return destination;
        }

        public synchronized void setDestination(Point destination) {
            this.destination = destination;
        }
    }

    class Dispatcher {
        @GuardedBy("this") private final Set<Taxi> taxis;
        @GuardedBy("this") private final Set<Taxi> availableTaxis;

        public Dispatcher() {
            taxis = new HashSet<Taxi>();
            availableTaxis = new HashSet<Taxi>();
        }

        public synchronized void notifyAvailable(Taxi taxi) {
            availableTaxis.add(taxi);
        }

        // 调用getImage()需要Dispatcher内置锁
        public synchronized Image getImage() {
            Image image = new Image();
            for (Taxi t : taxis)
                // 调用getLocation()需要Taxi内置锁
                image.drawMarker(t.getLocation());
            return image;
        }
    }

    class Image {
        public void drawMarker(Point p) {
        }
    }
}