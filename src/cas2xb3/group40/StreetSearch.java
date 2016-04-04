package cas2xb3.group40;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 */
public class StreetSearch {
    public static void findInput(Network net, String userInput, Intersection[] fromTo) {
        int mode = 0;
        if (userInput.contains("from: ")) {
            mode = 0;
            userInput = userInput.split("from: ")[1];
        } else if (userInput.contains("to: ")) {
            mode = 1;
            userInput = userInput.split("to: ")[1];
        } else {
            System.out.println("invalid input. Input should be \"to:|from: street one and street two\"");
            return;
        }

        String part0;
        String part1;
        try {
            String[] parts = userInput.split(" and | AND ");
            if (parts.length != 2) throw new Exception();
            part0 = parts[0].toUpperCase();
            part1 = parts[1].toUpperCase();
        } catch (Exception e) {
            System.out.println("invalid input. Input should be \"to:|from: street one and street two\"");
            return;
        }

        Intersection match = null;
        int matches = 0;
        String out = "The phrase, " +  "\"" + userInput  + "\", has the following matches\n";
        for (Intersection i: net.iterator()) {
            if (i.toString().contains(part0) && i.toString().contains(part1)) {
                matches++;
                match = i;
                out += "\t" + i.toString() + "\n";
            }
        }
        if (matches == 0) out = "The phrase, " + "\"" + userInput + "\", has no matches\n";
        else if (matches == 1) {
//            System.out.println("assignment");
            System.out.println("Okay. Match found: " + match);
            fromTo[mode] = match;
//            System.out.println("after assignment: " + fromTo[0] + " " + fromTo[1]);
        } else {
            System.out.println(out);
        }
    }

    public static ArrayList<Road> getUserInput(Network net, Camera cam) {
        Dijkstra djkOut = null;
        ArrayList<Road> path = null;
        Scanner input = new Scanner(System.in);
        System.out.print("Input start and destination\nto: street one and street two\nfrom: street three and street four\n> ");
        Intersection[] fromTo = {null, null};
        String inputString = input.nextLine();

        while (!inputString.equals("q")) {
            findInput(net, inputString, fromTo);

            //System.out.println("so far:" + fromTo[0] + " " + fromTo[1]);
            if (fromTo[0] != null && fromTo[1] != null) {
                //System.out.println("got both");
                Dijkstra djk = new Dijkstra(net, fromTo[0].getId());
                if (djk.hasPathTo(fromTo[1].getId())) {
                    //System.out.println("drawing");
                    //fromTo[0].getShape().setFill(Color.RED);
                    //fromTo[1].getShape().setFill(Color.BLUE);
                    djkOut = djk;
                    path = djk.pathTo(fromTo[1].getId());
                    break;
                } else {
                    System.out.println("No route found between \"" + fromTo[0] + "\" and \"" + fromTo[1] + "\"");
                    fromTo[0] = null;
                    fromTo[1] = null;
                }
            }

            System.out.print("> ");
            inputString = input.nextLine();
        }

        if (inputString.equals("q")) System.exit(0);

        return path;
        //return djkOut;
    }
}
