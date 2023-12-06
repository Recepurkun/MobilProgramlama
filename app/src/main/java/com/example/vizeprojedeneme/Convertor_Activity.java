package com.example.vizeprojedeneme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Convertor_Activity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private String[] convertDecimalSpinner = {"Bit","Ikilik","Sekizlik","On altilik"};
    private String[] convertMegaByteSpinner = {"Kilobyte","Byte","KibiByte","Bit"};
    private Spinner spn_dcm;
    private Spinner spn_mgb;
    private TextView decimal_convertor;
    private TextView celcius_convertor;
    private TextView byte_convertor;
    private EditText et_decimal_input;
    private EditText et_celcius_input;
    private EditText et_byte_input;
    boolean ilkSecimMi = true;
    private Button temizle_button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convertor);

        temizle_button = (Button) findViewById(R.id.btn_ConvertorTemizle);

        //input textleri
        et_decimal_input = (EditText) findViewById(R.id.et_decimal_input);
        et_celcius_input = (EditText) findViewById(R.id.et_celcius_input);
        et_byte_input = (EditText) findViewById(R.id.et_byte_input);

        //sonuc textleri
        decimal_convertor = (TextView) findViewById(R.id.tv_decimal_sonuc);
        celcius_convertor = (TextView) findViewById(R.id.tv_celcius_sonuc);
        byte_convertor = (TextView) findViewById(R.id.tv_byte_sonuc);

        //Secimler
        spn_dcm = (Spinner) findViewById(R.id.decimal_spinner);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        spn_mgb = (Spinner) findViewById(R.id.byte_spinner);
        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                float sonuc = 0;
                RadioButton Fahrenheit = findViewById(R.id.rbFah);
                RadioButton rbKelvin = findViewById(R.id.rbKel);
                String input = String.valueOf(et_celcius_input.getText());

                if (input.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Lütfen dönüştürmek istediğiniz Celcius değeri giriniz", Toast.LENGTH_SHORT).show();
                    celcius_convertor.setText("");
                    return;
                }

                if(Fahrenheit.isChecked()){
                    sonuc = calculateFahrenheit(input);
                }
                else if(rbKelvin.isChecked()){
                    sonuc = calculateKelvin(input);
                }
                //celcius_convertor.setText(String.valueOf((int) sonuc));
                celcius_convertor.setText(String.valueOf(sonuc));
            }
        });


        //Decimali digerlerine cevir
        //yeni bir arrayAdapter listesi oluşturup tipini de string veriyoruz
        // ve yukarıda tanımladığımız convertorSpinneri bunun içine atıyoruz
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, convertDecimalSpinner);
        //spinner bileşininde verilerin dropdown item şeklinde görüntüleneceğini söylüyoruz
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //sonra tanımladığımız adapter'i setAdapter ile spn_dcm adlı spinnera ekliyoruz
        spn_dcm.setAdapter(adapter);

        //secilen itemi alıyoruz burada
        spn_dcm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String secilen = spn_dcm.getSelectedItem().toString();
                if(!ilkSecimMi)
                    ConvertDecimalToOtherUnits(secilen);
                else
                    ilkSecimMi = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // MegaByte'i digerlerine cevir
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,convertMegaByteSpinner );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_mgb.setAdapter(adapter2);
        spn_mgb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String secim = spn_mgb.getSelectedItem().toString();
                ConvertBytesToOtherUnits(secim);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //herşeyi temizlemeye yarayan buton
        temizle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decimal_convertor.setText("Sonuç");
                celcius_convertor.setText("Sonuç");
                byte_convertor.setText("Sonuç");
                et_decimal_input.setText(String.valueOf(0));
                et_celcius_input.setText(String.valueOf(0));
                et_byte_input.setText(String.valueOf(0));
                spn_mgb.setSelection(0);
                spn_dcm.setSelection(0);
                radioGroup.clearCheck();
            }
        });
    }


    private float calculateKelvin(String celcius) {
        float kelvin_sonuc;
        try{
            float celciusFloat = Float.parseFloat(celcius);
            kelvin_sonuc = (celciusFloat + 273.15f);
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            kelvin_sonuc = Float.NaN;
            Toast.makeText(this, "Geçersiz değer girişi. Lütfen sayi giriniz.", Toast.LENGTH_SHORT).show();
        }
        return kelvin_sonuc;
    }

    private float calculateFahrenheit(String celcius) {
        float fah_sonuc;
        try {
            float celciusFloat = Float.parseFloat(celcius);
            fah_sonuc = ((9.0f * celciusFloat) / 5) + 32;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            fah_sonuc = Float.NaN; // Geçersiz giriş durumunda NaN (Not a Number) döndürülebilir.
            Toast.makeText(this, "Geçersiz değer girişi. Lütfen sayi giriniz.", Toast.LENGTH_SHORT).show();
        }
        return fah_sonuc;
    }

    private void ConvertDecimalToOtherUnits(@NonNull String secilen) {
        String input = String.valueOf(et_decimal_input.getText());
        String result = "";

        try{
            int number = Integer.parseInt(input);
            switch (secilen) {
                case "Ikilik":
                    result = Integer.toBinaryString(number);
                    break;
                case "Sekizlik":
                    result = Integer.toOctalString(number);
                    break;
                case "On altilik":
                    result = Integer.toHexString(number);
                    break;
                default:
                    Toast.makeText(this, "Geçersiz dönüştürme tipi", Toast.LENGTH_SHORT).show();
                    return;
            }

            decimal_convertor.setText(result);
        }
        catch (NumberFormatException  e) {
            e.printStackTrace();
            decimal_convertor.setText("");
            Toast.makeText(getApplicationContext(), "Lütfen dönüştürmek istediğiniz geçerli bir sayı giriniz", Toast.LENGTH_SHORT).show();
            }
        }

    //{"Kilobyte","Byte","KibiByte","Bit"};
    private void ConvertBytesToOtherUnits(String secim) {
        String input = String.valueOf(et_byte_input.getText());
        double result = 0.0;
        double number;

        try{
            number = Double.parseDouble(input);
            switch (secim) {
                case "Kilobyte":
                    result = number * 1024 ;
                    break;
                case "Byte":
                    result = number * 1024 * 1024;
                    break;
                case "KibiByte":
                    result = number * 8 * 1024 * 1024;
                    break;
                case "Bit":
                    result = number * 8 * 1024 * 1024 * 1024;
                    Toast.makeText(this, "Bit", Toast.LENGTH_SHORT).show();
                    break;
                case "":
                    Toast.makeText(this, "Geçersiz dönüştürme tipi", Toast.LENGTH_SHORT).show();
                    return;
            }

            //decimal_convertor.setText(result);
            byte_convertor.setText(String.valueOf(result));
        }
        catch (NumberFormatException  e) {
            e.printStackTrace();
            byte_convertor.setText("");
            Toast.makeText(getApplicationContext(), "Lütfen dönüştürmek istediğiniz geçerli bir sayı giriniz", Toast.LENGTH_SHORT).show();
        }

    }
}