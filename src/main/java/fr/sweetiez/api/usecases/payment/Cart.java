package fr.sweetiez.api.usecases.payment;

import java.math.BigDecimal;
import java.util.List;

public class Cart {
    private final List<Item> articles;
    private final BigDecimal price;

    public Cart(List<Item> articles, BigDecimal price) {
        this.articles = articles;
        this.price = price;
    }

    public boolean isValid() {
        return true;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String toString() {
        return "Cart{" +
                "articles=" + articles +
                ", price=" + price +
                '}';
    }
}
