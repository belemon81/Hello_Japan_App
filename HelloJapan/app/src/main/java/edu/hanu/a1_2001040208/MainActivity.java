package edu.hanu.a1_2001040208;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // GET VIEWS
        ScrollView alphabetH = findViewById(R.id.scrollViewH);
        ScrollView alphabetK = findViewById(R.id.scrollViewK);
        TableLayout tableH = findViewById(R.id.tableLayoutH);
        TableLayout tableK = findViewById(R.id.tableLayoutK);
        LinearLayout titleH = findViewById(R.id.hiraganaTitle);
        LinearLayout titleK = findViewById(R.id.katakanaTitle);
        Button hiraganaSwitch = findViewById(R.id.hiraganaSwitch);
        Button katakanaSwitch = findViewById(R.id.katakanaSwitch);

        // GET CHARACTER BUTTONS (NOT INCLUDE EMPTY BUTTON)
        ArrayList<ImageButton> allCharButtons = getAllCharButtons(tableH);
        allCharButtons.addAll(getAllCharButtons(tableK));

        // INITIALIZE STATES
        mediaPlayer = MediaPlayer.create(this,R.raw.a);
        hiraganaSwitch.setBackgroundColor(Color.parseColor("#6495ED"));
        katakanaSwitch.setBackgroundColor(Color.parseColor("#CCCCCC"));
        alphabetK.setVisibility(View.GONE);

        // SET ONCLICK LISTENER
        hiraganaSwitch.setOnClickListener(view -> {
            katakanaSwitch.setBackgroundColor(Color.parseColor("#CCCCCC"));
            hiraganaSwitch.setBackgroundColor(Color.parseColor("#6495ED"));
            alphabetH.setVisibility(View.VISIBLE);
            titleH.animate().alpha(1).setDuration(1000);
            titleK.animate().alpha(0).setDuration(1000);
            alphabetK.animate().alpha(0).setDuration(1000).withEndAction(()->
                    alphabetK.setVisibility(View.GONE)
            );
            alphabetH.animate().alpha(1).setDuration(1000);
        });

        katakanaSwitch.setOnClickListener(view -> {
            katakanaSwitch.setBackgroundColor(Color.parseColor("#6495ED"));
            hiraganaSwitch.setBackgroundColor(Color.parseColor("#CCCCCC"));
            titleK.animate().alpha(1).setDuration(1000);
            titleH.animate().alpha(0).setDuration(1000);
            alphabetK.setVisibility(View.VISIBLE);
            alphabetH.animate().alpha(0).setDuration(1000).withEndAction(() ->
                    alphabetH.setVisibility(View.GONE)
            );
            alphabetK.animate().alpha(1).setDuration(1000);
        });

        for (ImageButton charButton: allCharButtons) {
            charButton.setOnClickListener(view -> {
//                int viewId = charButton.getId();
//                String viewIdString = MainActivity.this.getResources().getResourceName(viewId);
//                viewIdString = viewIdString.substring(viewIdString.lastIndexOf("/")+3);
//                viewId = getResources().getIdentifier(viewIdString,"raw",getPackageName());

//                String contentDescription = charButton.getContentDescription().toString();
//                int viewId = getResources().getIdentifier(contentDescription,"raw",getPackageName());

                charButton.animate().scaleXBy(-0.2f).scaleYBy(-0.2f).setDuration(100).withEndAction(() ->
                        charButton.animate().scaleXBy(0.2f).scaleYBy(0.2f).setDuration(100));
                int viewId = charButton.getId();
                mediaPlayer.reset();
                mediaPlayer = MediaPlayer.create(MainActivity.this,viewId);
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            });
        }
    }

    public static ArrayList<ImageButton> getAllCharButtons(TableLayout table) {
        ArrayList<ImageButton> list = new ArrayList<>();
        for (int i = 0; i < table.getChildCount(); i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                ImageButton button = (ImageButton) row.getChildAt(j);
                if (!button.getContentDescription().toString().equals("empty")) {
                    list.add(button);
                }
            }
        }
        return list;
    }
}