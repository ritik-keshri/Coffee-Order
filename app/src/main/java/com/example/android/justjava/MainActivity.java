package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when plus button is clicked.
     */
    public void increment(View view) {
        if (quantity<100) {
            ++quantity;
            displayQuantity(quantity);
        }
        else
        Toast.makeText(getApplicationContext(),getString(R.string.Max_100_Coffee), Toast.LENGTH_SHORT).show();
    }

    /**
     * This method is called when minus button is clicked.
     */
    public void decrement(View view){
        if(quantity>0)
        {
            --quantity;
            displayQuantity(quantity);
        }
        else Toast.makeText(getApplicationContext(),getString(R.string.Min_1_Coffee), Toast.LENGTH_SHORT).show();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(calculatePrice(hasWhippedCream,hasChocolate),hasWhippedCream,hasChocolate,name));
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    /**
     * Create summary of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream,boolean addChocolate,String name){
        String priceMessage = getString(R.string.order_summary_name ,name) ;
        priceMessage = priceMessage + "\n" + getString(R.string.add_Whipped_Cream ,addWhippedCream);
        priceMessage = priceMessage + "\n" + getString(R.string.add_Chocolate ,addChocolate);
        priceMessage = priceMessage + "\n" + getString(R.string.order_summary_quantity ,quantity);
        priceMessage = priceMessage + "\n" + getString(R.string.total ,price)  ;
        priceMessage = priceMessage + "\n" + getString(R.string.Thank_You);
        return priceMessage;
    }

    private int calculatePrice(boolean hasWhippedCream,boolean hasChocolate) {
        int price = 5;
        if(hasChocolate)
            price += 2;
        if(hasWhippedCream)
            price += 1;
        return quantity * price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */

    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
}