package com.example.foodbeak.foodbreak.inc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.example.foodbeak.foodbreak.inc.entities.Product;
import com.example.foodbeak.foodbreak.inc.repositories.ProductCompanyRepository;
import com.example.foodbeak.foodbreak.inc.types.MyViewModel;
import com.example.foodbeak.foodbreak.inc.types.ProductType;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductsViewModel extends AndroidViewModel implements MyViewModel<ProductCompanyRepository> {
    private static final String TAG = "LoginVM";

    private ProductCompanyRepository mProductCompanyRepo;

    private MutableLiveData<ArrayList<Product>> mProducts;

    public ProductsViewModel(@NonNull Application application) {
        super(application);

        mProductCompanyRepo = ProductCompanyRepository.getInstance();
    }

    public void mockProducts() {
        mProductCompanyRepo.mockProductData();
    }

    private void initProducts() {
        if (mProducts != null) {
            return;
        }

        mProducts = new MutableLiveData<>();
        mProducts.setValue(new ArrayList<>());
    }

    public MutableLiveData<HashMap<ProductType, ArrayList<Product>>> getProducts(Company company) {
        return mProductCompanyRepo.getProducts(company);
    }

    @Override
    public ProductCompanyRepository getRepo() {
        return mProductCompanyRepo;
    }

    @Override
    public void init() {
        Company testCompany = new Company("food@food.nl", "food", "ams");

        initDefaults();
        initProducts();
        mProductCompanyRepo.initProducts(testCompany);
    }
}
