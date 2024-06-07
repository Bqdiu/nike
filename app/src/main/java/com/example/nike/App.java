package com.example.nike;

import android.app.Application;

import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.config.PaymentButtonIntent;
import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.config.UIConfig;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;

public class App extends Application {
    private static final String YOUR_CLIENT_ID = "ATTlBIbYrenLfQ6IE5VmP70LP04iOsOn6oosdEpdhD4jaVZBE0TqNJ63afTDUD1mdABaqSI6iuYs82Y3";


    @Override
    public void onCreate() {
        super.onCreate();
        payPalConfig();
    }
    private void payPalConfig(){
        PayPalCheckout.setConfig(new CheckoutConfig(
                this,
                YOUR_CLIENT_ID,
                Environment.SANDBOX,
                CurrencyCode.USD,
                UserAction.PAY_NOW,
                PaymentButtonIntent.CAPTURE,
                new SettingsConfig(true, false),
                new UIConfig(true),
                BuildConfig.APPLICATION_ID + "://paypalpay"
        ));
    }
}
