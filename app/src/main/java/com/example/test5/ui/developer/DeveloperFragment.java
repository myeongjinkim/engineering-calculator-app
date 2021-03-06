package com.example.test5.ui.developer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.test5.R;
import com.example.test5.databinding.FragmentDeveloperBinding;

import java.math.BigDecimal;

public class DeveloperFragment extends Fragment {

    private TextView number;
    private TextView progress;
    public String divideErrorMsg = "0으로 나눌 수 없습니다.";
    StringBuilder ColProcess;
    public FragmentDeveloperBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_developer, container, false);
        number = (TextView) rootView.findViewById(R.id.textView1);
        progress = (TextView) rootView.findViewById(R.id.textView2);
        ColProcess = new StringBuilder();
        binding = DataBindingUtil.bind(rootView);
        binding.setFragment(this);
        return rootView;
    }
    public void pressNumButton(View view) {
        String s = (String) ((Button) view).getText();
        if (this.number.getText().equals(divideErrorMsg)==false && this.number.getText().equals("0")==false) {
            this.number.setText(this.number.getText()+s);
        } else {
            this.number.setText(s);
        }
    }
    public void pressOperButton(View view) {
        String s = (String) ((Button) view).getText();
        String sNum2 = (String) this.number.getText();
        if(this.number.getText().equals(divideErrorMsg)){

        }else{
            this.ColProcess.append(sNum2);
            this.ColProcess.append(" " + s +" ");
            this.progress.setText(this.ColProcess);
            this.number.setText("0");
            String text = this.ColProcess.toString();
            System.out.println(text+" 계산식 ");
        }


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
            this.ColProcess = new StringBuilder();
            this.progress.setText(ColProcess);
        } else if (s.equals("CE")) {
            this.number.setText("0");
        } else if (s.equals("지우기")) {
            if(this.number.getText().equals(divideErrorMsg)==false){
                String paste = "";
                String num = (String) this.number.getText();
                if(num.length()==1){
                    paste = "0";
                }else{
                    for (int i = 0; i < (num.length()) - 1; i++) {
                        paste += num.charAt(i);
                    }
                }
                this.number.setText(paste);
            }
        }
    }
    public void pressColButton(View view) {
        String sNum2 = (String) this.number.getText();
        String text = this.ColProcess.toString();
        text = text+sNum2;
        System.out.println(text+" 계산중 ");
        if(text.equals("")==false){
            String[] a = text.split(" ");
            BigDecimal NResult = new BigDecimal(a[0]);
            for(int n = 2 ; n <= a.length ; n=n+2){
                BigDecimal num = new BigDecimal(a[n]);
                System.out.println(NResult+" "+ a[n-1]+" "+num);
                text = operate(NResult, a[n-1] ,num);
                if(text.equals(divideErrorMsg)){
                    break;
                }
                NResult = new BigDecimal(text);
            }

            this.number.setText(text);
            this.progress.setText("");
            this.ColProcess = new StringBuilder();
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
            num2 = num2.setScale(14, BigDecimal.ROUND_HALF_UP);
            sNum2 = checkBigDecimal(num2);
            for(int n=0;n < sNum2.length();n++){
                if(sNum2.charAt(n)=='.'){
                    for(int k=n+1, num9=0, num0=0 ;k<sNum2.length();k++){
                        if(sNum2.charAt(k)=='9'){
                            num9++;
                        }else if(sNum2.charAt(k)=='0'){
                            num0++;
                        }
                        if((num9==k-n && num9>3 ) || (num0==k-n && num0>3) ){
                            num2 = num2.setScale(0, BigDecimal.ROUND_HALF_UP);
                            sNum2 = checkBigDecimal(num2);
                            break;
                        }
                    }
                }
            }
            this.number.setText(sNum2);
        }
    }
    public void pressDenominatorButton(View view) {
        if (this.number.getText().equals(divideErrorMsg)==false) {
            String sNum2 = (String) this.number.getText();
            BigDecimal num2 = new BigDecimal(sNum2);
            BigDecimal one = new BigDecimal(1);
            num2 = one.divide(num2, 14, BigDecimal.ROUND_HALF_UP);
            sNum2 = checkBigDecimal(num2);
            for(int n=0;n < sNum2.length();n++){
                if(sNum2.charAt(n)=='.'){
                    for(int k=n+1, num9=0, num0=0 ;k<sNum2.length();k++){
                        if(sNum2.charAt(k)=='9'){
                            num9++;
                        }else if(sNum2.charAt(k)=='0'){
                            num0++;
                        }
                        if((num9==k-n && num9>3 ) || (num0==k-n && num0>3) ){
                            num2 = num2.setScale(0, BigDecimal.ROUND_HALF_UP);
                            sNum2 = checkBigDecimal(num2);
                            break;
                        }
                    }
                }
            }
            this.number.setText(sNum2);
        }
    }
    public String operate( BigDecimal num, String col, BigDecimal num2) {
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
                result = checkBigDecimal(num.divide(num2, 14, BigDecimal.ROUND_HALF_UP));
            }
            else{
                result = divideErrorMsg;
            }
        }
        return result;
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
