package com.example.smmobilecoursework;

/*Name: Stephen Moss
StudentID: S1716583
Program of Study: Computing*/

import android.R.layout;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener {

    private static final int ERROR_DIALOG_REQUEST = 0;
    ArrayList<EarthquakeItems> quakeItems;
    EarthquakeItems quake;
    private ArrayList<SpinnerItems> spinnerItems;
    private SpinnerItemsAdapter spinnerItemsAdapter;
    private ArrayList<EarthquakeItemObject> eItems;
    private ArrayList<EarthquakeItemObject> eItemstemp;
    ListView listview;
    public ArrayList<EarthquakeItemObject> itemObjects;
    public ArrayList<EarthquakeItems> listitem;
    private final static String TAG = "MainActivity";
    SearchView searchView;
    private GoogleMap map;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.lvListview);
        quakeItems = new ArrayList<EarthquakeItems>();
        spinner();
        init();


    /**
     * setting up portrait and landscape views
     */
         /*if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.landscape);
        }

        /**
         * /listview onclicklistener
         */
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Details.class);
                String title = (String) parent.getItemAtPosition(position);
                for (int i = 0; i < quakeItems.size(); i++) {
                    if (title == quakeItems.get(i).getTitle()) {
                        intent.putExtra("Earthquake", quakeItems.get(i));
                    }
                }
                startActivity(intent);
            };            ;
        });
        new ProcessInBackGround().execute();
    }



     public void init (){

        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
     }

    //adding a search menu and enabling a text search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem mItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) mItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

   @Override
    public boolean onQueryTextChange(String newText) {

        String input = newText.toLowerCase();
        eItems = new ArrayList<>();

        int i = 1;
        while (i < eItems.size()) {
            EarthquakeItemObject getitem = eItems.get(i);
            getitem.getTitle();

            if (getitem.getTitle().contains(input)) {
                eItems.add(getitem);
            }
            i++;
        }
        if (eItems.isEmpty()) {
            eItems = new ArrayList<>();
            listview(eItems);
            return true;
        } else {
            listview(eItems);
            return true;
        }
    }
    /**
     * Handle configuration changes
     */
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void listview(ArrayList<EarthquakeItemObject> eItems) {

        ListView listview = findViewById(R.id.lvListview);
       // ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, eItems );
       // listview.setAdapter(adapter);
        //if item is click in the listView
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //open map if google services are ready
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * class for the spinner
     */
    public void spinner() {

        Spinner spinner = findViewById(R.id.spinner);
        spinnerItems = new ArrayList<>();
        spinnerItems.add(new SpinnerItems("Date"));
        spinnerItems.add(new SpinnerItems("Depth"));
        spinnerItems.add(new SpinnerItems("Magnitude"));

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.DropDownTitles, android.R.layout.simple_spinner_item);
        //spinnerItemsAdapter = new SpinnerItemsAdapter(this, R.array.DropDownTitles, android.R.layout.simple_spinner_item);
        spinner.setAdapter(spinnerItemsAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String text = parent.getItemAtPosition(position).toString();

        /*if (parent.getId() == R.id.data) {
            if  (text.equals("Date"))
                eItems = DropDown.Date();
            else if (text.equals("Magnitude"))
                eItems = eItemstemp.Magnitude;
            else if (text.equals("Depth"))
                dropDownValue = DropDownValues.Depth;*/
    }

        /*if (position == 0 ){
            //eItems = eItemstemp;
            //listview();
        }*/

            /*eItems = eItemstemp;
            Collections.sort(eItems , Depth);
            Collections.reverse(eItems);
            listview(eItems);
        }
            eItems = eItemstemp;
            Collections.sort(eItems , Magnitude);
            Collections.reverse(eItems);
            listview(eItems);*/


        /*java.util.Comparator<EarthquakeItemObject> Depth = new java.util.Comparator<EarthquakeItemObject>()
        {
            @Override
            public int compare(EarthquakeItemObject o1, EarthquakeItemObject o2) {
                if (o1.getMagnitude() > o2.getMagnitude()) {return 1;}
                else if (o1.getMagnitude() < o2.getMagnitude()) return -1;
                return 0; }};

        java.util.Comparator<EarthquakeItemObject> Magnitude = new java.util.Comparator<EarthquakeItemObject>()
        {
            @Override
            public int compare(EarthquakeItemObject o1, EarthquakeItemObject o2) {
                if (o1.getMagnitude() > o2.getMagnitude()) {return 1;}
                else if (o1.getMagnitude() < o2.getMagnitude()) return -1;
                return 0; }};*/

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public InputStream getInputStream(URL url)
    {
        try {
            return url.openConnection().getInputStream();
        }
        catch (IOException e) {
            return null;
        }
    }

    public class ProcessInBackGround extends AsyncTask<Integer, Void, Exception> {

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        Exception exception = null;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            progressDialog.setMessage("Loading RSS Feed...");
            progressDialog.show();
        }


        @Override
        protected Exception doInBackground(Integer... integers) {


            try{
                URL url = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
                XmlPullParserFactory factory =XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser parser = factory.newPullParser();

                parser.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                int eventType = parser.getEventType();

                itemObjects = new ArrayList<>();
                listitem = new ArrayList<>();
                String title="title", desc="desc", lat="lat", geolong="long", date="date1";
                EarthquakeItems quake = new EarthquakeItems();
                while(eventType != XmlPullParser.END_DOCUMENT){

                    if(eventType == XmlPullParser.START_TAG){

                        if(parser.getName().equalsIgnoreCase("item")){
                            insideItem = true;
                        }
                        if (insideItem){
                            if(parser.getName().equalsIgnoreCase("title")){
                                quake.setTitle(parser.nextText());
                            }
                            if(parser.getName().equalsIgnoreCase("pubDate")) {
                                quake.setDate(parser.nextText());
                            }
                            if(parser.getName().equalsIgnoreCase("geo:lat")) {
                                quake.setLatitude(parser.nextText()); ;
                            }
                            if(parser.getName().equalsIgnoreCase("geo:long")) {
                                quake.setLongtitude(parser.nextText());
                            }
                            if(parser.getName().equalsIgnoreCase("description")) {
                                quake.setDescription(parser.nextText());

                            }

                        }
                    }//check start tag
                    if(eventType==XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item")){
                        insideItem = false;
                        if(quake !=null){
                            //quake.setTitle("fuck");
                            quakeItems.add(quake);
                        }
                        quake = new EarthquakeItems();
                    }
                    eventType = parser.next();
                }//while

            }catch(MalformedURLException e){
                exception = e;
            }catch(XmlPullParserException e) {
                exception = e;
            }catch(IOException e){
                exception = e;
            }
            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);

            ArrayList<String> names = new ArrayList<>();
             for (int i = 0; i < quakeItems.size(); i++)
             {
                 names.add(quakeItems.get(i).getTitle());
             }
            Toast.makeText(MainActivity.this, names.get(1), Toast.LENGTH_SHORT).show();
            ArrayAdapter quakeAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, names);

            listview.setAdapter(quakeAdapter);
           progressDialog.dismiss();
        }
    }
}
