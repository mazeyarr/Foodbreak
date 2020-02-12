package com.example.foodbeak.foodbreak.inc.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.foodbeak.foodbreak.inc.entities.Order;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrderRepository extends CoreRepository {
    private static final String TAG = "OrderRepo";
    private static volatile OrderRepository ORDER_REPOSITORY_INSTANCE;

    private MutableLiveData<Order> mConsumerOrder;

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

    public MutableLiveData<Order> getmConsumerOrder() {
        return mConsumerOrder;
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
