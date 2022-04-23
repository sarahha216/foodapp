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

import vn.edu.huflit.hmt_19dh110405.Adapter.FoodAdapter;
import vn.edu.huflit.hmt_19dh110405.Fragment.AddToBasketDialogFragment;
import vn.edu.huflit.hmt_19dh110405.Fragment.BasketDialogFragment;
import vn.edu.huflit.hmt_19dh110405.Model.Basket;
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

    FirebaseDatabase fDatabase;
    DatabaseReference dRestaurant;
    FirebaseStorage fStorage;


    public static int order = 0;
    public static double price = 0;
    public static int quantity = 0;
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

    }
    FoodBasket foodBasket = null;
    @Override
    public void onFoodItemClick(Food food) {
        int quantity = 1;

        if (foodBasket == null)
            foodBasket = new FoodBasket(food, quantity, food.getPrice());


        AddToBasketDialogFragment dialog = new AddToBasketDialogFragment(foodBasket);
        dialog.show(getSupportFragmentManager(), "add_to_basket_dialog");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layoutViewBasket) {
            if (foodBasket == null){
                foodBasket = new FoodBasket();
            }
            Basket basket = new Basket(map, price, quantity);
            Log.d("ABC", basket.foods.size()+"");
            BasketDialogFragment dialog = new BasketDialogFragment(basket);
            dialog.show(getSupportFragmentManager(), "basket_dialog");
        }

    }



    public void updateBasket(int a, double b) {
        price+=b;
        quantity+=a;
        tvTotalItems.setText(String.valueOf(quantity));
        tvTotalPrices.setText(String.valueOf(price));
        Log.d("ABC", map.size()+"");
    }
}
