package com.example.test5.ui.engineering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.test5.R;
import com.example.test5.databinding.FragmentEngineerBinding;

import java.math.BigDecimal;

import static java.lang.Math.PI;

public class EngineeringFragment extends Fragment {
    private TextView number;
    private TextView progress;
    public String divideErrorMsg = "0으로 나눌 수 없습니다.";
    StringBuilder ColProcess;
    public FragmentEngineerBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_engineer, container, false);
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
        if(this.number.getText().equals(divideErrorMsg)==false){
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
            String paste = "";
            String num = (String) this.number.getText();
            if(num.equals("0")){
                for (int i = 0; i < (num.length()) - 1; i++) {
                    paste += num.charAt(i);
                }
            }else{
                paste = "0";
            }
            this.number.setText(paste);
        }
    }
    public void pressColButton(View view) {
        String sNum2 = (String) this.number.getText();
        String text = this.ColProcess.toString();
        if(text.equals("")==false){
            BigDecimal num2 = new BigDecimal(sNum2);
            String[] a = text.split(" ");
            BigDecimal NResult = new BigDecimal(a[0]);
            for(int n = 2 ; n < a.length ; n=n+2){
                BigDecimal num = new BigDecimal(a[n]);
                NResult = operate(NResult, a[n-1] ,num);
            }
            operate(NResult,a[a.length-1],num2);
            this.ColProcess = new StringBuilder();
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
            this.number.setText(sNum2);
        }
    }
    public void pressDenominatorButton(View view) {
        if (this.number.getText().equals(divideErrorMsg)==false) {
            String sNum2 = (String) this.number.getText();
            BigDecimal num2 = new BigDecimal(sNum2);
            BigDecimal one = new BigDecimal(1);
            num2 = one.divide(num2, 15, BigDecimal.ROUND_HALF_UP);
            sNum2 = checkBigDecimal(num2);
            this.number.setText(sNum2);
        }
    }
    public void pressLParenthesesButton(View view) {
        String s = (String) ((Button) view).getText();
        if(this.number.getText().equals(divideErrorMsg)==false){
            this.ColProcess.append(" " + s +" ");
            this.progress.setText(this.ColProcess);
        }
    }
    public void pressRParenthesesButton(View view) {
        String s = (String) ((Button) view).getText();
        String sNum2 = (String) this.number.getText();
        if(this.number.getText().equals(divideErrorMsg)==false){
            this.ColProcess.append(sNum2);
            this.ColProcess.append(" " + s +" ");
            this.progress.setText(this.ColProcess);
        }
    }

    public void pressFactorialButton(View view) {
        String s = (String) ((Button) view).getText();
        String sNum2 = (String) this.number.getText();
        if (this.number.getText().equals(divideErrorMsg)==false) {
            this.number.setText(this.number.getText()+s);
            Double DNum = Double.parseDouble(sNum2);
            int INum= DNum.intValue();
            if(INum==DNum){
                System.out.println("들어감");
                INum = Factorial(INum);
                this.number.setText(Integer.toString(INum));
            }else {
                this.number.setText("0");
            }
        }
    }
    public void pressPiButton(View view) {
        String s = (String) ((Button) view).getText();
        if (this.number.getText().equals(divideErrorMsg)==false) {
            BigDecimal num = new BigDecimal(Double.toString(PI));
            num = num.setScale (14, BigDecimal.ROUND_HALF_UP);
            String result = checkBigDecimal(num);
            this.number.setText(result);
        }
    }

    public void pressModButton(View view) {
        String s = "%";
        String sNum2 = (String) this.number.getText();
        if(this.number.getText().equals(divideErrorMsg)==false){
            this.ColProcess.append(sNum2);
            this.ColProcess.append(" " + s +" ");
            this.progress.setText(this.ColProcess);
            this.number.setText("0");
        }
    }
    public void pressExpButton(View view) {
        String sNum2 = (String) this.number.getText();
        if (this.number.getText().equals(divideErrorMsg)==false) {
            BigDecimal num2 = new BigDecimal(sNum2);
            BigDecimal num = new BigDecimal(Math.exp(num2.doubleValue()));
            num = num.setScale (14, BigDecimal.ROUND_HALF_UP);
            String result = checkBigDecimal(num);
            this.number.setText(result);
        }
    }
    public void pressLogButton(View view) {
        String sNum2 = (String) this.number.getText();
        if (this.number.getText().equals(divideErrorMsg)==false) {
            BigDecimal num2 = new BigDecimal(sNum2);
            BigDecimal num = new BigDecimal(Math.log10(num2.doubleValue()));
            num = num.setScale (14, BigDecimal.ROUND_HALF_UP);
            String result = checkBigDecimal(num);
            this.number.setText(result);
        }
    }
    public void press10IndexButton(View view) {
        String sNum2 = (String) this.number.getText();
        if (this.number.getText().equals(divideErrorMsg)==false) {
            BigDecimal num2 = new BigDecimal(sNum2);
            BigDecimal num = new BigDecimal(Math.pow(10, num2.doubleValue()));
            num = num.setScale (14, BigDecimal.ROUND_HALF_UP);
            String result = checkBigDecimal(num);
            this.number.setText(result);
        }
    }
    public void pressTanButton(View view) {
        String sNum2 = (String) this.number.getText();
        if (this.number.getText().equals(divideErrorMsg)==false) {
            BigDecimal num2 = new BigDecimal(Math.tan(Double.parseDouble(sNum2)/ 180.0 * PI));
            String result = checkBigDecimal(num2);
            this.number.setText(result);
        }
    }
    public void pressCosButton(View view) {
        String sNum2 = (String) this.number.getText();
        if (this.number.getText().equals(divideErrorMsg)==false) {
            BigDecimal num2 = new BigDecimal(Math.cos(Double.parseDouble(sNum2)/ 180.0 * PI));
            String result = checkBigDecimal(num2);
            this.number.setText(result);
        }
    }
    public void pressSinButton(View view) {
        String sNum2 = (String) this.number.getText();
        if (this.number.getText().equals(divideErrorMsg)==false) {
            BigDecimal num2 = new BigDecimal(Math.sin(Double.parseDouble(sNum2)/ 180.0 * PI));
            String result = checkBigDecimal(num2);
            this.number.setText(result);
        }
    }
    public void pressIndexButton(View view) {
        String s = "^";
        String sNum2 = (String) this.number.getText();
        if(this.number.getText().equals(divideErrorMsg)==false){
            this.ColProcess.append(sNum2);
            this.ColProcess.append(" " + s +" ");
            this.progress.setText(this.ColProcess);
            this.number.setText("0");
        }
    }



    public BigDecimal operate( BigDecimal num, String col, BigDecimal num2) {
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
                result = divideErrorMsg;
            }
        } else if(col.equals("^")){
            num = new BigDecimal(Math.pow(num.doubleValue(), num2.doubleValue()));
            num = num.setScale (14, BigDecimal.ROUND_HALF_UP);
            result = checkBigDecimal(num);
        } else if(col.equals("%")){
            num = new BigDecimal(num.doubleValue() % num2.doubleValue());
            num = num.setScale (14, BigDecimal.ROUND_HALF_UP);
            result = checkBigDecimal(num);
        }
        BigDecimal BResult = new BigDecimal(result);
        this.number.setText(result);
        this.progress.setText("");

        System.out.println(result);
        return BResult;
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

    public static int Factorial(int n)
    {
        if(n==0) return 1;
        else return n * Factorial(n-1);
    }


}
