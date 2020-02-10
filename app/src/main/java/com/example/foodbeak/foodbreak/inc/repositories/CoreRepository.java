package com.example.foodbeak.foodbreak.inc.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.example.foodbeak.foodbreak.inc.entities.Consumer;

import java.util.ArrayList;

abstract public class CoreRepository {
    private MutableLiveData<Boolean> mIsUpdating;
    private MutableLiveData<ArrayList<String>> mErrors;
    private MutableLiveData<Boolean> mIsComplete;
    private MutableLiveData<Consumer> mAuthConsumer;
    private MutableLiveData<Company> mAuthCompany;

    public void initErrors() {
        mErrors = new MutableLiveData<>();
        mErrors.setValue(new ArrayList<>());
    }
    public void initIsUpdating() {
        if (mIsUpdating != null) {
            return;
        }

        mIsUpdating = new MutableLiveData<>();
        mIsUpdating.setValue(false);
    }
    public void initIsComplete() {
        mIsComplete = new MutableLiveData<>();
        mIsComplete.setValue(false);
    }
    public void initAuthConsumer() {
        if (mAuthConsumer !=null) {
            return;
        }

        mAuthConsumer = new MutableLiveData<>();
    }
    public void initAuthCompany() {
        if (mAuthCompany !=null) {
            return;
        }

        mAuthCompany = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return mIsUpdating;
    }
    public MutableLiveData<ArrayList<String>> getErrors() {
        return mErrors;
    }
    public MutableLiveData<Boolean> getIsComplete() {
        return mIsComplete;
    }
    public MutableLiveData<Consumer> getAuthConsumer() {
        return mAuthConsumer;
    }
    public MutableLiveData<Company> getAuthCompany() {
        return mAuthCompany;
    }


    public void updateErrors(ArrayList<String> errors) {
        mErrors.postValue(errors);
    }
    public void updateIsUpdating(Boolean toggle) {
        mIsUpdating.postValue(toggle);
    }
    public void updateIsComplete(Boolean toggle) {
        mIsComplete.postValue(toggle);
    }
    public void updateAuthConsumer(Consumer consumer) {
        this.mAuthConsumer.postValue(consumer);
    }
    public void updateAuthCompany(Company company) {
        this.mAuthCompany.postValue(company);
    }

    public void addError(String error) {
        ArrayList<String> errors = mErrors.getValue();

        errors.add(error);

        mErrors.postValue(errors);
    }
}
