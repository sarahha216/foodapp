package vn.edu.huflit.hmt_19dh110405.Model;

import java.io.Serializable;
import java.util.HashMap;

public class Basket implements Serializable {
    public HashMap<String, FoodBasket> foods;
    public double totalPrice;
    public int totalItem;

    public Basket(HashMap<String, FoodBasket> foodBasket, double price, int quantity) {
        foods = foodBasket;
        totalPrice = price;
        totalItem = quantity;
    }

    public Basket() {
        foods = new HashMap<>();
        totalItem = 0;
        totalPrice = 0;
    }


    public void addFood(FoodBasket food) {
        foods.put(food.getFoodKey(), food);
    }

    public FoodBasket getFood(String key) {
        return foods.get(key);
    }

    public void calculateBasket() {
        totalPrice = 0;
        totalItem = 0;
        for (FoodBasket foodBasket : foods.values()) {
            totalPrice += (foodBasket.price * foodBasket.quantity);
            //totalItem += foodBasket.quantity;
            totalItem+=1;
        }
    }

    public String getTotalPrice() {
        return totalPrice + " VND";
    }

    public int getTotalItem() {
        return totalItem;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "foods=" + foods +
                ", totalPrice=" + totalPrice +
                ", totalItem=" + totalItem +
                '}';
    }
}

