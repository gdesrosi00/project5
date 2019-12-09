import processing.core.PImage;

import java.awt.*;
import java.util.List;

public class Smoke extends AnimatedEntity {

    private static final int SMOKE_ANIMATION_REPEAT_COUNT = 1;

    public Smoke(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {

        super(id, position, images, actionPeriod, animationPeriod);

    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.getActionPeriod());

        scheduler.scheduleEvent(this, this.createAnimationAction(SMOKE_ANIMATION_REPEAT_COUNT),
                this.getAnimationPeriod());
    }

}
