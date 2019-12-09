import processing.core.PImage;

import java.util.List;

abstract class ActiveEntity extends Entity{

    private int actionPeriod;

    public ActiveEntity(String id, Point position, List<PImage> images, int actionPeriod)
    {
        super(id, position, images);
        this.actionPeriod = actionPeriod;

    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
    }

    protected int getActionPeriod() {return this.actionPeriod; }

    protected Action createActivityAction(
            WorldModel world, ImageStore imageStore)
    {
        return new Activity(this, world, imageStore, 0);
    }

    abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);



}
