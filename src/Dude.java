import processing.core.PImage;

import java.util.List;

public abstract class Dude extends MoveEntity implements CanTransform{

    public static final int ACTION_PERIOD_IDX = 0;
    public static final int ANIMATION_PERIOD_IDX = 1;
    public static final int RESOURCE_LIMIT_IDX = 2;
    public static final int NUM_PROPERTIES = 3;
    public static final String KEY = "dude";
    private static final PathingStrategy Dude_PATHING = new
            AStarPathingStrategy();


    protected int resourceLimit;


    public Dude(String id, Point p, List<PImage> i, int rl, double ap, double anp) {
        super(id, p, i, ap, anp, Dude_PATHING);
        this.resourceLimit = rl;
    }

    /*
    public Point nextPosition(WorldModel world, Point destPos) {

        int horiz = Integer.signum(destPos.x - this.position.x);
        Point newPos = new Point(this.position.x + horiz, this.position.y);

        if (horiz == 0 || world.isOccupied(newPos) && !(world.getOccupancyCell(newPos) instanceof Stump)) {
            int vert = Integer.signum(destPos.y - this.position.y);
            newPos = new Point(this.position.x, this.position.y + vert);

            if (vert == 0 || world.isOccupied(newPos) && !(world.getOccupancyCell(newPos) instanceof Stump)) {
                newPos = this.position;
            }
        }
        return newPos;
    }

 */
}

