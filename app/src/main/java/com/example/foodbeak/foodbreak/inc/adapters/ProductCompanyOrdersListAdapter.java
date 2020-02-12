package com.example.foodbeak.foodbreak.inc.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.entities.Order;
import com.example.foodbeak.foodbreak.inc.entities.Product;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProductCompanyOrdersListAdapter extends RecyclerView.Adapter<ProductCompanyOrdersListAdapter.ViewHolder>{
    private static final String TAG = "ProductConsumerListA";

    private ArrayList<Order> mOrders;
    private Context mContext;

    public ProductCompanyOrdersListAdapter(Context context, ArrayList<Order> orders) {
        this.mOrders = orders;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_company_order,
                parent,
                false
        );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.lblOrderNumber.setText(mOrders.get(position).getOrderNumber().toString());

        holder.products = mOrders.get(position).getProducts();
        holder.initProducts();

        holder.btnDone.setOnClickListener(v -> {
            CollectionReference storeCompanyOrders = FirebaseFirestore.getInstance()
                    .collection("orders")
                    .document(mOrders.get(position).getCompany().getEmail())
                    .collection("orders");

            storeCompanyOrders.whereEqualTo("orderNumber", mOrders.get(position).getOrderNumber())
                    .get()
                    .addOnSuccessListener(snapshots -> {
                        for (DocumentSnapshot snapshot : snapshots.getDocuments()) {
                            storeCompanyOrders.document(snapshot.getId()).update("done", true);
                        }
                    });

            Snackbar.make(v, "order is done", Snackbar.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView mOrderRecyclerView;
        private RecyclerView.Adapter mOrderListAdapter;
        private RecyclerView.LayoutManager mOrderListLayoutManager;

        private TextView lblOrderNumber;
        private MaterialButton btnDone;

        private ArrayList<Product> products = new ArrayList<>();

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.lblOrderNumber = itemView.findViewById(R.id.lblOrderNumber);

            this.btnDone = itemView.findViewById(R.id.btnDone);
        }

        private void initProducts() {
            this.mOrderRecyclerView = itemView.findViewById(R.id.rcvOrderItem);
            this.mOrderRecyclerView.setHasFixedSize(true);

            this.mOrderListLayoutManager = new LinearLayoutManager(mContext);
            this.mOrderRecyclerView.setLayoutManager(this.mOrderListLayoutManager);

            this.mOrderListAdapter = new ProductCartListAdapter(mContext, products);
            this.mOrderRecyclerView.setAdapter(this.mOrderListAdapter);
        }
    }

}
