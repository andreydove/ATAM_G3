package Wallet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditCard {

    private final static Logger LOG = LogManager.getLogger("Робот-бухгалтер");

    private final Map<String, Currency> money = new HashMap<>();
    private String name;
    private Currency currency;
    private Boolean limitIsActive = false;
    private Double limitSize = 0.0;
    private List<Currency> limitCurr = new ArrayList<>();

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


        Currency tempCurrency = currency.clone();
        tempCurrency.setNominal(sum);

        this.money.put(currency.getName().toString(), tempCurrency);
        return this;
    }

    public CreditCard enableCreditLimit(Boolean isActive) {
        if (isActive) {
            this.limitIsActive = true;
            this.limitSize = 20.0;
            for (int i = 0; i < this.limitSize; i++) {
                limitCurr.add(new Currency(currency.getName()).setNominal(1.00));
            }
        } else {
            this.limitIsActive = false;
            this.limitSize = 0.0;
            limitCurr.clear();
        }
        return this;
    }


    public List<Currency> getMoneyFromCard(CurrencyName currencyName, double sumOfMoney) {
        Currency result = this.money.get(currencyName.toString());
        List<Currency> res = new ArrayList<>();
        double startLimitSize = limitCurr.size();

        for (int i = ((result.getNominal() % 1 != 0) ? 1 : 0); i < result.getNominal(); i++) {
            res.add(new Currency(currencyName).setNominal(1.00));
        }
        if (result.getNominal() % 1 != 0) {
            res.add(new Currency(currencyName).setNominal(result.getNominal() % 1));
        }
        if (res != null) {
            double currentSumOfCurrencyInCard = 0;
            currentSumOfCurrencyInCard += res.size();
            if ((currentSumOfCurrencyInCard < sumOfMoney && !limitIsActive) || limitIsActive) {
                if (!limitIsActive || (limitIsActive && ((currentSumOfCurrencyInCard + this.limitSize) < sumOfMoney))) {
                    LOG.info("Недостаточно средств для снятия. Доступная сумма {} меньше запрашиваемой суммы {}.",
                            (currentSumOfCurrencyInCard + this.limitSize), sumOfMoney);
                    return res;
                } else {
                    List<Currency> returnedCurrency = new ArrayList<>();
                    double returnedSum = 0;
                    for (Currency currency : res) {
                        if (returnedSum < sumOfMoney) {
                            returnedCurrency.add(currency);
                            returnedSum += currency.getNominal();
                            sumOfMoney--;
                        } else {
                            break;
                        }
                    }
                    res.removeAll(returnedCurrency);
                    returnedCurrency.clear();
                    returnedSum = 0;
                    for (Currency currency : limitCurr) {
                        if (returnedSum < sumOfMoney) {
                            returnedCurrency.add(currency);
                            returnedSum += currency.getNominal();

                        } else {
                            break;
                        }
                    }
                    limitCurr.removeAll(returnedCurrency);
                    double balance = 0;
                    for (Currency rest : limitCurr) {
                        balance += rest.getNominal();
                    }
                    this.money.get(currencyName.toString()).setNominal(res.size());
                    LOG.info("Запрашиваемая сумма превышает доступную на карте. Выданы кредитные средства. Выдано кредитных средств: {}. Кредитный лимит: {}",
                            startLimitSize - balance, limitCurr.size());
                    return res;
                }
            } else {
                List<Currency> returnedCurrency = new ArrayList<>();
                double returnedSum = 0;
                for (Currency currency : res) {
                    if (returnedSum < sumOfMoney) {
                        returnedCurrency.add(currency);
                        returnedSum += currency.getNominal();
                    } else {
                        break;
                    }
                }
                res.removeAll(returnedCurrency);
                double balance = 0;
                for (Currency rest : res) {
                    balance += rest.getNominal();
                }
                this.money.get(currencyName.toString()).setNominal(res.size());
                LOG.info("Запрашиваемая сумма снята. Баланс: {}", balance);
                return res;
            }
        } else {
            return new ArrayList<>();
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
