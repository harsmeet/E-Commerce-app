package com.example.e_commerce.utlis;

import android.view.View;
import android.widget.TextView;

import com.example.e_commerce.R;

public class NotificationCounter {


    private static final String TAG = NotificationCounter.class.getSimpleName();


    private TextView notificationNum;
     int counter = 0;


    public NotificationCounter(View NotificationNumber) {
        notificationNum = NotificationNumber.findViewById(R.id.notification_num);
    }


    public void addCartNotification() {
        counter++;
        notificationNum.setText(counter + "");
    }
}
