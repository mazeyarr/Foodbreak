package com.example.foodbeak.foodbreak.inc.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.example.foodbeak.foodbreak.inc.entities.Product;
import com.example.foodbeak.foodbreak.inc.types.ProductType;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;

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
        Company company = getAuthCompany().getValue();

//        if (company != null) {
//            CollectionReference storeFood = FirebaseFirestore.getInstance()
//                    .collection("products")
//                    .document(company.getEmail())
//                    .collection(ProductType.FOOD.toString());
//
//            CollectionReference storeDrinks = FirebaseFirestore.getInstance()
//                    .collection("products")
//                    .document(company.getEmail())
//                    .collection(ProductType.DRINK.toString());
//
//            storeFood.add(new Product("FoodProduct 1", 2.00f, false, ProductType.FOOD, company));
//            storeFood.add(new Product("FoodProduct 2", 2.00f, false, ProductType.FOOD, company));
//            storeFood.add(new Product("FoodProduct 3", 2.00f, false, ProductType.FOOD, company));
//            storeFood.add(new Product("FoodProduct 4", 2.00f, false, ProductType.FOOD, company));
//            storeFood.add(new Product("FoodProduct 5", 2.00f, false, ProductType.FOOD, company));
//
//            storeDrinks.add(new Product("DrinkProduct 1", 2.00f, false, ProductType.DRINK, company));
//            storeDrinks.add(new Product("DrinkProduct 2", 2.00f, false, ProductType.DRINK, company));
//            storeDrinks.add(new Product("DrinkProduct 3", 2.00f, false, ProductType.DRINK, company));
//            storeDrinks.add(new Product("DrinkProduct 4", 2.00f, false, ProductType.DRINK, company));
//            storeDrinks.add(new Product("DrinkProduct 5", 2.00f, false, ProductType.DRINK, company));
//        }
    }

    public void initProducts(Company company) {
        if (mProducts != null) {
            return;
        }

        mProducts = new MutableLiveData<>();
        HashMap<ProductType, ArrayList<Product>> products = new HashMap<>();
        products.put(ProductType.FOOD, new ArrayList<>());
        products.put(ProductType.DRINK, new ArrayList<>());
        mProducts.setValue(products);

        CollectionReference storeFood = FirebaseFirestore.getInstance()
                .collection("products")
                .document(company.getEmail())
                .collection(ProductType.FOOD.toString());

        CollectionReference storeDrinks = FirebaseFirestore.getInstance()
                .collection("products")
                .document(company.getEmail())
                .collection(ProductType.DRINK.toString());

        this.initProductsObservable(storeFood, storeDrinks);
    }

    private void initProductsObservable(CollectionReference storeFood, CollectionReference storeDrinks) {
        storeFood.addSnapshotListener((snapshots, e) -> {
            HashMap<ProductType, ArrayList<Product>> products = mProducts.getValue();

            if (snapshots != null) {
                removeProductTypeFromProducts(ProductType.FOOD, products);

                ArrayList<Product> foodProducts = new ArrayList<>();

                for (DocumentSnapshot snapshot : snapshots) {
                    foodProducts.add(snapshot.toObject(Product.class));
                }

                products.put(ProductType.FOOD, foodProducts);
                mProducts.postValue(products);

                Log.d(TAG, "initProducts: food size = " + mProducts.getValue().get(ProductType.FOOD).size());
            }
        });

        storeDrinks.addSnapshotListener((snapshots, e) -> {
            HashMap<ProductType, ArrayList<Product>> products = mProducts.getValue();

            if (snapshots != null) {
                removeProductTypeFromProducts(ProductType.DRINK, products);

                ArrayList<Product> drinkProducts = new ArrayList<>();

                for (DocumentSnapshot snapshot : snapshots) {
                    drinkProducts.add(snapshot.toObject(Product.class));
                }

                products.put(ProductType.DRINK, drinkProducts);
                mProducts.postValue(products);

                Log.d(TAG, "initProducts: drink size = " + mProducts.getValue().get(ProductType.DRINK).size());
            }
        });
    }

    private HashMap<ProductType, ArrayList<Product>> removeProductTypeFromProducts(ProductType productType, HashMap<ProductType, ArrayList<Product>> products) {
        if (products != null) {
            products.remove(productType);
        }

        return products;
    }

    public MutableLiveData<HashMap<ProductType, ArrayList<Product>>> getCompanyProducts() {
        return mProducts;
    }

    public void createCompanyProduct(Product product) {
        CollectionReference companyProductsRef = FirebaseFirestore.getInstance()
                .collection("products")
                .document(product.getProvidedBy().getEmail())
                .collection(product.getProductType().toString());

        companyProductsRef.add(product);
    }

    public void updateCompanyProduct(Product product) {
        CollectionReference companyProductsRef = FirebaseFirestore.getInstance()
                .collection("products")
                .document(product.getProvidedBy().getEmail())
                .collection(product.getProductType().toString());

        Query query = companyProductsRef.whereEqualTo("id", product.getId());

        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.size() < 1) {
                        Log.e(TAG, "updateCompanyProduct: failed to query document with id: " + product.getId());
                        return;
                    }

                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                    DocumentReference productRef = companyProductsRef.document(documentSnapshot.getId());

                    productRef.set(product);
                });
    }
}
