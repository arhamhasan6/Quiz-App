package com.example.quiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.cardview.widget.CardView;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quiz.databinding.FragmentHomeBinding;

import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;


public class HomeFragment extends Fragment implements View.OnClickListener{




    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    CardView maths,drama,science,history,language,puzzle;
    TextView spinwheel;
    FragmentHomeBinding binding;
    FirebaseFirestore database;
    String s1[],s2[];
    private Context context;
    RecyclerView recyclerview;
    private MyAdapter recyclerViewAdapter;
    int images[]={R.drawable.mathematics,R.drawable.language,R.drawable.puzzle,R.drawable.science,R.drawable.drama,R.drawable.history};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        database = FirebaseFirestore.getInstance();

        final ArrayList<category_sample> categories = new ArrayList<>();

        final CategoryAdapter adapter = new CategoryAdapter(getContext(), categories);

        maths = (CardView) binding.maths;
        drama = (CardView) binding.drama;
        science = (CardView) binding.science;
        puzzle = (CardView) binding.puzzle;
        language = (CardView) binding.language;
        history = (CardView) binding.history;
        spinwheel =  binding.spinwheel;

        //maths.setOnClickListener(this);
        maths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), quiz_activity.class);
                intent.putExtra("name","maths");
                intent.putExtra("id", "L1G8ZeOcM29f37qSTO0C");
                startActivity(intent);
            }
        });
        drama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), quiz_activity.class);
                intent.putExtra("name","drama");
                intent.putExtra("id","CAhLngaqy28FPVnNrwi9");
                startActivity(intent);
            }
        });
        science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), quiz_activity.class);
                intent.putExtra("name","science");
                intent.putExtra("id", "mhRrfqkGKGlfva40mZNt");
                startActivity(intent);
            }
        });
        puzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), quiz_activity.class);
                intent.putExtra("name","puzzle");
                intent.putExtra("id","rplmvWtkJ0oLploGVcSe");
                startActivity(intent);
            }
        });
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), quiz_activity.class);
                intent.putExtra("name","language");
                intent.putExtra("id","st9tQdZWP15r1iJyOmVe");
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), quiz_activity.class);
                intent.putExtra("name","history");
                intent.putExtra("id", "xjyOojqkPggpCTNN2P5v");
                startActivity(intent);
            }
        });


        //s1=getResources().getStringArray(R.array.quiz_categories);
       // s2=getResources().getStringArray(R.array.description);
       // MyAdapter myAdapter = new MyAdapter(getActivity().getApplicationContext(),s1,s2,images);
       // recyclerViewAdapter = new MyAdapter(context, s1,s2,images);
      //  binding.categoryList.setAdapter(recyclerViewAdapter);
       // binding.categoryList.setAdapter(myAdapter);
      //  binding.categoryList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        /**database.collection("categories")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        categories.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            category_sample model = snapshot.toObject(category_sample.class);
                            model.setCategoryID(snapshot.getId());
                            categories.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });


        binding.categoryList.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.categoryList.setAdapter(adapter);

        binding.spinwheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SpinnerActivity.class));
            }
        });*/
        spinwheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(getContext(), SpinnerActivity.class));
                Intent intent = new Intent(getActivity(), SpinnerActivity.class);
                startActivity(intent);
           }
        });


        // Inflate the layout for this fragment
        return binding.getRoot();
    }



    @Override
    public void onClick(View v) {
        //Intent intent = new Intent(getActivity(), quiz_activity.class);
       // intent.putExtra("id", binding);
       // intent.putExtra("name",);
       // startActivity(intent);

    }
}