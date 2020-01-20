package com.example.foodbeak.foodbreak.inc.modules.product.entities;

import com.example.foodbeak.foodbreak.inc.modules.user.entities.CompanyUser;

public class Drink extends Product {
    public Drink(String name, String price, Boolean isReserved, CompanyUser company) {
        super(name, price, isReserved, company);
    }
}
