import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<Point>();
        Queue<Node> openList = new PriorityQueue<Node>(Comparator.comparingInt(Node :: getF));
        Map<Point, Node> closedMap = new HashMap<Point, Node>();
        Map<Point, Node> openMap = new HashMap<Point, Node>();

        Node startNode = new Node(0, manhattanDistance(start, end), manhattanDistance(start, end), start, null);
        Node prior;
        openList.add(startNode);
        openMap.put(start, startNode);

        while (!openList.isEmpty())
        {
            prior = openList.remove();
            openMap.remove(prior.getP());

            if (withinReach.test(prior.getP(), end))
            {
                closedMap.put(prior.getP(), prior);
                return foundPath(path, prior.getP(), closedMap, start);
            }


            List<Point> neighbors = potentialNeighbors.apply(prior.getP()).filter(canPassThrough).collect(Collectors.toList());
            for (Point neighbor : neighbors) {
                if (!closedMap.containsKey(neighbor)) {

                    int gVal = prior.getG() + 1;

                    if (!(openMap.get(neighbor) == null) && (openMap.get(neighbor).getG() > gVal + manhattanDistance(neighbor, end))) {
                        openMap.get(neighbor).setG(gVal);
                        openMap.get(neighbor).setF(gVal + manhattanDistance(neighbor, end));
                    }

                    else if (!openMap.containsKey(neighbor))
                    {
                        Node newNode = new Node(gVal, manhattanDistance(neighbor, end), gVal + manhattanDistance(neighbor, end), neighbor, prior);
                        openList.add(newNode);
                        openMap.put(neighbor, newNode);
                    }
                }
            }
            //System.out.println(openList.size());
            closedMap.put(prior.getP(), prior);
            //System.out.println(closedMap.entrySet());
            //System.out.println("loop");
        }
        return path;
    }

    public List<Point> foundPath(List<Point> path, Point current, Map<Point, Node> closedMap, Point start)
    {
        if (current != start)
        {
            path.add(current);
        }
        //System.out.println("run");
        if (closedMap.get(current).getPrev() == null)
        {
            Collections.reverse(path);
            return path;

        }
        return foundPath(path, closedMap.get(current).getPrev().getP(), closedMap, start);
    }

    public int manhattanDistance(Point p1, Point p2) {return (Math.abs(p2.x -p1.x) + Math.abs(p2.y -p1.y));}

    public class Node {
        int g;
        int h;
        int f;
        Point p;
        Node prev;

        public Node(int g, int h, int f, Point p, Node prev) {
            this.g = g;
            this.h = h;
            this.f = f;
            this.p = p;
            this.prev = prev;
        }

        public int getF() {
            return f;
        }

        public int getG() {
            return g;
        }

        public Point getP() {
            return p;
        }

        public Node getPrev() {
            return prev;
        }

        public void setG(int g) {
            this.g = g;
        }

        public void setF(int f) {
            this.f = f;
        }

    }
}
