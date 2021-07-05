package Entities;

public class Currency {

    private CurrencyName name;
    private double nominal;

    public Currency(CurrencyName name) {
        this.name = name;
    }

    public Currency() {
    }

    public CurrencyName getName() {
        return name;
    }

    public Currency setName(CurrencyName name) {
        this.name = name;
        return this;
    }


    public double getNominal() {
        return nominal;
    }

    public Currency setNominal(double nominal) {
        this.nominal = nominal;
        return this;
    }

    public Currency clone(){
        return new Currency(this.name);
    }

    @Override
    public String toString(){
        return String.format("{\"Название\": \"%s\", " +
                        "\"Номинал\": \"%s\"}",
                this.name, this.nominal);
    }
}
