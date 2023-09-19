import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Print {
    static PrintStream printStream;

    {
        try {
            printStream = new PrintStream(new File("output.txt"));
            System.setOut(printStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
