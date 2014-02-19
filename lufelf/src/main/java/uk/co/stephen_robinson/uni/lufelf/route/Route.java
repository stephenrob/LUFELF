package uk.co.stephen_robinson.uni.lufelf.route;

import java.util.ArrayList;
import java.util.List;

/**
 * @author James
 * This Route class is a list that holds the points that make up a route from a to b
 */
public class Route {

    List<Point> route;

    /**
     * initialised the route arrayList
     */
    public Route(){
        route=new ArrayList<Point>();
    }

    /**
     * add a point to the route
     * @param p the point to add
     */
    public void addNode(Point p){
        route.add(p);
    }

    /**
     * get the route
     * @return Route held by the class
     */
    public List getRoute(){
        return route;
    }

}
