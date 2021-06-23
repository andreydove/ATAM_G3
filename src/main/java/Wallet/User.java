package Wallet;

public class User {

    private String name;
    private CashHolder cashHolder;
    private CreditCard card;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setCashHolder(CashHolder cashHolder) {
        this.cashHolder = cashHolder;
        return this;
    }

    public User putMoneyToCashHolder(Currency currency, Double sum){
        this.cashHolder.putCashToCashHolder(currency, sum);
        return this;
    }

    public User setCard(CreditCard card) {
        this.card = card;
        return this;
    }

    public User putMoneyToCard(Currency currency, Double sum){
        this.card.putMoneyToCard(currency, sum);
        return this;
    }

}
