package com.akash.ashvawearabletechnologies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.akash.ashvawearabletechnologies.model.TimeZoneModel;
import com.akash.ashvawearabletechnologies.network.MyApi;
import com.akash.ashvawearabletechnologies.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    private static final String API_KEY ="J4QCH4UFR2H0";
    private TimeZoneModel timeZoneModel;
    private EditText latitude, longitude, time_edt, time_zone_edt;
    private Button check_time, fibon_btn, time_diff;
    private String time_london, time_germany, time_tokyo, time_melbourne, time_kolkata;
    private String time_diff_london, time_diff_german, time_diff_tokyo, time_diff_melbourn, time_diff_kolkat;
    private MyApi api;
    private String date, searchDate, lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        latitude = findViewById(R.id.latitude_edt);
        longitude = findViewById(R.id.longitude_edt);
        time_edt = findViewById(R.id.time_edt);
        time_zone_edt = findViewById(R.id.time_zone_edt);
        check_time = findViewById(R.id.check_time);
        time_diff = findViewById(R.id.time_difference);
        fibon_btn = findViewById(R.id.fibonacci_btn);

        api = RetrofitClient.getInstance().getClient().create(MyApi.class);

        check_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lat = latitude.getText().toString().trim();
                lng = longitude.getText().toString().trim();
                if (lat.isEmpty() && lng.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter Longitude & Latitude...", Toast.LENGTH_SHORT).show();
                } else {

                    Call<TimeZoneModel> call = api.getTime(API_KEY, "json", "position", lat, lng);

                    call.enqueue(new Callback<TimeZoneModel>() {
                        @Override
                        public void onResponse(Call<TimeZoneModel> call, Response<TimeZoneModel> response) {

                            String format = response.body().getFormatted();
                            if ((!format.isEmpty())){
                                String[] form = format.split(" ");
                                String time = form[1];
                                String[] realTime = time.split(":");
                                time_edt.setText(realTime[0] + ":" + realTime[1]);
                                time_zone_edt.setText(response.body().getZoneName());
                            }else {
                                Toast.makeText(MainActivity.this, "something went wrong!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TimeZoneModel> call, Throwable t) {
                            Log.d("timezonenow", "onError: " + t.getMessage());
                        }
                    });
                }
            }
        });

        fibon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String [] time = time_edt.getText().toString().split(":");
                String [] num = time[0].split("");
                String [] numn = time[1].split("");
                int sum = Integer.parseInt(num[1]) + Integer.parseInt(num[2])
                                + Integer.parseInt(numn[1]) + Integer.parseInt(numn[2]);
                //Toast.makeText(MainActivity.this,String.valueOf(sum), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,FibonacciActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("RESULT", String.valueOf(sum));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        time_diff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lat = latitude.getText().toString().trim();
                lng = longitude.getText().toString().trim();
                if (lat.isEmpty() && lng.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter Longitude & Latitude...", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, TimeDifferenceActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("lat", lat);
                    bundle.putString("lng", lng);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}