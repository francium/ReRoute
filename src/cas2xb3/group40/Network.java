package cas2xb3.group40;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.Serializable;

public class Network implements Serializable {

    private Intersection[] intsecs;
    private Intersection[] intsecsSortedStreet, intsecsSortedX, intsecsSortedY;
    private int nV;
    private int nE;

    public Network(int cap){
        intsecs = new Intersection[cap];
    }

    public void addIntersection(Intersection i) {
        intsecs[i.getId()] = i;
        nV++;
    }

    public Intersection get(int i) {
        return intsecs[i];
    }

    public void sort() {
        intsecsSortedStreet = intsecs.clone();
        intsecsSortedX = intsecs.clone();
        intsecsSortedY = intsecs.clone();

        MergeSort.sort(intsecsSortedStreet, Sortable.STREET);
        MergeSort.sort(intsecsSortedX, Sortable.X);
        MergeSort.sort(intsecsSortedY, Sortable.Y);
    }

    public Intersection[] iterator() {
        return intsecs.clone();
    }

    public Intersection[] iteratorSortedStreet() {
        return intsecsSortedStreet.clone();
    }

    public Intersection[] iteratorSortedX() {
        return intsecsSortedStreet.clone();
    }

    public Intersection[] iteratorSortedY() {
        return intsecsSortedStreet.clone();
    }

    public int V(){
        //Returns the number of vertices in the graph.
        return nV;
    }

    public int E(){
        //Returns the number of edges in the graph.
        return nE;
    }

    public Intersection[] findClosest(String street, Intersection intsec) {
        Intersection first = null;
        double firstDist = Double.POSITIVE_INFINITY;
        Intersection second = null;

        for (int i=1; i<intsecs.length; i++) {
            double dist = distTo(intsec, intsecs[i]);
            boolean isSame = (intsec == intsecs[i]);
            boolean isStreet = intsecs[i].getStreets()[0].equals(street) || intsecs[i].getStreets()[1].equals(street);

            if (dist < firstDist && !isSame && isStreet && dist < 0.005) {
                second = first;
                first = intsecs[i];
                firstDist = dist;
            }
        }

        if (first == null) return new Intersection[] {};
        if (second == null) return new Intersection[] {first};
        return new Intersection[] {first, second};
    }

    public static double distTo(Intersection i, Intersection j) {
        double x1 = i.getX();
        double y1 = i.getY();
        double x2 = j.getX();
        double y2 = j.getY();
        return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
    }

    public static void buildNetwork(Network net, Parser p) {
        for (int i = 0; i < p.numLines(); i++) {
            String[] data = p.readLine();
            Intersection a = new Intersection(data[8], data[9],
                    Math.abs(Double.parseDouble(data[12]) ),
                    Math.abs(Double.parseDouble(data[11]) ) );
            a.setVisible(false);
            net.addIntersection(a);
        }

        for (Intersection i: net.iterator()) {
            Intersection[] closest1 = net.findClosest(i.getStreets()[0], i);
            Intersection[] closest2 = net.findClosest(i.getStreets()[1], i);
            adjacentLogic(net, i, closest1);
            adjacentLogic(net, i, closest2);
        }
    }

    private static void adjacentLogic(Network net, Intersection i, Intersection[] closest) {
        if (closest.length == 0) return;

        double dist12 = net.distTo(i,closest[0]);

        if (closest.length > 1) {
            double dist13 = net.distTo(i, closest[1]);
            double dist23 = net.distTo(closest[0], closest[1]);

            if (dist13 < dist23) {
                i.addAdjacent(closest[0]);
                i.addAdjacent(closest[1]);
            } else if (dist12 > dist23) {
                if (dist12 < dist13) {
                    i.addAdjacent(closest[0]);
                } else{
                    i.addAdjacent(closest[1]);
                }
            }
        } else {
            i.addAdjacent(closest[0]);
            return;
        }

    }

    public static void assignNetwork(Network net, Network netLoad) {
        netLoad.intsecs = net.intsecs;
        netLoad.nE = net.nE;
        netLoad.nV = net.nV;
        for (Intersection i: netLoad.intsecs) {
            i.newCircle();
        }
    }


}
