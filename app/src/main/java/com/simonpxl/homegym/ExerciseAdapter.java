package com.simonpxl.homegym;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.simonpxl.homegym.Sqlite.Exercise;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    final private RecyclerViewClickListener mListener;
    private List<Exercise> exerciseList;

    public class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView name, targetArea, sets, reps, weight;
        public ImageView image;

        ExerciseViewHolder(View v){
            super(v);
            v.setOnClickListener(this);

            name = (TextView) v.findViewById(R.id.name);
            targetArea = (TextView) v.findViewById(R.id.targetArea);
            sets = (TextView) v.findViewById(R.id.sets);
            reps = (TextView) v.findViewById(R.id.reps);
            weight = (TextView) v.findViewById(R.id.weight);
            image = (ImageView) v.findViewById(R.id.item_icon);
        }

        @Override
        public void onClick(View view){
            int clickedPosition = getAdapterPosition();
            mListener.onClick(view, clickedPosition);
        }
    }

    public ExerciseAdapter(List<Exercise> exerciseList, RecyclerViewClickListener listener){
        this.mListener = listener;
        this.exerciseList = exerciseList;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.exercise_list_row, parent, false);
        return new ExerciseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {
        if (holder instanceof ExerciseViewHolder) {
            Exercise exercise = exerciseList.get(position);
            String targetArea = String.valueOf(exercise.getTargetArea());
            holder.name.setText(String.valueOf(exercise.getName()));
            holder.targetArea.setText(targetArea);
            holder.sets.setText("Sets: " + String.valueOf(exercise.getSets()));
            holder.reps.setText("Reps: " + String.valueOf(exercise.getReps()));
            holder.weight.setText("Weight: " + String.valueOf(exercise.getWeight()));

            if (position % 2 == 1) {
                holder.itemView.setBackgroundColor(Color.parseColor("#3399FF"));
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#66B3FF"));
            }

            switch (targetArea) {
                case "Cardio":
                    holder.image.setImageResource(R.drawable.cardio);
                    break;
                case "Full Body":
                    holder.image.setImageResource(R.drawable.fullbody);
                    break;
                case "Legs":
                    holder.image.setImageResource(R.drawable.legs);
                    break;
                case "Upper Body":
                    holder.image.setImageResource(R.drawable.upper);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

//    public void UpdateData(List<Exercise> dataset){
//        exerciseList.clear();
//        exerciseList.addAll(dataset);
//        notifyDataSetChanged();
//    }


}