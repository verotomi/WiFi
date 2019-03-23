package tamas.verovszki.wifi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Activity4 extends AppCompatActivity {
    Button Button_Generate;
    Button Button_Back;
    TextView TextView_IP_Address;
    ImageView ImageView_QrCode;
    String IpAddress;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);
        init();
        Button_Generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                IpAddress = getIPAddress();
                if (!IpAddress.equals("0.0.0.0")) {
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(IpAddress, BarcodeFormat.QR_CODE, 200, 200);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        ImageView_QrCode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity4.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                finish();
            }
        });
    }

    public void init(){
        Button_Generate = findViewById(R.id.Button_Generate);
        Button_Back = findViewById(R.id.Button_Back);
        TextView_IP_Address = findViewById(R.id.TextView_IP_Address);
        ImageView_QrCode = findViewById(R.id.ImageView_QrCode);
    }

    public String getIPAddress(){
        SharedPreferences sp = getSharedPreferences("WiFi", Context.MODE_PRIVATE);
        return sp.getString("IpAddress", "");
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
