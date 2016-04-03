package cas2xb3.group40;

import java.util.Scanner;

/**
 *
 */
public class StreetSearch {
    public static void findInput(Network net, String userInput) {
        //^(Test strings); input dataset above.
        String[] parts = userInput.split("and | AND");
        String part0 = parts[0].toUpperCase();
        String part1 = parts[1].toUpperCase();

        int matches = 0;
        String out = "The phrase, " +  "\"" + userInput  + "\", has the following matches\n";
        for (Intersection i: net.iterator()) {
            if (i.toString().contains(part0) && i.toString().contains(part1)) {
                matches++;
                out += "\t" + i.toString() + "\n";
            }
        }
        if (matches == 0) out = "The phrase, " + "\"" + userInput + "\", has no matches\n";
        System.out.println(out);
    }

    public static void getUserInput(Network net) {
        Scanner input = new Scanner(System.in);
        System.out.print("> ");
        String inputString = input.nextLine();
        while (!inputString.equals("q")) {
            findInput(net, inputString);
            System.out.print("> ");
            inputString = input.nextLine();
        }
    }
}
