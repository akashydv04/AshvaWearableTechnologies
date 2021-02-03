package com.akash.ashvawearabletechnologies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.akash.ashvawearabletechnologies.model.TimeZoneModel;
import com.akash.ashvawearabletechnologies.network.MyApi;
import com.akash.ashvawearabletechnologies.network.RetrofitClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class TimeDifferenceActivity extends AppCompatActivity {

    private static final String API_KEY ="J4QCH4UFR2H0";
    private EditText london_edt, germany_edt, tokyo_edt, melobourne_edt, kolkata_edt;
    private String time_diff;
    private MyApi api;
    private String date, searchDate, lat, lng, searchTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_difference);

        init();
        api = RetrofitClient.getInstance().getClient().create(MyApi.class);
        Bundle bundle = getIntent().getExtras();

        lat = bundle.getString("lat");
        lng = bundle.getString("lng");

        getSearchData();
    }

    private void getSearchData() {
        Call<TimeZoneModel> call = api.getTime(API_KEY, "json", "position", lat, lng);

        call.enqueue(new Callback<TimeZoneModel>() {
            @Override
            public void onResponse(Call<TimeZoneModel> call, Response<TimeZoneModel> response) {

                String format = response.body().getFormatted();
                String[] form = format.split(" ");
                String [] splitDate= form[0].split("-");
                searchDate = splitDate[2];
                searchTime = form[1];
                getSearchTime(searchTime, searchDate);

            }

            @Override
            public void onFailure(Call<TimeZoneModel> call, Throwable t) {
                Log.d("timezonenow", "onError: " + t.getMessage());
            }
        });
    }

    private void getSearchTime(String searchTime,String searchDate) {
        kolkataTime(searchTime, searchDate);
        londonTime(searchTime, searchDate);
        germanyTime(searchTime, searchDate);
        melbourneTime(searchTime, searchDate);
        tokyoTime(searchTime, searchDate);
    }

    private void init() {
        london_edt = findViewById(R.id.london_edt);
        germany_edt = findViewById(R.id.germany_edt);
        kolkata_edt = findViewById(R.id.kolkata_edt);
        tokyo_edt = findViewById(R.id.tokyo_edt);
        melobourne_edt = findViewById(R.id.melbourne_edt);
    }


    private void tokyoTime(String searchTime,String searchDate) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+09:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");
        date.setTimeZone(TimeZone.getTimeZone("GMT+09:00"));

        String localTime = date.format(currentLocalTime);
        String dt = String.valueOf(cal.getTime().getDay());

        String result = checkTimeDifference(searchTime,searchDate, dt, localTime);
        tokyo_edt.setText(result);
    }

    private void germanyTime(String searchTime,String searchDate) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+01:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");
        date.setTimeZone(TimeZone.getTimeZone("GMT+01:00"));

        String localTime = date.format(currentLocalTime);
        String dt = String.valueOf(cal.getTime().getDay());

        String result = checkTimeDifference(searchTime,searchDate, dt, localTime);
        germany_edt.setText(result);
    }

    private void melbourneTime(String searchTime,String searchDate) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+11:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");
        date.setTimeZone(TimeZone.getTimeZone("GMT+11:00"));

        String localTime = date.format(currentLocalTime);
        String dt = String.valueOf(cal.getTime().getDay());

        String result = checkTimeDifference(searchTime,searchDate, dt, localTime);
        melobourne_edt.setText(result);
    }

    private void kolkataTime(String searchTime,String searchDate) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");
        date.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));

        String localTime = date.format(currentLocalTime);
        String dt = String.valueOf(cal.getTime().getDay());

        String result = checkTimeDifference(searchTime,searchDate, dt, localTime);
        kolkata_edt.setText(result);
    }

    private void londonTime(String searchTime,String searchDate){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+00:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");
// you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));

        String localTime = date.format(currentLocalTime);
        String dt = String.valueOf(cal.getTime().getDay());

        String result = checkTimeDifference(searchTime,searchDate, dt, localTime);
        london_edt.setText(result);
    }

    private String checkTimeDifference(String searchTime, String searchDate, String zoneDate, String zoneTime) {
        int checkDate = Integer.parseInt(searchDate)-Integer.parseInt(zoneDate);
        if (checkDate == -1){
            String []time= zoneTime.split(":");
            int timePre = Integer.parseInt(time[0]);
            int timePos = Integer.parseInt(time[1]);
            int newTime = timePre+24;
            String [] lonTime = searchTime.split(":");
            int lonPre = Integer.parseInt(lonTime[0]);
            int lonPos = Integer.parseInt(lonTime[1]);

            int diffpre = lonPre - newTime;
            int diffpos = lonPos - timePos;
            if (diffpre > 0){
                time_diff = diffpre+" hours"+diffpos+" minutes";
            }else {
                int newDiff = diffpre *-1;
                time_diff = diffpre+" hours"+diffpos+" minutes";
            }

            if (diffpos>0){
                time_diff = diffpre+" hours"+diffpos+" minutes";
            }else {
                int newDiff = diffpos *-1;
                time_diff = diffpre+" hours"+diffpos+" minutes";
            }


            return time_diff;
        }
        if (checkDate == 1){
            String []time= zoneTime.split(":");
            int timePre = Integer.parseInt(time[0]);
            int timePos = Integer.parseInt(time[1]);
            String [] lonTime = searchTime.split(":");
            int lonPre = Integer.parseInt(lonTime[0]);
            int lonPos = Integer.parseInt(lonTime[1]);
            int newTime = lonPre+24;
            int diffpre = newTime - timePre;
            int diffpos = lonPos - timePos;
            if (diffpre > 0){
                time_diff = diffpre+" hours"+diffpos+" minutes";
            }else {
                int newDiff = diffpre *-1;
                time_diff = newDiff+" hours"+diffpos+" minutes";
            }

            if (diffpos>0){
                time_diff = diffpre+" hours"+diffpos+" minutes";
            }else {
                int newDiff = diffpos *-1;
                time_diff = diffpre+" hours"+newDiff+" minutes";
            }
            Toast.makeText(TimeDifferenceActivity.this, "For", Toast.LENGTH_SHORT).show();
        }
        if (checkDate == 0){
            String []time= searchTime.split(":");
            int timePre = Integer.parseInt(time[0]);
            int timePos = Integer.parseInt(time[1]);

            String [] lonTime = zoneTime.split(":");
            int lonPre = Integer.parseInt(lonTime[0]);
            int lonPos = Integer.parseInt(lonTime[1]);

            int diffpre = lonPre - timePre;
            int diffpos = lonPos - timePos;
            if (diffpre > 0){
                time_diff = diffpre+" hours"+diffpos+" minutes";
            }else {
                int newDiff = diffpre *-1;
                time_diff = diffpre+" hours"+diffpos+" minutes";
            }

            if (diffpos>0){
                time_diff = diffpre+" hours"+diffpos+" minutes";
            }else {
                int newDiff = diffpos *-1;
                time_diff = diffpre+" hours"+newDiff+" minutes";
            }
            return time_diff;
        }
        else {
            Toast.makeText(TimeDifferenceActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
        }
        return time_diff;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }
}