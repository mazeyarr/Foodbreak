package com.example.foodbeak.foodbreak.inc.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.example.foodbeak.foodbreak.inc.entities.Product;
import com.example.foodbeak.foodbreak.inc.types.ProductType;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductCompanyRepository extends CoreRepository {
    private static final String TAG = "ProductRepo";

    private static volatile ProductCompanyRepository PRODUCT_REPOSITORY_INSTANCE;

    private MutableLiveData<HashMap<ProductType, ArrayList<Product>>> mProducts;

    public static ProductCompanyRepository getInstance() {
        if (PRODUCT_REPOSITORY_INSTANCE == null) {
            synchronized (ProductCompanyRepository.class) {
                if (PRODUCT_REPOSITORY_INSTANCE == null) {
                    PRODUCT_REPOSITORY_INSTANCE = new ProductCompanyRepository();
                }
            }
        }
        return PRODUCT_REPOSITORY_INSTANCE;
    }

    public void mockProductData() {
//        Company company = new Company("food@food.nl", "food", "ams");
//
//        CollectionReference storeFood = FirebaseFirestore.getInstance()
//                .collection("products")
//                .document("food@food.nl")
//                .collection("food");
//
//        CollectionReference storeDrinks = FirebaseFirestore.getInstance()
//                .collection("products")
//                .document("food@food.nl")
//                .collection("drinks");
//
//        storeFood.add(new Product("FoodProduct 1", 2.00f, false, ProductType.FOOD, company));
//        storeFood.add(new Product("FoodProduct 2", 2.00f, false, ProductType.FOOD, company));
//        storeFood.add(new Product("FoodProduct 3", 2.00f, false, ProductType.FOOD, company));
//        storeFood.add(new Product("FoodProduct 4", 2.00f, false, ProductType.FOOD, company));
//        storeFood.add(new Product("FoodProduct 5", 2.00f, false, ProductType.FOOD, company));
//
//        storeDrinks.add(new Product("DrinkProduct 1", 2.00f, false, ProductType.DRINK, company));
//        storeDrinks.add(new Product("DrinkProduct 2", 2.00f, false, ProductType.DRINK, company));
//        storeDrinks.add(new Product("DrinkProduct 3", 2.00f, false, ProductType.DRINK, company));
//        storeDrinks.add(new Product("DrinkProduct 4", 2.00f, false, ProductType.DRINK, company));
//        storeDrinks.add(new Product("DrinkProduct 5", 2.00f, false, ProductType.DRINK, company));
    }

    public void initProducts(Company company) {
        if (mProducts != null) {
            return;
        }

        HashMap<ProductType, ArrayList<Product>> products = new HashMap<>();

        CollectionReference storeFood = FirebaseFirestore.getInstance()
                .collection("products")
                .document(company.getEmail())
                .collection("food");

        CollectionReference storeDrinks = FirebaseFirestore.getInstance()
                .collection("products")
                .document(company.getEmail())
                .collection("drinks");

        storeFood.get()
                .addOnSuccessListener(food -> {
                    Log.d(TAG, "getProducts: Got the food!");
                    List<DocumentChange> snapshots = food.getDocumentChanges();

                    ArrayList<Product> foodProducts = new ArrayList<>();

                    for (DocumentChange snapshot : snapshots) {
                        foodProducts.add(snapshot.getDocument().toObject(Product.class));
                    }

                    products.put(ProductType.FOOD, foodProducts);
                });

        storeDrinks.get()
                .addOnSuccessListener(drinks -> {
                    Log.d(TAG, "getProducts: Got the drinks!");

                    List<DocumentChange> snapshots = drinks.getDocumentChanges();

                    ArrayList<Product> drinkProducts = new ArrayList<>();

                    for (DocumentChange snapshot : snapshots) {
                        drinkProducts.add(snapshot.getDocument().toObject(Product.class));
                    }

                    products.put(ProductType.DRINK, drinkProducts);
                });

        mProducts = new MutableLiveData<>();
        mProducts.setValue(products);
    }

    public MutableLiveData<HashMap<ProductType, ArrayList<Product>>> getProducts(Company company) {
        return mProducts;
    }
}
