package tamas.verovszki.wifi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {
    private WifiManager wifiManager;
    boolean doubleBackToExitPressedOnce = false;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.Wifi_On:
                    wifiManager.setWifiEnabled(true);
                    Toast.makeText(Activity2.this, "Wifi on!", Toast.LENGTH_SHORT).show();
                    saveData("On");
                    return true;
                case R.id.Wifi_Off:
                    wifiManager.setWifiEnabled(false);
                    Toast.makeText(Activity2.this, "Wifi off!", Toast.LENGTH_SHORT).show();
                    saveData("Off");
                    return true;
                case R.id.Back:
                    Intent intent = new Intent(Activity2.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.Wifi_Off);
    }

    public void saveData(String state){
        SharedPreferences sp = getSharedPreferences("WiFi", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("WiFiState", state);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Press again to exit!", Toast.LENGTH_SHORT).show();
        if (doubleBackToExitPressedOnce) {
            Process.killProcess(Process.myPid());
            super.onBackPressed();
        }
        doubleBackToExitPressedOnce = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
