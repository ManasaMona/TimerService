package com.example.manasaa.timer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG="MainActivityClass";
    private TextView mTimer1,mTimer2;
    private Fragment fragment1,fragment2;
    FragmentManager fragmentManager = getFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"called ONCREATE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimer1=(TextView)findViewById(R.id.timer1);
        mTimer2=(TextView)findViewById(R.id.timer2);

        fragment1= fragmentManager.findFragmentById(R.id.fragment_timer1);
        fragment2= fragmentManager.findFragmentById(R.id.fragment_timer2);

        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .show(fragment1)
                .hide(fragment2)
                .commit();

        mTimer1.setOnClickListener(this);
        mTimer2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG,"called ONCLICK");
        int id=v.getId();
        switch (id){
            case R.id.timer1: addingFragment(v);
                                 break;
            case R.id.timer2: addingFragment(v);
                                 break;

        }

    }

    private void addingFragment(View v){
        Log.d(TAG,"called ADDINGFRAGMENT");
        Fragment fragment;
        if(v.getId()==R.id.timer1){
            Log.d(TAG,"called ADDINGFRAGMENT1");

            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .hide(fragment2)
                    .show(fragment1)
                    .commit();

        }
        else {
            Log.d(TAG,"called ADDINGFRAGMENT2");
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .show(fragment2)
                    .hide(fragment1)
                    .commit();


        }



    }


}
