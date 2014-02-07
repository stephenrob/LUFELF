package uk.co.stephen_robinson.uni.lufelf.route;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 06/02/2014.
 */
public class Route {

    List<Point> route;


    public Route(){
        route=new ArrayList<Point>();
    }
    public void addNode(Point p){
        route.add(p);
    }
    public List getRoute(){
        return route;
    }

}
