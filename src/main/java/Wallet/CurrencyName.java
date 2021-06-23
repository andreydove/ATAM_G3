package Wallet;

public enum CurrencyName {
    EUR("Євро"),
    USD("Долар"),
    UAH("Гривня");

    CurrencyName(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
