package com.example.foodbeak.foodbreak.inc.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.example.foodbeak.foodbreak.inc.entities.Consumer;

import java.util.ArrayList;

public class CoreRepository {
    private static volatile CoreRepository CORE_REPOSITORY_INSTANCE;

    private MutableLiveData<Boolean> mIsUpdating;
    private MutableLiveData<ArrayList<String>> mErrors;
    private MutableLiveData<Boolean> mIsComplete;
    private MutableLiveData<Consumer> mAuthConsumer;
    private MutableLiveData<Company> mAuthCompany;

    public static CoreRepository getInstance() {
        if (CORE_REPOSITORY_INSTANCE == null) {
            synchronized (LoginRepository.class) {
                if (CORE_REPOSITORY_INSTANCE == null) {
                    CORE_REPOSITORY_INSTANCE = new CoreRepository();
                }
            }
        }
        return CORE_REPOSITORY_INSTANCE;
    }

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
        if (getInstance().mAuthConsumer !=null) {
            return;
        }

        getInstance().mAuthConsumer = new MutableLiveData<>();
    }
    public void initAuthCompany() {
        if (getInstance().mAuthCompany !=null) {
            return;
        }

        getInstance().mAuthCompany = new MutableLiveData<>();
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
        return getInstance().mAuthConsumer;
    }
    public MutableLiveData<Company> getAuthCompany() {
        return getInstance().mAuthCompany;
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
        getInstance().mAuthConsumer.postValue(consumer);
    }
    public void updateAuthCompany(Company company) {
        getInstance().mAuthCompany.postValue(company);
    }

    public void addError(String error) {
        ArrayList<String> errors = mErrors.getValue();

        errors.add(error);

        mErrors.postValue(errors);
    }
}
