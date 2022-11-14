package com.example.quiz;

import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String data1[],data2[];
    int images[];
    Context context;
    private MyViewHolder holder;
    private int position;

    public MyAdapter(Context ct, String[] s1, String[] s2, int[] image){
        context = ct;
        data1=s1;
        data2=s2;
        images=image;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {
        holder.text1.setText(data1[position]);
        holder.text2.setText(data2[position]);
        holder.image1.setImageResource(images[position]);

        holder.mainlayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context,MainActivity.class);
               // intent.putExtra("data1",data1[position]);
                context.startActivity(intent);


            }
        });



    }

    @Override
    public int getItemCount() {
        return data1.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text1,text2;
        ImageView image1;
        ConstraintLayout mainlayout;

        public  MyViewHolder(@NonNull View itemView){
            super(itemView);

            text1= itemView.findViewById(R.id.textView16);
            text2= itemView.findViewById(R.id.textView15);
            image1= itemView.findViewById(R.id.imageView11);
            mainlayout = itemView.findViewById(R.id.mainlayout);
        }

    }

}
