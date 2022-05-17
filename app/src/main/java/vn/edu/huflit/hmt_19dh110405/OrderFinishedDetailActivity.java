package vn.edu.huflit.hmt_19dh110405;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.huflit.hmt_19dh110405.Adapter.FoodBasketAdapter;
import vn.edu.huflit.hmt_19dh110405.Model.FoodBasket;
import vn.edu.huflit.hmt_19dh110405.Model.OrderFinished;

public class OrderFinishedDetailActivity extends AppCompatActivity {
    public RecyclerView rvFoods;
    public ArrayList<FoodBasket> foodBaskets;
    public FoodBasketAdapter adapter;
    View view;
    TextView tvTotal;
    Button btnBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_finished_detail);

        Toolbar toolbar = findViewById(R.id.cartToolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        view = findViewById(R.id.relativeLayout);
        tvTotal = view.findViewById(R.id.tvTotal);
        btnBuy = findViewById(R.id.btnPlaceOrder);

        foodBaskets = new ArrayList<>();

        Intent intent = getIntent();

        OrderFinished orderFinished = (OrderFinished) intent.getSerializableExtra("order");
        foodBaskets = (ArrayList<FoodBasket>) orderFinished.getFoodBaskets();

        tvTotal.setText(orderFinished.getOrderSum()+"");
        toolbar.setTitle("Đơn hàng "+orderFinished.getOrderDate());

        rvFoods = findViewById(R.id.rvCart);
        adapter = new FoodBasketAdapter(foodBaskets);
        rvFoods.setAdapter(adapter);
        rvFoods.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rvFoods.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }
}