package com.example.smmobilecoursework;

/*Name: Stephen Moss
StudentID: S1716583
Program of Study: Computing*/

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        EarthquakeItems earthquakeItem = getIntent().getParcelableExtra("Earthquake");


        //getting the elements form the List_view layout
        TextView line1 = (TextView) findViewById(R.id.tvTitle);
        TextView line2 = (TextView) findViewById(R.id.tvDate);
        TextView line3 = (TextView) findViewById(R.id.tvDepth);
        TextView line4 = (TextView) findViewById(R.id.tvMagnitude );
        //TextView line5 = (TextView) findViewById(R.id.tvLatitu );
        //TextView line6 = (TextView) findViewById(R.id.tvMagnitude );
       // TextView line5 = (TextView) findViewById(R.id.depthDetails );

        //setting the text for each item in the listView
        line1.setText(earthquakeItem.getTitle());
        line2.setText(earthquakeItem.getDate());
        line3.setText(earthquakeItem.getDepth());
        line4.setText(earthquakeItem.getMagnitude());

    }

}
