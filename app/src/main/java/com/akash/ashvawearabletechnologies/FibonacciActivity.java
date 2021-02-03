package com.akash.ashvawearabletechnologies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class FibonacciActivity extends AppCompatActivity {
    private TextView fibon_txt, fibon_res;
    int n1=0,n2=1,n3=0;
    int count = 0;
    String sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fibonacci);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        sum = bundle.getString("RESULT");
        fibon_txt = findViewById(R.id.sum_of_time);
        fibon_res = findViewById(R.id.fibon_result);
        fibon_txt.setText("The fibonacci number for\n"+sum+" is");

        count=Integer.parseInt(sum);
        findfibonacci(count-1);

    }

    private void findfibonacci(int parseInt) {

        if(parseInt>0){
            n3 = n1 + n2;
            n1 = n2;
            n2 = n3;
            findfibonacci(parseInt-1);
        }
        else{
            fibon_res.setText(String.valueOf(n3));
        }

    }


}