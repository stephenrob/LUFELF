package uk.co.stephen_robinson.uni.lufelf.route;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * @author James
 * This Route class is a list that holds the points that make up a route from a to b
 */
public class Route {

    ArrayList<LatLng> route;

    /**
     * initialised the route arrayList
     */
    public Route(){
        route=new ArrayList<LatLng>();
    }

    /**
     * add a point to the route
     * @param point the point to add
     */
    public void addNode(LatLng point){
        route.add(point);
    }

    /**
     * get the route
     * @return Route held by the class
     */
    public ArrayList<LatLng> getRoute(){
        return route;
    }

    public void  combineList(List<LatLng> p){
        route.addAll(p);
    }
}
