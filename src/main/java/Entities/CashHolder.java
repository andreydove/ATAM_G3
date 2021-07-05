package Entities;

import java.util.*;
import java.util.stream.Collectors;

public class CashHolder extends BaseEntity {

    private final Map<String, List<Currency>> cash = new HashMap<>();

    private List<CreditCard> cardsList = new ArrayList<>();

    public CashHolder(String loggerName) {
        super(loggerName);
        log.debug("{} создан", loggerName);
    }

    public List<Currency> getAllMoney(){
        List<Currency> result = new ArrayList<>();
        this.cash.forEach((key, value) -> result.addAll(value));
        return result;
    }

    public List<String> showCurrencies(){
        return new ArrayList<>(this.cash.keySet());
    }

    public List<Currency> getCashInCurrency(String currency){
        return this.cash.get(currency) != null
                ? this.cash.get(currency) : new ArrayList<>();
    }

    public CashHolder putCashToCashHolder(String currencyName, List<Currency> money){
        this.cash.put(currencyName, money);
        return this;
    }

    public CashHolder putCashToCashHolder(List<Currency> money){
        if(!money.isEmpty()){
            money.stream()
                    .map(Currency::getName)
                    .distinct()
                    .forEach(name -> {
                        List<Currency> temp = money.stream()
                                .filter(currency -> currency.getName().equals(name))
                                .collect(Collectors.toList());
                        this.cash.put(name.toString(), temp);
                    });
        }
        return this;
    }

    public CashHolder putCashToCashHolder(Currency currency, Double sum){
        int intSum = sum.intValue();
        double doubleSum = sum % 1.00;
        List<Double> range = new ArrayList<>();
        for(int i = 0; i< intSum; i++){
            range.add(1.00);
        }
        if (doubleSum != 0.0){
            range.add(doubleSum);
        }
        String name = currency.toString();//??????
        List<Currency> temp = new ArrayList<>();
        for(Double nominal: range){
            Currency tempCurrency = currency.clone();
            tempCurrency.setNominal(nominal);
            temp.add(tempCurrency);
        }
        this.cash.put(name, temp);
        log.info("Валюта {} в количестве {} теперь доступна в кошельке", currency.getName(), sum);
        return this;
    }

    public List<Currency> getMoneyFromCashHolder(String currencyName, double sumOfMoney){
        List<Currency> result = this.cash.get(currencyName);
        if(result != null && !result.isEmpty()){
            double currentSumOfCurrencyInCashHolder = 0;
            for (Currency currency : result){
                currentSumOfCurrencyInCashHolder += currency.getNominal();
            }
            if(currentSumOfCurrencyInCashHolder < sumOfMoney){
                log.info("Доступная сумма {} валюты {} меньше запрашиваемой суммы {}. " +
                                "Будут возвращены все доступные средства",
                        currentSumOfCurrencyInCashHolder, currencyName, sumOfMoney);
                return result;
            } else {
                int intSum = (int) sumOfMoney;
                double doubleSum = sumOfMoney % 1.00;
                List<Double> range = new ArrayList<>();
                for(int i = 0; i< intSum; i++){
                    range.add(1.00);
                }
                if (doubleSum != 0.0){
                    range.add(doubleSum);
                }
                List<Currency> returnedCurrency = new ArrayList<>();
                double returnedSum = 0;
                for(int i = 0; i < range.size(); i++){
                    if(returnedSum < sumOfMoney){
                        Currency currency = result.get(i);
                        Currency tempCurrency = currency.clone();
                        tempCurrency.setNominal(range.get(i));
                        returnedCurrency.add(tempCurrency);
                        if(range.get(i) != 1){
                            currency.setNominal(currency.getNominal() - range.get(i));
                        } else {
                            result.remove(currency);
                        }
                        returnedSum+=range.get(i);
                    } else {
                        break;
                    }
                }
                double balance = 0;
                for (Currency rest: result) {
                    balance+=rest.getNominal();
                }
                log.info("Запрашиваемая сумма {} валюты {} возвращена. Баланс: {}",
                        sumOfMoney, currencyName, balance);
                return returnedCurrency;
            }

        } else {
            log.info("Запрашивемой валюты {} в кошельке нет. Будет возвращено 0.0 валюты", currencyName);
            return new ArrayList<>();
        }
    }

    public CashHolder putCardToCashHolder(CreditCard card) {
        if (this.cardsList.size() == 5) {
            log.info("Превышен лимит количества карт. Максимум 5 карт");
            return this;
        }
        if (card.getCurrency() != null && card.getName() != null) {
            this.cardsList.add(card);
        } else {
            log.info("Не указано имя или валюта карты. Имя: ({}), Валюта:({})",
                    card.getName(), card.getCurrency().getName());
            return this;
        }
        return this;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("{ \n\"Количество денег в кошельке\": \n");
        this.cash.forEach((k, v) -> builder.append("\"")
                .append(k)
                .append("\": ")
                .append(v.size())
                .append(",\n"));
        builder.append("}");
        return builder.toString();
    }


}
