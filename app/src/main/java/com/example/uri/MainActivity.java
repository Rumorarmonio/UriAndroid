package com.example.uri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText someText;
    public boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        CheckBox chBx = findViewById(R.id.chooseAnOption);

        chBx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton checkBox, boolean checked) {
                check = check == false ? true : false;
                for (int i = 0; i < radioGroup.getChildCount(); i++)
                    radioGroup.getChildAt(i).setEnabled(checked);
            }
        });

        for (int i = 0; i < radioGroup.getChildCount(); i++)
            radioGroup.getChildAt(i).setEnabled(false);
    }

    public void doIt(View view) throws SecurityException {

        someText = findViewById(R.id.someText);
        String someLine = someText.getText().toString();

        if (check) {
            if (someLine.equals(""))
                Toast.makeText(getApplicationContext(), "Pls type something", Toast.LENGTH_SHORT).show();
            else {
                RadioGroup radioGroup = findViewById(R.id.radioGroup);
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();

                switch (checkedRadioButtonId) {
                    case -1:
                        Toast.makeText(getApplicationContext(), "Nothing is picked ¯\\_(ツ)_/¯", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.webAddress:
                        Uri address = Uri.parse(someLine);
                        Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, address);

                        if (openLinkIntent.resolveActivity(getPackageManager()) != null)
                            startActivity(openLinkIntent);
                        else
                            Toast.makeText(getApplicationContext(), "Something is wrong :^((", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.geoPoint:
                        if (someLine.contains(",")) {
                            String[] subStr = someLine.split(",");
                            String latitude = subStr[0];
                            String longitude = subStr[1];
//                            if (latitude.matches("[-+]?\\d+") & longitude.matches("[-+]?\\d+")) {
                            String geoUriString = String.format("geo:%s,%s?z=15", latitude, longitude);
                            Uri geoUri = Uri.parse(geoUriString);
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri);
                            if (mapIntent.resolveActivity(getPackageManager()) != null)
                                startActivity(mapIntent);
//                            } else
//                                Toast.makeText(getApplicationContext(), "Something is wrong :^((", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getApplicationContext(), "Something is wrong :^((", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.phoneNumber:
                        if (someLine.matches("[-+]?\\d+")) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + someLine));
                            if (intent.resolveActivity(getPackageManager()) != null)
                                startActivity(intent);
                        } else
                            Toast.makeText(getApplicationContext(), "Something is wrong :^((", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
            }
        } else {
            if (someLine.equals(""))
                Toast.makeText(getApplicationContext(), "Pls type something", Toast.LENGTH_SHORT).show();
            else {
                if (someLine.contains("/")) {
                    Uri address = Uri.parse(someLine);
                    Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, address);
                    if (openLinkIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(openLinkIntent);
                        return;
                    }
                }

                if (someLine.contains(",")) {
                    String[] subStr = someLine.split(",");
                    String latitude = subStr[0];
                    String longitude = subStr[1];
//                    if (latitude.matches("[-+]?\\d+") & longitude.matches("[-+]?\\d+")) {
                    String geoUriString = String.format("geo:%s,%s?z=15", latitude, longitude);
                    Uri geoUri = Uri.parse(geoUriString);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri);
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                        return;
                    }
//                    } else
//                        Toast.makeText(getApplicationContext(), "Something is wrong :^((", Toast.LENGTH_SHORT).show();
                }

                if (someLine.matches("[-+]?\\d+")) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + someLine));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                        return;
                    }
                }
                Toast.makeText(getApplicationContext(), "Something is wrong :^((", Toast.LENGTH_SHORT).show();
            }
        }
    }
}