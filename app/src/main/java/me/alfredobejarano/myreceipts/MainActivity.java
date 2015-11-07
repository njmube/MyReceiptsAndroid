package me.alfredobejarano.myreceipts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Spinner ivaSpinner;
    private Spinner personSpinner;
    private ArrayAdapter<CharSequence> personAdapter;
    private ArrayAdapter<CharSequence> ivaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().setTitle(R.string.main_activity_title);

        personSpinner = (Spinner) findViewById(R.id.person_spinner);
        ivaSpinner = (Spinner) findViewById(R.id.iva_spinner);

        //Filling the person type Spinner
        personAdapter = ArrayAdapter.createFromResource(this, R.array.person_spinner_array, android.R.layout.simple_spinner_item);
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        personSpinner.setAdapter(personAdapter);

        //Filling the iva rate Spinner
        ivaAdapter = ArrayAdapter.createFromResource(this, R.array.iva_spinner_array, android.R.layout.simple_spinner_item);
        ivaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ivaSpinner.setAdapter(ivaAdapter);
    }
}
