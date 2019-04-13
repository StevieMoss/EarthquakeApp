package com.example.smmobilecoursework;

/*Name: Stephen Moss
StudentID: S1716583
Program of Study: Computing*/

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinnerItemsAdapter extends ArrayAdapter<SpinnerItems> {

    //class constructor
    public SpinnerItemsAdapter(Context context, ArrayList<SpinnerItems> spinnerItems) {
        super(context, 0, spinnerItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }


    private View initView(int position, View convertView, ViewGroup parent) {
        //To inflate the our new custome spinner view layout
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.layout_spinner, parent, false
            );
        }

        //getting the elements form the Spinnerlayout
        TextView textViewName = convertView.findViewById(R.id.text_view_name  );

        SpinnerItems currentItem = getItem(position);

        //adding elements.
        if (currentItem != null) {
            textViewName.setText(currentItem.getmSpinnerItemsTitle());
        }

        return convertView;
    }
}