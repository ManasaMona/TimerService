package com.example.manasaa.timer;


import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Timer1Fragment extends Fragment implements View.OnClickListener{
    private static final String TAG="TIMER_1_FRAGEMNTClass";
    private EditText mEditText;
    private Button mStartButton,mStopButton;
    private String mInputTime;
    private Intent mIntentService;
    MyReceiver myReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //registering broadcastReceiver for getting time
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getString(R.string.timerUpdateService));
        intentFilter.addAction(getString(R.string.timerOnFinish));
        getActivity().registerReceiver(myReceiver, intentFilter);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"called ONCREATEVIEW");
        View v=inflater.inflate(R.layout.fragment_timer1, container, false);

        mStartButton=(Button)v.findViewById(R.id.startButton);
        mStopButton=(Button)v.findViewById(R.id.stopButton);
        mEditText=(EditText)v.findViewById(R.id.displayTime);

        mStartButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        //setting service
        mIntentService = new Intent(getActivity(), CountDownTimerService1.class);
        Log.d(TAG,"called ONCLICK");
        int id=v.getId();
        switch (id){
            case R.id.startButton:
                                mInputTime= mEditText.getText().toString();
                                if(mInputTime.length()!=0) {
                                    int time = Integer.parseInt(mInputTime);
                                    Log.d(TAG, time + "");
                                    mIntentService.putExtra("GIVEN_INPUT", time);
                                    getActivity().startService(mIntentService);
                                }

                                break;
            case R.id.stopButton:
                                Log.d(TAG,"called STOPBuutton");
                                CountDownTimerService1.cancelTimer();

                                getActivity().stopService(mIntentService);
                                 break;
        }

    }


     class MyReceiver extends BroadcastReceiver{
         private static final String TAG="TIMER_1_RECIEVERClass";

         @Override
         public void onReceive(Context context, Intent intent) {
             if(intent.getAction().equals(getString(R.string.timerUpdateService))) {
                 Long second = intent.getLongExtra("EACH_SECOND", 0);
                 Log.d(TAG, second + " second in reciver");
                 mEditText.setText(second + "");
             }
             if(intent.getAction().equals(getString(R.string.timerOnFinish))){
                 String timeup = intent.getStringExtra("TIMECOMPLETED");
                 mEditText.setText(timeup);
             }
         }
     }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myReceiver);

    }
}
