package vn.edu.huflit.hmt_19dh110405.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import vn.edu.huflit.hmt_19dh110405.Model.FoodBasket;
import vn.edu.huflit.hmt_19dh110405.R;

public class FoodBasketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public class ViewHolderFoodBasket extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQuantity, tvSum;
        View view;

        public ViewHolderFoodBasket(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.view);
            tvName = view.findViewById(R.id.tvFName);
            tvPrice = view.findViewById(R.id.tvFPrice);
            tvQuantity = itemView.findViewById(R.id.tvFQuantity);
            tvSum = itemView.findViewById(R.id.tvFSum);

        }
    }

    public FoodBasketAdapter(){

    }

    public FoodBasketAdapter(List<FoodBasket> foods){
        this.foodBaskets = foods;
    }

    private List<FoodBasket> foodBaskets;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_foodbasket, parent, false);
        return new ViewHolderFoodBasket(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FoodBasket foodBasket =  foodBaskets.get(position);
        ViewHolderFoodBasket viewHolderFoodBasket = (ViewHolderFoodBasket) holder;
        viewHolderFoodBasket.tvName.setText(foodBasket.getName());
        viewHolderFoodBasket.tvQuantity.setText(String.valueOf(foodBasket.getQuantity()));
        viewHolderFoodBasket.tvPrice.setText(String.valueOf(foodBasket.getPrice()));
        viewHolderFoodBasket.tvSum.setText(String.valueOf(foodBasket.getSum()));
    }

    @Override
    public int getItemCount() {
        return foodBaskets.size();
    }
}
