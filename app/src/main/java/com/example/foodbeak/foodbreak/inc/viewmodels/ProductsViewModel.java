package com.example.foodbeak.foodbreak.inc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
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

    private MutableLiveData<ArrayList<Company>> mCompanies;
    private MutableLiveData<Company> mConsumerSelectedCompany;

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

    private void initCompanies() {
        if (mCompanies != null) {
            return;
        }

        mCompanies = new MutableLiveData<>();
        mCompanies.setValue(new ArrayList<>());
    }

    private void initConsumerSelectedCompany() {
        if (mConsumerSelectedCompany != null) {
            return;
        }

        mConsumerSelectedCompany = new MutableLiveData<>();
    }

    public void createCompanyProduct(Product product) {
        mProductCompanyRepo.createCompanyProduct(product);
    }

    public void updateCompanyProduct(Product product) {
        mProductCompanyRepo.updateCompanyProduct(product);
    }

    public void updateConsumerSelectedCompany(Company company) {
        mProductCompanyRepo.updateConsumerSelectedCompany(company);
    }

    public LiveData<Company> getSelectedCompany() {
        return mProductCompanyRepo.getConsumerSelectedCompany();
    }

    public LiveData<ArrayList<Company>> getCompanies() {
        return mProductCompanyRepo.getCompanies();
    }

    public LiveData<HashMap<ProductType, ArrayList<Product>>> getCompanyProducts() {
        return mProductCompanyRepo.getCompanyProducts();
    }

    public LiveData<HashMap<ProductType, ArrayList<Product>>> getCompanyProducts(Company company) {
        return mProductCompanyRepo.getCompanyProducts(company);
    }

    @Override
    public ProductCompanyRepository getRepo() {
        return mProductCompanyRepo;
    }

    @Override
    public void init() {
        initDefaults();
        initProducts();
        initCompanies();
        mProductCompanyRepo.initConsumerSelectedCompany();
        mProductCompanyRepo.initCompanies();
        mProductCompanyRepo.initProducts(mProductCompanyRepo.getAuthCompany().getValue());
    }
}
