package budget;

public class Purchase implements Comparable<Purchase> {

    String key;
    Double value;

    public Purchase(String key, Double value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public int compareTo(Purchase purchase) {
        return purchase.getValue().compareTo(getValue());
    }
}
