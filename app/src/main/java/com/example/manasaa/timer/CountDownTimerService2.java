package com.example.manasaa.timer;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class CountDownTimerService2 extends Service {
    private static final String TAG="CountDownTimerService2";
    private int mGivenInputTime;
    private static  MyCountDownTimer2 mMyCountDownTimer;
    private static boolean isRunningTimer2;

    @Override
    public void onCreate() {
        Log.d(TAG,"called ONCREATE");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isRunningTimer2) {
            isRunningTimer2=true;
            Log.d(TAG, "called onStartCommand");
            mGivenInputTime = intent.getIntExtra("GIVEN_INPUT_2", 1);
            Log.d(TAG, mGivenInputTime + "---");
            mMyCountDownTimer = new MyCountDownTimer2(mGivenInputTime * 1000, 1000);
            mMyCountDownTimer.start();
        }

        return START_NOT_STICKY;
    }

    static void cancelTimer(){
        Log.d(TAG,"called CANCEL TIMER");
        mMyCountDownTimer.cancel();
        isRunningTimer2=false;

    }

    public class MyCountDownTimer2 extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer2(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long eachSecond=millisUntilFinished/1000;
            updateToFragment(eachSecond);
            Log.d(TAG,eachSecond+"-----");
        }

        private void updateToFragment(long eachSecond) {//sending broadcast to display in Edit text field
            Intent fragmentIntent = new Intent();
            fragmentIntent.setAction(getString(R.string.timer2UpdateService));
            fragmentIntent.putExtra("EACH_SECOND_2",eachSecond);
            sendBroadcast(fragmentIntent);
        }

        @Override
        public void onFinish() {
            Toast.makeText(getApplicationContext(), "TIME is Completed", Toast.LENGTH_SHORT).show();
            Intent fragmentIntent = new Intent();
            fragmentIntent.setAction(getString(R.string.timer2OnFinish));
            CountDownTimerService2.isRunningTimer2=false;
            fragmentIntent.putExtra("TIMECOMPLETED2","Time is completed");
            sendBroadcast(fragmentIntent);
            stopSelf();

        }
    }

}
