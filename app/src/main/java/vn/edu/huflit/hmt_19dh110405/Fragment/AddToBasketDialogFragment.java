package vn.edu.huflit.hmt_19dh110405.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import vn.edu.huflit.hmt_19dh110405.Model.App;
import vn.edu.huflit.hmt_19dh110405.Model.Cart;
import vn.edu.huflit.hmt_19dh110405.Model.CartRepository;
import vn.edu.huflit.hmt_19dh110405.Model.FoodBasket;
import vn.edu.huflit.hmt_19dh110405.R;
import vn.edu.huflit.hmt_19dh110405.RestaurantDetailActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class AddToBasketDialogFragment extends DialogFragment implements View.OnClickListener{
    TextView tvName, tvPrice, tvQuantity;
    Button btnBuy;
    ImageView btnSubtract, btnAdd;
    int q = 0;
    double p = 0;
    boolean flag = false;
    FoodBasket food;

    App app;
    CartRepository cartRepository;
    public AddToBasketDialogFragment() {
    }

    @SuppressLint("ValidFragment")
    public AddToBasketDialogFragment(FoodBasket food) {
        this.food = food;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_to_basket_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName = view.findViewById(R.id.tvFBName);
        tvPrice = view.findViewById(R.id.tvFBPrice);
        tvQuantity = view.findViewById(R.id.tvQuantity);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnSubtract = view.findViewById(R.id.btnSubtract);
        btnBuy = view.findViewById(R.id.btnLogout);
        btnBuy.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSubtract.setOnClickListener(this);

        cartRepository = new CartRepository(getActivity().getApplication());

        tvName.setText(food.getName());
        tvPrice.setText(food.getPrice() + " VND");

        updateStats();
        app = (App) getActivity().getApplication();

    }

    @Override
    public void onResume() {
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().setCancelable(true);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btnSubtract:
                food.decrease();
                updateStats();
                break;
            case R.id.btnAdd:
                food.increase();
                updateStats();
                break;
            case R.id.btnLogout:
                if (food.quantity > 0) {
//                    RestaurantDetailActivity.map.put(RestaurantDetailActivity.order+"", food);
//                    RestaurantDetailActivity.order++;
//                    ((RestaurantDetailActivity) getActivity()).updateBasket(food.quantity, food.sum);
                    app.basket.addFood(food);
                }

                Cart cart = new Cart(food.getFoodKey(), food.getName(), food.getPrice(), food.getImage(),food.getRate(),food.getResKey(), food.getQuantity(), food.getSum());
                cartRepository.insert(cart);
                getDialog().dismiss();
                ((RestaurantDetailActivity) getActivity()).onResume();
                ((RestaurantDetailActivity) getActivity()).updateBasket();
                break;
        }
    }

    private void updateStats() {
//        if (food.getQuantity() > 0) {
//            tvQuantity.setText(String.valueOf(food.getQuantity()));
//            String add = getResources().getString(R.string.add_to_basket);
//            btnBuy.setText(add + " : " + food.getSum()+ " VND");
//        } else {
//            btnBuy.setText(getResources().getString(R.string.back_to_menu));
//        }
        if (food.getQuantity() > 0) {
            tvQuantity.setText(String.valueOf(food.getQuantity()));
            String add = getResources().getString(R.string.add_to_basket);
            btnBuy.setText(add + " : " + food.getSum()+ " VND");
        } else {
            btnBuy.setText(getResources().getString(R.string.back_to_menu));
        }
    }
}