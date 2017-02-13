package com.crisppic.app.crisppic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserViewsAdapter extends ArrayAdapter<UserMovieModel> implements View.OnClickListener{

    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView title;
        TextView txtType;
        ImageView info;
    }

    public UserViewsAdapter(ArrayList<UserMovieModel> data, Context context) {
        super(context, R.layout.movie_item, data);
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();


        Object object = getItem(position);
        UserMovieModel userMovieModel = (UserMovieModel) object;

        Log.d("Click", String.valueOf(userMovieModel.moviesID));

//        switch (v.getId()) {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date2 " +dataModel.getType(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//
//                break;
//        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        UserMovieModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_item, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.title.setText(dataModel.getTitle());
        viewHolder.txtType.setText(dataModel.getType());
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);

        // Return the completed view to render on screen
        return convertView;
    }
}
