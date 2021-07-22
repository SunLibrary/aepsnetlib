package com.netpaisa.aepsriseinlib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.netpaisa.aepsriseinlib.R;

import java.util.ArrayList;

public class  SpinnerAdapter extends ArrayAdapter {
    Context context;
    ArrayList<String> arrayList;

    public SpinnerAdapter(Context context, int textViewResourceId, ArrayList<String> arrayList) {
        super(context, textViewResourceId, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View layout = convertView;
        if (convertView == null) {
            layout = LayoutInflater.from(this.context).inflate(R.layout.custom_spinner, parent, false);
        }

        TextView txtOperator = (TextView)layout.findViewById(R.id.txt_item);
        txtOperator.setText((CharSequence)this.arrayList.get(position));
        if (position == 0) {
            txtOperator.setTextSize(16.0F);
        }

        txtOperator.setTextSize(16.0F);
        txtOperator.setTextColor(-16777216);
        return layout;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return this.getCustomView(position, convertView, parent);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return this.getCustomView(position, convertView, parent);
    }
}
