package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.id;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=0;
    int price=8;
    boolean whipped=false;
    boolean chocolate=false;
    CheckBox whippedCream;
    CheckBox chckCoko;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        whippedCream= (CheckBox) findViewById(R.id.chck_whipped);
        chckCoko= (CheckBox) findViewById(R.id.chck_choko);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int priceToDisplay=calculatePrice();
        EditText editText= (EditText) findViewById(R.id.nameOfCustomer);
        name= editText.getText().toString();
        String poruka=getString(R.string.order_summary_name,name);
        poruka+="\n"+getString(R.string.add_cream)+" "+whipped;
        poruka+="\n"+getString(R.string.add_chocolate)+" "+chocolate;
        poruka+="\n"+getString(R.string.quantity)+": "+quantity;
        poruka+="\n"+getString(R.string.total)+": "+priceToDisplay+" "+getString(R.string.currency);
        poruka+="\n"+getString(R.string.thank_you);
        sendEmail(poruka, name);
        //displayMessage(createOrderSummary(priceToDisplay,name));
    }

    public void sendEmail(String poruka, String name){
        Intent intent= new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT,poruka);
        intent.putExtra(Intent.EXTRA_SUBJECT,"JustJava order for "+name);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }
    public int calculatePrice(){
        int cijena=quantity*price;
        if(whipped) cijena +=2*quantity;
        if(chocolate) cijena +=3*quantity;
        return cijena;
    }
    public void increment(View view){
        if(quantity<100){
            quantity++;
        }else{
            Context context= getApplicationContext();
            CharSequence text="You cannot have more than 100 cups of coffee";
            int duration= Toast.LENGTH_SHORT;
            Toast toast= Toast.makeText(context,text,duration);
            toast.show();
        }
        displayQuantity(quantity);
    }
    public void decrement(View view){
        if(quantity>1){
            quantity--;
        }else{
            Context context= getApplicationContext();
            CharSequence text="You cannot have less than 1 cups of coffee";
            int duration= Toast.LENGTH_SHORT;
            Toast toast= Toast.makeText(context,text,duration);
            toast.show();
        }
        displayQuantity(quantity);
    }
    public void hasWhipped(View view){
        if(whippedCream.isChecked()) whipped=true;
        else whipped=false;
    }
    public void hasChoko(View view){
        if(chckCoko.isChecked()) chocolate=true;
        else chocolate=false;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);

    }

    /**
     * This method displays the given text on the screen.
     */
    /*private void displayMessage(String message){
        TextView orderSummaryTextView=(TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }*/
}