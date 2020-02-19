package com.example.foodbeak.foodbreak.inc.activities.product;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.Router;
import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.example.foodbeak.foodbreak.inc.entities.Product;
import com.example.foodbeak.foodbreak.inc.entities.Route;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;
import com.example.foodbeak.foodbreak.inc.types.ProductType;
import com.example.foodbeak.foodbreak.inc.viewmodels.ProductsViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.text.DecimalFormat;
import java.util.List;

public class ProductCreateCompanyActivity extends AppCompatActivity implements MyActivity, Validator.ValidationListener {
    private static final String TAG = "CreateProductCompanyA";
    private static DecimalFormat df = new DecimalFormat("0.00");

    private ProductsViewModel mProductsViewModel;

    private ImageView imgBtnBack;

    @NotEmpty
    @Length(min = 3)
    private TextInputEditText etProductName;

    private RadioGroup rdGroupProductType;
    private RadioButton rdBtnDrink;
    private RadioButton rdBtnFood;

    @NotEmpty
    private TextInputEditText etProductAmount;

    @NotEmpty
    @Length(min = 1)
    private TextInputEditText etProductPrice;

    private MaterialButton btnProductAdd;

    private Company company;

    Validator validator;
    boolean validated;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        df.setMaximumFractionDigits(2);
        super.onCreate(savedInstanceState);

        setContentView(Router.getInstance().getCurrentRoute().getLayout());

        mProductsViewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);
        mProductsViewModel.getAuthCompany().observe(this, newCompany -> company = newCompany);

        initUIData();
        initUIFields();
        initListeners();
    }

    @Override
    public void initUIData() {
    }

    @Override
    public void initUIFields() {
        this.imgBtnBack = findViewById(R.id.imgBtnBack);

        this.etProductName = findViewById(R.id.etProductName);
        this.rdGroupProductType = findViewById(R.id.rdGroupProductType);
        this.rdBtnFood = findViewById(R.id.rdBtnFood);
        this.rdBtnDrink = findViewById(R.id.rdBtnDrink);
        this.etProductAmount = findViewById(R.id.etProductAmount);
        this.etProductPrice = findViewById(R.id.etProductPrice);

        this.btnProductAdd = findViewById(R.id.btnProductAdd);
    }


    @Override
    public void initListeners() {
        validator = new Validator(this);
        validator.setValidationListener(this);

        this.imgBtnBack.setOnClickListener(v -> goBack());

        this.btnProductAdd.setOnClickListener(this::addProductToCompany);
    }

    @Override
    public void uiCleanup() {
    }

    public void addProductToCompany(View v) {
        validator.validate();

        if (this.validated && !mProductsViewModel.isUpdating().getValue()) {
            String productName = this.etProductName.getText().toString();
            ProductType productType = getSelectedProductType();
            Integer productAmount = Integer.parseInt(this.etProductAmount.getText().toString());

            Float productPrice = Float.parseFloat(this.etProductPrice.getText().toString());
            productPrice = Float.parseFloat(df.format(productPrice));

            Product newProduct = new Product(
                    productName,
                    productPrice,
                    productAmount,
                    false,
                    productType,
                    mProductsViewModel.getAuthCompany().getValue()
            );

            mProductsViewModel.createCompanyProduct(newProduct);

            Router.getInstance().goTo(ProductCompanyActivity.getRoute(this));
            finish();
        }
    }

    public void goBack() {
        Router.getInstance().goBack();
        finish();
    }

    public static Route getRoute(Context context) {
        return new Route(
                "Create company product",
                context,
                ProductCreateCompanyActivity.class,
                R.layout.activity_product_create_company
        );
    }

    private ProductType getSelectedProductType() {
        int selectedProductId = this.rdGroupProductType.getCheckedRadioButtonId();

        switch (selectedProductId) {
            case R.id.rdBtnFood:
                return ProductType.FOOD;
            case R.id.rdBtnDrink:
                return ProductType.DRINK;
            default:
                return ProductType.DRINK;
        }
    }

    @Override
    public void onValidationSucceeded() {
        this.validated = true;
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String errorMessage = error.getCollatedErrorMessage(this);

            if (view instanceof TextInputEditText) {
                View textInputLayout = (View) view.getParent().getParent();

                if (textInputLayout instanceof TextInputLayout) {
                    TextInputLayout textInputLay = findViewById(textInputLayout.getId());

                    textInputLay.setErrorEnabled(true);
                    ((TextInputLayout) textInputLayout).setError(errorMessage);
                }

            } else {
                Log.e(TAG, "onValidationFailed: " + errorMessage);
                Snackbar.make(view, errorMessage, Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
