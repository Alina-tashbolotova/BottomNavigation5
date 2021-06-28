package com.example.bottomnavigation5;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<FragmentModel> list = new ArrayList<>();
    private  OnItemClickListener onItemClickListener;


    public  void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public FragmentAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);

    }

    public void delete(int position){
        list.remove(position);
        notifyDataSetChanged();
    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_fragment, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(FragmentAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));

    }
    public void dataTry(int position,FragmentModel modelF){
        list.get(position).setTitle(modelF.getTitle());
        list.get(position).setDescription(modelF.getDescription());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addTask(FragmentModel model) {
        this.list.add(model);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtDescription;


        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDescription = itemView.findViewById(R.id.txt_description);

        }

        public void bind(FragmentModel model) {
            txtTitle.setText(model.getTitle());
            txtDescription.setText(model.getDescription());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.itemClick(getAdapterPosition(),model);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext()).create();
                    alertDialog.setTitle("Внимание!!!");
                    alertDialog.setMessage("Вы действительно хотите удалить???");
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Нет", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete(getAdapterPosition());

                        }
                    });
                    alertDialog.show();
                    return false;
                }
            });

        }
    }
}
