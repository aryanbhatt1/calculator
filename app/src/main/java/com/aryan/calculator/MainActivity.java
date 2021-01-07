package com.aryan.calculator;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;



public class MainActivity extends AppCompatActivity {

    private int[] numericButtons = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};
    private int[] operatorButtons = {R.id.buttonadd, R.id.buttonsub, R.id.buttonmul, R.id.buttondiv,R.id.Remainder};
    private TextView txtScreen;
    private TextView txtScreen2;
    private boolean lastNumeric;
    private boolean stateError;
    private boolean lastDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.txtScreen = (TextView) findViewById(R.id.display);
        setNumericOnClickListener();
        setOperatorOnClickListener();
        this.txtScreen2=(TextView)findViewById(R.id.result);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_main);
    }

    private void setNumericOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if(txtScreen2.getText().length()>0){
                    txtScreen.setText(null);
                    txtScreen2.setText(null);
                    if (stateError) {
                        txtScreen.setText(button.getText());
                        stateError = false;
                    } else {
                        txtScreen.append(button.getText());
                    }
                    lastNumeric = true;
                }
                else{
                    if (stateError) {
                        txtScreen.setText(button.getText());
                        stateError = false;
                    } else {
                        txtScreen.append(button.getText());
                    }
                    lastNumeric = true;
                }
            }
        };
        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }
    private void setOperatorOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !stateError) {
                    Button button = (Button) v;
                    txtScreen.append(button.getText());
                    lastNumeric = false;
                    lastDot = false;
                }
            }
        };
        for (int id : operatorButtons) {
            findViewById(id).setOnClickListener(listener);
        }

        findViewById(R.id.buttonDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !stateError && !lastDot) {
                    txtScreen.append(".");
                    lastNumeric = false;
                    lastDot = true;
                }
            }
        });
        findViewById(R.id.buttonDel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtScreen2.getText().length()>0){
                    txtScreen2.setText(null);
                    CharSequence name = txtScreen.getText().toString();
                    txtScreen.setText(name.subSequence(0,name.length()-1));
                }
                else {
                    if(txtScreen.getText().length()>0){
                        CharSequence name = txtScreen.getText().toString();
                        txtScreen.setText(name.subSequence(0,name.length()-1));
                    }
                }
                lastNumeric = false;
                stateError = false;
                lastDot = false;
            }
        });

        findViewById(R.id.button10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtScreen.setText("");
                txtScreen2.setText("");
                lastNumeric = false;
                stateError = false;
                lastDot = false;
            }
        });
        // Equal button
        findViewById(R.id.buttoneql).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });
    }


    private void onEqual() {

        if (lastNumeric && !stateError) {

            String txt = txtScreen.getText().toString();
            Expression expression = new ExpressionBuilder(txt).build();
            try {
                double result = expression.evaluate();
                txtScreen2.setText(Double.toString(result));
                lastDot = true;
            } catch (ArithmeticException ex) {
                txtScreen.setText("Error");
                stateError = true;
                lastNumeric = false;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }
}
