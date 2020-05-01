package com.deepen.deepen_testbed;


import android.app.Application;
import io.branch.referral.Branch;

public class CustomApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Branch logging for debugging
        //Branch.enableDebugMode();

        Branch.enableLogging();
        // Branch object initialization
        Branch.getAutoInstance(this);
    }
}