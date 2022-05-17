package vn.edu.huflit.hmt_19dh110405.Model;

import android.app.Application;

public class App extends Application {
    public Basket basket;
    public App(){
        basket = new Basket();
    }

    public App(Basket basket) {
        this.basket = basket;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }
}
