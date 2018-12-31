package com.johncarden.additionbraintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    // Define variables for use throughout class in various methods
    GridLayout answerLayout;
    LinearLayout headerLayout;

    Button startButton;
    Button answer1;
    Button answer2;
    Button answer3;
    Button answer4;

    TextView currentProblemDisplay;
    TextView answerStatus;
    TextView timeLeft;
    TextView currentScore;

    CountDownTimer timer;
    int totalCorrect;
    int totalComplete;
    int answerLocation;
    boolean freeze = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign variables to views within the app layout
        answerLayout = (GridLayout) findViewById(R.id.answerLayout);
        headerLayout =  (LinearLayout) findViewById(R.id.headerLayout);
        startButton =  (Button) findViewById(R.id.startButton);
        answer1 =  (Button) findViewById(R.id.answer1);
        answer2 =  (Button) findViewById(R.id.answer2);
        answer3 =  (Button) findViewById(R.id.answer3);
        answer4 =  (Button) findViewById(R.id.answer4);
        currentProblemDisplay = (TextView) findViewById(R.id.currentProblem);
        answerStatus = (TextView) findViewById(R.id.answerCheck);
        timeLeft = (TextView) findViewById(R.id.timeRemaining);
        currentScore = (TextView) findViewById(R.id.currentScore);

    }


    // Sets layout elements visible for execution of game, starts a round of the game
    public void startGame(View v){
        startButton.setVisibility(View.INVISIBLE);
        startButton.setAlpha(0f);
        answerLayout.setVisibility(View.VISIBLE);
        headerLayout.setVisibility(View.VISIBLE);
        answerStatus.setVisibility(View.INVISIBLE);

        // unfreeze the layout so answers can be selected and problems can be generated
        freeze = false;

        // reset round stats
        totalComplete = 0;
        totalCorrect = 0;
        currentScore.setText("0/0");

        generateProblem();


        timer = new CountDownTimer(31100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft.setText(Long.toString((millisUntilFinished/1000) - 1) + "s");
            }

            @Override
            public void onFinish() {
                String finalMessage = "Congratulations! " + "\n" + "Your Score:  " + currentScore.getText() + "\n \n" + "Click here to Play again !!";
                startButton.setTextSize(20f);
                startButton.setText(finalMessage);
                startButton.setVisibility(View.VISIBLE);
                startButton.animate().alpha(.75f).setDuration(500);
                answerStatus.setText("Done");
                freeze = true;
            }
        }.start();
    }


    public void generateProblem(){

            Random randInt = new Random();
            int first = randInt.nextInt(21);
            int second = randInt.nextInt(21);
            int answer = first + second;
            ArrayList<Integer> answerList = new ArrayList<>();
            answerLocation = randInt.nextInt(4);


            // store the correct answer and store and produce 3 alternative answers into an array data structure
            for (int i = 0; i < 4; i++) {
                if (i == answerLocation) {

                    answerList.add(answer);

                } else {

                    int fakeAnswer = randInt.nextInt(41);
                    while (fakeAnswer == answer)
                        fakeAnswer = randInt.nextInt(41);
                    answerList.add(fakeAnswer);

                }
            }

            answer1.setText(Integer.toString(answerList.get(0)));
            answer2.setText(Integer.toString(answerList.get(1)));
            answer3.setText(Integer.toString(answerList.get(2)));
            answer4.setText(Integer.toString(answerList.get(3)));

            currentProblemDisplay.setText(Integer.toString(first) + " + " + Integer.toString(second));

    }


    // evaluate the answer chosen by the user to check for correctness.  Update the user score based on the result and generate the next problem
    public void selectAnswer(View v){
        if(!freeze) {
            totalComplete++;

            if (Integer.parseInt(v.getTag().toString()) - 1 == answerLocation) {

                totalCorrect++;
                answerStatus.setText("Correct!");

            } else {

                answerStatus.setText("Incorrect ...");

            }

            answerStatus.setVisibility(View.VISIBLE);
            currentScore.setText(Integer.toString(totalCorrect) + "/" + Integer.toString(totalComplete));
            generateProblem();
        }else{}
    }
}
