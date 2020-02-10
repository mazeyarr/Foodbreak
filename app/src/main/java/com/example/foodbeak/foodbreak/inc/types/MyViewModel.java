package com.example.foodbeak.foodbreak.inc.types;

import androidx.lifecycle.LiveData;

import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.example.foodbeak.foodbreak.inc.entities.Consumer;
import com.example.foodbeak.foodbreak.inc.repositories.CoreRepository;

import java.util.ArrayList;

public interface MyViewModel<Repo extends CoreRepository> {
    default LiveData<ArrayList<String>> getErrors() {
        return getRepo().getErrors();
    }
    default LiveData<Boolean> isUpdating() {
        return getRepo().getIsUpdating();
    }
    default LiveData<Boolean> isComplete() {
        return getRepo().getIsComplete();
    }
    default LiveData<Consumer> getAuthConsumer() {
        return getRepo().getAuthConsumer();
    }
    default LiveData<Company> getAuthCompany() {
        return getRepo().getAuthCompany();
    }

    Repo getRepo();

    void init();

    default void initDefaults() {
        getRepo().initErrors();
        getRepo().initIsUpdating();
        getRepo().initIsComplete();
        getRepo().initAuthConsumer();
        getRepo().initAuthCompany();
    }
}
