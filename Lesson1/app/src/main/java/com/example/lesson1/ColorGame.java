package com.example.lesson1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ColorGame extends AppCompatActivity implements View.OnClickListener {
    Button[] buttons;
    Button lastPressed;
    ArrayList<Integer> colors;
    int pairsFound, lastColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_game);
        buttons = new Button[16];
        colors = new ArrayList<>();
        initButtons();
        pairsFound = 0;

    }

    private void initButtons() {
        buttons[0] = findViewById(R.id.CGButton1);
        buttons[1] = findViewById(R.id.CGButton2);
        buttons[2] = findViewById(R.id.CGButton3);
        buttons[3] = findViewById(R.id.CGButton4);
        buttons[4] = findViewById(R.id.CGButton5);
        buttons[5] = findViewById(R.id.CGButton6);
        buttons[6] = findViewById(R.id.CGButton7);
        buttons[7] = findViewById(R.id.CGButton8);
        buttons[8] = findViewById(R.id.CGButton9);
        buttons[9] = findViewById(R.id.CGButton10);
        buttons[10] = findViewById(R.id.CGButton11);
        buttons[11] = findViewById(R.id.CGButton12);
        buttons[12] = findViewById(R.id.CGButton13);
        buttons[13] = findViewById(R.id.CGButton14);
        buttons[14] = findViewById(R.id.CGButton15);
        buttons[15] = findViewById(R.id.CGButton16);

        Random rnd = new Random();
        for (int i = 0; i < 8; i++) {
            int col = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            colors.add(col);
            colors.add(col);
        }
        Collections.shuffle(colors);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int curColor = 0;
        if (lastPressed != null) {
            for (int i = 0; i < buttons.length; i++) {
                if (v.equals(buttons[i])) {
                    Drawable buttonDrawable = DrawableCompat.wrap(v.getBackground());
                    buttonDrawable.setTint(colors.get(i));
                    v.setBackground(buttonDrawable);
                    curColor = colors.get(i);
                    break;
                }
            }

            if (lastColor == curColor) {
                lastPressed.setEnabled(false);
                v.setEnabled(false);
                lastPressed = null;
                pairsFound++;
                if(pairsFound == 8){
                    Toast.makeText(this, "You Won!", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(),LandingPage.class);
                            startActivity(intent);
                        }
                    }, 1500); // 5000ms delay
                }
            } else {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lastPressed.setBackgroundColor(getResources().getColor(R.color.purple_500, null));
                        v.setBackgroundColor(getResources().getColor(R.color.purple_500, null));
                        lastPressed = null;
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    }

                }, 1500); // 5000ms delay

            }
        } else {
            lastPressed = (Button) v;
            for (int i = 0; i < buttons.length; i++) {
                if (v.equals(buttons[i])) {
                    Drawable buttonDrawable = DrawableCompat.wrap(v.getBackground());
                    buttonDrawable.setTint(colors.get(i));
                    v.setBackground(buttonDrawable);
                    lastColor = colors.get(i);
                    break;
                }
            }
        }
    }
}