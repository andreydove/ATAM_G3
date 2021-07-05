import Entities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static helper.FileHelper.readFile;
import static java.lang.Double.parseDouble;

public class MainClass {

    private final static Logger LOG = LogManager.getLogger("Программа");

    public static void main(String[] args) {

        List<String> data = readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\data\\app-data.txt");

        List<Item> itemsForSale = new ArrayList<>();
        Bank bank = new Bank(valueFromString(lineByName("Банк", data)));

        itemsForSale.add(new Item(
                valueFromString(lineByName("Товар", data)),
                doubleValue("Цена", data)));

        Seller seller = new Seller(
                valueFromString(lineByName("Имя продавца", data)),
                itemsForSale);

        User buyer = new User(valueFromString(lineByName("Имя покупателя", data)));

        buyer.setCardCurrency(CurrencyName.USD);

        buyer.putMoneyToCard(new Currency(
                        CurrencyName.valueOf(valueFromString(lineByName("Валюта", data)))),
                doubleValue("Деньги", data));

        buyer.changeCurrencyOnCardAndSaveIt(CurrencyName.UAH, bank);

        List<Currency> buyerMoney = buyer.getMoneyFromCard(CurrencyName.USD, doubleValue("Цена", data));

        buyer.putItemInBag(
                seller.sellItem(
                        valueFromString(lineByName("Покупатель", data)),
                        buyerMoney));

        buyer.getBag().showBagEntry();
    }

    private static String valueFromString(String line) {
        try {
            return line.split(": ")[1];
        } catch (ArrayIndexOutOfBoundsException a) {
            LOG.error("Строка {} не разделена на массив. Возвращена целиком.", line);
            return line;
        }
    }

    private static String lineByName(String name, List<String> lines) {
        String result = lines.stream()
                .filter(line -> line.startsWith(name))
                .findFirst()
                .orElse("");
        if (result.equals("")) {
            LOG.error("В полученном списке нет строк, начинающихся на {}. Возвращена пустая строка", name);
        }
        return result;
    }

    private static Double doubleValue(String name, List<String> lines) {
        return parseDouble(
                valueFromString(lineByName(name, lines).equals("")
                        ? " :  " : lineByName(name, lines))
                        .equals(" ")
                        ? "0.00" : valueFromString(lineByName(name, lines)));
    }

}
