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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryHealthTextView = findViewById(R.id.battery_health);
        getApplicationContext().registerReceiver(batteryHealthReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private BroadcastReceiver batteryHealthReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String condition="", source = "Unplugged", technology, status="", property;

            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            int batteryPercentage = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            int present = intent.getIntExtra(BatteryManager.EXTRA_PRESENT, 0);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            int charging_status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            int temp_celsius = (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)/10);
            int temp_f = (int)(temp_celsius * 1.8 + 32);
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

            switch (health) {
                case BatteryManager.BATTERY_HEALTH_COLD:
                    condition = "Cold";
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    condition = "Dead";
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    condition = "Good";
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    condition = "Over Voltage";
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    condition = "Overheat";
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    condition = "Unknown";
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    condition = "Unspecified Failure";
            }

            switch (plugged) {
                case BatteryManager.BATTERY_PLUGGED_AC:
                    source = "AC Adapter";
                case BatteryManager.BATTERY_PLUGGED_USB:
                    source = "USB";
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    source = "Wireless";
            }

            switch (charging_status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    status = "Charging";
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    status = "Discharging";
                case BatteryManager.BATTERY_STATUS_FULL:
                    status = "Fully charged";
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    status = "Not charging";
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    status = "Unknown";
            }

            batteryHealthTextView.setText("Battery Percentage: "+batteryPercentage+"%\n\n"+
                    "Battery Condition: "+ condition +"\n\n"+
                    "Battery Temperature: "+ temp_celsius +"C /"+ temp_f+"F\n\n"+
                    "Power Source: "+ source +"\n\n"+
                    "Charging Status: " + status +"\n\n"+
                    "Battery Type: " + technology +"\n\n"+
                    "Voltage: " + voltage + "mV");
        }
    };
}
