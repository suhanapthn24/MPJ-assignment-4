import java.io.*;
import java.util.*;

// Custom Exceptions
class MinimumBalanceException extends Exception {
    MinimumBalanceException(String msg) {
        super(msg);
    }
}

class InvalidCIDException extends Exception {
    InvalidCIDException(String msg) {
        super(msg);
    }
}

class Customer {
    int cid;
    String cname;
    double amount;

    Customer(int cid, String cname, double amount) {
        this.cid = cid;
        this.cname = cname;
        this.amount = amount;
    }

    public String toString() {
        return cid + "," + cname + "," + amount;
    }
}

public class BankingSystemTxt {
    static final String FILE_NAME = "customers.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Banking Menu ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Display All");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        createAccount(sc);
                        break;
                    case 2:
                        deposit(sc);
                        break;
                    case 3:
                        withdraw(sc);
                        break;
                    case 4:
                        displayAll();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (choice != 5);

        sc.close();
    }

    // Create Account
    static void createAccount(Scanner sc) throws Exception {
        System.out.print("Enter CID (1-20): ");
        int cid = sc.nextInt();

        if (cid < 1 || cid > 20)
            throw new InvalidCIDException("CID must be between 1 and 20!");

        List<Customer> list = readAll();
        for (Customer c : list) {
            if (c.cid == cid) {
                throw new Exception("CID already exists! Use a different ID.");
            }
        }

        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Amount: ");
        double amt = sc.nextDouble();

        if (amt <= 0)
            throw new ArithmeticException("Amount must be positive!");

        if (amt < 1000)
            throw new MinimumBalanceException("Minimum balance is Rs.1000!");

        Customer c = new Customer(cid, name, amt);

        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true));
        bw.write(c.toString());
        bw.newLine();
        bw.close();

        System.out.println("Account created successfully!");
    }

    // Deposit
    static void deposit(Scanner sc) throws Exception {
        System.out.print("Enter CID: ");
        int cid = sc.nextInt();

        System.out.print("Enter deposit amount: ");
        double amt = sc.nextDouble();

        if (amt <= 0)
            throw new ArithmeticException("Amount must be positive!");

        List<Customer> list = readAll();

        boolean found = false;
        for (Customer c : list) {
            if (c.cid == cid) {
                c.amount += amt;
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
    static void withdraw(Scanner sc) throws Exception {
        System.out.print("Enter CID: ");
        int cid = sc.nextInt();

        System.out.print("Enter withdrawal amount: ");
        double amt = sc.nextDouble();

        List<Customer> list = readAll();

        boolean found = false;
        for (Customer c : list) {
            if (c.cid == cid) {

                if (amt > c.amount)
                    throw new ArithmeticException("Insufficient balance!");

                c.amount -= amt;
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

        System.out.println("\nCID | Name | Amount");

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