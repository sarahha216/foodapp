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

import java.util.ArrayList;
import java.util.List;

import vn.edu.huflit.hmt_19dh110405.Model.Restaurant;
import vn.edu.huflit.hmt_19dh110405.R;

public class RestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public interface OnRestaurantItemClickListener {
        void onRestaurantItemClick(Restaurant restaurant);
    }

    public class ViewHolderRestaurant extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvOpenHour;
        ImageView ivImage;

        public ViewHolderRestaurant(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvOpenHour = itemView.findViewById(R.id.tvOpenHour);
        }
    }

    public class ViewHolderTopRestaurant extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvRate;
        ImageView ivImage;

        public ViewHolderTopRestaurant(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvRate = itemView.findViewById(R.id.tvRate);
        }


    }

    private List<Restaurant> mRestaurants;
    private OnRestaurantItemClickListener mListener;
    private int TYPE_LAYOUT ;

    public RestaurantAdapter(List<Restaurant> restaurants, OnRestaurantItemClickListener listener, int type_layout) {
        mRestaurants = restaurants;
        mListener = listener;
        TYPE_LAYOUT =type_layout;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if(TYPE_LAYOUT == 1){
            View view = inflater.inflate(R.layout.row_restaurant, parent, false);
            return new ViewHolderRestaurant(view);
        }else {
            View view = inflater.inflate(R.layout.row_top_restaurant, parent, false);
            return new ViewHolderTopRestaurant(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Restaurant restaurant = mRestaurants.get(position);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        if(TYPE_LAYOUT == 1){
            ViewHolderRestaurant viewHolderRestaurant = (ViewHolderRestaurant) viewHolder;
            StorageReference profileRef = storageReference.child("restaurants/"+ restaurant.getLogo());
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(viewHolderRestaurant.ivImage.getContext()).load(uri).into(viewHolderRestaurant.ivImage);
                }
            });
            viewHolderRestaurant.tvName.setText(restaurant.getName());
            viewHolderRestaurant.tvAddress.setText(restaurant.getAddress());
            viewHolderRestaurant.tvOpenHour.setText(restaurant.openHours);
            viewHolderRestaurant.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onRestaurantItemClick(restaurant);
                }
            });
        }
        else {
            ViewHolderTopRestaurant viewHolderTopRestaurant = (ViewHolderTopRestaurant) viewHolder;
            StorageReference profileRef = storageReference.child("restaurants/"+ restaurant.getLogo());
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(viewHolderTopRestaurant.ivImage.getContext()).load(uri).into(viewHolderTopRestaurant.ivImage);
                }
            });
            viewHolderTopRestaurant.tvName.setText(restaurant.getName());
            viewHolderTopRestaurant.tvRate.setText("Rate: ".concat(String.valueOf(restaurant.rate)));
            viewHolderTopRestaurant.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onRestaurantItemClick(restaurant);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }

    public void addRestaurants(ArrayList<Restaurant> restaurants){
        mRestaurants.addAll(restaurants);
        notifyDataSetChanged();
    }
}


