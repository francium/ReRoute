package cas2xb3.group40;

import java.io.*;

/**
 *
 */
public class Util {
    private static final String SAVE_FILENAME = "data/save.ser";

    public static Network load() {
        Network network;
        try {
            FileInputStream filein = new FileInputStream(SAVE_FILENAME);
            ObjectInputStream in = new ObjectInputStream(filein);
            network = (Network) in.readObject();
            in.close();
            filein.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return network;
    }

    /**
     * Save a network
     * @param network network object
     * @return successful save
     */
    public static boolean save(Network network) {
        try {
            FileOutputStream fileout = new FileOutputStream(SAVE_FILENAME);
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            try {
                out.writeObject(network);
            } catch (Exception e) {
                System.out.println("got an ol' exception");
                e.printStackTrace();
            }
            out.close();
            fileout.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
