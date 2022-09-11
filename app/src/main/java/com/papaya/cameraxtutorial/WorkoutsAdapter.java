package com.papaya.cameraxtutorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.ViewHolder> {
    Context context;
    ArrayList<Workout> workoutList;
    private static ItemClickListener clickListener;

    public WorkoutsAdapter(Context context, ArrayList<Workout> workoutList) {
        this.context = context;
        this.workoutList = workoutList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.workout_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Workout workout = workoutList.get(position);
        holder.workoutName.setText(workout.getName());
        holder.workoutDescription.setText(workout.getDescription());
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView workoutName, workoutDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutName = itemView.findViewById(R.id.workoutName);
            workoutDescription = itemView.findViewById(R.id.workoutDescription);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        WorkoutsAdapter.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View v, int position);
    }
}
