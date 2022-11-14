package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.databinding.ActivityQuizBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class quiz_activity extends AppCompatActivity {

    ActivityQuizBinding binding;

    ArrayList<Question> questions;
    int index = 0;
    Question question;
    CountDownTimer timer;
    FirebaseFirestore database;
    int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());;
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String id = intent.getStringExtra("id");


        questions = new ArrayList<>();
        database = FirebaseFirestore.getInstance();


        Random random = new Random();
        final int rand = random.nextInt(5);

        database.collection("categories")
                .document(id)
                .collection("questions")
                .whereGreaterThanOrEqualTo("index", rand)
                .orderBy("index")
                .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.getDocuments().size() < 5) {
                            database.collection("categories")
                                    .document(id)
                                    .collection("questions")
                                    .whereLessThanOrEqualTo("index", rand)
                                    .orderBy("index")
                                    .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            ArrayList<ArrayList<String> > x = new ArrayList<ArrayList<String> >();
                                            for(DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                                Question question = snapshot.toObject(Question.class);
                                                System.out.println(question);
                                                questions.add(question);
                                                x.add(new ArrayList<String>(Arrays.asList(question.getQuestion(),question.getOption1(),question.getOption2(),question.getOption3(),question.getOption4(),question.getAnswer())));

                                                binding.question.setText(x.get(0).get(0));
                                                binding.optionA.setText(x.get(0).get(1));
                                                binding.optionB.setText(x.get(0).get(2));
                                                binding.optionC.setText(x.get(0).get(3));
                                                binding.optionD.setText(x.get(0).get(4));
                                            }
                                           setNextQuestion();
                                        }
                                    });
                        } else {
                            for(DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                ArrayList<ArrayList<String> > x = new ArrayList<ArrayList<String> >();
                                Question question = snapshot.toObject(Question.class);
                                questions.add(question);
                                x.add(new ArrayList<String>(Arrays.asList(question.getQuestion(),question.getOption1(),question.getOption2(),question.getOption3(),question.getOption4(),question.getAnswer())));

                                binding.question.setText(x.get(0).get(0));
                                binding.optionA.setText(x.get(0).get(1));
                                binding.optionB.setText(x.get(0).get(2));
                                binding.optionC.setText(x.get(0).get(3));
                                binding.optionD.setText(x.get(0).get(4));
                            }
                           setNextQuestion();
                        }
                    }
                });



        resetTimer();

    }

    void resetTimer() {
        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

            }
        };
    }

    void showAnswer() {
        if(question.getAnswer().equals(binding.optionA.getText().toString()))
            binding.optionA.setBackground(getResources().getDrawable(R.drawable.correct));
        else if(question.getAnswer().equals(binding.optionB.getText().toString()))
            binding.optionB.setBackground(getResources().getDrawable(R.drawable.correct));
        else if(question.getAnswer().equals(binding.optionC.getText().toString()))
            binding.optionC.setBackground(getResources().getDrawable(R.drawable.correct));
        else if(question.getAnswer().equals(binding.optionD.getText().toString()))
            binding.optionD.setBackground(getResources().getDrawable(R.drawable.correct));
    }

    void setNextQuestion() {
        if(timer != null)
            timer.cancel();

        timer.start();
        if(index < questions.size()) {
            binding.counter.setText(String.format("%d/%d", (index+1), questions.size()));
            question = questions.get(index);

            ArrayList<ArrayList<String> > x = new ArrayList<ArrayList<String> >();
            x.add(new ArrayList<String>(Arrays.asList(question.getQuestion(),question.getOption1(),question.getOption2(),question.getOption3(),question.getOption4(),question.getAnswer())));
            binding.question.setText(x.get(0).get(0));
            binding.optionA.setText(x.get(0).get(1));
            binding.optionB.setText(x.get(0).get(2));
            binding.optionC.setText(x.get(0).get(3));
            binding.optionD.setText(x.get(0).get(4));
        }
    }

    void checkAnswer(TextView textView) {
        String selectedAnswer = textView.getText().toString();
        if(selectedAnswer.equals(question.getAnswer())) {
            correctAnswers++;
            textView.setBackground(getResources().getDrawable(R.drawable.correct));
        } else {
            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.wrong));
        }
    }

    void reset() {
        binding.optionA.setBackground(getResources().getDrawable(R.drawable.unselected));
        binding.optionB.setBackground(getResources().getDrawable(R.drawable.unselected));
        binding.optionC.setBackground(getResources().getDrawable(R.drawable.unselected));
        binding.optionD.setBackground(getResources().getDrawable(R.drawable.unselected));
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.option_a:
            case R.id.option_b:
            case R.id.option_c:
            case R.id.option_d:
                if(timer!=null)
                    timer.cancel();
                TextView selected = (TextView) view;
                checkAnswer(selected);

                break;
            case R.id.next_btn:
                reset();
                if(index <= questions.size()) {
                    index++;
                    setNextQuestion();
                } else {
                    Intent intent = new Intent(quiz_activity.this, ResultActivity.class);
                    intent.putExtra("correct", correctAnswers);
                    intent.putExtra("total", questions.size());
                    startActivity(intent);
                    Toast.makeText(this, "Quiz Finished.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
