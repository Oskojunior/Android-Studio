package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_EXTRA_ANSWER = "pl.edu.pb.wi.quiz.correctAnswer";
    private static final String QUIZ_TAG = "MainActivity";
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final int REQUEST_CODE_PROMPT = 0;
    private Question[] questions = new Question[] {
            new Question(R.string.q_cola, false),
            new Question(R.string.q_chips, false),
            new Question(R.string.q_fish, true),
            new Question(R.string.q_rice, true),
            new Question(R.string.q_candy, false)
    };
    private int currentIndex = 0;
    private Button nextButton;
    private Button falseButton;
    private Button trueButton;
    private Button helpButton;
    private TextView helloText;
    private boolean answerWasShown;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) return;
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG, "Wywołana została metoda cyklu życia: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(QUIZ_TAG, "Wywołana została metoda cyklu życia: onStart");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(QUIZ_TAG, "Wywołana została metoda cyklu życia: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(QUIZ_TAG, "Wywołana została metoda cyklu życia: onDestroy");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(QUIZ_TAG, "Wywołana została metoda cyklu życia: onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(QUIZ_TAG, "Wywołana została metoda cyklu życia: onPause");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG, "Wywołana została metoda cyklu życia: onCreate");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        nextButton = findViewById(R.id.next_button);
        falseButton = findViewById(R.id.false_button);
        trueButton = findViewById(R.id.true_button);
        helloText = findViewById(R.id.Hello);
        helpButton = findViewById(R.id.button_help);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
    });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerWasShown = false;
                currentIndex = (currentIndex + 1)%questions.length;
                setNextQuestion();
            }
        });
        helpButton.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);
            }
        });
        setNextQuestion();
    }

    private void setNextQuestion(){
        helloText.setText(questions[currentIndex].getQuestionId());
    }

    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId;
        if (answerWasShown) {
            resultMessageId = R.string.answer_was_shown;
        } else {

            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correctAnswer;
            } else {
                resultMessageId = R.string.incorrectAnswer;
            }
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();

    }
}