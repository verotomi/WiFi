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
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Activity3 extends AppCompatActivity {
    Button Button_IP_Address;
    Button Button_Back;
    TextView TextView_IP_Address;
    WifiManager wifiManager;
    WifiInfo wifiInfo;
    String converted_ip;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        init();
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        Button_IP_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            wifiInfo = wifiManager.getConnectionInfo();
            int ip = wifiInfo.getIpAddress();
            converted_ip = Formatter.formatIpAddress(ip);
            Toast.makeText(Activity3.this, "IP address saved!" + converted_ip, Toast.LENGTH_SHORT).show();
            TextView_IP_Address.setVisibility(View.VISIBLE);
            TextView_IP_Address.setText("IP Address: " + converted_ip);
            Toast.makeText(Activity3.this, "IP Address saved!", Toast.LENGTH_SHORT).show();
            saveData();
            }
        });
        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity3.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                finish();
            }
        });
    }

    public void init(){
        Button_IP_Address = findViewById(R.id.Button_IP_Address);
        Button_Back = findViewById(R.id.Button_Back);
        TextView_IP_Address = findViewById(R.id.TextView_IP_Address);
    }

    public void saveData(){
        SharedPreferences sp = getSharedPreferences("WiFi", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("IpAddress", String.valueOf(converted_ip));
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
