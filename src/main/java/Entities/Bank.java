package Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Bank extends BaseEntity{

    public Bank(String name) {
        super(name);
    }

    private final static CurrencyName BASE_CURRENCY = CurrencyName.UAH;

    private final Map<String, Double[]> currencyCourses = new HashMap<>(){
        {
            put("USD", new Double[]{28.5, 27.5});
            put("EUR", new Double[]{30.5, 29.5});
        }
    };

    public List<Currency> changeFromUah(CurrencyName requestedCurrency, List<Currency> uahs){
        List<Currency> result = new ArrayList<>();
        if(currencyCourses.containsKey(requestedCurrency)){
            List<Currency> uahList = uahs.stream()
                    .filter(currency -> currency.getName().equals(BASE_CURRENCY))
                    .collect(Collectors.toList());
            log.info("В полученной массе денег было найдено {} единиц валюты {}. " +
                    "Приступаю к конвртации в валюту {}.", uahList.size(), BASE_CURRENCY, requestedCurrency);
            double course = currencyCourses.get(requestedCurrency)[0];
            double sumOfUah = 0;
            for (Currency uah: uahList) {
                sumOfUah+=uah.getNominal();
            }
            Double sumOfRequestedCurrncy = sumOfUah / course;
            Currency requested = new Currency(requestedCurrency);
            int intSum = sumOfRequestedCurrncy.intValue();
            double doubleSum = sumOfRequestedCurrncy % 1.00;
            List<Double> range = new ArrayList<>();
            for(int i = 0; i< intSum; i++){
                range.add(1.00);
            }
            if (doubleSum != 0.0){
                range.add(doubleSum);
            }
            for(Double nominal: range){
                Currency tempCurrency = requested.clone();
                tempCurrency.setNominal(nominal);
                result.add(tempCurrency);
            }
            log.info("Валюта {} в количестве {} выдана банком", requested.getName(), sumOfRequestedCurrncy);
        }
        return result;
    }

    public List<Currency> changeToUah(List<Currency> noUah){
        List<Currency> result = new ArrayList<>();
        List<Currency> filtered = noUah
                .stream()
                .filter(currency -> currencyCourses.containsKey(currency.getName()))
                .collect(Collectors.toList());
        double sum = 0.00;
        if(!filtered.isEmpty()){
            currencyCourses.keySet()
                    .forEach(convertedCurrency ->{
                        List<Currency> temp = filtered.stream()
                                .filter(currency -> currency.getName().equals(convertedCurrency))
                                .collect(Collectors.toList());
                        log.info("В полученной массе денег было найдено {} единиц валюты {}. " +
                                "Приступаю к конвертации в валюту {}.", temp.size(), convertedCurrency, BASE_CURRENCY);
                        double course = currencyCourses.get(convertedCurrency)[1];
                        double sumOfNoUah = 0;
                        for (Currency currency: temp) {
                            sumOfNoUah += currency.getNominal();
                        }
                        Double sumOfRequestedCurrncy = sumOfNoUah * course;
                        Currency requested = new Currency(BASE_CURRENCY);
                        int intSum = sumOfRequestedCurrncy.intValue();
                        double doubleSum = sumOfRequestedCurrncy % 1.00;
                        List<Double> range = new ArrayList<>();
                        for(int i = 0; i< intSum; i++){
                            range.add(1.00);
                        }
                        if (doubleSum != 0.0){
                            range.add(doubleSum);
                        }
                        for(Double nominal: range){
                            Currency tempCurrency = requested.clone();
                            tempCurrency.setNominal(nominal);
                            result.add(tempCurrency);
                        }
                    });
            for (Currency uah: result){
                sum += uah.getNominal();
            }
        }
        log.info("Валюта {} в количестве {} выдана банком", BASE_CURRENCY, sum);

        return result;
    }

}
