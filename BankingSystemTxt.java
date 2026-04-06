import java.io.*;
import java.util.*;

// Custom Exceptions
class MinBalanceException extends Exception {
    MinBalanceException(String msg) {
        super(msg);
    }
}

class CIDException extends Exception {
    CIDException(String msg) {
        super(msg);
    }
}

class Customer {
    int cid;
    String name;
    double amt;

    Customer(int cid, String name, double amt) {
        this.cid = cid;
        this.name = name;
        this.amt = amt;
    }

    public String toString() {
        return cid + "," + name + "," + amt;
    }
}

public class BankingSystemTxt {
    static final String FILE_NAME = "customers.txt";

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n Menu: ");
            System.out.println("1. Create");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. List all");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = s.nextInt();

            try {
                switch (choice) {
                    case 1:
                        createAccount(s);
                        break;
                    case 2:
                        deposit(s);
                        break;
                    case 3:
                        withdraw(s);
                        break;
                    case 4:
                        displayAll();
                        break;
                    case 5:
                        System.out.println("Exit");
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (choice != 5);

        s.close();
    }

    // Create Account
    static void createAccount(Scanner s) throws Exception {
        System.out.print("Enter CID (1-20): ");
        int cid = s.nextInt();

        if (cid < 1 || cid > 20)
            throw new CIDException("CID must be between 1 and 20!");

        List<Customer> list = readAll();
        for (Customer c : list) {
            if (c.cid == cid) {
                throw new Exception("CID already exists! Use a different ID.");
            }
        }

        s.nextLine();
        System.out.print("Enter Name: ");
        String name = s.nextLine();

        System.out.print("Enter amt: ");
        double amt = s.nextDouble();

        if (amt <= 0)
            throw new ArithmeticException("amt must be positive!");

        if (amt < 1000)
            throw new MinBalanceException("Minimum balance is Rs.1000");

        Customer c = new Customer(cid, name, amt);

        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true));
        bw.write(c.toString());
        bw.newLine();
        bw.close();

        System.out.println("Successful");
    }

    // Deposit
    static void deposit(Scanner s) throws Exception {
        System.out.print("Enter CID: ");
        int cid = s.nextInt();

        System.out.print("Enter deposit amt: ");
        double amt = s.nextDouble();

        if (amt <= 0)
            throw new ArithmeticException("Amount must be positive!");

        List<Customer> list = readAll();

        boolean found = false;
        for (Customer c : list) {
            if (c.cid == cid) {
                c.amt += amt;
                found = true;
            }
        }

        if (!found) {
            System.out.println("Customer not found!");
            return;
        }

        writeAll(list);
        System.out.println("Deposit successful!");
    }

    // Withdraw
    static void withdraw(Scanner s) throws Exception {
        System.out.print("Enter CID: ");
        int cid = s.nextInt();

        System.out.print("Enter withdrawal amt: ");
        double amt = s.nextDouble();

        List<Customer> list = readAll();

        boolean found = false;
        for (Customer c : list) {
            if (c.cid == cid) {

                if (amt > c.amt)
                    throw new ArithmeticException("Insufficient balance!");

                c.amt -= amt;
                found = true;
            }
        }

        if (!found) {
            System.out.println("Customer not found!");
            return;
        }

        writeAll(list);
        System.out.println("Withdrawal successful!");
    }

    // Display
    static void displayAll() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
        String line;

        System.out.println("\nCID | Name | amt");

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            System.out.println(data[0] + " | " + data[1] + " | " + data[2]);
        }

        br.close();
    }

    // Read all customers
    static List<Customer> readAll() throws Exception {
        List<Customer> list = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return list;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            list.add(new Customer(
                Integer.parseInt(data[0]),
                data[1],
                Double.parseDouble(data[2])
            ));
        }

        br.close();
        return list;
    }

    // Rewrite file
    static void writeAll(List<Customer> list) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));

        for (Customer c : list) {
            bw.write(c.toString());
            bw.newLine();
        }

        bw.close();
    }
}