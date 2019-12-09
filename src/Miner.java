import processing.core.PImage;

import java.util.List;

public abstract class Miner extends AnimatedEntity {

    private int resourceLimit;
    private int resourceCount;

    public Miner (String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    protected int getResourceCount() {
        return resourceCount;
    }

    protected int getResourceLimit() {
        return resourceLimit;
    }

    protected void incResourceCount(){
        this.resourceCount += 1;
    }
}
