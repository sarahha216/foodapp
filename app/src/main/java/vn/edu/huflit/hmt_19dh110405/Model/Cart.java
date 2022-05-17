package vn.edu.huflit.hmt_19dh110405.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Cart {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "foodKey")
    public
    String foodKey;
    @ColumnInfo(name = "foodName")
    public
    String foodName;
    @ColumnInfo(name = "foodPrice")
    public
    int foodPrice;
    @ColumnInfo(name= "image")
    public
    String foodImage;
    @ColumnInfo(name = "rate")
    public
    int foodRate;
    @ColumnInfo(name = "reskey")
    public
    String resKey;
    @ColumnInfo(name = "quantity")
    public
    int quantity;
    @ColumnInfo(name = "sum")
    public
    double sum;
    @Ignore
    public Cart() {
    }

    public Cart(String foodKey, String foodName, int foodPrice, String foodImage, int foodRate, String resKey, int quantity, double sum) {
        this.foodKey = foodKey;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodImage = foodImage;
        this.foodRate = foodRate;
        this.resKey = resKey;
        this.quantity = quantity;
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodKey() {
        return foodKey;
    }

    public void setFoodKey(String foodKey) {
        this.foodKey = foodKey;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public String getResKey() {
        return resKey;
    }

    public void setResKey(String resKey) {
        this.resKey = resKey;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

}
