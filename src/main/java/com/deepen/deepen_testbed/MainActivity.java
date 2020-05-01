package com.deepen.deepen_testbed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.BranchEvent;


public class MainActivity extends AppCompatActivity {

    TextView log_text;
    Button custom_event_button;
    Button commerce_event_button;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI elements initialization
        custom_event_button = findViewById(R.id.custom_event);
        commerce_event_button = findViewById(R.id.commerce_event);
        log_text = findViewById(R.id.log_text);

        //Button to trigger custom event
        custom_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Trigger the Branch event
                new BranchEvent("clicked_custom_button")
                        .addCustomDataProperty("Custom_Event_Property_Key11", "Custom_Event_Property_val11")
                        .addCustomDataProperty("Custom_Event_Property_Key22", "Custom_Event_Property_val22")
                        .logEvent(MainActivity.this);
            }
        });

    }

    @Override public void onStart() {
        super.onStart();
        //Initialize the SDK here
        Branch.sessionBuilder(this).withCallback(branchReferralInitListener).withData(getIntent() != null ? getIntent().getData() : null).init();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // if activity is in foreground (or in backstack but partially visible) launching the same
        // activity will skip onStart, handle this case with reInitSession
        Branch.sessionBuilder(this).withCallback(branchReferralInitListener).reInit();
    }
    private Branch.BranchReferralInitListener branchReferralInitListener =
            new Branch.BranchReferralInitListener() {
                @Override public void onInitFinished(JSONObject jsonObject, BranchError branchError) {
                    // do something with branchUniversalObject/linkProperties.
                    log_text.setText(jsonObject.toString());
                    Log.d("BRANCH DEBUG", jsonObject.toString());
                }
};
}
