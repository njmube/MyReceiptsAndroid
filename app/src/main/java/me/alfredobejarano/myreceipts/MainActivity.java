package me.alfredobejarano.myreceipts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /* Variables foor calculations */
    private double importQuantity;
    private double subtotalQuantity;
    private double ivaQuantity;
    private double ivaRetQuantiy;
    private double isrQuantity;
    private double totalQuantity;
    private double[] values;

    private int ivaValue;
    private int personType;

    private ArrayList<EditText> editTexts;

    private Spinner ivaSpinner;
    private Spinner personSpinner;

    private EditText importEditText,subtotalEditText,ivaQuantityEditText,ivaRetentionEditText,isrRetentionEditText,totalEditText;

    private ArrayAdapter<CharSequence> personAdapter;
    private ArrayAdapter<CharSequence> ivaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().setTitle(R.string.main_activity_title);

        //Initialize variables
        importQuantity = 0.00;
        subtotalQuantity = 0.00;
        ivaQuantity = 0.00;
        ivaRetQuantiy = 0.00;
        isrQuantity = 0.00;
        totalQuantity = 0.00;

        ivaValue = 16;
        personType = 0;

        personSpinner = (Spinner) findViewById(R.id.person_spinner);
        ivaSpinner = (Spinner) findViewById(R.id.iva_spinner);

        importEditText = (EditText) findViewById(R.id.import_quantity_edittext);
        ivaQuantityEditText = (EditText) findViewById(R.id.iva_quantity_edittext);
        subtotalEditText = (EditText) findViewById(R.id.subtotal_quantity_edittext);
        ivaRetentionEditText = (EditText) findViewById(R.id.iva_retention_quantity_edittext);
        isrRetentionEditText = (EditText) findViewById(R.id.isr_retention_quantity_edittext);
        totalEditText = (EditText) findViewById(R.id.total_quantity_edittext);

        editTexts = new ArrayList<>();
        editTexts.add(importEditText);
        editTexts.add(subtotalEditText);
        editTexts.add(ivaQuantityEditText);
        editTexts.add(ivaRetentionEditText);
        editTexts.add(isrRetentionEditText);
        editTexts.add(totalEditText);

        //Filling the person type Spinner
        personAdapter = ArrayAdapter.createFromResource(this, R.array.person_spinner_array, android.R.layout.simple_spinner_item);
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        personSpinner.setAdapter(personAdapter);

        //Filling the iva rate Spinner
        ivaAdapter = ArrayAdapter.createFromResource(this, R.array.iva_spinner_array, android.R.layout.simple_spinner_item);
        ivaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ivaSpinner.setAdapter(ivaAdapter);

        //Quantities listeners
        personSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                double iva = setIvaRate(ivaSpinner.getSelectedItemPosition());
                try {
                    setEditTexts(calculate(Double.parseDouble(String.valueOf(importEditText.getText())), position, iva), editTexts);
                }
                catch(Exception e) {
                    Log.e("## ERROR ##", "Cantidad vacía");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public double[] calculate(double quantity,int persontype, double rate) {
        double[] array = new double[6];
        double iva = 0;
        double ivaRetention = 0;
        double isrRetention = 0;

        if(persontype == 0) {
            isrRetention = 0;
            ivaRetention = 0;
            iva = quantity*rate;
        } else if(persontype == 1) {
            ivaRetention = (rate/3)*2;
            isrRetention = (quantity * 0.1);
            iva = quantity*rate;
        } else if(persontype == 2) {
            ivaRetention = 0;
            isrRetention = (quantity * 0.1);
            rate = 0;
            iva = 0;
        } else if(persontype == 3) {
            rate = 0;
            iva = 0;
            isrRetention = 0;
            ivaRetention = 0;
        }

        double subtotal = rate + quantity;
        double total = subtotal - (ivaRetention + isrRetention);

        array[0] = quantity;
        array[1] = iva;
        array[2] = subtotal;
        array[3] = ivaRetention;
        array[4] = isrRetention;
        array[5] = total;

        return array;
    }

    private void setEditTexts(double[] values, ArrayList<EditText> texts) {
        for (int i = 0; i <= 5; i++) {
            texts.get(i).setText(String.valueOf(values[i]));
        }
    }
    private double setIvaRate(int type) {
        if(type == 0) {
            return 0.16;
        } else if (type == 1) {
            return 0.10;
        } else {
            return 0.0;
        }
    }

}
