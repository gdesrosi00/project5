import processing.core.PImage;

import java.util.List;

abstract public class Entity
{
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;


    public Entity(String id, Point position, List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }

    public PImage getCurrentImage() {
        return (this.images.get(this.imageIndex));
    }

    protected String getId() {return this.id;}

    protected List<PImage> getImages(){return this.images;}

    protected int getImageIndex(){return this.imageIndex;}

    protected Point getPosition() {return this.position;}

    protected void setPosition(Point pos) {this.position = pos;}

    protected void setImageIndex(int imageIndex) {this.imageIndex = imageIndex;}
}
