package com.example.demo.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.Model.Post;
import com.example.demo.R;
import com.example.demo.db.DatabaseHelper;

import java.util.ArrayList;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {

    private ArrayList<Post> list;
    private Context context;
    private UpdateClick updateClick;
    DatabaseHelper databaseHelper;

    public MainListAdapter(ArrayList<Post> list, Context context, DatabaseHelper databaseHelper, UpdateClick updateClick) {
        this.list = list;
        this.context = context;
        this.updateClick = updateClick;
        this.databaseHelper = databaseHelper;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.itemview_main_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.list_id_txt.setText(String.valueOf(list.get(position).getId()));
        holder.list_title_txt.setText(list.get(position).getTitle());
        holder.list_body_txt.setText(list.get(position).getBody());

        holder.dataDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.noteDao().delete(list.get(position).getNote_id());
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.dataEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog updateDialog = new Dialog(context);
                updateDialog.requestWindowFeature(1);
                updateDialog.setContentView(R.layout.dialog_update_data);
                updateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                updateDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                updateDialog.show();

                EditText titalEditText = updateDialog.findViewById(R.id.titalEditText);
                Button updateBtn = updateDialog.findViewById(R.id.updateBtn);

                titalEditText.setText(list.get(position).getTitle());
                updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseHelper.noteDao().update(titalEditText.getText().toString(), list.get(position).getNote_id());
                        updateClick.onUpdate();
                        updateDialog.dismiss();
                    }
                });
            }
        });
    }

    public void updateData(ArrayList<Post> viewModels) {
        list.clear();
        list.addAll(viewModels);
        notifyDataSetChanged();
    }

    public interface UpdateClick {
        void onUpdate();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView list_id_txt;
        public TextView list_title_txt;
        public TextView list_body_txt;
        public Button dataDeleteBtn;
        public Button dataEditBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            list_id_txt = itemView.findViewById(R.id.list_id_txt);
            list_title_txt = itemView.findViewById(R.id.list_title_txt);
            list_body_txt = itemView.findViewById(R.id.list_body_txt);
            dataDeleteBtn = itemView.findViewById(R.id.dataDeleteBtn);
            dataEditBtn = itemView.findViewById(R.id.dataEditBtn);
        }
    }
}  