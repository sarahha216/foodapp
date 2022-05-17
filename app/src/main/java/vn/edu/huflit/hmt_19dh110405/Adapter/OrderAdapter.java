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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import vn.edu.huflit.hmt_19dh110405.Model.OrderFinished;
import vn.edu.huflit.hmt_19dh110405.Model.Restaurant;
import vn.edu.huflit.hmt_19dh110405.Model.User;
import vn.edu.huflit.hmt_19dh110405.R;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnOrderItemListener{
        void onOrderItemListener(OrderFinished orderFinished);
    }

    public class ViewHolderOrder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvSum, tvID, tvDate, tvUserName;
        ImageView ivImage;

        public ViewHolderOrder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvOrderName);
            ivImage = itemView.findViewById(R.id.ivOrderImage);
            tvAddress = itemView.findViewById(R.id.tvOrderAddress);
            tvSum = itemView.findViewById(R.id.tvOrderSum);
            tvID = itemView.findViewById(R.id.tvOrderID);
            tvDate = itemView.findViewById(R.id.tvOrderDate);
            tvUserName = itemView.findViewById(R.id.tvOrderUserName);
        }
    }

    private  List<Restaurant> restaurants;
    private List<OrderFinished> orderFinisheds;
    private OnOrderItemListener onOrderItemListener;

    public OrderAdapter(List<OrderFinished> orderFinisheds, List<Restaurant> restaurants, OnOrderItemListener onOrderItemListener) {
        this.orderFinisheds = orderFinisheds;
        this.restaurants = restaurants;
        this.onOrderItemListener = onOrderItemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_order_finished, parent, false);
        return new OrderAdapter.ViewHolderOrder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderFinished orderFinished = orderFinisheds.get(position);
        Restaurant restaurant = new Restaurant();
        for (Restaurant restaurant1:restaurants){
            if (orderFinished.getFoodBaskets().get(0).getResKey().equals(restaurant1.getResKey())){
                restaurant = restaurant1;
                break;
            }
        }
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        ViewHolderOrder viewHolderOrder = (ViewHolderOrder) holder;
        StorageReference profileRef = storageReference.child("restaurants/"+ restaurant.getLogo());
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(viewHolderOrder.ivImage);
            }
        });

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        reference.child("users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
////                viewHolderOrder.tvUserName.setText(user.getFirstname()+" "+user.getLastname());
//                viewHolderOrder.tvUserName.setText("Dang Van Chuyen");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        if(orderFinished.getOrderStatus()==1){
            viewHolderOrder.tvUserName.setText("Đang vận chuyển");
        }else {
            viewHolderOrder.tvUserName.setText("Đã giao");
        }
        viewHolderOrder.tvName.setText(restaurant.getName());
        viewHolderOrder.tvAddress.setText(restaurant.getAddress());
        viewHolderOrder.tvID.setText(orderFinished.orderID);
        viewHolderOrder.tvDate.setText(orderFinished.orderDate);
        viewHolderOrder.tvSum.setText(orderFinished.orderSum);

        viewHolderOrder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOrderItemListener.onOrderItemListener(orderFinished);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderFinisheds.size();
    }
}