package Entities;

import java.util.List;

public class User extends BaseEntity{

    private String name;
    private CashHolder cashHolder;
    private CreditCard card;
    private Bag bag;

    public User(String name) {
        super(name);
        this.name = name;
        this.cashHolder = new CashHolder("Кошелёк пользователя " + name);
        this.card = new CreditCard("Карта пользователя " + name);
        this.bag = new Bag("Сумка пользователя " + name);
        log.debug("Пользователь {} создан", name);
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

    public User setCard(CreditCard card) {
        this.card = card;
        return this;
    }

    public Bag getBag() {
        log.info("Возвращаю свою сумку");
        return bag;
    }

    public User setBag(Bag bag) {
        this.bag = bag;
        return this;
    }

    public User putMoneyToCashHolder(Currency currency, Double sum){
        log.info("Кладу в кошелёк валюту {} в количестве {}", currency.getName(), sum);
        this.cashHolder.putCashToCashHolder(currency, sum);
        return this;
    }

    public User putMoneyToCashHolder(String name, List<Currency> money){
        this.cashHolder.putCashToCashHolder(name, money);
        return this;
    }

    public User putMoneyToCashHolder(List<Currency> money){
        this.cashHolder.putCashToCashHolder(money);
        return this;
    }

    public List<Currency> getMoney(String currencyName, double sumOfMoney){
        log.info("Беру из кошелька валюту {} в количестве {}", currencyName, sumOfMoney);
        return this.cashHolder.getMoneyFromCashHolder(currencyName, sumOfMoney);
    }

    public List<Currency> getMoneyFromCard(CurrencyName currencyName, double sumOfMoney){
        log.info("Снимаю из карты валюту {} в количестве {}", currencyName, sumOfMoney);
        return this.card.getMoneyFromCard(currencyName, sumOfMoney);
    }

    public List<String> getBuyersCurrencies(){
        return this.cashHolder.showCurrencies();
    }

    public String getMoneyStatus(){
        return this.cashHolder.toString();
    }

    public User putItemsInBag(List<Item> items){
        this.bag.putItems(items);
        return this;
    }
    public User putItemInBag(Item item){
        if (item!=null){
            log.info("Кладу товар \"{}\" себе в корзину", item.getName());
            this.bag.putItem(item);
        } else {
            log.info("Нечего складывать в сумку");
        }
        return this;
    }

    public User setCardCurrency(CurrencyName currencyName){
        this.card.setCurrency(new Currency(currencyName));
        return this;
    }

    public User changeCurrencyAndSaveIt(CurrencyName convertTo, List<Currency> cash, Bank bank){
        List<Currency> newCash;
        if(convertTo.getName().equals("UAH")){
            newCash = bank.changeToUah(cash);
        } else {
            newCash = bank.changeFromUah(convertTo, cash);
        }
        this.cashHolder.putCashToCashHolder(newCash);
        return this;
    }

    public User changeCurrencyAndSaveIt(CurrencyName convertTo, Bank bank){
        return changeCurrencyAndSaveIt(convertTo, this.cashHolder.getAllMoney(), bank);
    }

    public User changeCurrencyOnCardAndSaveIt(CurrencyName convertTo, Bank bank){
        return changeCurrencyAndSaveIt(convertTo, this.card.getAllMoney(), bank);
    }

    public User putMoneyToCard(Currency currency, Double sum){
        log.info("Кладу на карту валюту {} в количестве {}", currency.getName(), sum);
        this.card.putMoneyToCard(currency, sum);
        return this;
    }

}

