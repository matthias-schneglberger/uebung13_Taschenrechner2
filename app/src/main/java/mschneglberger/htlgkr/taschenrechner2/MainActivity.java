package mschneglberger.htlgkr.taschenrechner2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity{
    private TextView ergView;
    private TextView postFixView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ergView = findViewById(R.id.textViewErg);
        postFixView = findViewById(R.id.textViewPostfix);
        setupButtons();
    }

    public void onClickIstGleich(View view){
        String inputStr = ergView.getText().toString();
        Log.i("Main", inputStr);
        String postfix = convertToPostfix(inputStr);
        postFixView.setText("PostFix: " + postfix);
        try{
            ergView.setText(evaluatePostFix(postfix));
        }
        catch(Exception e){
            ergView.setText("An Unexpected Error Concurred While Evuluating!!!");
        }


    }

    public void onClickClear(View view){
        ergView.setText("");
        postFixView.setText("PostFix: ");
    }


    public void setupButtons(){
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ergView.append("1");
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ergView.append("2");
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ergView.append("3");
            }
        });

        Button buttonPlus = findViewById(R.id.buttonPlus);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ergView.append("+");
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ergView.append("4");
            }
        });

        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ergView.append("5");
            }
        });

        Button button6 = findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ergView.append("6");
            }
        });

        Button buttonMinus = findViewById(R.id.buttonMinus);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ergView.append("-");
            }
        });

        Button button7 = findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ergView.append("7");
            }
        });

        Button button8 = findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ergView.append("8");
            }
        });

        Button button9 = findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ergView.append("9");
            }
        });

        Button buttonMal = findViewById(R.id.buttonMal);
        buttonMal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ergView.append("*");
            }
        });

        Button buttonKlammerAuf = findViewById(R.id.buttonKlammerAuf);
        buttonKlammerAuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ergView.append("(");
            }
        });

        Button button0 = findViewById(R.id.button0);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ergView.append("0");
            }
        });

        Button buttonKlammerZu = findViewById(R.id.buttonKlammerZu);
        buttonKlammerZu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ergView.append(")");
            }
        });

        Button buttonDividieren = findViewById(R.id.buttonDividieren);
        buttonDividieren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ergView.append("/");
            }
        });

        Button buttonDecimal = findViewById(R.id.buttonDecimal);
        buttonDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ergView.append(".");
            }
        });

        Button buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickClear(view);
            }
        });

        Button buttonGleich = findViewById(R.id.buttonGleich);
        buttonGleich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickIstGleich(view);
            }
        });

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullStr = ergView.getText().toString();
                try{
                    ergView.setText(fullStr.substring(0,fullStr.length()-1));
                }
                catch(IndexOutOfBoundsException e){

                }

            }
        });
    }


    private static boolean isOperator(char c){
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^'
                || c == '(' || c == ')';
    }
    private static boolean isOperatorString(String c){
        return c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/") || c.equals("(") || c.equals(")");
    }
    private static boolean isLowerPrecedence(char op1, char op2){
        switch (op1){
            case '+':
            case '-':
                return !(op2 == '+' || op2 == '-');

            case '*':
            case '/':
                return op2 == '^' || op2 == '(';

            case '^':
                return op2 == '(';

            case '(':
                return true;

            default:
                return false;
        }
    }

    public static String convertToPostfix(String infix){
        Stack<Character> stack = new Stack<Character>();
        StringBuffer postfix = new StringBuffer(infix.length());
        char c;
        boolean newNum = false;

        for (int i = 0; i < infix.length(); i++){
            c = infix.charAt(i);

            if (!isOperator(c)){
                if(newNum){
                    postfix.append(" " + c);
                    newNum = false;
                }
                else{
                    postfix.append(c);
                }

            }
            else{
                newNum = true;
                if (c == ')'){

                    while (!stack.isEmpty() && stack.peek() != '('){
                        postfix.append(" " + stack.pop());
                    }
                    if (!stack.isEmpty()){
                        stack.pop();
                    }
                }

                else{
                    if (!stack.isEmpty() && !isLowerPrecedence(c, stack.peek())){
                        stack.push(c);
                    }
                    else{
                        while (!stack.isEmpty() && isLowerPrecedence(c, stack.peek())){
                            Character pop = stack.pop();
                            if (c != '('){
                                postfix.append(" " + pop);
                            }
                            else{
                                c = pop;
                            }
                        }
                        stack.push(c);
                    }

                }
            }
        }
        while (!stack.isEmpty()) {
            postfix.append(" " + stack.pop());
        }
        return postfix.toString();
    }

    public static String evaluatePostFix(String postfix){
        String[] parts = postfix.split(" ");
        Stack<String> mainStack = new Stack<>();

        for(int i = 0; i < parts.length; i++){
            if(!isOperatorString(parts[i])){
                mainStack.push(parts[i]);
            }
            else{
                String temp = String.valueOf(eval(mainStack.pop(), mainStack.pop(), parts[i]));
                mainStack.push(temp);
            }
        }
        return mainStack.pop();
    }
    private static double eval(String num, String num2, String op){
        double numInt = Double.valueOf(num);
        double num2Int = Double.valueOf(num2);

        switch(op)
        {
            case "+":
                return(num2Int+numInt);
            case "-":
                return(num2Int- numInt);
            case "/":
                return(num2Int/numInt);
            case "*":
                return(num2Int*numInt);
        }
        return 0.0;
    }







}
