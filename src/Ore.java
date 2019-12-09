import processing.core.PImage;

import java.util.List;

import java.util.Random;

public class Ore extends ActiveEntity{

    private static final Random rand = new Random();
    private static final String BLOB_KEY = "blob";
    private static final String BLOB_ID_SUFFIX = " -- blob";
    private static final int BLOB_PERIOD_SCALE = 4;
    private static final int BLOB_ANIMATION_MIN = 50;
    private static final int BLOB_ANIMATION_MAX = 150;

    public Ore (
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod) {
        super(id, position, images, actionPeriod);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Point pos = super.getPosition();

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Entity blob = new OreBlob(super.getId() + BLOB_ID_SUFFIX, pos, imageStore.getImageList(BLOB_KEY),
                this.getActionPeriod() / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN + rand.nextInt(
                        BLOB_ANIMATION_MAX
                                - BLOB_ANIMATION_MIN));

        world.addEntity(blob);
        ((OreBlob)blob).scheduleActions(scheduler, world, imageStore);
    }

}
