package me.alfredobejarano.myreceipts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
    private EditText importEditText;
    private EditText subtotalEditText;
    private EditText ivaQuantityEditText;
    private EditText ivaRetentionEditText;
    private EditText isrRetentionEditText;
    private EditText totalEditText;
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

        importEditText = (EditText) findViewById(R.id.edittext_import_quantity);
        ivaQuantityEditText = (EditText) findViewById(R.id.edittext_iva_quantity);
        subtotalEditText = (EditText) findViewById(R.id.edittext_subtotal_quantity);
        ivaRetentionEditText = (EditText) findViewById(R.id.edittext_iva_retention_quantity);
        isrRetentionEditText = (EditText) findViewById(R.id.edittext_isr_retention_quantity);
        totalEditText = (EditText) findViewById(R.id.edittext_total_quantity);

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
                double quantity = 0;

                if (!String.valueOf(importEditText.getText()).equals("")) {
                    quantity = Double.parseDouble(String.valueOf(importEditText.getText()));
                }

                int persontype = personSpinner.getSelectedItemPosition();
                int selectedRate = ivaSpinner.getSelectedItemPosition();

                if (persontype == 0) {
                    if (selectedRate == 0) {
                        values = calculate(quantity, 0, 16);
                    } else if (selectedRate == 1) {
                        values = calculate(quantity, 0, 11);
                    } else {
                        values = calculate(quantity, 0, 0);
                    }
                } else if (persontype == 1) {
                    if (selectedRate == 0) {
                        values = calculate(quantity, 1, 16);
                    } else if (selectedRate == 1) {
                        values = calculate(quantity, 1, 11);
                    } else {
                        values = calculate(quantity, 1, 0);
                    }
                } else {
                    if (selectedRate == 0) {
                        values = calculate(quantity, 2, 16);
                    } else if (selectedRate == 1) {
                        values = calculate(quantity, 2, 11);
                    } else {
                        values = calculate(quantity, 2, 0);
                    }
                }

                setEditTexts(values,editTexts);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public double[] calculate(double quantity,int persontype, double rate) {
        double[] array = new double[6];
        double iva = quantity*(rate*0.01);
        double subtotal = iva + quantity;
        double ivaRetention = 0;
        double isrRetention = 0;

        if(persontype == 0) {
            isrRetention = 0;
            ivaRetention = 0;
        } else if(persontype == 1) {
            ivaRetention = (iva/3)*2;
            isrRetention = (quantity * 0.1);
        } else if(persontype == 2) {
            ivaRetention = 0;
            isrRetention = (quantity * 0.1);
        } else if(persontype == 3) {
            rate = 0;
            iva = 0;
            isrRetention = 0;
            ivaRetention = 0;
        }

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
}
