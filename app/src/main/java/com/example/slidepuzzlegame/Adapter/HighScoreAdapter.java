package com.example.slidepuzzlegame.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slidepuzzlegame.MyDatabaseHandler;
import com.example.slidepuzzlegame.MyDatabaseScoreModel;
import com.example.slidepuzzlegame.R;

import java.util.ArrayList;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.HighScoreDataViewHolder> {

    Context context;
    ArrayList<MyDatabaseScoreModel> scoreModels;

    public HighScoreAdapter(Context context, ArrayList<MyDatabaseScoreModel> scoreModels) {
        this.context = context;
        this.scoreModels = scoreModels;
    }

    @NonNull
    @Override
    public HighScoreDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.high_score_item, parent, false);
        return new HighScoreDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighScoreDataViewHolder holder, int position) {
        MyDatabaseHandler myDatabaseHandler = new MyDatabaseHandler(context);
        MyDatabaseScoreModel myDatabaseScoreModel = scoreModels.get(position);
        holder.txtUserIdHS.setText(myDatabaseScoreModel.getUserId());
        holder.txtMovesHS.setText(myDatabaseScoreModel.getMoves());
        holder.txtTimeTakenHS.setText(myDatabaseScoreModel.getTimeTaken());

    }

    @Override
    public int getItemCount() {
        if (scoreModels != null) {
            return scoreModels.size();
        } else {
            return 0;
        }
    }

    public static class HighScoreDataViewHolder extends RecyclerView.ViewHolder {

        TextView txtUserIdHS, txtMovesHS,txtTimeTakenHS;

        public HighScoreDataViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserIdHS = itemView.findViewById(R.id.txtUserIdHS);
            txtMovesHS = itemView.findViewById(R.id.txtMovesHS);
            txtTimeTakenHS = itemView.findViewById(R.id.txtTimeTakenHS);

        }
    }
}
