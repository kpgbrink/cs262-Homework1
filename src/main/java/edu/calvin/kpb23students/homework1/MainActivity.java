package edu.calvin.kpb23students.homework1;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.NumberFormat;

/**
 * This is a single-page, calculator app based on Murach's Android Programming, 2nd
 * <p>
 * For use as CS 262, Homework 1
 *
 * @author Kristofer
 * @version Fall, 2016
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // widget variabls
    private EditText valueEditText;
    private EditText valueEditText2;
    private TextView resultTextView;
    private Button calculateButton;

    // http://stackoverflow.com/a/5241720/2948122
    private String[] arraySpinner;

    // instance variables saved
    private String valueAmountString = "";
    private String valueAmountString2 = "";

    // define the Shared Preferences object
    private SharedPreferences savedValues;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get references to the widgets
        valueEditText = (EditText) findViewById(R.id.value);
        valueEditText2 = (EditText) findViewById(R.id.value2);
        resultTextView = (TextView) findViewById(R.id.result);
        calculateButton = (Button) findViewById(R.id.button);

        // Set spinner
        this.arraySpinner = new String[] {
                "+", "-", "*", "/", "%", "^"
        };
        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);

        // set the listeners
        calculateButton.setOnClickListener(this);

        // get the SharedPreference object
        savedValues = getSharedPreferences("SharedValues", MODE_PRIVATE);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onPause() {
        // save the instance variables
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("valueAmountString", valueAmountString);
        editor.putString("valueAmountString2", valueAmountString2);
        editor.apply();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        valueAmountString = savedValues.getString("valueAmountString", "");
        valueAmountString2 = savedValues.getString("valueAmountString2", "");


        valueEditText.setText(valueAmountString);
        valueEditText2.setText(valueAmountString2);

        calculateAndDisplay();
    }

    // Get integer from string
    private int getInt(String intString) {
        return (intString.equals("")) ? 0 : Integer.parseInt(intString);
    }

    private int calculateResult(int value, int value2, String operator) {
        switch(operator) {
            case "+":
                return value + value2;
            case "-":
                return value - value2;
            case "*":
                return value * value2;
            case "/":
                return value / value2;
            case "%":
                return value % value2;
            case "^":
                return (int) Math.pow(value, value2);
        }
        // If all else fails
        return 0;
    }

    public void calculateAndDisplay() {
        valueAmountString = valueEditText.getText().toString();
        valueAmountString2 = valueEditText2.getText().toString();
        int value = getInt(valueAmountString);
        int value2 = getInt(valueAmountString2);

        Spinner mySpinner=(Spinner) findViewById(R.id.spinner);
        String operator = mySpinner.getSelectedItem().toString();

        int resultValue = calculateResult(value, value2, operator);
        NumberFormat number = NumberFormat.getNumberInstance();
        resultTextView.setText(number.format(resultValue));
    }

    @Override
    public void onClick(View v) {
        calculateAndDisplay();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
        // change
    }
}