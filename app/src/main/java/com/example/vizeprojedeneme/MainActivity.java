package com.example.vizeprojedeneme;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn_Convertor;
    Button btn_Random;
    Button btn_Sms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Convertor = (Button) findViewById(R.id.btn_convertor);
        btn_Random = (Button) findViewById(R.id.btn_random);
        btn_Sms = (Button) findViewById(R.id.btn_sms);

        //Yazdigimiz animasyonlar sayfa yuklenirken calissin
        AnimasyonOlustur();

        btn_Convertor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent convertorIntent = new Intent(MainActivity.this,Convertor_Activity.class);
                startActivity(convertorIntent);
            }
        });

        btn_Random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent randomIntent = new Intent(getApplicationContext(), RandomGenerator_Activity22.class);
                startActivity(randomIntent);
            }
        });

        btn_Sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(MainActivity.this, SMS_Activity.class);
                startActivity(smsIntent);
            }
        });

    }
    //Sayfa tekrar yuklendiginde(kaldigi yerden devam ettiginde) animasyonlari tekrar calistir
    @Override
    protected void onResume() {
        super.onResume();
        AnimasyonOlustur();
    }

    // soldan saga dogru ekranin ortasina dogru gelecek sekilde animasyon olusturuyoruz(x duzleminde)
    private void AnimasyonOlustur(){
        // ObjectAnimator'lar ile Animasyon oluşturma
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(btn_Convertor, "translationX", -500f, 0f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(btn_Random, "translationX", -500f, 0f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(btn_Sms, "translationX", -500f, 0f);

        // Animasyonların süresi ayarlandı | 2 saniye
        animator1.setDuration(2000);
        animator2.setDuration(2000);
        animator3.setDuration(2000);

        // Animasyonlar aynı anda çalışacak şekilde ayarlandi
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator1, animator2, animator3);

        // Animasyonların hangi efektle gelecegi ayarlandi
        // *animasyon hedefe ulaştıktan sonra bir miktar fazladan ilerler ve sonra geri döner.*
        animatorSet.setInterpolator(new OvershootInterpolator());

        // Animasyonu baslat
        animatorSet.start();
    }
}