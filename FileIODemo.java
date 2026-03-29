import java.io.*;

public class FileIODemo {
    public static void main(String[] args) {

        // Writing to a file
        try {
            FileWriter writer = new FileWriter("sample.txt");
            writer.write("Hello, this is a File I/O example in Java.\n");
            writer.write("Learning Exception Handling!");
            writer.close();

            System.out.println("Data written to file successfully.");

        } catch (IOException e) {
            System.out.println("Error while writing to file: " + e.getMessage());
        }

        // Reading from a file
        try {
            FileReader reader = new FileReader("sample.txt");
            BufferedReader br = new BufferedReader(reader);

            String line;
            System.out.println("\nReading from file:");

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());

        } catch (IOException e) {
            System.out.println("Error while reading file: " + e.getMessage());
        }

        // Finally block example
        finally {
            System.out.println("\nProgram execution completed.");
        }
    }
}