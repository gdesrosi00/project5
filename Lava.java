import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lava extends ActiveEntity{

    private static final Random rand = new Random();
    private boolean mid;

    public Lava (
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod, boolean mid) {
        super(id, position, images, actionPeriod);
        this.mid = mid;
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        List<Point> points = new ArrayList<>();
        points.add(new Point(super.getPosition().x + 2, super.getPosition().y));
        points.add(new Point(super.getPosition().x - 2, super.getPosition().y));
        points.add(new Point(super.getPosition().x, super.getPosition().y + 2));
        points.add(new Point(super.getPosition().x + 2, super.getPosition().y - 2));
        System.out.println("exAct");
        if (this.isMid()) {
            // Fireball fireball = new Fireball(points.get(rand), )
            //if point is not occupied, add fireball to world
            Point randomPoint = points.get(rand.nextInt(4));
            if(!world.isOccupied(randomPoint))
            {
                System.out.println("executed");
                Entity fireball = new FireBall("fireBall", randomPoint, imageStore.getImageList("fireBall"), 50, 50);
                world.addEntity(fireball);
                ((FireBall)fireball).scheduleActions(scheduler, world, imageStore);
            }

        }
        this.scheduleActions(scheduler, world, imageStore);
    }

    public boolean isMid()
    {
        return this.mid;
    }
}