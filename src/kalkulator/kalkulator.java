package kalkulator;

import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class kalkulator {

	public double kalkulatorek(String expression) {
		// OPERATORY
		expression = expression.replaceAll("[\t\n ]", "") + "=";
		String operator = "*/+-=";
		// ODDZIELANIE OPERATOROW
		StringTokenizer tokenizer = new StringTokenizer(expression, operator, true);
		Stack<String> operatorStack = new Stack<String>();
		Stack<String> valueStack = new Stack<String>();
		while (tokenizer.hasMoreTokens()) {
			// DODAWANIE KOLEJNEGO TOKENA
			String token = tokenizer.nextToken();
			if (operator.indexOf(token) < 0)
				valueStack.push(token);
			else
				operatorStack.push(token);
			resolve(valueStack, operatorStack);
		}
		// ZWRACANIE
		String lastOne = (String) valueStack.pop();
		return Double.parseDouble(lastOne);
	}

	// KOLEJNOSC DZIALAN
	public int getPriority(String op) {
		if (op.equals("*") || op.equals("/"))
			return 1;
		else if (op.equals("+") || op.equals("-"))
			return 2;
		else if (op.equals("="))
			return 3;
		else
			return Integer.MIN_VALUE;
	}
	public void resolve(Stack<String> values, Stack<String> operators) {
		while (operators.size() >= 2) {
			String first = (String) operators.pop();
			String second = (String) operators.pop();
			if (getPriority(first) < getPriority(second)) {
				operators.push(second);
				operators.push(first);
				return;
			} else {
				String firstValue = (String) values.pop();
				String secondValue = (String) values.pop();
				values.push(getResults(secondValue, second, firstValue));
				operators.push(first);
			}
		}
	}

	// MENU
	public static int menu() {
		System.out.println();
		System.out.println("     *              KALKULATOR                 *");
		System.out.println("     ****************************************");
		System.out.println("     *                 MENU                 *");
		System.out.println("     ****************************************");
		System.out.println("     1. Wpisywanie z klawiatury");
		System.out.println("     2. Czytanie z pliku");
		System.out.println("     0. Koniec");
		System.out.println("     ****************************************");
		System.out.println();
		System.out.println("Wybierz: 1,2 lub 3");

		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		int w = in.nextInt();
		return w;
	}

	// WYNIK
	public String getResults(String operand1, String operator, String operand2) {
		double op1 = Double.parseDouble(operand1);
		double op2 = Double.parseDouble(operand2);
		if (operator.equals("*"))
			return "" + (op1 * op2);
		else if (operator.equals("/"))
			return "" + (op1 / op2);
		else if (operator.equals("+"))
			return "" + (op1 + op2);
		else if (operator.equals("-"))
			return "" + (op1 - op2);
		else
			return null;
	}

	// MAIN
	public static void main(String[] args) throws IOException {
		Scanner keyboard = new Scanner(System.in);
		String expression = new String();
		String file = new String();

		int wybor = menu();
		kalkulator fix = new kalkulator();
		while (wybor != 0) {
			switch (wybor) {
			case 1:
				System.out.println("Wprowadz dzialanie (np. 2+2*2): ");
				expression = keyboard.nextLine();
				System.out.println(expression + "=" + fix.kalkulatorek(expression));
				break;

			case 2:
				System.out.println("Podaj nazwe pliku: ");
				FileReader fileReader = null;
				file = keyboard.nextLine();
				// OTWIERANIE PLIKU
				try {
					fileReader = new FileReader(file);
				} catch (FileNotFoundException e) {
					System.out.println("Blad");
					System.exit(1);
				}

				BufferedReader bufferedReader = new BufferedReader(fileReader);
				// ODCZYT KOLEJNYCH LINII Z PLIKU
				try {
					while ((expression = bufferedReader.readLine()) != null) {
						System.out.println(expression + "=" + fix.kalkulatorek(expression));
					}
				} catch (IOException e) {
					System.out.println("Blad");
					System.exit(2);
				}

				// ZAMYKANIE PLIKU
				try {
					fileReader.close();
				} catch (IOException e) {
					System.out.println("Blad");
					System.exit(3);
				}
			}

			break;

		}
		keyboard.close();

	}

}
