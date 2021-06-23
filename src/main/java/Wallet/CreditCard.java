package Wallet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CreditCard {

    private final static Logger LOG = LogManager.getLogger("Робот-бухгалтер");

    private final Map<String, Currency> money = new HashMap<>();
    private String name;
    private Currency currency;
    private Boolean limitIsActive = false;
    private Double limitSize = 0.0;

    public Boolean getLimitIsActive() {
        return limitIsActive;
    }

    public void setLimitIsActive(Boolean limitIsActive) {
        this.limitIsActive = limitIsActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Currency getMoneyInCurrency(String currency) {
        return this.money.get(currency) != null
                ? this.money.get(currency) : new Currency();
    }

    public CreditCard putMoneyToCard(Currency currency, Double sum) {
        if (!currency.getName().equals(this.currency.getName())) {
            LOG.info("Вносимая валюта ({}) и валюта карты ({}) не совпадают. " +
                    "Карта не пополнена", currency.getName(), this.currency.getName());
            return this;
        }

        String name = currency.getName();
        Currency tempCurrency = currency.clone();
        tempCurrency.setNominal(sum);

        this.money.put(name, tempCurrency);
        return this;
    }

    public CreditCard enableCreditLimit(Boolean isActive) {
        if (isActive) {
            this.limitIsActive = true;
            this.limitSize = 1000.0;
        } else {
            this.limitIsActive = false;
            this.limitSize = 0.0;
        }
        return this;
    }

    public Currency getMoneyFromCard(String currencyName, double sumOfMoney) {
        Currency result = this.money.get(currencyName);
        if (result != null) {
            double currentSumOfCurrencyInCard = 0;
            currentSumOfCurrencyInCard += result.getNominal();
            if (limitIsActive && currentSumOfCurrencyInCard < sumOfMoney) {
                if ((currentSumOfCurrencyInCard + this.limitSize) < sumOfMoney) {
                    LOG.info("Недостаточно средств для снятия. Доступная сумма {} меньше запрашиваемой суммы {}.",
                            (currentSumOfCurrencyInCard + this.limitSize), sumOfMoney);
                    return result;
                } else {
                    Currency returnedCurrency = new Currency();
                    double returnedLimitSize = this.limitSize;
                    this.limitSize = result.getNominal() + this.limitSize - sumOfMoney;
                    returnedLimitSize -= this.limitSize;
                    returnedCurrency.setNominal(returnedLimitSize);
                    result.setNominal(this.limitSize);
                    double balance = result.getNominal();
                    LOG.info("Запрашиваемая сумма превышает доступную на карте. Выданы кредитные средства. Кредитный баланс: {}", balance);
                    return returnedCurrency;
                }
            } else {
                Currency returnedCurrency = new Currency();
                double residueSum = result.getNominal() - sumOfMoney;
                returnedCurrency.setNominal(sumOfMoney);
                result.setNominal(residueSum);
                double balance = result.getNominal();
                LOG.info("Запрашиваемая сумма снята. Баланс: {}", balance);
                return returnedCurrency;
            }
        } else {
            return new Currency();
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{ \n\"Количество денег на карте\": \n");
        this.money.forEach((k, v) -> builder.append("\"")
                .append(k)
                .append("\": ")
                .append(v)
                .append(",\n"));
        builder.append("}");
        return builder.toString();
    }
}
