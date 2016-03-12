package cas2xb3.group40;

import java.io.File;
import java.util.Scanner;

public class Parser {

    private final String FILENAME = "Intersections.csv";

    private Scanner input;

    public Parser() {
        try {
            input = new Scanner(new File(FILENAME);
            input.nextLine(); // ignore first line with headers
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        input.close();
    }

    public String[] readLine() {
        if (input.hasNext()) {
            return input.nextLine().split("\\)\\\",|,\\\"\\(|, |,| AND ");
        } else {
            return null;
        }
    }

    public boolean hasNext() {
        return input.hasNext();
    }

}
