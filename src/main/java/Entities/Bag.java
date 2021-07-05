package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bag extends BaseEntity{

    private List<Item> items = new ArrayList<>();

    public Bag(String loggerName) {
        super(loggerName);
        log.debug(loggerName + " создана");
    }

    public List<Item> getItems() {
        return items;
    }

    public Bag setItems(List<Item> items) {
        this.items = items;
        return this;
    }

    public Bag putItems(List<Item> items){
        this.items.addAll(items);
        return this;
    }

    public Bag putItem(Item item){
        this.items.add(item);
        return this;
    }

    public Item getItemByName(String name){
        return this.items.stream()
                .filter(item -> item.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<Item> getItemsByName(String name){
        return this.items.stream()
                .filter(item -> item.getName().equals(name))
                .collect(Collectors.toList());
    }

    public void showBagEntry(){
        if(this.items.isEmpty()){
            log.info("Сумка пуста");
        } else {
            log.info("Сейчас в корзине следующие товары: " + this.items);
        }
    }



}
