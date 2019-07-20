package test.quiz.com.quiztest;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    CheckBox option1,option2,option3,option4;
    Button submitQuestion;
    TextView queDisplay,queID;
    int queNumber = 0,answerSelected = 0;
    String [] questionThread;
    String [] separateThread = new String[7];

    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //Create database,EmployeeDB database name
        db=openOrCreateDatabase("QuestionsDB", Context.MODE_PRIVATE, null);
        //create table Employee


        if (savedInstanceState != null ){
            queNumber = savedInstanceState.getInt("queNumber");
            answerSelected = savedInstanceState.getInt("answerSelected");
            questionThread = savedInstanceState.getStringArray("questionThread");
        }
        else {
            Cursor c = db.rawQuery("SELECT * FROM Questions WHERE QueVisit=0", null);
        /*if(c.getCount() == 0)
        {
            Toast.makeText(QuizActivity.this,"No data Found",Toast.LENGTH_SHORT).show();
            return;
        }*/
            questionThread = new String[10];  /*10 is the max number of question*/
            int i = 0;
            while(c.moveToNext()){
                questionThread[i] =  c.getInt(0) + ",," + c.getString(1);
                i++;
            }
        }
        submitQuestion = findViewById(R.id.idSubmitQue);
        queDisplay = findViewById(R.id.idQueDisplay);
        queID = findViewById(R.id.idQue);
        option1 = findViewById(R.id.idOption1);
        option2 = findViewById(R.id.idOption2);
        option3 = findViewById(R.id.idOption3);
        option4 = findViewById(R.id.idOption4);

        /*Setting Display*/

        setData();

        /*User Selection of Options*/
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerSelected = 1;
                option1.setChecked(true);
                option2.setChecked(false);
                option3.setChecked(false);
                option4.setChecked(false);
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerSelected = 2;
                option1.setChecked(false);
                option2.setChecked(true);
                option3.setChecked(false);
                option4.setChecked(false);
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerSelected = 3;
                option1.setChecked(false);
                option2.setChecked(false);
                option3.setChecked(true);
                option4.setChecked(false);
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerSelected = 4;
                option1.setChecked(false);
                option2.setChecked(false);
                option3.setChecked(false);
                option4.setChecked(true);
            }
        });
        /*User hit on Submit Button*/
        submitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*No option Selected*/
                if (answerSelected == 0){
                    Toast.makeText(QuizActivity.this,"Select any Option",Toast.LENGTH_SHORT).show();
                }
                /*any option selected*/
                else {
                    /*Disabling submit button*/
                    submitQuestion.setEnabled(false);
                    /*Disabling all options*/
                    option1.setEnabled(false);
                    option2.setEnabled(false);
                    option3.setEnabled(false);
                    option4.setEnabled(false);

                    /*applying green color to correct option*/
                    if (Integer.valueOf(separateThread[1]) == 1) {
                        setGreenBackground(option1);
                    }
                    if (Integer.valueOf(separateThread[1]) == 2) {
                        setGreenBackground(option2);
                    }
                    if (Integer.valueOf(separateThread[1]) == 3) {
                        setGreenBackground(option3);
                    }
                    if (Integer.valueOf(separateThread[1]) == 4) {
                        setGreenBackground(option4);
                    }

                    /*User selected the correct option*/
                    if (answerSelected == Integer.valueOf(separateThread[1])) {

                        /*forward to next question after 2 seconds*/
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                queNumber++;

                                option1.setChecked(false);
                                option2.setChecked(false);
                                option3.setChecked(false);
                                option4.setChecked(false);

                                setDefaultBackground(option1);
                                setDefaultBackground(option2);
                                setDefaultBackground(option3);
                                setDefaultBackground(option4);

                                answerSelected = 0;

                                option1.setEnabled(true);
                                option2.setEnabled(true);
                                option3.setEnabled(true);
                                option4.setEnabled(true);

                                submitQuestion.setEnabled(true);

                                setData();
                            }
                        }, 800);
                    }
                    /*User selected Incorrect options*/
                    else {
                        /*applying red color to User selected option*/
                        if (answerSelected == 1) {
                            setRedBackground(option1);
                        }
                        if (answerSelected == 2) {
                            setRedBackground(option2);
                        }
                        if (answerSelected == 3) {
                            setRedBackground(option3);
                        }
                        if (answerSelected == 4) {
                            setRedBackground(option4);
                        }

                        /*forward to GameOverActivity after 2 seconds*/
                        Handler handler1 = new Handler();
                        handler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                game over here
                            }
                        }, 800);
                    }

                }
            }
        });

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("queNumber",queNumber);
        outState.putInt("answerSelected",answerSelected);
        outState.putStringArray("questionThread",questionThread);
        Log.d("event: ","*************************In onSaved*******************");
    }

    private void setRedBackground(final View v) {
        v.setBackground(getResources().getDrawable(R.drawable.f));
    }
    private void setGreenBackground(final View v) {
        v.setBackground(getResources().getDrawable(R.drawable.e));
    }
    private void setDefaultBackground(final View v) {
        v.setBackground(getResources().getDrawable(R.drawable.round_button));
    }

    @SuppressLint("SetTextI18n")
    private void setData() {

        queID.setText("Que " + (queNumber + 1));
        separateThread = questionThread[queNumber].split(",,");
        queDisplay.setText(separateThread[2]);
        option1.setText("A. " + separateThread[3]);
        option2.setText("B. " + separateThread[4]);
        option3.setText("C. " + separateThread[5]);
        option4.setText("D. " + separateThread[6]);

        db.execSQL("UPDATE Questions SET QueVisit=1 WHERE QueId=" + separateThread[0]);

    }
    /*public static void RandomizeArray(String[] array){
        Random rgen = new Random();  // Random number generator

        for (int i=0; i<array.length; i++) {
            int randomPosition = rgen.nextInt(array.length);
            String temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }
    }*/
}
