package cas2xb3.group40;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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

        String[] parts = userInput.split("and | AND");
        String part0 = parts[0].toUpperCase();
        String part1 = parts[1].toUpperCase();

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
        System.out.println(out);
        System.out.println("matches: " + matches);
        if (matches == 1) {
            System.out.println("assignment");
            fromTo[mode] = match;
            System.out.println("after assignment: " + fromTo[0] + " " + fromTo[1]);
        }
    }

    public static Dijkstra getUserInput(Network net, Camera cam) {
        Dijkstra djkOut = null;
        Road[] path = null;
        Scanner input = new Scanner(System.in);
        System.out.print("Input start and destination\nto: street one and street two\nfrom: street three and street four\n> ");
        Intersection[] fromTo = {null, null};
        String inputString = input.nextLine();

        while (!inputString.equals("q")) {
            findInput(net, inputString, fromTo);

            System.out.println("so far:" + fromTo[0] + " " + fromTo[1]);
            if (fromTo[0] != null && fromTo[1] != null) {
                System.out.println("got both");
                Dijkstra djk = new Dijkstra(net, fromTo[0].getId());
                if (djk.hasPathTo(fromTo[1].getId())) {
                    System.out.println("drawing");
                    fromTo[0].getShape().setFill(Color.RED);
                    System.out.println("drawing");
                    fromTo[1].getShape().setFill(Color.BLUE);
                    System.out.println("1");
                    cam.zoom(false);
                    System.out.println("2");
                    cam.zoom(false);
                    System.out.println("3");
                    //cam.panTo(net, fromTo[0]);
                    djk.pathTo(fromTo[1].getId());
                    System.out.println("4");
                    djkOut = djk;
                    System.out.println("pre break");
                    break;
                } else {
                    System.out.println("No route found between \"" + fromTo[0] + "\" and \"" + fromTo[1] + "\"");
                }
            }

            System.out.print("> ");
            inputString = input.nextLine();
        }
        System.out.println("post break ->>> " + (djkOut == null));
        //return path;
        return djkOut;
    }
}
