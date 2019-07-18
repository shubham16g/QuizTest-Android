package test.quiz.com.quiztest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        db = openOrCreateDatabase("QuestionsDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Questions(QueId INTEGER PRIMARY KEY AUTOINCREMENT,QueData VARCHAR,QueVisit INTEGER);");

        /*
         * Getting Question Data from Resource File (questionfile.xml) and randomizing it
         */
        serverQuestions = getResources().getStringArray(R.array.QueList);
        /*RandomizeArray(serverQuestions);*/

        db.execSQL("DELETE FROM Questions;");

        int maxServerQuestion = 10;

        for (int i = 0; i < maxServerQuestion; i++) {
            db.execSQL("INSERT INTO Questions(QueData,QueVisit)VALUES('" + serverQuestions[i] + "'," + 0 + ");");
        }

/*/////////////////////////////////////////////////////////////////////////////////////////*/
        instnatQuiz = findViewById(R.id.idInstant);
        exit = findViewById(R.id.idExit);

        instnatQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this,QuizActivity.class));
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
