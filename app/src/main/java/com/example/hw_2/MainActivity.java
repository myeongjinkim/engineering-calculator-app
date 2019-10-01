package com.example.hw_2;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;


public class MainActivity extends AppCompatActivity {
    private TextView number;
    private TextView progress;
    public String divideErrorMsg = "0으로 나눌 수 없습니다.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        number = (TextView) findViewById(R.id.textView1);
        progress = (TextView) findViewById(R.id.textView2);
    }
    public void pressNumButton(View view) {
        String s = (String) ((Button) view).getText();
        if (this.number.getText().equals("0")||this.number.getText().equals(divideErrorMsg)) {
            this.number.setText(s);
        } else {
            this.number.setText(this.number.getText() + s);
        }
    }
    public void pressOperButton(View view) {
        String s = (String) ((Button) view).getText();
        if(this.progress.getText().equals("")==false) {
            String sNum2 = (String) this.number.getText();
            BigDecimal num2 = new BigDecimal(sNum2);
            String text = (String) this.progress.getText();
            String[] a = text.split(" ");
            BigDecimal num = new BigDecimal(a[0]);
            operate(a[1],num,num2);
        }
        this.progress.setText(this.number.getText() + " " + s);
        this.number.setText("0");
    }
    public void pressPointButton(View view) {
        String num = (String)this.number.getText();
        boolean check = true;
        for(int i=0;i<num.length() ;i++) {
            if(num.charAt(i)=='.') {
                check=false;
            }
        }
        if(check) {
            String s = (String) ((Button) view).getText();
            this.number.setText(this.number.getText() + s);
        }
    }
    public void pressSignButton(View view) {
        String s = (String) ((Button) view).getText();
        String num = (String) this.number.getText();
        if (num.charAt(0) == '0') {
        } else if (num.charAt(0) == '-') {
            String paste = "";
            for (int i = 0; i < (num.length()) - 1; i++) {
                paste += num.charAt(i + 1);
            }
            this.number.setText(paste);
        } else {
            this.number.setText("-" + num);
        }
    }
    public void pressRemoveButton(View view) {
        String s = (String) ((Button) view).getText();
        if (s.equals("C")) {
            this.number.setText("0");
            this.progress.setText("");
        } else if (s.equals("CE")) {
            this.number.setText("0");
        } else if (s.equals("지우기")) {
            String paste = "";
            String num = (String) this.number.getText();
            for (int i = 0; i < (num.length()) - 1; i++) {
                paste += num.charAt(i);
            }
            this.number.setText(paste);
        }
    }
    public void pressColButton(View view) {
        String sNum2 = (String) this.number.getText();
        String text = (String) this.progress.getText();
        if(text.equals("")==false){
            BigDecimal num2 = new BigDecimal(sNum2);
            String[] a = text.split(" ");
            BigDecimal num = new BigDecimal(a[0]);
            operate(a[1],num,num2);
        }
    }
    public void operate(String col, BigDecimal num, BigDecimal num2) {
        String result="";
        BigDecimal chkPoint;
        if (col.equals("+")) {
            result = checkBigDecimal(num.add(num2));
        } else if (col.equals("-")) {
            result = checkBigDecimal(num.subtract(num2));
            System.out.println(num);
            System.out.println(num2);
            System.out.println(num.subtract(num2));
            System.out.println(result);
        } else if (col.equals("*")) {
            result = checkBigDecimal(num.multiply(num2));
        } else if (col.equals("/")){
            if(num2.intValue()!=0){
                result = checkBigDecimal(num.divide(num2));
            }
            else{
                result=divideErrorMsg;
            }
        }
        this.number.setText(result);
        this.progress.setText("");
    }
    public String checkBigDecimal(BigDecimal chkPoint){
        String result;
        int convInt;
        double convDouble;
        convInt = chkPoint.intValue();
        convDouble = chkPoint.doubleValue();
        if(convInt==convDouble) {
            result = Integer.toString(convInt);
        }else {
            result = Double.toString(convDouble);
        }
        return  result;
    }
}
