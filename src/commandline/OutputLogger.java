package commandline;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class OutputLogger {

    private File file;


    public OutputLogger() {

        file = new File("toptrumps.log");

        try {
            if (file.createNewFile()) {
            } else {
                clearFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void clearFile() {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void printToLog(String s) {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print(s);
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
