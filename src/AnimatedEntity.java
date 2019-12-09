import processing.core.PImage;

import java.util.List;

abstract class AnimatedEntity extends ActiveEntity{

    private int animationPeriod;

    public AnimatedEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;

    }

    public void nextImage() {
        this.setImageIndex( (this.getImageIndex() + 1) % this.getImages().size());
    }

    protected int getAnimationPeriod(){return this.animationPeriod;}

    protected Action createAnimationAction(int repeatCount) {
        return new Animation(this, null, null,
                repeatCount);
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                super.getActionPeriod());
        scheduler.scheduleEvent(this,
                this.createAnimationAction(0),
                this.getAnimationPeriod());
    }


}
