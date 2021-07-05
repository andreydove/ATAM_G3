package Entities;

public enum CurrencyName {
    EUR("Євро"),
    USD("Долар"),
    UAH("Гривня");

    private String name;

    CurrencyName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
