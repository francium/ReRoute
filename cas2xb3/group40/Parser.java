package cas2xb3.group40;

import java.io.File;
import java.util.Scanner;

public class Parser {

    private final String FILENAME = "data/Intersections.csv";

    private Scanner input;
    private String[][] data;
    private int line;

    public Parser() {
        try {
            input = new Scanner(new File(FILENAME));
            int c = 0;
            while (input.hasNext()) {
                input.nextLine();
                c++;
            }

            data = new String[c-1][];

            input = new Scanner(new File(FILENAME));
            input.nextLine(); // ignore first line with headers
            int l = 0;
            while (input.hasNext()) {
                data[l++] = input.nextLine().split("\\)\\\",|,\\\"\\(|, |,| AND ");
            }
            input.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        line = 0;
    }

    /**
     * @return single lines data
     */
    public String[] readLine() {
        return data[line++];
    }

    /**
     * @return boolean has more lines left to read
     */
    public boolean hasNext() {
        return line < data.length;
    }

    /**
     * Move to first line of data
     */
    public void reset() {
        line = 0;
    }

    public int numLines() {
        return data.length;
    }

}
