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

import java.util.Random;

//class problem{
//        Random randInt = new Random();
//        int first = randInt.nextInt(26);
//        int second = randInt.nextInt(26);
//        int answer = first + second;
//        int[] answerList = new int[4];
//        int answerLocation = randInt.nextInt(4);
//
//public int [] generateProblem(){
//        for(int i = 0; i < 4; i++){
//        if(i == answerLocation)
//        answerList[i] = answer;
//        else{
//        int fakeAnswer = randInt.nextInt(52);
//        while(fakeAnswer != answer)
//        fakeAnswer = randInt.nextInt(52);
//        answerList[i] = fakeAnswer;
//        }
//        }
//        return answerList;
//        }
//        }

public class MainActivity extends AppCompatActivity {



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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void startGame(View v){
        startButton.setVisibility(View.INVISIBLE);
        startButton.setAlpha(0f);
        answerLayout.setVisibility(View.VISIBLE);
        headerLayout.setVisibility(View.VISIBLE);
        answerStatus.setVisibility(View.INVISIBLE);



        totalComplete = 0;
        totalCorrect = 0;
        currentScore.setText("0/0");

        generateProblem();

        timer = new CountDownTimer(31200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //Log.i("Timer", Long.toString(millisUntilFinished/1000));
                timeLeft.setText(Long.toString((millisUntilFinished/1000) - 1) + "s");
            }

            @Override
            public void onFinish() {
                String finalMessage = "Congratulations, you got " + "\n" + currentScore.getText() + "\n" + "Correct!" + "\n \n" + "Click here to Play again !!";
                startButton.setTextSize(20f);
                startButton.setText(finalMessage);
                startButton.setVisibility(View.VISIBLE);
                startButton.animate().alpha(.75f).setDuration(500);
            }
        }.start();
    }

    public void generateProblem(){
        Random randInt = new Random();
        int first = randInt.nextInt(26);
        int second = randInt.nextInt(26);
        int answer = first + second;
        int[] answerList = new int[4];
        answerLocation = randInt.nextInt(4);

        for(int i = 0; i < 4; i++){
            if(i == answerLocation) {
                answerList[i] = answer;
                //Log.i("Location", Integer.toString(i));
                //Log.i("real answer", Integer.toString(answer));
            }else{
                int fakeAnswer = randInt.nextInt(52);
                while(fakeAnswer == answer)
                    fakeAnswer = randInt.nextInt(52);
                answerList[i] = fakeAnswer;
                //Log.i("fakeanswer", Integer.toString(fakeAnswer));
            }

        }

        if(Integer.toString(answerList[0]).length() == 1) {
            answer1.setText("0" + Integer.toString(answerList[0]));
        }else {
            answer1.setText(Integer.toString(answerList[0]));
        }
        if(Integer.toString(answerList[1]).length() == 1) {
            answer2.setText("0" + Integer.toString(answerList[1]));
        }else {
            answer2.setText(Integer.toString(answerList[1]));
        }
        if(Integer.toString(answerList[2]).length() == 1) {
            answer3.setText("0" + Integer.toString(answerList[2]));
        }else {
            answer3.setText(Integer.toString(answerList[2]));
        }
        if(Integer.toString(answerList[3]).length() == 1) {
            answer4.setText("0" + Integer.toString(answerList[3]));
        }else {
            answer4.setText(Integer.toString(answerList[3]));
        }


        currentProblemDisplay.setText(Integer.toString(first) + " + " + Integer.toString(second));
    }

    public void selectAnswer(View v){

        totalComplete++;
        if(Integer.parseInt(v.getTag().toString())-1 == answerLocation) {
            totalCorrect++;
            answerStatus.setText("Correct!");
        }else{
            answerStatus.setText("Incorrect ...");
        }
        answerStatus.setVisibility(View.VISIBLE);
        currentScore.setText(Integer.toString(totalCorrect) + "/" + Integer.toString(totalComplete));
        generateProblem();


    }
}
