import Wallet.CashHolder;
import Wallet.CreditCard;
import Wallet.Currency;
import Wallet.CurrencyName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainClass {

    private final static Logger LOG = LogManager.getLogger(MainClass.class);

    public static void main(String[] args) {
        CashHolder holder = new CashHolder();
        CreditCard card = new CreditCard();
        card.setCurrency(new Currency(CurrencyName.UAH.getName()));
        card.setName("Основна гривнева");
        card.putMoneyToCard(new Currency(CurrencyName.UAH.getName()), 200.0);
        card.enableCreditLimit(false);
        holder.putCardToCashHolder(card);
        LOG.info(card
                .getMoneyFromCard(
                        CurrencyName.UAH.getName(),
                        30.00)
                .getNominal()
        );

         /*CashHolder cash = new CashHolder();
        cash.putCashToCashHolder(new Currency(CurrencyName.UAH.getName()), 200.0);
        LOG.info(cash
                        .getMoneyFromCashHolder(
                                "Гривня",
                                30.0)
                .size()
        );*/
    }

}
