package com.example.slidepuzzlegame.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slidepuzzlegame.MyDatabaseHandler;
import com.example.slidepuzzlegame.MyDatabaseModel;
import com.example.slidepuzzlegame.R;
import com.example.slidepuzzlegame.Tools;

import java.util.ArrayList;

public class RegisteredUserAdapter extends RecyclerView.Adapter<RegisteredUserAdapter.RegisteredUserDataViewHolder> {

    Context context;
    private ArrayList<MyDatabaseModel> dataModelList;
    MyDatabaseHandler myDatabaseHandler;
    MyDatabaseModel myDatabaseModel;
    Tools tools;

    public RegisteredUserAdapter(Context context, ArrayList<MyDatabaseModel> dataModelList) {
        this.context = context;
        this.dataModelList = dataModelList;
    }

    @NonNull
    @Override
    public RegisteredUserDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.registered_user_list_item,parent,false);
        return new RegisteredUserDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisteredUserDataViewHolder holder, int position) {
        tools = new Tools(context.getApplicationContext());
        myDatabaseHandler = new MyDatabaseHandler(holder.itemView.getContext());
        myDatabaseModel = dataModelList.get(position);
        holder.txtUserName.setText(myDatabaseModel.getName());

        new android.os.Handler().postDelayed(() -> {
            Tools.DisplayImage(context.getApplicationContext(), holder.imgUserPhoto, myDatabaseModel.getImage());
        }, 2000);
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

    public static class RegisteredUserDataViewHolder extends RecyclerView.ViewHolder{

        TextView txtUserName;
        ImageView imgUserPhoto;

        public RegisteredUserDataViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserName = itemView.findViewById(R.id.txtUserName);
            imgUserPhoto = itemView.findViewById(R.id.imgUserPhoto);

        }
    }
}
