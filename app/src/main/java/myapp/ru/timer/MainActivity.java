package myapp.ru.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView    timer;
    private SeekBar     seekBar;
    private Button      start;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar);
        start = findViewById(R.id.start);

        seekBar.setMax(600);
        seekBar.setProgress(60);
        timer.setText("01:00");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress * 1000);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void start(View view) {
        if (start.getText().equals("START")) {
            start.setText("STOP");
            seekBar.setEnabled(false);
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000,  1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell_sound);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        } else {
            countDownTimer.cancel();
            resetTimer();
        }
    }

    private void updateTimer(long progress) {
        int minutes = (int) progress / 1000 / 60;
        int seconds = (int) progress / 1000 - (minutes * 60);
        String minutesString = "";
        String secondsString = "";

        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = "" + minutes;
        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        timer.setText(minutesString + ":" + secondsString);
    }

    private void resetTimer() {
        start.setText("START");
        timer.setText("01:00");
        seekBar.setEnabled(true);
        seekBar.setProgress(60);
    }
}
