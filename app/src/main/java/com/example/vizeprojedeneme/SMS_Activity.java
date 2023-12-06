package com.example.vizeprojedeneme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;

public class SMS_Activity extends AppCompatActivity {

    private EditText gonderilecek_numara;
    private EditText gonderilecek_mesaj;

    private Button sms_gonder;
    private Button sms_Temizle;
    boolean ilkTiklamaNumara = true;
    boolean ilkTiklamaMesaj = true;

    private static final int SMS_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        gonderilecek_numara = (findViewById(R.id.et_telNumarasi));
        gonderilecek_mesaj = (findViewById(R.id.et_telMesaji));
        sms_gonder = (findViewById(R.id.btn_gonder));
        sms_Temizle = (findViewById(R.id.btn_smsTemizle));

        sms_gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mesaj = gonderilecek_mesaj.getText().toString();
                String numara = gonderilecek_numara.getText().toString();
                checkSmsPermissionAndSendSms(numara,mesaj);
            }
        });

        sms_Temizle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gonderilecek_numara.setText("");
                gonderilecek_mesaj.setText("");
            }
        });

        gonderilecek_numara.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && ilkTiklamaNumara){
                    gonderilecek_numara.setText("");
                    ilkTiklamaNumara = false;
                }
            }
        });

        gonderilecek_mesaj.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && ilkTiklamaMesaj){
                    gonderilecek_mesaj.setText("");
                    ilkTiklamaMesaj = false;
                }
            }
        });
    }

    private void sendSms(String numara, String mesaj) {
        if(numara != null && !numara.isEmpty()){
            if(numara.length() == 11){
                if(numara.matches("\\d+")){
                    if( mesaj != null &&!mesaj.isEmpty()){
                        try {
                            SmsManager sms = SmsManager.getDefault();
                            sms.sendTextMessage(numara, null, mesaj, null, null);
                            Toast.makeText(getApplicationContext(), "Mesaj gönderildi!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e("------------- SMS ERROR ----------------",e.getMessage());
                            Toast.makeText(getApplicationContext(), "Mesaj gönderilemedi! Hata: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        //Göndermek için bir mesaj giriniz
                        Toast.makeText(getApplicationContext(), "Sms göndermek için bir mesaj giriniz!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Telefon numarası sadece rakamlardan oluşmalıdır!", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Telefon numarasi 11 haneli olmalidir!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            //Numara boş olamaz
            Toast.makeText(getApplicationContext(), "Sms göndermek istediğiniz numara boş olamaz!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkSmsPermissionAndSendSms(String numara, String mesaj) {
    //izin verilmiş mi kontrol et
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // eğer izin verilmemişse
            // izin iste (requestPermissions() methodu ile)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    SMS_PERMISSION_CODE);
        } else {
            // izin zaten verilmişse burayı çalıştır
            sendSms(numara, mesaj);
        }
    }
}