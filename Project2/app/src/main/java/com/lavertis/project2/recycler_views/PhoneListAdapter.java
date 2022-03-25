package com.lavertis.project2.recycler_views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lavertis.project2.R;
import com.lavertis.project2.data.Phone;

import java.util.List;

public class PhoneListAdapter extends RecyclerView.Adapter<PhoneListAdapter.PhoneViewHolder> {
    private final LayoutInflater layoutInflater;
    private List<Phone> phoneList;

    public PhoneListAdapter(Activity context) {
        layoutInflater = context.getLayoutInflater();
        phoneList = null;
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = layoutInflater.inflate(R.layout.phone_row, parent, false);
        return new PhoneViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        holder.brandTextView.setText(phoneList.get(position).getBrand());
        holder.modelTextView.setText(phoneList.get(position).getModel());
    }

    @Override
    public int getItemCount() {
        if (phoneList != null)
            return phoneList.size();
        return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
        notifyDataSetChanged();
    }

    static class PhoneViewHolder extends RecyclerView.ViewHolder {

        TextView brandTextView;
        TextView modelTextView;

        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            brandTextView = itemView.findViewById(R.id.brandTextView);
            modelTextView = itemView.findViewById(R.id.modelTextView);
        }
    }
}
