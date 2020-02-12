package com.example.foodbeak.foodbreak.inc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodbeak.foodbreak.inc.entities.Product;
import com.example.foodbeak.foodbreak.inc.repositories.CartRepository;
import com.example.foodbeak.foodbreak.inc.types.MyViewModel;

import java.util.ArrayList;

public class CartViewModel extends AndroidViewModel implements MyViewModel<CartRepository> {
    private static final String TAG = "LoginVM";

    private CartRepository mCartRepo;

    public CartViewModel(@NonNull Application application) {
        super(application);

        mCartRepo = CartRepository.getInstance();
    }

    public LiveData<ArrayList<Product>> getProductsFromCart() {
        return mCartRepo.getProductsFromCart();
    }

    public void addProductToCart(Product product) {
        if (product.getAmount() > 0) {
            mCartRepo.addProductToCart(product);
        }
    }

    public void removeProductFromCartById(String id) {
        mCartRepo.removeProductFromCart(id);
    }

    @Override
    public CartRepository getRepo() {
        return mCartRepo;
    }

    @Override
    public void init() {
        initDefaults();
        this.mCartRepo.initCart();
    }
}
