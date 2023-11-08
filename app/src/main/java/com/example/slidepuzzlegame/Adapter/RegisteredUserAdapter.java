package com.example.slidepuzzlegame.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.collection.CircularArray;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slidepuzzlegame.Activity.GameScreenActivity;
import com.example.slidepuzzlegame.Activity.HistoryActivity;
import com.example.slidepuzzlegame.MyDatabaseHandler;
import com.example.slidepuzzlegame.MyDatabaseModel;
import com.example.slidepuzzlegame.R;
import com.example.slidepuzzlegame.Tools;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisteredUserAdapter extends RecyclerView.Adapter<RegisteredUserAdapter.RegisteredUserDataViewHolder> {

    Context context;
    private ArrayList<MyDatabaseModel> dataModelList;
    Tools tools;

    public RegisteredUserAdapter(Context context, ArrayList<MyDatabaseModel> dataModelList) {
        this.context = context;
        this.dataModelList = dataModelList;
    }

    @NonNull
    @Override
    public RegisteredUserDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.registered_user_list_item, parent, false);
        return new RegisteredUserDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisteredUserDataViewHolder holder, @SuppressLint("RecyclerView") int position) {
        tools = new Tools(context);
        MyDatabaseHandler myDatabaseHandler = new MyDatabaseHandler(holder.itemView.getContext());
        MyDatabaseModel myDatabaseModel = dataModelList.get(position);
        holder.txtUserName.setText(myDatabaseModel.getName());
        Tools.DisplayImage(context, holder.imgUserPhoto, myDatabaseModel.getImage());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Delete Item?");
                builder.setMessage("Do you want to Delete " + myDatabaseModel.getName());
                builder.setCancelable(false);

                builder.setPositiveButton("Yes", (dialogInterface, which) -> {
                    myDatabaseHandler.deleteData(myDatabaseModel.getId());
                    dataModelList.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(view.getContext(), "Item Deleted", Toast.LENGTH_SHORT).show();
                });

                builder.setNegativeButton("No", (dialogInterface, which) -> dialogInterface.dismiss());

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GameScreenActivity.class);
                intent.putExtra("userId", myDatabaseModel.getId());
                intent.putExtra("Name", myDatabaseModel.getName());
                intent.putExtra("ImagePath", myDatabaseModel.getImage());
                context.startActivity(intent);
            }
        });

        holder.btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HistoryActivity.class);
                int userId = myDatabaseModel.getId();
                intent.putExtra("userId", userId);
                intent.putExtra("Name", myDatabaseModel.getName());
                intent.putExtra("ImagePath", myDatabaseModel.getImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public void updateData(ArrayList<MyDatabaseModel> databaseModelArrayList) {
        dataModelList.clear();
        dataModelList.addAll(databaseModelArrayList);
        notifyDataSetChanged();
    }

    public static class RegisteredUserDataViewHolder extends RecyclerView.ViewHolder {

        TextView txtUserName;
        CircleImageView imgUserPhoto;
        ImageView imgDelete;
        Button btnPlay, btnViewHistory;

        public RegisteredUserDataViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserName = itemView.findViewById(R.id.txtUserName);
            imgUserPhoto = itemView.findViewById(R.id.imgUserPhoto);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            btnPlay = itemView.findViewById(R.id.btnPlay);
            btnViewHistory = itemView.findViewById(R.id.btnViewHistory);

        }
    }
}
