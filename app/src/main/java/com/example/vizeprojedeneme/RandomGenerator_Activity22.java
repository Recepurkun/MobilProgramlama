package com.example.vizeprojedeneme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class RandomGenerator_Activity22 extends AppCompatActivity {

    private EditText minValue, maxValue, adetValue;
    private LinearLayout progressBarLayout;
    private Button btn_pbUret;
    int minAralikDeger, maxAralikDeger, adetDeger;//kullanicinin girmiş olduğu değerler

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_generator22);

        //bul ve bu degişkenlere ata
        minValue = (findViewById(R.id.et_random_min));
        maxValue = (findViewById(R.id.et_random_max));
        adetValue = (findViewById(R.id.et_random_adet));
        progressBarLayout = (findViewById(R.id.progressBarsLayout));
        btn_pbUret = (findViewById(R.id.btn_uret));

        try {
            btn_pbUret.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //EditText'lara girilen degerleri once string olarak al ve kontrol et
                    String minAralikText = minValue.getText().toString();
                    String maxAralikText = maxValue.getText().toString();
                    String adetText = adetValue.getText().toString();

                    if(minAralikText.isEmpty() || maxAralikText.isEmpty() ||adetText.isEmpty()){
                        //Eger bu alanlardan herhangi biri bos bile olsa butonu calistirma
                        Toast.makeText(getApplicationContext(), "Lütfen tüm alanları doldurun.", Toast.LENGTH_LONG).show();
                    }

                    else{
                        try {
                            //daha sonra bunlarla islem yapabilmek icin int'e cevi
                            minAralikDeger = Integer.parseInt(minAralikText);
                            maxAralikDeger = Integer.parseInt(maxAralikText);
                            adetDeger = Integer.parseInt(adetText);
                            progressBarLayout.removeAllViews();
                            for (int i = 0; i < adetDeger; i++) {
                                progressBarOlustur();
                            }
                        }
                        catch (NumberFormatException e){
                            Toast.makeText(getApplicationContext(), "Geçersiz sayı formatı!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Bir hatayla karsilasildi!" + e.getMessage() , Toast.LENGTH_LONG).show();
        }
    }

    private void progressBarOlustur() {
        try {
            ProgressBar newProgressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);

            // Rastgele minimum ve maksimum değerleri üret
            int minDeger = getRandomValue(minAralikDeger, maxAralikDeger-1);
            int maxDeger = getRandomValue(minAralikDeger+1, maxAralikDeger);
            if(minDeger>maxDeger){
                int gecici = minDeger;
                minDeger = maxDeger;
                maxDeger = gecici;
            }

            Log.d("-----TAG----", "min: " + minDeger + ", max: " + maxDeger);

            // Rastgele ilerleme miktarı üret
            int progressValue = getRandomValue(minDeger+1, maxDeger-1);

            // ProgressBar'a değerleri ayarla
            newProgressBar.setMax(maxDeger); // İlerleme maksimum değeri
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                newProgressBar.setMin(Math.abs(minDeger)); // İlerleme minimum değeri
            }
            newProgressBar.setProgress(progressValue); // Rastgele bir ilerleme değeri

            // TextView oluştur ve değerleri yazdır
            TextView textView = new TextView(this);
            float e = (maxDeger-minDeger);
            float r = (progressValue - minDeger);
            float yuzde = r/e;
            String sonuc = "\t" + progressValue + " = " + " %" + Math.floor(yuzde*100) + "\t";
            textView.setText(sonuc);
            textView.setGravity(Gravity.CENTER);

            TextView mintext = new TextView(this);
            String a = String.valueOf(minDeger);
            mintext.setText(a);
            mintext.setGravity(Gravity.CENTER);

            TextView maxtext = new TextView(this);
            String b = String.valueOf(maxDeger);
            maxtext.setText(b);
            maxtext.setGravity(Gravity.CENTER);

            // LinearLayout oluştur ve ProgressBar ile TextView'ı içine ekle
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(textView);
            linearLayout.addView(newProgressBar);
            linearLayout.setGravity(Gravity.CENTER);

            LinearLayout linearLayout2 = new LinearLayout(this);
            linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout2.addView(mintext);
            linearLayout2.addView(linearLayout);
            linearLayout2.addView(maxtext);
            linearLayout2.setGravity(Gravity.CENTER);

            progressBarLayout.addView(linearLayout2);
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Bir hatayla karsilasildi!" + e.getMessage() , Toast.LENGTH_LONG).show();
        }
    }
    private int getRandomValue(int min, int max) {
        if(max-min <= 0)
            return min;
        Random random = new Random();
            return random.nextInt((max - min) + 1)+min;
    }

}