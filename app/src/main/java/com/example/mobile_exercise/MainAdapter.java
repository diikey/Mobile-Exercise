package com.example.mobile_exercise;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    ArrayList<ModelArray> modelArrayArrayList;
    Context context;

    HTTPHandler httpHandler;

    public MainAdapter(ArrayList<ModelArray> modelArrayArrayList, Context context) {
        this.modelArrayArrayList = modelArrayArrayList;
        this.context = context;
    }

    public MainAdapter(){

    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.main_recycler_layout, parent, false);
        MainViewHolder mainViewHolder = new MainViewHolder(view);

        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.mainFirstName.setText(modelArrayArrayList.get(position).getFirstname());
        holder.mainLastName.setText(modelArrayArrayList.get(position).getLastname());
        holder.mainAddress.setText(modelArrayArrayList.get(position).getAddress());
        holder.mainContact.setText(modelArrayArrayList.get(position).getContact_number());
        String username = modelArrayArrayList.get(position).getUsername();

        holder.btnMainEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditUser.class);
                intent.putExtra("firstname", holder.mainFirstName.getText());
                intent.putExtra("lastname", holder.mainLastName.getText());
                intent.putExtra("address", holder.mainAddress.getText());
                intent.putExtra("contact", holder.mainContact.getText());
                intent.putExtra("username", username);
                view.getContext().startActivity(intent);
            }
        });

        holder.btnMainDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                httpHandler = new HTTPHandler();

                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setMessage("Do you want to delete " + holder.mainFirstName.getText().toString())
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Call<Model> modelCall = httpHandler.jsonAPI.deleteUsers("UNILEVER", username);
                                modelCall.enqueue(new Callback<Model>() {
                                    @Override
                                    public void onResponse(Call<Model> call, Response<Model> response) {
                                        if(!response.isSuccessful()){
                                            Log.d("Code: ", response.code() + "");
                                            return;
                                        }
                                        Toast.makeText(context, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();

                                        modelArrayArrayList.remove(holder.getAdapterPosition());
                                        notifyItemRemoved(holder.getAdapterPosition());
                                        if (modelArrayArrayList.size() == 0){
                                            FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new NothingToShow());
                                            fragmentTransaction.commit();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Model> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayArrayList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{
        TextView mainFirstName, mainLastName, mainAddress, mainContact;
        ImageButton btnMainEdit, btnMainDelete;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            //textview
            mainFirstName = itemView.findViewById(R.id.mainFirstName);
            mainLastName = itemView.findViewById(R.id.mainLastName);
            mainAddress = itemView.findViewById(R.id.mainAddress);
            mainContact = itemView.findViewById(R.id.mainContact);

            //imagebutton
            btnMainEdit = itemView.findViewById(R.id.btnMainEdit);
            btnMainDelete = itemView.findViewById(R.id.btnMainDelete);
        }
    }
}
