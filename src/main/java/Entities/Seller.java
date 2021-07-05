package Entities;

import java.util.List;

public class Seller extends User{

    private CashHolder cashHolder;

    private CreditCard card;

    public Seller(String name, List<Item> items) {
        super("Продавец " + name);
        this.itemsForSale = items;
        this.cashHolder = new CashHolder("Кошелёк продавца " + name);
        this.card = new CreditCard("Карта продавца " + name);
        log.debug("Продавец {} создан", name);
        log.info("Продавец {} теперь может продавать товары: {}", name, items);
    }

    private final CurrencyName currencyForSelling = CurrencyName.UAH;

    private List<Item> itemsForSale;

    public List<Item> getItemsForSale() {
        return itemsForSale;
    }

    public Seller setItemsForSale(List<Item> itemsForSale) {
        this.itemsForSale = itemsForSale;
        return this;
    }

    @Override
    public Seller setCashHolder(CashHolder cashHolder) {
        this.cashHolder = cashHolder;
        return this;
    }

    public Item sellItem(String itemName, List<Currency> currencies){
        double amount = 0.0;
        log.info("Беру из полученных денег те, с валютой которых работаю (валюта {})",
                this.currencyForSelling);
        for (Currency currency: currencies){
            if(currency.getName().equals(currencyForSelling)){
                amount += currency.getNominal();
            }
        }
        return sellItem(itemName, amount);
    }


    public Item sellItem(String name, Double amount){
        log.info("Получил запрос на продажу товара \"{}\". и сумму денег {}", name, amount);
        for(Item item: this.itemsForSale){
            if(item.getName().equals(name)){
                log.info("Товар {} есть в наличии", name);
                if(item.getPrice()==amount){
                    log.info("Продано!");
                    this.cashHolder.putCashToCashHolder(new Currency(currencyForSelling), amount);
                    return item;
                } else if (item.getPrice() < amount){
                    log.info("Слишком большая сумма. Для покупки товара заплатите на {} меньше", amount - item.getPrice() );
                    return null;
                } else {
                    log.info("Не хватает денег. Для покупки товара заплатите на {} больше", item.getPrice() - amount);
                    return null;
                }
            }
        }
        log.info("У меня такого товара нет.");
        return null;
    }

    public Item sellItemUsingCard(String name, Double amount){
        log.info("Получил запрос на продажу товара \"{}\". и сумму денег {}", name, amount);
        for(Item item: this.itemsForSale){
            if(item.getName().equals(name)){
                log.info("Товар {} есть в наличии", name);
                if(item.getPrice()==amount){
                    log.info("Продано!");
                    this.card.putMoneyToCard(new Currency(currencyForSelling), amount);
                    return item;
                } else if (item.getPrice() < amount){
                    log.info("Слишком большая сумма. Для покупки товара заплатите на {} меньше", amount - item.getPrice() );
                    return null;
                } else {
                    log.info("Не хватает денег. Для покупки товара заплатите на {} больше", item.getPrice() - amount);
                    return null;
                }
            }
        }
        log.info("У меня такого товара нет.");
        return null;
    }

    public void showProfit(){
        log.info("На текущий момент сумма на средств счету составляет {} гривен",
                this.cashHolder.getCashInCurrency(currencyForSelling.toString()).size());
    }

    public void showCardProfit(){
        log.info("На текущий момент сумма на средств счету составляет {} гривен",
                this.card.getMoneyInCurrency(currencyForSelling.toString()).getNominal());
    }
}
