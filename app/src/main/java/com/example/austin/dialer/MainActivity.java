package com.example.austin.dialer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
*   159336 Assignment 1
*   Junyi Chen
*   16192554
* */

public class MainActivity extends Activity {

    /*12 Buttons for number*/
    private Button[] numberButtons = new Button[12];
    private final int[] buttonIds ={
        R.id.button0, R.id.button1,R.id.button2, R.id.button3,
        R.id.button4, R.id.button5, R.id.button6, R.id.button7,
        R.id.button8, R.id.button9, R.id.buttonStar, R.id.buttonHash,
    };

    /*button use to delete the number*/
    private Button deleteButton;
    /*button use to make the phone call*/
    private Button callButton;
    /*Text view for display the number*/
    private TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Permission checking for sdk >= 23
        * if permission is not accpeted yet, request permission*/
        if(Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
        }

        /*Getting buttons from activity_main.xml*/
        deleteButton = (Button)findViewById(R.id.deleteButton);
        callButton = (Button)findViewById(R.id.callButton);
        display = (TextView)findViewById(R.id.display);
        display.setTextSize(30);

        for (int i = 0; i < 12;i++){
            numberButtons[i] = (Button)findViewById(buttonIds[i]);
            final int finalI = i;
            /*Adding action for each number button*/
            numberButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(finalI == 10){
                        display.append("*");
                    } else if (finalI == 11){
                        display.append("#");
                    } else {
                        String temp = Integer.toString(finalI);
                        display.append(temp);
                    }

                }
            });
        }

        /*adding action for delete button*/
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = display.getText().toString();
                if(!temp.equals("")) {
                    temp = temp.substring(0, temp.length() - 1);
                    display.setText(temp);
                }
            }
        });

        /*Adding button for call button*/
        callButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                {
                    makeCall(display);
                }
            }
        });

    }

    /*method to make a phone call*/
    public void makeCall(TextView view){
        /*check if the permission is accepted
        * if it is not accpeted, request for the permission
        * else make the phone call with the number displayed on TextView
        * */
        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
        }else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + view.getText()));
            this.startActivity(intent);
        }
    }



}
