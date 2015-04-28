package com.example.frank.tipscalculator2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.SeekBar;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    SeekBar percentSB;
    EditText percentText,splitText;
    Button btnMinus, btnAdd;
    int numOfBills = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        percentSB = (SeekBar)findViewById(R.id.percentBar);
        percentText = (EditText) findViewById(R.id.percentNum);

        //handles seekbar

        percentSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percentText.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        splitText = (EditText) findViewById(R.id.splitAmnt);
        btnMinus = (Button) findViewById(R.id.minusBtn);
        btnAdd = (Button) findViewById(R.id.addBtn);

        //implement add button and minus button

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numOfBills--;
                splitText.setText(String.valueOf(numOfBills));
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numOfBills++;
                splitText.setText(String.valueOf(numOfBills));
            }
        });
    }

    //Button on click listener

    public void showPopup(View view){
        final EditText subtotal = (EditText) findViewById(R.id.subTotal);
        final EditText tipPercent = (EditText) findViewById(R.id.percentNum);

        String subtotalString = subtotal.getText().toString();
        String percent = tipPercent.getText().toString();
        String total;
        String split;
        double tipAmnt, totalAmnt, splitTotal;

        //handle the situation when no input to subtotal

        if (subtotalString.equalsIgnoreCase("")){
            percent = "0.00";
            total = "0.00";
            subtotalString = "0.00";
            split = "N/A";
        }else {

            //handle the situation when no input to tips percentage

            if (percent.equalsIgnoreCase("")){
                tipAmnt = 0.00;
                percent = String.valueOf(tipAmnt);
                totalAmnt = tipAmnt + Double.valueOf(subtotalString);
                totalAmnt = (double)Math.round(totalAmnt*100)/100;
                total = String.valueOf(totalAmnt);

                //handle the situation if split bills into negative numbers

                if (numOfBills<=0){
                    split = "N/A";
                }else {
                    splitTotal = (double)Math.round(totalAmnt/numOfBills*100)/100;
                    split = String.valueOf(splitTotal);
                }
            }else {

                //handle the normal situation

                tipAmnt = Double.valueOf(subtotalString) * Double.valueOf(percent)/100;
                tipAmnt = (double)Math.round(tipAmnt*100)/100;
                percent = String.valueOf(tipAmnt);
                totalAmnt = tipAmnt + Double.valueOf(subtotalString);
                totalAmnt = (double)Math.round(totalAmnt*100)/100;
                total = String.valueOf(totalAmnt);

                if (numOfBills<=0){
                    split = "N/A";
                }else {
                    splitTotal = (double)Math.round(totalAmnt/numOfBills*100)/100;
                    split = String.valueOf(splitTotal);
                }
            }
        }

        //Start a popup window to show the result

        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_menu, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        TextView subView = (TextView) popupView.findViewById(R.id.subAmnt);
        TextView tipView = (TextView) popupView.findViewById(R.id.tipAmnt);
        TextView totalView = (TextView) popupView.findViewById(R.id.totalAmnt);
        TextView splitView = (TextView) popupView.findViewById(R.id.eachAmnt);

        subView.setText(subtotalString);
        tipView.setText(percent);
        totalView.setText(total);
        splitView.setText(split);

        Button btnDismiss = (Button) popupView.findViewById(R.id.dismissBtn);

        btnDismiss.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(findViewById(R.id.showPop), Gravity.CENTER,0,0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
