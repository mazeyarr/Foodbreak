package com.example.foodbeak.foodbreak.inc.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.foodbeak.foodbreak.inc.entities.Product;

import java.util.ArrayList;

public class CartRepository extends CoreRepository {
    private static final String TAG = "CartRepo";
    private static volatile CartRepository CART_REPOSITORY_INSTANCE;

    private MutableLiveData<ArrayList<Product>> mCart;

    public static CartRepository getInstance() {
        if (CART_REPOSITORY_INSTANCE == null) {
            synchronized (CartRepository.class) {
                if (CART_REPOSITORY_INSTANCE == null) {
                    CART_REPOSITORY_INSTANCE = new CartRepository();
                }
            }
        }
        return CART_REPOSITORY_INSTANCE;
    }

    public void initCart() {
        if (mCart != null) {
            return;
        }

        this.mCart = new MutableLiveData<>();
        this.mCart.setValue(new ArrayList<>());
    }

    public MutableLiveData<ArrayList<Product>> getProductsFromCart() {
        if (mCart == null) {
            this.initCart();
        }

        return mCart;
    }

    public void addProductToCart(Product product) {
        ArrayList<Product> products = mCart.getValue();

        products.add(product);

        mCart.postValue(products);
    }

    public void removeProductFromCart(String id) {
        ArrayList<Product> products = mCart.getValue();

        for (Product product : products) {
            if (product.getId().equals(id)) {
                int indexOfCartItem = products.indexOf(product);
                products.remove(indexOfCartItem);
                mCart.postValue(products);
                break;
            }
        }
    }
}
