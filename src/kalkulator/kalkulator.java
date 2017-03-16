package kalkulator;


import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class kalkulator
{

    public double kalkulator(String expression)
    {
        //remove white space and add evaluation operator
        expression=expression.replaceAll("[\t\n ]", "")+"=";
        String operator="*/+-=";
        //split up the operators from the values
        StringTokenizer tokenizer=new StringTokenizer(expression, operator, true);
        Stack operatorStack=new Stack();
        Stack valueStack=new Stack();
        while(tokenizer.hasMoreTokens())
        {
            //add the next token to the proper stack
            String token=tokenizer.nextToken();
            if(operator.indexOf(token)<0)
                valueStack.push(token);
            else
                operatorStack.push(token);
            //perform any pending operations
            resolve(valueStack, operatorStack);
        }
        //return the top of the value stack
        String lastOne=(String)valueStack.pop();
        return Double.parseDouble(lastOne);   
    }
        
    public int getPriority(String op)
    {
        if(op.equals("*") || op.equals("/"))
            return 1;
        else if(op.equals("+") || op.equals("-"))
            return 2;
        else if(op.equals("="))
            return 3;
        else
            return Integer.MIN_VALUE;
    }
    
    public void resolve(Stack values, Stack operators)
    {
        while(operators.size()>=2)
        {
            String first=(String)operators.pop();
            String second=(String)operators.pop();
            if(getPriority(first)<getPriority(second))
            {
                operators.push(second);
                operators.push(first);
                return;
            }
            else
            {
                String firstValue=(String)values.pop();
                String secondValue=(String)values.pop();
                values.push(getResults(secondValue, second, firstValue));
                operators.push(first);
            }
        }
    }
    
    public static int menu(){
        System.out.println();                 
        System.out.println("     *              KALKULATOR                 *");
        System.out.println("     ****************************************");
        System.out.println("     *                 MENU                 *");
        System.out.println("     ****************************************");
        System.out.println("     1. Wpisywanie z klawiatury");
        System.out.println("     2. Czytanie z pliku");
        System.out.println("     0. Koniec");
        System.out.println();
        System.out.println("Wybierz: 1,2 lub 3");
 
        Scanner in = new Scanner(System.in);
        int w = in.nextInt();
 
        return w;
    }
    
    public String getResults(String operand1, String operator, String operand2)
    {
        double op1=Double.parseDouble(operand1);
        double op2=Double.parseDouble(operand2);
        if(operator.equals("*"))
            return ""+(op1*op2);
        else if(operator.equals("/"))
            return ""+(op1/op2);
        else if(operator.equals("+"))
            return ""+(op1+op2);
        else if(operator.equals("-"))
            return ""+(op1-op2);
        else
            return null;
    }
    
    public static void main(String[] args) throws IOException
    {
    	Scanner in = new Scanner(System.in);
        Scanner keyboard = new Scanner(System.in);
        String expression =new String();
        String file =new String();
        
        int wybor = menu();
    	kalkulator fix=new kalkulator();
    	while(wybor!=0){
            switch(wybor){
                case 1:
             
                	System.out.println("Wprowadz dzialanie: ");
                	expression = keyboard.nextLine();
                	System.out.println(expression+"="+fix.kalkulator(expression));
                	break;
        
                case 2:
                	System.out.println("Podaj nazwe pliku: ");
                	FileReader fileReader = null;
                	file = keyboard.nextLine();
                	// OTWIERANIE PLIKU:
                	   try {
                		   fileReader = new FileReader(file);
                	   } catch (FileNotFoundException e) {
                	       System.out.println("Error");
                	       System.exit(1);
                	   }

                	   BufferedReader bufferedReader = new BufferedReader(fileReader);
                	   // ODCZYT KOLEJNYCH LINII Z PLIKU:
                	   try {
                	     while((expression = bufferedReader.readLine()) != null){
                	    	 System.out.println(expression+"="+fix.kalkulator(expression));
                	     }
                	    } catch (IOException e) {
                	        System.out.println("Error");
                	        System.exit(2);
                	   }

                	   // ZAMYKANIE PLIKU
                	   try {
                		   fileReader.close();
                	    } catch (IOException e) {
                	         System.out.println("Error");
                	         System.exit(3);
                	        }
                	    }
    	
                		
                    break;
    	}
           
 
        }
    }
