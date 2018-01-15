package rodrigodavy.com.github.wiiugamepadbruteforcesync;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> suitList = new ArrayList<>();
    private CountDownTimer timer;
    private int position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSuitList();

        TextView suitTextView = (TextView) findViewById(R.id.suits_text_view);
        suitTextView.setText(suitList.get(position));

        final TextView timerTextView = (TextView) findViewById(R.id.timer);

        timer = new CountDownTimer(180000, 1000) {

            private boolean alert = false;

            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished / 60000) +
                        ":" + String.format("%02d",(millisUntilFinished / 1000 - (millisUntilFinished / 60000)*60)));

                if( (millisUntilFinished<16000) && (!alert) ) {
                    timerTextView.setTextColor(Color.RED);
                }
            }

            public void onFinish() {
                timerTextView.setText(R.string.restart_sync);
            }

        };
    }

    public void next(View v) {
        if(position<255)
            position++;
        else
            position=0;

        TextView suitTextView = (TextView) findViewById(R.id.suits_text_view);
        suitTextView.setText(suitList.get(position));

        TextView positionTextView = (TextView) findViewById(R.id.position_text_view);
        positionTextView.setText(String.valueOf(position+1) + "/256");
    }

    public void resetTimer(View v) {
        timer.cancel();

        TextView timerTextView = (TextView) findViewById(R.id.timer);
        timerTextView.setTextColor(Color.GRAY);
        
        ((Button) v).setText(R.string.reset);

        timer.start();
    }

    private void initSuitList() {
        char[] suits = {'♠','♥','♦','♣'};

        for(char i:suits) {
            for(char j:suits) {
                for(char k:suits) {
                    for(char l:suits) {
                        suitList.add(String.valueOf(i) + String.valueOf(j) +
                                String.valueOf(k) + String.valueOf(l));
                    }
                }
            }
        }
    }
}
