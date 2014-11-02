package swiatowski.piotr.bibliotekapwr;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import java.util.concurrent.TimeUnit;
import roboguice.activity.RoboActivity;

/**
 * Created by Piotrek on 2014-11-02.
 *
 */
public class SplashActivity extends RoboActivity {

    private static final long DEFAULT_MIN_DISPLAY_MILLIS = TimeUnit.SECONDS.toMillis(2);
    protected long mMinDisplayMillis = DEFAULT_MIN_DISPLAY_MILLIS;

    private void runBackgroundThread() {
        final long startTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    // make sure min display time has elapsed
                    boolean interuppted = false;
                    final long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime())
                            - startTime;
                    if (duration < mMinDisplayMillis) {
                        try {
                            Thread.sleep(mMinDisplayMillis - duration);
                        } catch (InterruptedException e) {
                            Thread.interrupted(); // clear interruption flag
                            interuppted = true;
                        }
                    }

                    if (!interuppted) {
                        startNextActivity();
                        finishThisActivity();
                    }
                } catch (Exception e) {
                    SplashActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }
            }
        }).start();
    }

    protected void startNextActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    protected void finishThisActivity() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        runBackgroundThread();
    }

}