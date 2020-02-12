package com.example.foodbeak.foodbreak.inc.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.foodbeak.foodbreak.inc.entities.Order;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class OrderRepository extends CoreRepository {
    private static final String TAG = "OrderRepo";
    private static volatile OrderRepository ORDER_REPOSITORY_INSTANCE;

    private MutableLiveData<Order> mConsumerOrder;
    private MutableLiveData<ArrayList<Order>> mCompanyOrders;

    public static OrderRepository getInstance() {
        if (ORDER_REPOSITORY_INSTANCE == null) {
            synchronized (OrderRepository.class) {
                if (ORDER_REPOSITORY_INSTANCE == null) {
                    ORDER_REPOSITORY_INSTANCE = new OrderRepository();
                }
            }
        }
        return ORDER_REPOSITORY_INSTANCE;
    }

    public void initConsumerOrder() {
        if (mConsumerOrder != null) {
            return;
        }

        this.mConsumerOrder = new MutableLiveData<>();
    }

    public void initCompanyOrders() {
        if (mCompanyOrders != null) {
            return;
        }

        this.mCompanyOrders = new MutableLiveData<>();

        CollectionReference storeOrders = FirebaseFirestore.getInstance()
                .collection("orders");

        storeOrders
                .document(this.getAuthCompany().getValue().getEmail())
                .collection("orders")
                .addSnapshotListener((snapshots, e) -> {
                    ArrayList<Order> orders = new ArrayList<>();

                    for (DocumentSnapshot snapshot : snapshots.getDocuments()) {
                        Order order = snapshot.toObject(Order.class);

                        if (!order.getDone()) {
                            orders.add(order);
                        }
                    }

                    mCompanyOrders.postValue(orders);

                });
    }

    public MutableLiveData<Order> getmConsumerOrder() {
        return mConsumerOrder;
    }

    public MutableLiveData<ArrayList<Order>> getCompanyOrders() {
        if (mCompanyOrders == null) {
            this.initCompanyOrders();
        }
        return mCompanyOrders;
    }

    public void updateConsumerOrder(Order order) {
        CollectionReference storeOrders = FirebaseFirestore.getInstance()
                .collection("orders");

        storeOrders
                .document(order.getCompany().getEmail())
                .collection("orders")
                .add(order)
                .addOnSuccessListener(ordered -> {
                    ordered.addSnapshotListener((orderSnapshot, e) -> {
                        Log.e(TAG, "updateConsumerOrder: done = " + orderSnapshot.get("done"));

                        this.mConsumerOrder.postValue(
                                orderSnapshot.toObject(Order.class)
                        );
                    });
                });

    }

}
