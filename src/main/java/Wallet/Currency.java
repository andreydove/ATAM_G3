package Wallet;

public class Currency {

    private String name;
    private double nominal;
    private int buyCourse;
    private int sellCourse;

    public Currency(String name) {
        this.name = name;
    }

    public Currency() {
    }

    public String getName() {
        return name;
    }

    public Currency setName(String name) {
        this.name = name;
        return this;
    }

    public int getBuyCourse() {
        return buyCourse;
    }

    public Currency setBuyCourse(int buyCourse) {
        this.buyCourse = buyCourse;
        return this;
    }

    public int getSellCourse() {
        return sellCourse;
    }

    public Currency setSellCourse(int sellCourse) {
        this.sellCourse = sellCourse;
        return this;
    }

    public double getNominal() {
        return nominal;
    }

    public Currency setNominal(double nominal) {
        this.nominal = nominal;
        return this;
    }

    public Currency clone() {
        return new Currency(this.name)
                .setBuyCourse(this.buyCourse)
                .setSellCourse(this.sellCourse);
    }

    @Override
    public String toString() {
        return String.format("{\"Название\": \"%s\", " +
                        "\"Номинал\": \"%s\", " +
                        "\"Курс покупки\": \"%s\", " +
                        "\"Курс продажи\": \"%s\"}",
                this.name, this.nominal, this.buyCourse, this.sellCourse);
    }
}
