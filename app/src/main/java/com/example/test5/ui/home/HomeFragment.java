package com.example.test5.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.test5.R;
import java.math.BigDecimal;

public class HomeFragment extends Fragment{

    private HomeViewModel homeViewModel;
    private TextView number;
    private TextView progress;
    public String divideErrorMsg = "0으로 나눌 수 없습니다.";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        number = (TextView) rootView.findViewById(R.id.textView1);
        progress = (TextView) rootView.findViewById(R.id.textView2);
/*
        FragmentHomeBinding binding = DataBindingUtil.bind(rootView);
        binding.setFragment(this);*/
        return rootView;
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
    public void pressPercentButton(View view) {

        String text = (String) this.progress.getText();
        String[] a = new String[2];
        a = text.split(" ");
        if (this.number.getText().equals(divideErrorMsg)==false) {
            if (a.length==2&&(a[1].equals("*")==true||a[1].equals("/")==true)) {
                String sNum2 = (String) this.number.getText();
                BigDecimal num2 = new BigDecimal(sNum2);
                BigDecimal num = new BigDecimal(100);
                sNum2 = checkBigDecimal(num2.divide(num));
                this.number.setText(sNum2);
            }
        }
    }
    public void pressSqrtButton(View view) {
        if (this.number.getText().equals(divideErrorMsg)==false) {
            String sNum2 = (String) this.number.getText();
            BigDecimal num2 = new BigDecimal(sNum2);
            num2 = BigDecimal.valueOf(Math.sqrt(num2.doubleValue()));
            sNum2 = checkBigDecimal(num2);
            this.number.setText(sNum2);
        }
    }
    public void pressSquareButton(View view) {
        if (this.number.getText().equals(divideErrorMsg)==false) {
            String sNum2 = (String) this.number.getText();
            BigDecimal num2 = new BigDecimal(sNum2);
            num2 = num2.pow(2);
            sNum2 = checkBigDecimal(num2);
            this.number.setText(sNum2);
        }
    }
    public void pressDenominatorButton(View view) {
        if (this.number.getText().equals(divideErrorMsg)==false) {
            String sNum2 = (String) this.number.getText();
            BigDecimal num2 = new BigDecimal(sNum2);
            BigDecimal one = new BigDecimal(1);
            num2 = one.divide(num2);
            sNum2 = checkBigDecimal(num2);
            this.number.setText(sNum2);
        }
    }
    public void operate(String col, BigDecimal num, BigDecimal num2) {
        String result="";
        if (col.equals("+")) {
            result = checkBigDecimal(num.add(num2));
        } else if (col.equals("-")) {
            result = checkBigDecimal(num.subtract(num2));
        } else if (col.equals("*")) {
            result = checkBigDecimal(num.multiply(num2));
        } else if (col.equals("/")){
            BigDecimal zero = new BigDecimal(0);
            if(num2.compareTo(zero)!=0){
                result = checkBigDecimal(num.divide(num2));
            }
            else{
                result=divideErrorMsg;
                System.out.println(1);
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