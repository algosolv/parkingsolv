package com.algosolv.parkingsolv;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity {
    EditText name, carno, mobile, licienceno;
    static EditText dob;
    Button submit;
    static SimpleDateFormat dateFormatter, changeDateFormatter;
    private final String serverUrl = "http://www.algosolv.com/parkingsolv/signup.php";
    private static int DELAY_TIME_OUT = 2000;
    static String formattedDOB;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name = (EditText) findViewById(R.id.name_edittext);
        licienceno = (EditText) findViewById(R.id.licence_editText);
        carno = (EditText) findViewById(R.id.carnumber_editText);
        mobile = (EditText) findViewById(R.id.mobile_editText);
        dob = (EditText) findViewById(R.id.dob);
        /*code to set date picker*/
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.UK);
        changeDateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        addListenerOnButton();

        submit = (Button) findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();

                String Mobile = mobile.getText().toString();

                String Licence = licienceno.getText().toString();

                String Carno = carno.getText().toString();

                String selectedDOB = dob.getText().toString();
                String changedFormatDob = formattedDOB;

                System.out.println("Changed date: " + changedFormatDob);

                if (Name.equals("") || Mobile.equals("") || Licence.equals("") || selectedDOB.equals("") || Carno.equals(null)) {
                    Toast.makeText(getApplicationContext(), "All fields are mandatory", Toast.LENGTH_LONG).show();
                } else if (Mobile.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Mobile No should be 10 digits", Toast.LENGTH_LONG).show();
                } else {
                    RegistrationActivityAsyncTask asyncRequestObject = new RegistrationActivityAsyncTask();
                    asyncRequestObject.execute(serverUrl, Name, Mobile, Carno, changedFormatDob, Licence);
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Registration Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.sanjaykrishna.parkingsolv/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Registration Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.sanjaykrishna.parkingsolv/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private class RegistrationActivityAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpParams httpParameters = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);

            HttpConnectionParams.setSoTimeout(httpParameters, 5000);

            HttpClient httpClient = new DefaultHttpClient(httpParameters);

            HttpPost httpPost = new HttpPost(params[0]);
            System.out.println("params[0]: " + params[0]);
            String jsonResult = "";

            try {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

                nameValuePairs.add(new BasicNameValuePair("name", params[1]));

                System.out.println("Name: " + params[1]);

                nameValuePairs.add(new BasicNameValuePair("mobile", params[2]));

                nameValuePairs.add(new BasicNameValuePair("Car number", params[3]));

                nameValuePairs.add(new BasicNameValuePair("dob", params[4]));

                nameValuePairs.add(new BasicNameValuePair("licence number", params[5]));

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);

                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();


                System.out.println("jsonResult Value: " + jsonResult);

                return jsonResult;
            } catch (ClientProtocolException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }
            return jsonResult;
        }

        @Override
        protected void onPostExecute(String result) {
            //database retrivel code comes here
            super.onPostExecute(result);


            try {
                if (result.equals("") || result == null) {

                    Toast.makeText(getApplicationContext(), "Server connection failed", Toast.LENGTH_LONG).show();

                    return;

                }
                System.out.println("Result: " + result);
                int jsonResult = returnParsedJsonObject(result);


                System.out.println("Resulted Value: " + jsonResult);
                if (jsonResult == 0) {

                    Toast.makeText(getApplicationContext(), "Insertion failed, Try again", Toast.LENGTH_LONG).show();


                } else {

                    Toast.makeText(getApplicationContext(), "You have been successfully registered ", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                           /*code to move to Main Activity*/
                            SharedPreferences sharedPreferences = getSharedPreferences("prefs", 0);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            System.out.println("Editor: " + editor.toString());
                            editor.putBoolean("firstRun", true);
                            editor.commit();

                            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }, DELAY_TIME_OUT);


                }
            } catch (Exception e) {

                e.printStackTrace();
            }

        }


        private StringBuilder inputStreamToString(InputStream is) {

            String rLine = "";

            StringBuilder answer = new StringBuilder();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            try {

                while ((rLine = br.readLine()) != null) {

                    answer.append(rLine);

                }

            } catch (IOException e) {

// TODO Auto-generated catch block

                e.printStackTrace();

            }

            return answer;

        }


        private int returnParsedJsonObject(String result){

            JSONObject resultObject = null;

            int returnedResult = 0;

            try {

                resultObject = new JSONObject(result);

                //returnedResult = resultObject.getInt("success");
                //returnedResult = resultObject.optString("name");
                returnedResult = resultObject.getInt("success");
            } catch (JSONException e) {

                e.printStackTrace();

            }


            return returnedResult;
        }
    }

    public void addListenerOnButton() {

        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Initialize a new date picker dialog fragment
                DialogFragment dFragment = new DatePickerFragment();

                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Date Picker");

            }

        });

    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // DatePickerDialog THEME_TRADITIONAL
            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_TRADITIONAL, this, year, month, day);

            // Return the DatePickerDialog
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Display the chosen date
            Calendar newCalender = Calendar.getInstance();
            newCalender.set(year, month, day);
            System.out.println(newCalender.getTime());
            System.out.println("the month value is:" + month);
            dob.setText(dateFormatter.format(newCalender.getTime()));
            System.out.println("Previous Date: " + dateFormatter.format(newCalender.getTime()));
            formattedDOB = changeDateFormatter.format(newCalender.getTime());
            System.out.println("formattedDOB: " + formattedDOB);
        }


    }
}
