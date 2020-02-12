package com.example.foodbeak.foodbreak.inc.activities.product;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.Router;
import com.example.foodbeak.foodbreak.inc.adapters.ProductCartListAdapter;
import com.example.foodbeak.foodbreak.inc.entities.Product;
import com.example.foodbeak.foodbreak.inc.entities.Route;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;
import com.example.foodbeak.foodbreak.inc.viewmodels.CartViewModel;
import com.google.android.material.snackbar.Snackbar;

public class ProductConsumerCheckoutActivity extends AppCompatActivity implements MyActivity {
    private static final String TAG = "ProductConsCheckoutA";

    CartViewModel mCartViewModel;

    private RecyclerView mCartRecyclerView;
    private RecyclerView.Adapter mCartListAdapter;
    private RecyclerView.LayoutManager mCartListLayoutManager;

    private ConstraintLayout mCslPay;
    private TextView mLblSubtotaal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "onCreate: products created");

        setContentView(Router.getInstance().getCurrentRoute().getLayout());

        mCartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);

        initUIData();
        initUIFields();
        initListeners();
    }

    @Override
    public void initUIFields() {
        mCslPay = findViewById(R.id.cslPay);
        mLblSubtotaal = findViewById(R.id.lblSubtotal);

        mCartViewModel.getProductsFromCart().observe(this, products -> {
            this.mCartRecyclerView = findViewById(R.id.rcvCart);
            this.mCartRecyclerView.setHasFixedSize(true);

            this.mCartListLayoutManager = new LinearLayoutManager(this);
            this.mCartRecyclerView.setLayoutManager(mCartListLayoutManager);

            this.mCartListAdapter = new ProductCartListAdapter(this, products);
            mCartRecyclerView.setAdapter(mCartListAdapter);

            Float total = 0f;

            for (Product product : products) {
                total = total + product.getPrice();
            }

            mLblSubtotaal.setText("€" + total);
        });
    }

    @Override
    public void initUIData() {

    }

    @Override
    public void initListeners() {
        mCslPay.setOnClickListener(v -> Snackbar.make(v, "payed", Snackbar.LENGTH_SHORT).show());
    }

    @Override
    public void uiCleanup() {
    }

    public static Route getRoute(Context context) {
        return new Route(
                "Product Consumer checkout",
                context,
                ProductConsumerCheckoutActivity.class,
                R.layout.activity_product_checkout
        );
    }
}
