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
        card.setCurrency(new Currency(CurrencyName.UAH));
        card.setName("Основна гривнева");
        card.putMoneyToCard(new Currency(CurrencyName.UAH), 3.00);
        card.enableCreditLimit(true);
        holder.putCardToCashHolder(card);
        LOG.info(card
                .getMoneyFromCard(
                        CurrencyName.UAH,
                        10.0)
                .size()
        );
    }
}
