package com.example.android.justjava;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompatExtras;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {
    int count=2,price=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inflates java object to represent a linear layout
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        count++;
        if (count>8){
            count=8;
            Context context=getApplicationContext();
            Toast toastMessage= Toast.makeText(context,"Our coffee machine cannot make more than 8, please put another order. ",Toast.LENGTH_SHORT);
            toastMessage.show();
        }
        displayQuantity();
    }
    public void decrement(View view) {
        count--;
        if (count<1){
            count=1;
            Context context=getApplicationContext();
            Toast toastMessage= Toast.makeText(context,"We make delicious coffee, try once!",Toast.LENGTH_SHORT);
            toastMessage.show();
        }
        displayQuantity();
        /*Uri number = Uri.parse("tel:8951262709");                     //Calls telephone number
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);*/

                                                                        //performs web search
        /*Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
        String query="He";
        search.putExtra(SearchManager.QUERY, query);
        if(search.resolveActivity(getPackageManager())!=null){          //If any app exists for web search then only
            startActivity(search);                                     //open it
        }*/
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        displayMessage();
/*        Context context=getApplicationContext();
        Toast toastMessage= Toast.makeText(context,"Order placed !",Toast.LENGTH_SHORT);
        toastMessage.show();*/
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity() {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + count);
    }

    private int NewPrice(){
        int newPrice=price;

        CheckBox whippedCream=(CheckBox)findViewById(R.id.whipped_cream);
        boolean wC=whippedCream.isChecked();
        boolean chocolate=((CheckBox) findViewById(R.id.chocolate)).isChecked();

        if(wC)newPrice+=1;
        if(chocolate)newPrice+=2;
        return newPrice;
    }
    private void displayMessage() {
        String message;
        CheckBox whippedCream=(CheckBox)findViewById(R.id.whipped_cream);
        message="Name: "+ ((EditText)findViewById(R.id.name_edit)).getText() + "\n";

        boolean wC=whippedCream.isChecked();
        boolean chocolate=((CheckBox) findViewById(R.id.chocolate)).isChecked();
        message+="Add whipped cream ? "+ wC +"\n" ;

        message+="Add chocolate ? "+ chocolate +"\n" ;

        message+="Quantity: " + Integer.toString(count) + "\n";

        message+="Total: " + NumberFormat.getCurrencyInstance().format(count*NewPrice())+ "\n";

        message+=getString(R.string.thank_you);

        //Send email
        Intent mail = new Intent(Intent.ACTION_SEND);
        mail.setType("text/plain");
        String emailID[] = new String[1];
        emailID[0]=((EditText)findViewById(R.id.name_edit)).getText()+"@gmail.com";
        String subject="Missing you";
        mail.putExtra(Intent.EXTRA_EMAIL,emailID);
        mail.putExtra(Intent.EXTRA_SUBJECT,subject);
        mail.putExtra(Intent.EXTRA_TEXT, message);
        if(mail.resolveActivity(getPackageManager())!=null){
            startActivity(mail);
        }
        else{
            TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
            priceTextView.setText(message);
        }

    }
}