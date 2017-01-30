package com.crisppic.app.crisppic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends ArrayAdapter {
    List list = new ArrayList();

    static class DataHandler {
        TextView year;
    }

    public MovieAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;

        DataHandler handler;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.movie_row, parent, false);
            handler = new DataHandler();
            handler.year = (TextView) row.findViewById(R.id.movie_year);
            row.setTag(handler);
        } else {
            handler = (DataHandler) row.getTag();
        }

        return super.getView(position, convertView, parent);
    }
}
