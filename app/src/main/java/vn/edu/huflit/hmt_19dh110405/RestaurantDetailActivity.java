package vn.edu.huflit.hmt_19dh110405;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import vn.edu.huflit.hmt_19dh110405.Adapter.FoodAdapter;
import vn.edu.huflit.hmt_19dh110405.Fragment.AddToBasketDialogFragment;
import vn.edu.huflit.hmt_19dh110405.Fragment.BasketDialogFragment;
import vn.edu.huflit.hmt_19dh110405.Model.App;
import vn.edu.huflit.hmt_19dh110405.Model.Basket;
import vn.edu.huflit.hmt_19dh110405.Model.Cart;
import vn.edu.huflit.hmt_19dh110405.Model.CartRepository;
import vn.edu.huflit.hmt_19dh110405.Model.Food;
import vn.edu.huflit.hmt_19dh110405.Model.FoodBasket;
import vn.edu.huflit.hmt_19dh110405.Model.Restaurant;

public class RestaurantDetailActivity extends AppCompatActivity implements FoodAdapter.OnFoodItemClickListener, View.OnClickListener {

    TextView tvName, tvAddress, tvOpenHours, tvTotalPrices, tvTotalItems;
    ImageView ivCover;
    View layoutViewBasket;
    RecyclerView rvFoods;
    FoodAdapter foodAdapter;
    Restaurant restaurant;
    Food food;
    ArrayList<Food> foods;
    public final static HashMap<String, FoodBasket> map = new HashMap<>();

    CartRepository cartRepository;


    FirebaseDatabase fDatabase;
    DatabaseReference dRestaurant;
    FirebaseStorage fStorage;

    App app;

//    public static int order = 0;
//    public static double price = 0;
//    public static int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        foods = new ArrayList<>();

        tvName = findViewById(R.id.tvName);
        tvAddress = findViewById(R.id.tvAddress);
        tvOpenHours = findViewById(R.id.tvOpenHours);
        ivCover = findViewById(R.id.ivCover);

        rvFoods = findViewById(R.id.rvFoods);

        foodAdapter = new FoodAdapter(foods, this);
        rvFoods.setAdapter(foodAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvFoods.setLayoutManager(layoutManager);
        rvFoods.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        fDatabase = FirebaseDatabase.getInstance();
        fStorage = FirebaseStorage.getInstance();

        cartRepository = new CartRepository(getApplication());

        Intent intent = getIntent();
        food = (Food) intent.getSerializableExtra("food");
        if(food != null){
            onFoodItemClick(food);
            dRestaurant = fDatabase.getReference();
            Query query = dRestaurant.child("restaurants").child(food.getResKey());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    restaurant = snapshot.getValue(Restaurant.class);
                    foods.addAll(restaurant.getMenu());
                    foodAdapter.notifyDataSetChanged();
                    tvName.setText(restaurant.name);
                    tvAddress.setText(restaurant.address);
                    tvOpenHours.setText(restaurant.getOpenHours());
                    StorageReference profileRef = fStorage.getReference().child("restaurants/covers/"+ restaurant.getCover());
                    profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(ivCover);
                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else {
            restaurant = (Restaurant) intent.getSerializableExtra("restaurant");
            foods.addAll(restaurant.getMenu());
            foodAdapter.notifyDataSetChanged();
            tvName.setText(restaurant.getName());
            tvAddress.setText(restaurant.getAddress());
            tvOpenHours.setText(restaurant.getOpenHours());
            StorageReference profileRef = fStorage.getReference().child("restaurants/covers/"+ restaurant.getCover());
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(ivCover);
                }
            });
        }

        layoutViewBasket = findViewById(R.id.layoutViewBasket);
        layoutViewBasket.setOnClickListener(this);

        tvTotalPrices = layoutViewBasket.findViewById(R.id.tvTotalPrices);
        tvTotalItems = layoutViewBasket.findViewById(R.id.tvTotalItems);

        app = (App) getApplication();
        app.basket = new Basket();
        updateBasket();
    }
    boolean flag =false;

//    FoodBasket foodBasket = null;
    @Override
    public void onFoodItemClick(Food food) {
//        int quantity = 1;
//
//        if (foodBasket == null)
//            foodBasket = new FoodBasket(food, quantity, food.getPrice());
//
//
//        AddToBasketDialogFragment dialog = new AddToBasketDialogFragment(foodBasket);
//        dialog.show(getSupportFragmentManager(), "add_to_basket_dialog");
        int quantity = 1;
        double price = food.price;
        FoodBasket foodBasket = app.basket.getFood(food.getFoodKey());


        if (foodBasket == null)
            foodBasket = new FoodBasket(food, 1, price);


        AddToBasketDialogFragment dialog = new AddToBasketDialogFragment(foodBasket);
        dialog.show(getSupportFragmentManager(), "add_to_basket_dialog");
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.layoutViewBasket) {
//            if (foodBasket == null){
//                foodBasket = new FoodBasket();
//            }
//            Basket basket = new Basket(map, price, quantity);
//            Log.d("ABC", basket.foods.size()+"");
//            BasketDialogFragment dialog = new BasketDialogFragment(basket);
//            dialog.show(getSupportFragmentManager(), "basket_dialog");
//        }

        if (v.getId() == R.id.layoutViewBasket) {
            BasketDialogFragment dialog = new BasketDialogFragment(app.basket);
            Log.d("ABC", app.getBasket().getTotalItem()+"");
            dialog.show(getSupportFragmentManager(), "basket_dialog");
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        ArrayList<Cart> carts = new ArrayList<>();
        try {
            carts.addAll(cartRepository.getAllCarts());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i< carts.size();i++) {
            Cart cart = carts.get(i);
            FoodBasket foodBasket = new FoodBasket(cart.foodName, cart.foodImage, cart.foodPrice, cart.foodRate, cart.resKey, cart.foodKey, cart.getQuantity(), (int) cart.getSum());
            app.basket.addFood(foodBasket);
        }
        app.basket.calculateBasket();
        updateBasket();
    }




//    public void updateBasket(int a, double b) {
//        price+=b;
//        quantity+=a;
//        tvTotalItems.setText(String.valueOf(quantity));
//        tvTotalPrices.setText(String.valueOf(price));
//        Log.d("ABC", map.size()+"");
//    }
    public void updateBasket() {
        tvTotalItems.setText(String.valueOf(app.basket.getTotalItem()) );
        tvTotalPrices.setText(String.valueOf(app.basket.getTotalPrice()));
        Log.d("ABC", map.size()+"");
    }
}
