package com.example.trackdem;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ItemAdapter extends BaseAdapter {
    LayoutInflater mInflater;

    ArrayList<Missions> arraylist;

    public ItemAdapter(Context c,  ArrayList<Missions> arrayList) {

        arraylist=arrayList;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylist.get(i).getMissionId();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.my_listview_detail, null);
        TextView missionsTextView = (TextView) v.findViewById(R.id.missionsTextView);
        TextView locationTextView = (TextView) v.findViewById(R.id.locationTextView);
        TextView descriptionTextView = (TextView) v.findViewById(R.id.descriptiontextView);
        String miss = arraylist.get(i).getMissionId();
        String loc = arraylist.get(i).getLocationFrom();
        String desc = arraylist.get(i).getDescription();

        missionsTextView.setText("Mission"+miss);
        locationTextView.setText(loc);
        descriptionTextView.setText(desc);
        return v;
    }
}