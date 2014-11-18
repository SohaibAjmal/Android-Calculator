package com.example.calculator;

import java.util.Hashtable;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Calculator extends ActionBarActivity {

	String inputString,outputString,operatorString,operandString;
	Stack<String> calcStack = new Stack<String>();
	
	Boolean operatorClicked;
	
	/*Constructor*/
	public Calculator()
	{
		inputString = "";
		outputString = "";
		operatorString = "";
		operandString = "";
		
		calcStack.push("0");
		
		operatorClicked = false;
	}
	
	
    /**************************/
    /*   Calculator methods   */
    /**************************/
    
    String calculateResult(String operand1, String operand2, String operator)
    {
    	Double op1D,op2D,resultD = 0.0;
    	
    	op1D = Double.parseDouble(operand1);

		op2D = Double.parseDouble(operand2);
	
    	String result = "";
    
		if (operator == "+")
			resultD = op1D + op2D;
    	
    	else if (operator == "-")
    		resultD = op1D - op2D;
    	
    	else if(operator == "*")
    		resultD = op1D * op2D;
    	
    	else if(operator == "/")
    		resultD = op1D / op2D;
		
		result = Double.toString(resultD);
    	
		String[] decPresent = result.split("\\.");
		
		if (decPresent[1].matches("0"))
		{
			result = decPresent[0];
		}
    	
    	return result;
    	
    	
    	
    }
    
    public static Boolean isOperator(String value)
	{
		String pattern = "[\\+\\*\\-\\/]";
		
		Pattern ptn = Pattern.compile(pattern);
		Matcher m =ptn.matcher(value);
		
		if (value != ".")
			if (m.find())
				return true;
		
		return false;
		

	}
    
    public static Boolean validNumber(String value)
	{
		String pattern = "[\\d]";
		
		Pattern ptn = Pattern.compile(pattern);
		Matcher m =ptn.matcher(value);
		
		if (m.find())
			return true;
		
		return false;
		

	}
    
    public void operandCall(String operand)
    {
    	TextView inputView = (TextView)findViewById(R.id.textViewInput);
    	TextView outputView = (TextView)findViewById(R.id.textViewOutput);
    	
    	/*if only zero in the stack*/
    	if(calcStack.size() == 1 && calcStack.peek() == "0")
    		calcStack.pop();
    		
    	calcStack.push(operand);
    	
    	
    	
    	
    }
    
    public void operatorCall(String operator)
    {
    	TextView inputView = (TextView)findViewById(R.id.textViewInput);
    	TextView outputView = (TextView)findViewById(R.id.textViewOutput);
    	
    	
    	int operand2,operand1;
    	String operatorT,operand2Str,operand1Str;
    	
    
    	/*else further calculation will be continued*/
    	if(calcStack.size() == 3)
    	{
    		String result = "";
    		
    		operand2Str = calcStack.pop();
    		operatorT = calcStack.pop().toString();
    		operand1Str = calcStack.pop();
    		
    		result = calculateResult(operand1Str, operand2Str, operatorT);
    		
        		
    		calcStack.push(result);
    		
    		
    		
    		if (operator.toString() != "=")
    		{
    			calcStack.push(operator);
    		
    			
    		}
    		else
    		{	
    			inputView.setText("");
    			inputString = result;    			
 
    		}
    		
    		outputView.setText(result);
    		
    	}
    	else if(calcStack.size() == 2)
    	{
    		String value = calcStack.pop();
    		
    		if (isOperator(value))
    		{
    			if (operator != "=")
    			{
    				calcStack.pop();
    				calcStack.push(operator);
    			
    			}
    			
    		}
    		
    	}
    	else
    	{
    		calcStack.push(operator);
    		
    	}
    	
    	
   
    	
    }
    
    public void calculateMethod(View view)
    {
    	
    	TextView inputView = (TextView)findViewById(R.id.textViewInput);
    	TextView outputView = (TextView)findViewById(R.id.textViewOutput);
    	
    	Hashtable<Integer, String> button_map = new Hashtable<Integer, String>();
    	button_map.put(R.id.ZeroBtn,"0");
    	button_map.put(R.id.OneBtn,"1");
    	button_map.put(R.id.TwoBtn,"2");
    	button_map.put(R.id.ThreeBtn,"3");
    	button_map.put(R.id.FourBtn,"4");
    	button_map.put(R.id.FiveBtn,"5");
    	button_map.put(R.id.SixBtn,"6");
    	button_map.put(R.id.SevenBtn,"7");
    	button_map.put(R.id.EightBtn,"8");
    	button_map.put(R.id.NineBtn,"9");
    	
    	button_map.put(R.id.PlusBtn,"+");
    	button_map.put(R.id.MinusBtn,"-");
    	button_map.put(R.id.DivideBtn,"/");
    	button_map.put(R.id.MultiplyBtn,"*");
    	button_map.put(R.id.EqualBtn,"=");
    	button_map.put(R.id.SignChangeBtn,"+/-");
    	button_map.put(R.id.ReciprocalBtn,"1/x");
    	button_map.put(R.id.BackSpcBtn,"BACK");
    	button_map.put(R.id.DecimalBtn,".");
    	button_map.put(R.id.ClearAllBtn,"Clear");
    	
    	String button_input = button_map.get(view.getId());
    	
    	try
    	{
	    	if (isOperator(button_input))
	    	{
	    		if (button_input == "=")
	    		{
	    			 //operatorString = "=";
	    	   		 
	    		}
	    		else
	    		{
	    			 operatorString = button_input;
		    		 
		    		 inputString += button_input;
		    		 
		    		 inputView.setText(inputString);
	    		}
	    		 
	    		 operatorClicked = true;
	    		 
	    		 inputView.setText(inputString);
	    		 
	    		// outputView.setText(operandString);
	    	}
	    	else if (validNumber(button_input))
	    	{
	    		 if(operatorClicked)
	    			 operandString = button_input;
	    		 else
	    			 operandString += button_input;
	    		 
	    		 inputString += button_input;
	    		 
	    		 inputView.setText(inputString);
	    		 
	    		 outputView.setText(operandString);
	    		 
	    		 operatorClicked = false;
	    		 
	    		 inputView.setText(inputString);
	    		 
	    		 outputView.setText(operandString);
	    		
	    	}
	    	else if (view.getId() == R.id.EqualBtn)
	    	{
	    		operatorString = "=";
	    		
	    		operatorClicked = true;
	    		
	    	}
	    	else if (view.getId() == R.id.ClearAllBtn)
	    	{
	    		operatorString = "";
	    		operandString = "";
	    		inputString = "";
	    		
	    		outputString = "";
	    		inputView.setText("");
	    		outputView.setText("0");
	    		
	    		calcStack.clear();
	    		 
	    		calcStack.push("0");
	    	}
	    	else if (view.getId() == R.id.BackSpcBtn)
	    	{
	    		if (inputString.length() == 1)
	    		{
	    			inputString = "";
	    			outputString = "0";
	    			
	    			operandString = "";
	    			
	    			outputView.setText(outputString);
	    			inputView.setText(inputString);
	    		}
	    		else if (inputString.length() > 0 )
	    		{
	    			String lastChar = String.valueOf((inputString.charAt(inputString.length()-1)));
	    			
	    			if (isOperator(lastChar))
	    			{
	    				calcStack.pop();
	    				operatorClicked =false;
	    			}
	    			else
	    			{
	    				operandString = operandString.substring(0, operandString.length()-1);
		    		}
	    			inputString = inputString.substring(0, inputString.length()-1);
	    			
	    			inputView.setText(inputString);
	    		}
	    	
	    		
	    		 
	    	}
	    	else if (view.getId() == R.id.DecimalBtn)
	    	{
	    		 if(operatorClicked)
	    			 operandString = "0.";
	    		 else if (!operandString.contains("."))
	    		 {
	    			 if(operandString == "")
	    			 {
	    				 operandString = "0.";
	    				 inputString +="0.";
	    			 }
	    			 	
	    			 else
	    			 {
	    				 operandString += ".";
	    				 inputString +=".";
	    			 }
	    		 }
	    		 
	    		 
	    		 inputView.setText(inputString);
	    		 
	    		 outputView.setText(operandString);
	    	}
	    	
	   
	    	 if(operatorClicked)
	    	 {
	    		 if (operandString != "")
	    			 operandCall(operandString);
	    		 
	    		 if(operatorString != "")
	    			 operatorCall(operatorString);
	    		 
	    		 operandString = "";
	    		 operatorString = "";
	    		 
	    		 
	    	 }
    	 
    	}
    	catch(Exception ex)
    	{
    		
    	}
  
    	
    }
    

    /***************************/
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_calculator, container, false);
            return rootView;
        }
    }

}
