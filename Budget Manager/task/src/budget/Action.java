package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class Action {
    private Scanner scanner = new Scanner(System.in);
    private Map<String, Double> mapAll = new LinkedHashMap<>();
    private Map<String, Double> mapFood = new LinkedHashMap<>();
    private Map<String, Double> mapClothes = new LinkedHashMap<>();
    private Map<String, Double> mapEntertainment = new LinkedHashMap<>();
    private Map<String, Double> mapOther = new LinkedHashMap<>();

    private double total = 0;

    public void start() {
        while (true) {
            System.out.println("Choose your action:\n" +
                    "1) Add income\n" +
                    "2) Add purchase\n" +
                    "3) Show list of purchases\n" +
                    "4) Balance\n" +
                    "5) Save\n" +
                    "6) Load\n" +
                    "7) Analyze (Sort)\n" +
                    "0) Exit");
            int action = scanner.nextInt();
            scanner.nextLine();
            switch (action) {
                case 1:
                    addIncome();
                    break;
                case 2:
                    addPurchase();
                    break;
                case 3:
                    showListOfPurchases();
                    break;
                case 4:
                    balance();
                    break;
                case 5:
                    save();
                    break;
                case 6:
                    load();
                    break;
                case 7:
                    analyze();
                    break;
                default:
                    exit();
                    return;
            }
        }
    }

    private void analyze() {
        while (true) {
            System.out.println("\nHow do you want to sort?\n" +
                    "1) Sort all purchases\n" +
                    "2) Sort by type\n" +
                    "3) Sort certain type\n" +
                    "4) Back");
            int type = scanner.nextInt();
            scanner.nextLine();
            switch (type) {
                case 1:
                    sortAll();
                    break;
                case 2:
                    sortByType();
                    break;
                case 3:
                    sortCertainType();
                    break;
                default:
                    System.out.println();
                    return;
            }
        }
    }

    private void sortAll() {
        if (mapAll.size() == 0) {
            System.out.println("\nThe purchase list is empty!");
        } else {
            sort(mapAll);
        }
    }

    private void sortCertainType() {
        System.out.println("\nChoose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other");
        int type = scanner.nextInt();
        scanner.nextLine();
        switch (type) {
            case 1:
                if (mapFood.size() == 0) {
                    System.out.println("\nThe purchase list is empty!\n");
                } else {
                    sort(mapFood);
                }
                break;
            case 2:
                if (mapClothes.size() == 0) {
                    System.out.println("\nThe purchase list is empty!\n");
                } else {
                    sort(mapClothes);
                }
                break;
            case 3:
                if (mapEntertainment.size() == 0) {
                    System.out.println("\nThe purchase list is empty!\n");
                } else {
                    sort(mapEntertainment);
                }
                break;
            case 4:
                if (mapOther.size() == 0) {
                    System.out.printf("\nThe purchase list is empty!\n");
                } else {
                    sort(mapOther);
                }
                break;
            default:
                return;
        }
    }

    private void sort(Map<String, Double> map) {
        double total = 0;
        List<Purchase> list = new ArrayList<>();
        for (var entry: map.entrySet()) {
            list.add(new Purchase(entry.getKey(), entry.getValue()));
            total += entry.getValue();
        }
        Collections.sort(list);
        System.out.println();
        for (Purchase purchase: list) {
            System.out.printf("%s $%.2f\n", purchase.getKey(), purchase.getValue());
        }
        System.out.printf("Total sum: $%.2f\n", total);
    }


    private void sortByType() {
        List<Purchase> list = new ArrayList<>();
        double total = 0;
        double value = 0;

        for (var entry: mapFood.entrySet()) {
            value += entry.getValue();
        }
        list.add(new Purchase("Food", value));
        total += value;
        value = 0;

        for (var entry: mapClothes.entrySet()) {
            value += entry.getValue();
        }
        list.add(new Purchase("Clothes", value));
        total += value;
        value = 0;

        for (var entry: mapEntertainment.entrySet()) {
            value += entry.getValue();
        }
        list.add(new Purchase("Entertainment", value));
        total += value;
        value = 0;

        for (var entry: mapOther.entrySet()) {
            value += entry.getValue();
        }
        total += value;
        list.add(new Purchase("Other", value));

        Collections.sort(list);

        System.out.println("\nTypes: ");
        for (Purchase purchase: list) {
            if (purchase.getValue() == 0) {
                System.out.printf("%s - $0\n", purchase.getKey());
            } else {
                System.out.printf("%s - $%.2f\n", purchase.getKey(), purchase.getValue());
            }
        }
        System.out.println("Total sum: $" + total);
    }

    private void addIncome() {
        System.out.println("\nEnter income:");
        double income = scanner.nextDouble();
        scanner.nextLine();
        total += income;
        System.out.println("Income was added!\n");
    }

    private void balance() {
        System.out.printf("\nBalance: $%.2f\n\n", total);
    }

    private void addPurchase() {
        while (true) {
            System.out.println("\nChoose the type of purchase\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) Back");
            int type = scanner.nextInt();
            scanner.nextLine();

            if (type == 5) {
                System.out.println();
                return;
            }

            System.out.printf("\nEnter purchase name:\n");
            String name = scanner.nextLine();
            System.out.printf("Enter its price:\n");
            double price = Double.valueOf(scanner.nextLine());
            mapAll.put(name, price);
            total -= price;
            if (total < 0) {
                total = 0;
            }
            switch (type) {
                case 1:
                    mapFood.put(name, price);
                    break;
                case 2:
                    mapClothes.put(name, price);
                    break;
                case 3:
                    mapEntertainment.put(name, price);
                    break;
                case 4:
                    mapOther.put(name, price);
                    break;
                default:
                    return;
            }
            System.out.printf("Purchase was added!\n");
        }
    }



    private void showListOfPurchases() {
        if (mapAll.size() == 0) {
            System.out.println("\nThe purchase list is empty!\n");
            return;
        }
        while (true) {
            System.out.println("\nChoose the type of purchases\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) All\n" +
                    "6) Back");
            int type = scanner.nextInt();
            scanner.nextLine();
            switch (type) {
                case 1:
                    listOfPurchases(mapFood);
                    break;
                case 2:
                    listOfPurchases(mapClothes);
                    break;
                case 3:
                    listOfPurchases(mapEntertainment);
                    break;
                case 4:
                    listOfPurchases(mapOther);
                    break;
                case 5:
                    listOfPurchases(mapAll);
                    break;
                default:
                    System.out.println();
                    return;
            }
        }
    }

    private void listOfPurchases (Map<String, Double> map) {
        System.out.println();
        if (map.size() > 0) {
            double totalSum = 0;
            for (var entry: map.entrySet()) {
                System.out.printf("%s $%.2f\n", entry.getKey(), entry.getValue());
                totalSum += entry.getValue();
            }
            System.out.printf("Total sum: %.2f\n", totalSum);
        } else {
            System.out.printf("The purchase list is empty\n");
        }
    }

    private void save() {
        File file = new File("purchases.txt");
        try {
            PrintWriter printWriter = new PrintWriter(file);
            for (var entry: mapFood.entrySet()) {
                printWriter.println(1);
                printWriter.printf("%s $%.2f", entry.getKey(), entry.getValue());
                printWriter.println();
            }
            for (var entry: mapClothes.entrySet()) {
                printWriter.println(2);
                printWriter.printf("%s $%.2f", entry.getKey(), entry.getValue());
                printWriter.println();
            }
            for (var entry: mapEntertainment.entrySet()) {
                printWriter.println(3);
                printWriter.printf("%s $%.2f", entry.getKey(), entry.getValue());
                printWriter.println();
            }
            for (var entry: mapOther.entrySet()) {
                printWriter.println(4);
                printWriter.printf("%s $%.2f", entry.getKey(), entry.getValue());
                printWriter.println();
            }
            printWriter.println("5\n$" + total);
            printWriter.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("\nPurchases were saved!\n");
    }

    private void load() {
        File file = new File("purchases.txt");
        try {
            Scanner scannerReadFile = new Scanner(file);
            while (scannerReadFile.hasNextLine()) {
                int type = scannerReadFile.nextInt();
                scannerReadFile.nextLine();
                switch (type) {
                    case 1:
                        String line = scannerReadFile.nextLine().replace(',','.');
                        int dollar = line.lastIndexOf('$');
                        String name = line.substring(0, dollar - 1);
                        Double value = Double.valueOf(line.substring(dollar + 1));
                        mapFood.put(name, value);
                        mapAll.put(name, value);
                        break;
                    case 2:
                        line = scannerReadFile.nextLine().replace(',','.');
                        dollar = line.lastIndexOf('$');
                        name = line.substring(0, dollar - 1);
                        value = Double.valueOf(line.substring(dollar + 1));
                        mapClothes.put(name, value);
                        mapAll.put(name, value);
                        break;
                    case 3:
                        line = scannerReadFile.nextLine().replace(',','.');
                        dollar = line.lastIndexOf('$');
                        name = line.substring(0, dollar - 1);
                        value = Double.valueOf(line.substring(dollar + 1));
                        mapEntertainment.put(name, value);
                        mapAll.put(name, value);
                        break;
                    case 4:
                        line = scannerReadFile.nextLine().replace(',','.');
                        dollar = line.lastIndexOf('$');
                        name = line.substring(0, dollar - 1);
                        value = Double.valueOf(line.substring(dollar + 1));
                        mapOther.put(name, value);
                        mapAll.put(name, value);
                        break;
                    default:
                        line = scannerReadFile.nextLine().replace(',','.');
                        dollar = line.lastIndexOf('$');
                        total = Double.valueOf(line.substring(dollar + 1));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("\nPurchases were loaded!\n");
    }

    private void exit() {
        System.out.printf("\nBye!");
    }

}
