import java.io.*;
import java.util.Scanner;

public class FileIODemo {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            FileWriter writer = new FileWriter("sample.txt");

            System.out.println("Enter text (type 'exit' to stop):");

            while (true) {
                String input = sc.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    break;
                }

                writer.write(input + "\n");
            }

            writer.close();
            System.out.println("Data written to file successfully.");

        } catch (IOException e) {
            System.out.println("Error while writing to file: " + e.getMessage());
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("sample.txt"));

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

        finally {
            System.out.println("\nProgram execution completed.");
        }

        sc.close();
    }
}