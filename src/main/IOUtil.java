package main;

import java.util.Scanner;

import javax.swing.JOptionPane;


public class IOUtil {
	
	
	public static int getIntByScanner(String msg){ 		
		System.out.println(msg);
		Scanner sc = new Scanner(System.in);
		return sc.nextInt();
	} 
	 
	public static int getInt(String msg){ 
		String newIntStr = JOptionPane.showInputDialog(msg);
		int newInt = Integer.parseInt(newIntStr); 
		return newInt;
	}
	
	public static void display(String msg){
		JOptionPane.showMessageDialog(null, msg);
	}
	
	public static String getString(String msg){ 
		
		String newString = JOptionPane.showInputDialog(msg);
		return newString;
	}

	
}
