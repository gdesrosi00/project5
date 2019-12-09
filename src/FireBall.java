import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class FireBall extends AnimatedEntity {


    public FireBall(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    // Remove miners on contact
    void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fireBallTarget = world.findNearest(this.getPosition(), Miner.class);
        long nextPeriod = this.getActionPeriod();

        if (fireBallTarget.isPresent()) {
            Point tgtPos = fireBallTarget.get().getPosition();

            if (this.moveTo(world, fireBallTarget.get(), scheduler)) {
                AnimatedEntity           }
        }

    }

    private Point nextPosition(WorldModel world, Point destPos) {
        AStarPathingStrategy aStar = new AStarPathingStrategy();
        List<Point> path;
        path = aStar.computePath(this.getPosition(), destPos, canPassThrough(world), withinReach(), PathingStrategy.CARDINAL_NEIGHBORS);

        if (path.size() == 0) {
            return this.getPosition();
        }
        return path.get(0);
    }

    private Predicate<Point> canPassThrough(WorldModel world) {
        return point -> (!world.isOccupied(point) && world.withinBounds(point));
    }

    private BiPredicate<Point, Point> withinReach() { return Point::adjacent; }

    private boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {

        if (Point.adjacent(this.getPosition(), target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        } else {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }

            return false;

        }

    }

}
