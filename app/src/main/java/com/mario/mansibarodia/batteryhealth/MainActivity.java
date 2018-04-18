package com.mario.mansibarodia.batteryhealth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static TextView batteryHealthTextView;
    private static TextView batteryPercentageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryHealthTextView = findViewById(R.id.battery_health);
        batteryPercentageTextView = findViewById(R.id.battery_percent);
        getApplicationContext().registerReceiver(batteryHealthReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private BroadcastReceiver batteryHealthReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String condition="", source = "Unplugged", technology, status="", property;

            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            int batteryPercentage = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            int charging_status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            int temp_celsius = (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)/10);
            int temp_f = (int)(temp_celsius * 1.8 + 32);
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

            switch (health) {
                case BatteryManager.BATTERY_HEALTH_COLD:
                    condition = "Cold";
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    condition = "Dead";
                    break;
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    condition = "Good";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    condition = "Over Voltage";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    condition = "Overheat";
                    break;
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    condition = "Unknown";
                    break;
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    condition = "Unspecified Failure";
                    break;
            }

            switch (plugged) {
                case BatteryManager.BATTERY_PLUGGED_AC:
                    source = "AC Adapter";
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB:
                    source = "USB";
                    break;
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    source = "Wireless";
                    break;
            }

            switch (charging_status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    status = "Charging";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    status = "Discharging";
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    status = "Fully charged";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    status = "Not charging";
                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    status = "Unknown";
                    break;
            }

//            batteryPercentageTextView.setText("Battery Percentage: "+batteryPercentage+"%\n\n");
//
//            batteryHealthTextView.setText("Battery Condition: "+ condition +"\n\n"+
//                    "Phone Fever: "+ temp_celsius +"C / "+ temp_f+"F\n\n"+
//                    "Oxygen Supplier: "+ source +"\n\n"+
//                    "Charging Status: " + status +"\n\n"+
//                    "Battery Type: " + technology +"\n\n"+
//                    "Voltage: " + voltage + "mV");
        }
    };
}
