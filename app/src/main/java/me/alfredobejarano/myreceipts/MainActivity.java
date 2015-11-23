package me.alfredobejarano.myreceipts;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
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

    private ArrayList editTexts;

    private Spinner ivaSpinner;
    private Spinner personSpinner;

    private EditText importEditText;
    private TextView subtotalEditText,ivaQuantityEditText,ivaRetentionEditText,isrRetentionEditText,totalEditText;

    private ArrayAdapter<CharSequence> personAdapter;
    private ArrayAdapter<CharSequence> ivaAdapter;

    private String[] RIds;

    private Button calculateButton;
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
        ivaQuantityEditText = (TextView) findViewById(R.id.iva_quantity_textview);
        subtotalEditText = (TextView) findViewById(R.id.subtotal_quantity_textview);
        ivaRetentionEditText = (TextView) findViewById(R.id.iva_retention_quantity_textview);
        isrRetentionEditText = (TextView) findViewById(R.id.isr_retention_quantity_textview);
        totalEditText = (TextView) findViewById(R.id.total_quantity_textview);

        calculateButton = (Button) findViewById(R.id.calculate_button);

        editTexts = new ArrayList<>();
        editTexts.add(ivaQuantityEditText);
        editTexts.add(subtotalEditText);
        editTexts.add(ivaRetentionEditText);
        editTexts.add(isrRetentionEditText);
        editTexts.add(totalEditText);

        //Filling the R id's values
        RIds = new String[5];

        RIds[0] = getResources().getString(R.string.iva);
        RIds[1] = getResources().getString(R.string.subtotal_quantity);
        RIds[2] = getResources().getString(R.string.iva_retention);
        RIds[3] = getResources().getString(R.string.isr_retention);
        RIds[4] = getResources().getString(R.string.total_quantity);

        //Filling the person type Spinner
        personAdapter = ArrayAdapter.createFromResource(this, R.array.person_spinner_array, android.R.layout.simple_spinner_item);
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        personSpinner.setAdapter(personAdapter);

        //Filling the iva rate Spinner
        ivaAdapter = ArrayAdapter.createFromResource(this, R.array.iva_spinner_array, android.R.layout.simple_spinner_item);
        ivaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ivaSpinner.setAdapter(ivaAdapter);

        //Calculate listener

        personSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                double iva = setIvaRate(ivaSpinner.getSelectedItemPosition());
                int personType = personSpinner.getSelectedItemPosition();

                try {
                    setEditTexts(calculateByImport(Double.parseDouble(String.valueOf(importEditText.getText())), personType, iva), editTexts);
                }
                catch(Exception e) {
                    Log.e("RubberDuck",e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,getResources().getText(R.string.empty_import),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ivaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                double iva = setIvaRate(ivaSpinner.getSelectedItemPosition());
                int personType = personSpinner.getSelectedItemPosition();

                try {
                    setEditTexts(calculateByImport(Double.parseDouble(String.valueOf(importEditText.getText())), personType, iva), editTexts);
                } catch (Exception e) {
                    Log.e("RubberDuck", e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, getResources().getText(R.string.empty_import), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double iva = setIvaRate(ivaSpinner.getSelectedItemPosition());
                int personType = personSpinner.getSelectedItemPosition();

                try {
                    setEditTexts(calculateByImport(Double.parseDouble(String.valueOf(importEditText.getText())), personType, iva), editTexts);
                }
                catch(Exception e) {
                    Log.e("RubberDuck",e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,getResources().getText(R.string.empty_import),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public double[] calculateByImport(double quantity,int persontype, double rate) {
        double[] array = new double[6];
        double iva = 0;
        double ivaRetention = 0;
        double isrRetention = 0;

        if(persontype == 0) {
            isrRetention = 0;
            ivaRetention = 0;
            iva = quantity*rate;
        } else if(persontype == 1) {
            isrRetention = (quantity * 0.1);
            iva = quantity*rate;
            ivaRetention = (iva/3)*2;
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

        double subtotal = iva + quantity;
        double total = subtotal - (ivaRetention + isrRetention);

        array[0] = round(quantity,2);
        array[1] = round(iva,2);
        array[2] = round(subtotal,2);
        array[3] = round(ivaRetention,2);
        array[4] = round(isrRetention,2);
        array[5] = round(total,2);

        return array;
    }

    private void setEditTexts(double[] values, ArrayList<TextView> texts) {
        for (int i = 0; i <= texts.size()-1; i++) {
            texts.get(i).setText("");
            texts.get(i).setText(String.valueOf(RIds[i])+": $"+String.valueOf(values[i+1]));
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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
