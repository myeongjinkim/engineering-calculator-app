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
import java.util.Queue;
import java.util.Stack;

import static java.lang.Math.PI;

public class EngineeringFragment extends Fragment {
    private TextView number;
    private TextView progress;
    public String divideErrorMsg = "입력이 잘못되었습니다.";
    StringBuilder ColProcess;
    public Boolean NumClick = false;
    public Boolean Parentheses = true;
    public int RightParentheses = 0;

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
        if(Parentheses==false){

        }
        else if (NumClick) {
            this.number.setText(this.number.getText()+s);
            NumClick=true;
        } else {
            this.number.setText(s);
            NumClick=true;
        }
    }
    public void pressOperButton(View view) {
        String s = (String) ((Button) view).getText();
        String sNum2 = (String) this.number.getText();
        String sNum = (String) this.progress.getText();
        if(NumClick || Parentheses==false){
            Parentheses=true;
            NumClick=false;
            if(sNum.equals("") || sNum.charAt(sNum.length()-1)!=')'){
                this.ColProcess.append(" " +sNum2);
            }
            this.ColProcess.append(" " + s);
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
            NumClick = false;
            this.number.setText("0");
            this.ColProcess = new StringBuilder();
            this.progress.setText(ColProcess);
        } else if (s.equals("CE")) {
            NumClick = false;
            this.number.setText("0");
        } else if (s.equals("지우기")) {
            String paste = "";
            String num = (String) this.number.getText();
            if(num.length()==1){
                NumClick = false;
                paste = "0";
            }else{
                for (int i = 0; i < (num.length()) - 1; i++) {
                    paste += num.charAt(i);
                }
            }
            this.number.setText(paste);
        }
    }
    public void pressColButton(View view) {
        String sNum2 = (String) this.number.getText();
        String text = this.ColProcess.toString();
        if(RightParentheses==0){
            String result = BackMarkingMethod(text +" "+ sNum2);
            System.out.println(result);
            result = BackMarkingMethodCol(result);
            System.out.println(result);
            this.progress.setText("");
            this.number.setText(result);
            Parentheses=true;
            NumClick=false;
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
    public void pressLParenthesesButton(View view) {
        String s = (String) ((Button) view).getText();
        if(NumClick==false){
            this.ColProcess.append(s +" ");
            this.progress.setText(this.ColProcess);
            RightParentheses++;
        }
    }
    public void pressRParenthesesButton(View view) {
        String s = (String) ((Button) view).getText();
        String sNum2 = (String) this.number.getText();
        String sNum = (String) this.progress.getText();
        if(this.number.getText().equals(divideErrorMsg)==false&&RightParentheses>0){
            Parentheses=false;
            try{
                String st = String.valueOf(sNum.charAt(sNum.length()-1));
                Integer.parseInt(st);
            }catch(NumberFormatException e){
                this.ColProcess.append(" " + sNum2);
            }
            this.ColProcess.append(" "+ s);
            this.progress.setText(this.ColProcess);
            this.number.setText("0");
            NumClick=false;
            RightParentheses--;
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
                BigDecimal BNum = new BigDecimal(INum);
                BNum = Factorial(BNum);
                sNum2 = checkBigDecimal(BNum);
                this.number.setText(sNum2);
            }else {
                this.number.setText("0");
                NumClick=false;
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
        if (this.number.getText().equals(divideErrorMsg)==false&& sNum2.equals(0)==false) {
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
        String sNum = (String) this.progress.getText();
        if(this.number.getText().equals(divideErrorMsg)==false){
            if(sNum.equals("")){
                this.ColProcess.append(sNum2);
            }

            this.ColProcess.append(" " + s);
            this.progress.setText(this.ColProcess);
            this.number.setText("0");
            NumClick=false;
        }
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

    public static BigDecimal Factorial(BigDecimal n)
    {
        if(n.intValue()== 1) {
            return new BigDecimal(1);
        }else {
            return n.multiply(Factorial(n.subtract(new BigDecimal(1))));
        }
    }

    public static int priority(String s) {
        switch (s) {
            case "^":
            case "*":
            case "/":
                return 2;
            case "+":
            case "-":
            case "Mod":
                return 1;
            case "(":
            case ")":
                return 0;
        }
        return -1;
    }

    public String BackMarkingMethod(String free){
        String[] s = free.split(" ");
        int len = s.length;
        Stack<String> stack = new Stack<String>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            int p = priority(s[i]);
            String oper = s[i];
            switch (oper) {
                case "+":
                case "-":
                case "*":
                case "/":
                case "Mod":
                case "^":
                    while (!stack.isEmpty() && priority(stack.peek()) >= p) {
                        sb.append(stack.pop()+" ");
                        System.out.println(sb);
                    }
                    stack.push(oper);
                    break;
                case "(":
                    stack.push(oper);
                    break;
                case ")":
                    while (!stack.isEmpty() && stack.peek().contentEquals("(") == false) {
                        sb.append(stack.pop()+" ");
                        System.out.println(sb);
                    }
                    stack.pop();
                    break;
                default:
                    sb.append(oper+" ");
                    System.out.println(sb);
            }
        }
        System.out.println(sb+"전과정");
        while (!stack.isEmpty()) {
            sb.append(stack.pop()+" ");
            System.out.println(sb);
        }
        System.out.println(sb);
        return sb.toString();
    }
    public String BackMarkingMethodCol(String str) {
        Stack<String> op = new Stack<String>();
        String[] s = str.split(" ");
        int N = s.length;
        String result = "";

        for(int i = 0 ; i <  N; ++ i)
        {

            if( s[i].equals("+") || s[i].equals("-") || s[i].equals("*")|| s[i].equals("/")|| s[i].equals("Mod")|| s[i].equals("^") ) {
                BigDecimal num2 = new BigDecimal(op.pop());
                BigDecimal num = new BigDecimal(op.pop());
                if(s[i].equals("^")){
                    num = new BigDecimal(Math.pow(num.doubleValue(), num2.doubleValue()));
                    num = num.setScale (14, BigDecimal.ROUND_HALF_UP);
                    result = checkBigDecimal(num);
                    System.out.println(num+" ^ "+num2+" "+result);

                }else if(s[i].equals("Mod")){
                    System.out.println("%연산 "+num+" "+num2);
                    num = new BigDecimal(num.doubleValue() % num2.doubleValue());
                    num = num.setScale (14, BigDecimal.ROUND_HALF_UP);
                    result = checkBigDecimal(num);
                    System.out.println(num+" mod "+num2+" "+result);
                }else if(s[i].equals("+")){

                    result = checkBigDecimal(num.add(num2));
                    System.out.println(num+" + "+num2+" "+result);
                }else if(s[i].equals("-")){
                    result = checkBigDecimal(num.subtract(num2));
                    System.out.println(num+" - "+num2+" "+result);
                }else if(s[i].equals("*")){
                    result = checkBigDecimal(num.multiply(num2));
                    System.out.println(num+" * "+num2+" "+result);
                }else if(s[i].equals("/")){
                    BigDecimal zero = new BigDecimal(0);
                    if(num2.compareTo(zero)!=0){
                        System.out.println(num+" / "+num2+" ");
                        result = checkBigDecimal(num.divide(num2, 14, BigDecimal.ROUND_HALF_UP));
                        System.out.println(num+" / "+num2+" ");
                    }
                    else{
                        NumClick=false;
                        return divideErrorMsg;
                    }
                    System.out.println(num+" / "+num2+" "+result);
                }
                op.push(result);
            }else{
                op.add(s[i]);
            }

        }

        // System.out.println(op.pop());
        return op.pop();
    }



}
