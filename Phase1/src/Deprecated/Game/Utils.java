package Deprecated.Game;

import java.io.*;

/**
 * Created by nibbla on 14.03.16.
 */
public class Utils {

    static String readFile(String path){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String everything = "";
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return everything;
    }

    static void saveFile(String path, String content){
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter(path));
            writer.write(content);

        }
        catch ( IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if ( writer != null)
                    writer.close( );
            }
            catch ( IOException e)
            {
            }
        }
    }
}
