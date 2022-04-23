package vn.edu.huflit.hmt_19dh110405.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.huflit.hmt_19dh110405.Adapter.FoodBasketAdapter;
import vn.edu.huflit.hmt_19dh110405.Model.Basket;
import vn.edu.huflit.hmt_19dh110405.OrderActivity;
import vn.edu.huflit.hmt_19dh110405.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class BasketDialogFragment extends DialogFragment implements  View.OnClickListener {

    public TextView tvTotal;
    public RecyclerView rvFoods;
    public Basket basket;
    public FoodBasketAdapter adapter;
    public Button btnPlaceOrder;



    public BasketDialogFragment() {
    }

    @SuppressLint("ValidFragment")
    public BasketDialogFragment(Basket basket) {
        this.basket = basket;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basket_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTotal = view.findViewById(R.id.tvTotal);
        tvTotal.setText(basket.getTotalPrice()+"");
        rvFoods = view.findViewById(R.id.rvFoods);
        adapter = new FoodBasketAdapter(new ArrayList<>(basket.foods.values()));
        rvFoods.setAdapter(adapter);
        rvFoods.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        btnPlaceOrder = view.findViewById(R.id.btnBPlaceOrder);
        btnPlaceOrder.setOnClickListener(this);


    }

    @Override
    public void onResume() {
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().setCancelable(true);
        super.onResume();
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnBPlaceOrder) {
            if (basket.getTotalItem() > 0) {
                Intent intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra("basket", basket);
                startActivity(intent);
                getActivity().finish();
                getDialog().dismiss();
            } else {
                getDialog().dismiss();
            }
        }


        int id = v.getId();


    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}