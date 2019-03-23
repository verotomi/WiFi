package tamas.verovszki.wifi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button Button_WiFi;
    Button Button_WiFi_Info;
    Button Button_QR_Code;
    WifiManager wifiManager;
    WifiInfo wifiInfo;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
        getWiFiState();

        if (getWiFiState()){
            wifiManager.setWifiEnabled(true);
        }else{
            wifiManager.setWifiEnabled(false);
        }

        if (wifiManager.isWifiEnabled()){
            Button_WiFi_Info.setEnabled(true);
            Button_QR_Code.setEnabled(true);
        }else{
            Button_WiFi_Info.setEnabled(false);
            Button_QR_Code.setEnabled(false);
        }

        Button_WiFi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                finish();
            }
        });

        Button_WiFi_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity3.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                finish();
            }
        });

        Button_QR_Code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity4.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                finish();
            }
        });
    }

    public void init(){
        Button_QR_Code = findViewById(R.id.Button_QR_Code);
        Button_WiFi = findViewById(R.id.Button_WiFi);
        Button_WiFi_Info = findViewById(R.id.Button_WiFi_Info);
    }

    public boolean getWiFiState(){
        SharedPreferences sp = getSharedPreferences("WiFi", Context.MODE_PRIVATE);
        return sp.getString("WiFiState", "").equals("On") ? true : false;
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
