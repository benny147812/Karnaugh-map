import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class kmap{
	public static void main(String[] args) {
		ArrayList<String> FileContent = FileIO.readfile("input.txt");
		
		int VarNum = Integer.parseInt(FileContent.get(0).split(" ")[1]);//���o�ܼƭӼ�
		ArrayList<String> minterm = new ArrayList<String>();
		ArrayList<String> DontCare = new ArrayList<String>();
		//���ominterm
		try {
			for(int i = 0; i < FileContent.get(1).split(" ")[1].split(",").length; i++) {
				minterm.add(FileContent.get(1).split(" ")[1].split(",")[i]);
			}
		}
		catch(java.lang.ArrayIndexOutOfBoundsException e) {
			minterm.add("null");
		}
		//�ھ��ܼƭӼƨϥΤ��P���B�z�覡
		switch(VarNum) {
		case 2:
			TwoVariables two = new TwoVariables();
			two.DoKmap(minterm);
		break;
		case 3:
			ThreeVariables three = new ThreeVariables();
			three.DoKmap(minterm);
		break;
		case 4:
			FourVariables four = new FourVariables();
			four.DoKmap(minterm);
		break;
		}
		System.out.println("Program ended");	
		
	}
}