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
public class ScoreHistoryAdapter extends RecyclerView.Adapter<ScoreHistoryAdapter.ScoreHistoryDataViewHolder> {

    Context context;
    ArrayList<MyDatabaseScoreModel> scoreModels;

    public ScoreHistoryAdapter(Context context, ArrayList<MyDatabaseScoreModel> scoreModels) {
        this.context = context;
        this.scoreModels = scoreModels;
    }

    @NonNull
    @Override
    public ScoreHistoryDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.score_history_item, parent, false);
        return new ScoreHistoryDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreHistoryDataViewHolder holder, int position) {

        MyDatabaseHandler myDatabaseHandler = new MyDatabaseHandler(context);
        MyDatabaseScoreModel myDatabaseScoreModel = scoreModels.get(position);
        holder.txtMoves.setText(myDatabaseScoreModel.getMoves());
        holder.txtTime.setText(myDatabaseScoreModel.getTimeTaken());
    }

    @Override
    public int getItemCount() {
        if (scoreModels != null) {
            return scoreModels.size();
        } else {
            return 0;
        }
    }

    public static class ScoreHistoryDataViewHolder extends RecyclerView.ViewHolder {

        TextView txtMoves, txtTime;

        public ScoreHistoryDataViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMoves = itemView.findViewById(R.id.txtMoves);
            txtTime = itemView.findViewById(R.id.txtTime);

        }
    }
}
