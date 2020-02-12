package com.example.foodbeak.foodbreak.inc.activities.product;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.Router;
import com.example.foodbeak.foodbreak.inc.entities.Route;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;
import com.example.foodbeak.foodbreak.inc.viewmodels.OrderViewModel;

public class ProductOrderConsumerActivity extends AppCompatActivity implements MyActivity {
    private static final String TAG = "ProductOrdersCompanyA";

    OrderViewModel mOrderViewModel;

    private TextView mLblOrderNumber;
    private TextView mLblPleaseWait;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: product orders created");

        setContentView(Router.getInstance().getCurrentRoute().getLayout());

        mOrderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        initUIData();
        initUIFields();
        initListeners();
    }

    @Override
    public void initUIData() {
        mOrderViewModel.init();
    }

    @Override
    public void initUIFields() {
        this.mLblOrderNumber = findViewById(R.id.lblOrderNumber);
        this.mLblPleaseWait = findViewById(R.id.lblPleaseWait);
    }

    @Override
    public void initListeners() {
        mOrderViewModel.getConsumerOrder().observe(this, order -> {
            Log.d(TAG, "initListeners: order changed: isdone = " + order.getDone());
            this.mLblOrderNumber.setText(order.getOrderNumber().toString());

            if (order.getDone()) {
                mLblOrderNumber.setTextColor(Color.GREEN);
                mLblPleaseWait.setText("Go get your products at: " + order.getCompany().getName());
            }
        });
    }

    @Override
    public void uiCleanup() {
    }

    public static Route getRoute(Context context) {
        return new Route(
                "Product Orders Company",
                context,
                ProductOrderConsumerActivity.class,
                R.layout.activity_product_order_consumer
        );
    }
}
