package rodrigodavy.com.github.wiiugamepadbruteforcesync;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private Uri helpPage = Uri.parse("https://github.com/RodrigoDavy/Wii-U-Gamepad-Brute-Force-Sync/blob/master/README.md");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                Intent intent = new Intent(Intent.ACTION_VIEW,helpPage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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
