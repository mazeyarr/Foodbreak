package com.example.foodbeak.foodbreak.inc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodbeak.foodbreak.inc.entities.Order;
import com.example.foodbeak.foodbreak.inc.repositories.OrderRepository;
import com.example.foodbeak.foodbreak.inc.types.MyViewModel;

import java.util.ArrayList;

public class OrderViewModel extends AndroidViewModel implements MyViewModel<OrderRepository> {
    private static final String TAG = "OrderVM";

    private OrderRepository mOrderRepo;

    public OrderViewModel(@NonNull Application application) {
        super(application);

        mOrderRepo = OrderRepository.getInstance();
    }

    @Override
    public OrderRepository getRepo() {
        return mOrderRepo;
    }

    @Override
    public void init() {
        initDefaults();
        mOrderRepo.initConsumerOrder();
    }

    public void updateConsumerOrder(Order order) {
        mOrderRepo.updateConsumerOrder(order);
    }

    public LiveData<ArrayList<Order>> getCompanyOrders() {
        return mOrderRepo.getCompanyOrders();
    }

    public LiveData<Order> getConsumerOrder() {
        return mOrderRepo.getmConsumerOrder();
    }
}
