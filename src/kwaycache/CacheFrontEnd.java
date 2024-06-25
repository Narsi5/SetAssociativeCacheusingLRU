package kwaycache;
import java.util.Scanner;

public class CacheFrontEnd {

    private static KWaySetAssociativeCache<Integer, Integer> cache;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Set Size :");
        System.out.print("Enter the Entry Size :");
        int setSize = scanner.nextInt();
        int entrySize = scanner.nextInt();

        initializeCache(setSize,entrySize);


        boolean running = true;

        while (running) {
            displayMenu();

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    performGetOperation(scanner);
                    break;
                case 2:
                    performPutOperation(scanner);
                    break;
                case 3:
                    printCacheContents();
                    break;
                case 4:
                    printMetrics();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("Exiting the cache application.");
        scanner.close();
    }

    private static void initializeCache(Integer setSize,Integer entrySize ) {
        cache = new KWaySetAssociativeCache<>(setSize, entrySize);
        System.out.printf("Cache initialized with %d sets and each set has a capacity of %d.",setSize,entrySize);
    }

    private static void displayMenu() {
        System.out.println("\nCache Operations Menu:");
        System.out.println("1. Get value for a key");
        System.out.println("2. Put key-value pair");
        System.out.println("3. Print cache contents");
        System.out.println("4. Print cache metrics");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void performGetOperation(Scanner scanner) {
        System.out.print("Enter key to get value: ");
        int key = scanner.nextInt();
        Integer value = cache.get(key);
        System.out.println("Value for key " + key + ": " + (value != null ? value : "null"));
    }

    private static void performPutOperation(Scanner scanner) {
        System.out.print("Enter key to put: ");
        int key = scanner.nextInt();
        System.out.print("Enter value to associate with key: ");
        int value = scanner.nextInt();
        cache.put(key, value);
        System.out.println("Key-value pair (" + key + ", " + value + ") added to the cache.");
    }

    private static void printCacheContents() {
        cache.printCacheContents();
    }

    private static void printMetrics() {
        cache.printMetrics();
    }
}
