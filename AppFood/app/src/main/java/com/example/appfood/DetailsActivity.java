package com.example.appfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.appfood.classs.Constant;
import com.example.appfood.classs.DBHelper;
import com.example.appfood.classs.MyApplication;
import com.example.appfood.food.Food;
import com.example.appfood.food.FoodAdapter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DetailsActivity extends AppCompatActivity {
    ImageView imageView;
    ActionBar actionBar;
    TextView textView;
    TextView textView1, textView2, textView3;
    ToggleButton button;
    DBHelper dbHelper;
    int id, favourite;

    Constant constant;
    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;

    //Time
    CountDownTimer countDownTimer;
    Button btn;
    TextView txtTime;
    EditText edtNumber;
    ProgressBar progressBar;
    private Handler handler = new Handler();
    private static final String FORMAT = "%02d:%02d:%02d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        appColor = app_preferences.getInt("color", 0);
        appTheme = app_preferences.getInt("theme", 0);
        themeColor = appColor;
        constant.color = appColor;

        if (themeColor == 0){
            setTheme(Constant.theme);
        }else if (appTheme == 0){
            setTheme(Constant.theme);
        }else{
            setTheme(appTheme);
        }
        setContentView(R.layout.activity_details);

        dbHelper=new DBHelper(DetailsActivity.this);

        imageView = findViewById(R.id.imgFood);
        textView = findViewById(R.id.txtTimeFoodDetail);
        textView1 = findViewById(R.id.txtMaterialFoodDetail);
        textView2 = findViewById(R.id.txtMakingFoodDetail);
        textView3 = findViewById(R.id.txtDesFoodDetail);

        Intent intent = getIntent();
        id = intent.getIntExtra("idFood", 0);
        String img = intent.getStringExtra( "imgFood");
        int timeF = intent.getIntExtra("timeFood", 0);
        String name = intent.getStringExtra("nameFood");
        String material = intent.getStringExtra("materialFood");
        String making = intent.getStringExtra("makingFood");
        String des = intent.getStringExtra("desFood");
        favourite = intent.getIntExtra("faFood", 0);

        textView.setText(timeF+"");
        textView1.setText(material);
        textView2.setText(making);
        imageView.setImageBitmap(Food.convertStringToBitmapFromAccess(getApplicationContext(), img));
        textView3.setText(des);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(name);
        button = findViewById(R.id.favouritebutton);
        setFavouriteButton(favourite);

        //Time
        btn = findViewById(R.id.btn_start_timer);
        txtTime = findViewById(R.id.txt_countdown);
        edtNumber = findViewById(R.id.editText_Number);
        progressBar = findViewById(R.id.progress_time);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer = new CountDownTimer(Integer.valueOf(edtNumber.getText().toString())*60000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        txtTime.setText(""+String.format(FORMAT,
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    }

                    public void onFinish() {
                        sendNotification();
                        txtTime.setText("Kết thúc");
                    }
                }.start();
                final int MAX = Integer.valueOf(edtNumber.getText().toString())*60;
                progressBar.setMax(MAX);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                btn.setEnabled(false);
                            }
                        });
                        for( int i = 1; i < MAX; i++) {
                            final int progress = i + 1;
                            SystemClock.sleep(1000);
                            handler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(progress);
                                    int percent = (progress * 100) / MAX;
                                    if(progress == MAX)  {
                                        btn.setEnabled(true);
                                    }
                                }
                            });
                        }
                    }
                });
                thread.start();
            }
        });
    }

    private void setFavouriteButton(int favourite) {
        if(favourite == 1){
            button.setChecked(true);
        }
        else{
            button.setChecked(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.backhome:
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onFavouriteClick(View view) {
        if (favourite == 1){
            dbHelper.updateFavoutite(id, favourite);
            Toast.makeText(this, "Bỏ yêu thích", Toast.LENGTH_SHORT).show();
            button.setChecked(false);

        }
        else {
            dbHelper.updateFavoutite(id, favourite);
            Toast.makeText(this, "Thêm vào yêu thích", Toast.LENGTH_SHORT).show();
            button.setChecked(true);
        }

    }

    private void sendNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        Notification notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentTitle("Hết thời gian")
                .setContentText("Thời gian đã kết thúc hãy kiểm tra món ăn của bạn")
                .setSmallIcon(R.drawable.ic_baseline_timer_24)
                .setLargeIcon(bitmap)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notification != null){
            notificationManager.notify(getIdNotifi(), notification);
        }

    }

    private int getIdNotifi() {
        return (int) new Date().getTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}