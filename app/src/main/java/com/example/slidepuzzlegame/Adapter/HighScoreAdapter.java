package com.example.slidepuzzlegame.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slidepuzzlegame.R;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.HighScoreDataViewHolder> {

    @NonNull
    @Override
    public HighScoreDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.high_score_item, parent, false);
        return new HighScoreDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighScoreDataViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class HighScoreDataViewHolder extends RecyclerView.ViewHolder {

        TextView txtUserNameHS, txtScoreHS;

        public HighScoreDataViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserNameHS = itemView.findViewById(R.id.txtUserNameHS);
            txtScoreHS = itemView.findViewById(R.id.txtScoreHS);

        }
    }
}
