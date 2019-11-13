package com.example.moodlight;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import static com.example.moodlight.MainActivity.backPressCloseHandler;
import static com.example.moodlight.MainActivity.mThreadConnectedBluetooth;

public class ControlActivity extends AppCompatActivity {

    Button red;
    Button orange;
    Button green;
    Button blue;
    Button choice;
    String data;
    ColorPickerDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        red = findViewById(R.id.red);
        orange = findViewById(R.id.orange);
        green = findViewById(R.id.green);
        blue = findViewById(R.id.blue);
        choice = findViewById(R.id.btnSelect);

        red.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                data = "255 0 0 ";
                mThreadConnectedBluetooth.write(data);
            }
        });
        orange.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                data = "255 50 0 ";
                mThreadConnectedBluetooth.write(data);
            }
        });
        green.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                data = "0 255 0 ";
                mThreadConnectedBluetooth.write(data);
            }
        });
        blue.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                data = "0 0 255 ";
                mThreadConnectedBluetooth.write(data);
            }
        });

        choice.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                builder.show();
            }
        });

        builder = new ColorPickerDialog.Builder(this,
                AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setTitle("Choose Color");
        builder.setPreferenceName("ColorPicker");
        builder.setPositiveButton(getString(R.string.confirm),                                      // OK버튼 클릭시
                new ColorEnvelopeListener(){
                    @Override
                    public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
//                        setLayoutColor(envelope);
                        int[] arr = envelope.getArgb();
                        data = ConvertArrayToString(arr);
                        mThreadConnectedBluetooth.write(data);
                    }
                });
        builder.setNegativeButton(getString(R.string.cancel),                                       // 취소버튼 클릭시
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.attachAlphaSlideBar(true);                                                          // 투명도 슬라이드바
        builder.attachBrightnessSlideBar(true);                                                     // 명도 슬라이드바

    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    String ConvertArrayToString(int[] arr){
        StringBuilder stringBuilder = new StringBuilder();
        for (int value: arr) {
            stringBuilder.append(value);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}
