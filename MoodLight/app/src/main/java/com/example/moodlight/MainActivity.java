package com.example.moodlight;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    TextView mCheckConn;
    TextView mDeviceName;
    Button mStart;
    Button mBTConn;

    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> mPairedDevices;
    List<String> mListPairedDevices;

    public Handler mBluetoothHandler;
    public static ConnectedBluetoothThread mThreadConnectedBluetooth;
    BluetoothDevice mBluetoothDevice;
    BluetoothSocket mBluetoothSocket;
    public static BackPressCloseHandler backPressCloseHandler;

//    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static Context mContext;                                                                 //BT Thread와 연결해주는 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mCheckConn = findViewById(R.id.checkConn);
        mDeviceName = findViewById(R.id.deviceName);
        mStart = findViewById(R.id.mStart);
        mBTConn = findViewById(R.id.btConn);

        /*--------- BluetoothAdapter와 BluetoothHandler 객체 생성 --------*/
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothHandler = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == BT_MESSAGE_READ){
                    String readMessage = null;
                    try{
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    }catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        backPressCloseHandler = new BackPressCloseHandler(this);                        //뒤로 버튼 클릭시 종료 객체 생성

        mBTConn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                listPairedDevices();
            }
        });
        mStart.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                startControl();
            }
        });

    }

    /*--------- 뒤로 버튼 클릭시 종료 메소드 호출 ---------*/
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    /*--------- ControlActivity로 전환 ---------*/
    private void startControl() {
        startActivity(new Intent(this, ControlActivity.class));
    }

    /*--------- 연결할 수 있는 블루투스 Device 검색 및 연결 ---------*/
    public void listPairedDevices(){
        if(mBluetoothAdapter.isEnabled()){
            mPairedDevices = mBluetoothAdapter.getBondedDevices();

            if(mPairedDevices.size() > 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("장치 선택");

                mListPairedDevices = new ArrayList<String>();
                for(BluetoothDevice device : mPairedDevices){
                    mListPairedDevices.add(device.getName());
                }
                final CharSequence[] items = mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);
                mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        connectSelectedDevice(items[which].toString());
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }else{
                mCheckConn.setText(getString(R.string.disconnected));
                int redColor = ContextCompat.getColor(getApplicationContext(), R.color.colorRed);
                mCheckConn.setTextColor(redColor);
                mStart.setEnabled(false);
                mStart.setTextColor(Color.parseColor("#808080"));
                Toast.makeText(getApplicationContext(), "페어링된 장치가 없습니다.", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되어 있습니다", Toast.LENGTH_LONG).show();
        }
    }

    /*--------- 선택된 블루투스 Device 연결 Process ---------*/
    void connectSelectedDevice(String selectedDeviceName) {
        for(BluetoothDevice tempDevice : mPairedDevices){
            if(selectedDeviceName.equals(tempDevice.getName())){
                mBluetoothDevice = tempDevice;
                mDeviceName.setText(tempDevice.toString());
                mCheckConn.setText(getString(R.string.connected));
                int blueColor = ContextCompat.getColor(getApplicationContext(), R.color.colorBlue);
                mCheckConn.setTextColor(blueColor);
                mStart.setEnabled(true);
                mStart.setTextColor(Color.parseColor("#ffffff"));

                break;
            }
        }
        try{
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(BT_UUID);
            mBluetoothSocket.connect();
            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket);
            mThreadConnectedBluetooth.start();
            mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();
        }catch(IOException e){
            Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생하였습니다.", Toast.LENGTH_LONG).show();
        }
    }
}
