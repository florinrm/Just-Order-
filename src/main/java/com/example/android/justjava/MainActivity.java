package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

import static android.R.attr.checked;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    public int quantity = 2;
    public boolean ordered = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price_choco = 0, price_cream = 0;
        CheckBox check_box_cream = (CheckBox) findViewById(R.id.checkBox);
        CheckBox check_box_choco = (CheckBox) findViewById(R.id.checkBox2);
        boolean checked_cream = check_box_cream.isChecked();
        boolean checked_choco = check_box_choco.isChecked();
        if (checked_choco)
            price_choco = 1;
        if (checked_cream)
            price_cream = 2;
        displayPrice(quantity * (5 + price_choco + price_cream));
        displayMessage("Thank you!");
    }

    public void increment (View view)
    {
        //quantity = 3;
        display(++quantity);
    }

    public void decrement (View view)
    {
        //quantity = 1;
        display(--quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        if (number < 1)
            return;
        quantityTextView.setText("" + number);
    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        CheckBox check_box_cream = (CheckBox) findViewById(R.id.checkBox);
        CheckBox check_box_choco = (CheckBox) findViewById(R.id.checkBox2);
        boolean checked_cream = check_box_cream.isChecked();
        boolean checked_choco = check_box_choco.isChecked();
        EditText name = (EditText) findViewById(R.id.editText);
        String whipped_cream;
        String choco;
        if (checked_cream)
            whipped_cream = "Yes";
        else
            whipped_cream = "No";

        if (checked_choco)
            choco = "Yes";
        else
            choco = "No";
        int price_choco = 0, price_cream = 0;
        if (checked_choco)
            price_choco = 1;
        if (checked_cream)
            price_cream = 2;
        int price = quantity * (5 + price_choco + price_cream);
        String copy = "Order Summary";
        ordered = true;
        if (!ordered)
        {
            copy = orderSummaryTextView.getText().toString();
            orderSummaryTextView.setText(copy);
        }
        else
        {
            orderSummaryTextView.setText(copy + "\nName: " + name.getText().toString()
                    +"\nWhipped Cream? " + whipped_cream
                    + "\nChocolate? " + choco +"\nPrice: "
                    + NumberFormat.getCurrencyInstance().format(price)
                    + "\n"+getString(R.string.thank_you)+"\n");
        }
        // ordering via e-mail
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order for " + name.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, copy + "\nName: " + name.getText().toString()
                +"\nWhipped Cream? " + whipped_cream
                + "\nChocolate? " + choco +"\nPrice: "
                + NumberFormat.getCurrencyInstance().format(price)
                + "\n"+getString(R.string.thank_you)+"\n");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
}
