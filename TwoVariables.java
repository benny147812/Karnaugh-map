import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TwoVariables{
	private String[][] kmap = {{"0","0"},{"0","0"}};
	//for two variables,prime implicants are all essential
	private ArrayList<String> prime = new ArrayList<String>();
	private ArrayList<String> SOP = new ArrayList<String>();
	
	public void DoKmap(ArrayList<String> minterm) {
		FillIn(minterm);
		FindPrime(minterm);
		removeDuplicate(prime);
		if(prime.size() == 4) {
			prime.clear();
			prime.add("1");
		}
		FindSOP(prime);
		output();
	}
	
	//Initialize the terms in the K-map
	private void FillIn(ArrayList<String> min) {
		for(int i = 0; i < min.size(); i++) {
			switch(min.get(i)) {
			case "0":
				kmap[0][0] = "1";
			break;
			case "1":
				kmap[1][0] = "1";
			break;
			case "2":
				kmap[0][1] = "1";
			break;
			case "3":
				kmap[1][1] = "1";
			break;
			}
		}
	}
	
	private void FindPrime(ArrayList<String> minterm) {
		for(int i = 0; i < minterm.size(); i++) {
			//all implicants are 1
			if(minterm.size() == 4) {
				prime.add("1");
				SOP.add("1");
				break;
			}
			//all implicants are 0
			if(minterm.get(0) == "null") {
				prime.add("0");
				SOP.add("0");
				break;
			}
			boolean covered = false;//代表這一minterm是否有被cover到
			//檢查各項有無相鄰，並將其boolean algebra加入prime implicant
			switch(minterm.get(i)) {
			case "0":
				if(minterm.contains("1")) {
					prime.add("A'");
					covered = true;
				}
				if(minterm.contains("2")) {
					prime.add("B'");
					covered = true;
				}
			break;
			case "1":
				if(minterm.contains("0")) {
					prime.add("A'");
				}
				if(minterm.contains("3")) {
					prime.add("B");
				}
				if(!minterm.contains("0") && !minterm.contains("3")) {
					prime.add("A'B");
				}
			break;
			case "2":
				if(minterm.contains("0")) {
					prime.add("B'");
				}
				if(minterm.contains("3")) {
					prime.add("A");
				}
				if(!minterm.contains("0") && !minterm.contains("3")) {
					prime.add("AB'");
				}
			break;
			case "3":
				if(minterm.contains("1")) {
					prime.add("B");
				}
				if(minterm.contains("2")) {
					prime.add("A");
				}
				if(!minterm.contains("1") && !minterm.contains("2")) {
					prime.add("AB");
				}
			break;
			}
		}
	}
	
	//移除ArrayList中的重複項
	private void removeDuplicate(ArrayList<String> list) {
		ArrayList<String> result = new ArrayList<String>(list.size());
		for (String str : list) {
			if (!result.contains(str)) {
				result.add(str);
		    }
		}
		list.clear();
		list.addAll(result);
	}
	
	private void FindSOP(ArrayList<String> P) {
		if(!P.get(0).equals("None")) {
			for(int i = 0; i < P.size(); i++) {
				SOP.add(P.get(i));
			}
		}
	}
	
	//輸出資料至"output.txt"
	private void output() {
		try{
			PrintWriter writer = new PrintWriter(new FileOutputStream("output.txt"));
			writer.println("  \\ A|       |");
			writer.println(" B \\ | 0   1 |");
			writer.println("-----+---+---+");
			writer.println("   0 | " + kmap[0][0] + " | " + kmap[0][1] + " |");
			writer.println("-----+---+---+");
			writer.println("   1 | " + kmap[1][0] + " | " + kmap[1][1] + " |");
			writer.println("-----+---+---+");
			writer.println(" ");
			writer.print("F(A,B) = ");
			for(int i = 0; i < SOP.size(); i++) {
				if(i == 0) {
					writer.print(" " + SOP.get(i));
				}
				else {
					writer.print(" + " + SOP.get(i));
				}
			}
			writer.println(" ");
			writer.flush(); 
			writer.close();
		} 
		catch(FileNotFoundException e){ 
			System.out.println("File Not Found/Can not be Created"); 
		}

	}
}