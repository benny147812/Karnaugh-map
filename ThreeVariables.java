import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class ThreeVariables{
	private String[][] kmap = {{"0","0","0","0"},{"0","0","0","0"}};
	private ArrayList<String> prime = new ArrayList<String>();
	private ArrayList<String> essential = new ArrayList<>();
	private ArrayList<String> SOP = new ArrayList<String>();
	
	private ArrayList<String[]> First0 = new ArrayList<>();//�Ĥ@�����ɡA��0��1����
	private ArrayList<String[]> First1 = new ArrayList<>();//�Ĥ@�����ɡA��1��1����
	private ArrayList<String[]> First2 = new ArrayList<>();//�Ĥ@�����ɡA��2��1����
	private ArrayList<String[]> First3 = new ArrayList<>();//�Ĥ@�����ɡA��3��1����
	private ArrayList<String[]> Second0 = new ArrayList<>();//�ĤG�����ɡA��0��1����
	private ArrayList<String[]> Second1 = new ArrayList<>();//�ĤG�����ɡA��1��1����
	private ArrayList<String[]> Second2 = new ArrayList<>();//�ĤG�����ɡA��2��1����
	private ArrayList<String[]> Third0 = new ArrayList<>();//�ĤT�����ɡA��0��1����
	private ArrayList<String[]> Third1 = new ArrayList<>();//�ĤT�����ɡA��1��1����
	
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
			case "4":
				kmap[0][3] = "1";
			break;
			case "5":
				kmap[1][3] = "1";
			break;
			case "6":
				kmap[0][2] = "1";
			break;
			case "7":
				kmap[1][2] = "1";
			break;
			}
		}
	}
	
	public int DoKmap(ArrayList<String> minterm) {
		FillIn(minterm);	
		//all implicants are 1
		if(minterm.size() == 8) {
			prime.add("1");
			essential.add("1");
			SOP.add("1");
			output();
			return 0;
		}
		//all implicants are 0
		if(minterm.get(0) == "null") {
			prime.add("0");
			essential.add("0");
			SOP.add("0");
			output();
			return 0;
		}
		toBinary(minterm);
		Compare(First0, First1);
		Compare(First1, First2);
		Compare(First2, First3);
		Compare(Second0, Second1);
		Compare(Second1, Second2);
		FindPrime();
		FindEssential(minterm);
		FindSOP(minterm);
		output();
		return 0;
	}
	
	/*
	 * �Nminterm�ন�G�i��A�å[�W��L��T�Φ��@��array
	 * index 0~2:�G�i�쪺�T��bit
	 * index 3:��Ӫ��Q�i��
	 * index 4:���Ƶ���1��boolean algebra
	 * index 5:���ƬO�_�Qcover��
	 */
	private void toBinary(ArrayList<String> decimal) {
		for(int i = 0; i < decimal.size(); i++) {
			switch(decimal.get(i)) {
			case"0":
				String[] change0 = {"0", "0", "0", "0", "A'B'C'", "no"};
				First0.add(change0);
			break;
			case"1":
				String[] change1 = {"0", "0", "1", "1", "A'B'C", "no"};
				First1.add(change1);
			break;
			case"2":
				String[] change2 = {"0", "1", "0", "2", "A'BC'", "no"};
				First1.add(change2);
			break;
			case"3":
				String[] change3 = {"0", "1", "1", "3", "A'BC", "no"};
				First2.add(change3);
			break;
			case"4":
				String[] change4 = {"1", "0", "0", "4", "AB'C'", "no"};
				First1.add(change4);
			break;
			case"5":
				String[] change5 = {"1", "0", "1", "5", "AB'C", "no"};
				First2.add(change5);
			break;
			case"6":
				String[] change6 = {"1", "1", "0", "6", "ABC'", "no"};
				First2.add(change6);
			break;
			case"7":
				String[] change7 = {"1", "1", "1", "7", "ABC", "no"};
				First3.add(change7);
			break;
			}
		}
	}
	
	private void Compare(ArrayList<String[]> group1, ArrayList<String[]> group2) {
		//�ˬd�Y��Ӽƪ��G�i�즳�X��bit���P
		for(int i = 0; i < group1.size(); i++) {
			for(int j = 0; j < group2.size(); j++) {
				int DifferentBits = 0;
				int DifferentIndex = 0;
				for(int k = 0; k < 3; k++) {
					if(!group1.get(i)[k].equals(group2.get(j)[k])) {
						DifferentBits++;
						DifferentIndex = k;
					}
				}
				//�Y�u�ۮt�@��bit�A�N���۾F�i��²
				if(DifferentBits == 1) {
					//�ΨӤ�²���ⶵ���Qcover�A�Nno�令yes
					group1.get(i)[5] = "yes";
					group2.get(j)[5] = "yes";
					
					String[] compared = new String[6];//�x�s��²�����G
					int ones = 0;//�p���²���ѴX��1�A�H�P�_�n������ArrayList��
					int round = 0;//�p���²�����X��-1(�w���Q��²�X��)�A�H�P�_�n���ĴX����²��ArrayList
					compared[5] = "no";//��²�������٨S�Qcover

					for(int index = 0; index < 3; index++) {
						//�N���e�ⶵ���P��bit�B�s-1�N�����w�Q��²
						if(index == DifferentIndex) {
							compared[index] = "-1";
						}
						//�]���u�t�@��bit�A�ҥH��Lbit�N����e�ⶵ�@��
						else {
							compared[index] = group1.get(i)[index];
						}
					}
					for(int index = 0; index < 3; index++) {
						if(compared[index].equals("1")) {
							ones++;
						}
						if(compared[index].equals("-1")) {
							round++;
						}
						//�̧Ǳ��L�T��bit�A�P�_�O0�٬O1�w�M�w��²�����o����boolean algebra
						switch(index) {
						case 0:
							if(compared[index].equals("1")) {
								if(compared[4] != null) {
									compared[4] += "A";
								}
								else {
									compared[4] = "A";
								}
							}
							if(compared[index].equals("0")) {
								if(compared[4] != null) {
									compared[4] += "A'";
								}
								else {
									compared[4] = "A'";
								}
							}
						break;
						case 1:
							if(compared[index].equals("1")) {
								if(compared[4] != null) {
									compared[4] += "B";
								}
								else {
									compared[4] = "B";
								}
							}
							if(compared[index].equals("0")) {
								if(compared[4] != null) {
									compared[4] += "B'";
								}
								else {
									compared[4] = "B'";
								}
							}
						break;
						case 2:
							if(compared[index].equals("1")) {
								if(compared[4] != null) {
									compared[4] += "C";
								}
								else {
									compared[4] = "C";
								}
							}
							if(compared[index].equals("0")) {
								if(compared[4] != null) {
									compared[4] += "C'";
								}
								else {
									compared[4] = "C'";
								}
							}
						break;
						}
					}
					//�N�ΨӤ�²���ⶵ�Q�i��s�i�ӡA�H�O���s�����O�ѭ��ⶵ��²��
					compared[3] = group1.get(i)[3] + "," + group2.get(j)[3];
					
					//�ھ�round��ones�K�[�������ArrayList
					switch(round) {
					case 1:
						switch(ones) {
						case 0:
							Second0.add(compared);
						break;
						case 1:
							Second1.add(compared);
						break;
						case 2:
							Second2.add(compared);
						break;
						}
					break;
					case 2:
						switch(ones) {
						case 0:
							Third0.add(compared);
						break;
						case 1:
							Third1.add(compared);
						break;
						}
					break;
					}
				}
			}
		}
	}
	
	//�N�Ҧ��S�Qcover�쪺�[�iprime implicant�A�Ĥ@�Ӧs�Ӷ��ѽ֤�²�A�ĤG�Ӧs�Ӷ���boolean algebra
	private void FindPrime() {
		for(int i =0; i < First0.size(); i++) {
			if(First0.get(i)[5].equals("no")){
				prime.add(First0.get(i)[3]);
				prime.add(First0.get(i)[4]);
			}
		}
		for(int i =0; i < First1.size(); i++) {
			if(First1.get(i)[5].equals("no")){
				prime.add(First1.get(i)[3]);
				prime.add(First1.get(i)[4]);
			}
		}
		for(int i =0; i < First2.size(); i++) {
			if(First2.get(i)[5].equals("no")){
				prime.add(First2.get(i)[3]);
				prime.add(First2.get(i)[4]);
			}
		}
		for(int i =0; i < First3.size(); i++) {
			if(First3.get(i)[5].equals("no")){
				prime.add(First3.get(i)[3]);
				prime.add(First3.get(i)[4]);
			}
		}
		for(int i =0; i < Second0.size(); i++) {
			if(Second0.get(i)[5].equals("no")){
				prime.add(Second0.get(i)[3]);
				prime.add(Second0.get(i)[4]);
			}
		}
		for(int i =0; i < Second1.size(); i++) {
			if(Second1.get(i)[5].equals("no")){
				prime.add(Second1.get(i)[3]);
				prime.add(Second1.get(i)[4]);
			}
		}
		for(int i =0; i < Second2.size(); i++) {
			if(Second2.get(i)[5].equals("no")){
				prime.add(Second2.get(i)[3]);
				prime.add(Second2.get(i)[4]);
			}
		}
		for(int i =0; i < Third0.size(); i++) {
			if(Third0.get(i)[5].equals("no")){
				prime.add(Third0.get(i)[3]);
				prime.add(Third0.get(i)[4]);
			}
		}
		for(int i =0; i < Third1.size(); i++) {
			if(Third1.get(i)[5].equals("no")){
				prime.add(Third1.get(i)[3]);
				prime.add(Third1.get(i)[4]);
			}
		}
		//�������ƶ�
		ArrayList<String> removed = new ArrayList<>();
		for(int i = 1; i < prime.size(); i += 2) {
			if(!removed.contains(prime.get(i))) {
				removed.add(prime.get(i-1));
				removed.add(prime.get(i));
			}
		}
		prime.clear();
		prime.addAll(removed);
	}
	
	private void FindEssential(ArrayList<String> minterm) {
		//���L�C��minterm�A�p�⦹minterm�Q�X��prime implicant cover��
		for(int i = 0; i < minterm.size(); i++) {
			int IncludeTime = 0;
			int index = -1;
			for(int j = 0; j < prime.size(); j += 2) {
				ArrayList<String> implicant = new ArrayList<>();
				for(int k = 0; k < prime.get(j).split(",").length; k++) {
					implicant.add(prime.get(j).split(",")[k]);
				}
				if(implicant.contains(minterm.get(i))) {
					IncludeTime++;
					index = j + 1;
				}
			}
			//�Y�u�Qcover��@���A�hcover��������prime implicant�Oessential
			if(IncludeTime == 1) {
				essential.add(prime.get(index));
			}
		}
		//�������ƶ�
		ArrayList<String> removed = new ArrayList<>();
		for(int i = 0; i < essential.size(); i++) {
			if(!removed.contains(essential.get(i))) {
				removed.add(essential.get(i));
			}
		}
		essential.clear();
		essential.addAll(removed);
	}
	
	private void FindSOP(ArrayList<String> minterm) {
		//�Nessential�[�JSOP
		if(essential.size() > 0) {
			for(int i = 0; i < essential.size(); i++) {
				SOP.add(essential.get(i));
			}
		}
		//�Y�S��essential�A�ϥ�Petrick's method�M�wSOP
		else {
			Petrick(minterm, prime);
		}
	}
	
	private void Petrick(ArrayList<String> MinTermsLeft, ArrayList<String> PrimesLeft) {
		int CoverMinTerms[] = new int[PrimesLeft.size()];//�����C��prime implicant cover��X��minterm
		ArrayList<String> copy = (ArrayList<String>) PrimesLeft.clone();
		for(int i = 0; i < PrimesLeft.size(); i += 2) {
			for(int j = 0; j < PrimesLeft.get(i).split(",").length; j++) {
				for(int k = 0; k < MinTermsLeft.size(); k++) {
					if(PrimesLeft.get(i).split(",")[j].equals(MinTermsLeft.get(k))) {
						CoverMinTerms[i]++;
					}
				}
			}
		}
		SOP.add(PrimesLeft.get(getIndexOfLargest(CoverMinTerms) + 1));//�Ncover�̦hminterm��prime implicant�[�JSOP
		//�N���ǳQcover�쪺minterm�h��
		for(int i = 0; i < PrimesLeft.get(getIndexOfLargest(CoverMinTerms)).split(",").length; i++) {
			for(int j = 0; j < MinTermsLeft.size(); j++) {
				if(PrimesLeft.get(getIndexOfLargest(CoverMinTerms)).split(",")[i].equals(MinTermsLeft.get(j))) {
					MinTermsLeft.remove(j);
				}
			}
		}
		//�N�Q�[�JSOP��prime implicant����
		copy.remove(getIndexOfLargest(CoverMinTerms) + 1);
		copy.remove(getIndexOfLargest(CoverMinTerms));
		//�Y�٦�minterm�٨S�Qcover��A���Ƥ@��Petrick's method(recursion)
		if(MinTermsLeft.size() > 0) {
			Petrick(MinTermsLeft, copy);
		}
	}
	
	//��X�@��array���̤j�Ȫ�index
	public int getIndexOfLargest(int[] array)
	{
	  if ( array == null || array.length == 0 ) return -1;

	  int largest = 0;
	  for ( int i = 1; i < array.length; i++ )
	  {
	      if ( array[i] > array[largest] ) largest = i;
	  }
	  return largest;
	}
	
	//��X��Ʀ�"output.txt"
	private void output() {
		try{
			PrintWriter writer = new PrintWriter(new FileOutputStream("output.txt"));
			writer.println("  \\AB|               |");
			writer.println(" C \\ | 00  01  11  10|");
			writer.println("-----+---+---+---+---+");
			writer.println("   0 | "+ kmap[0][0] +" | "+ kmap[0][1] +" | "+ kmap[0][2] +" | "+ kmap[0][3] +" |");
			writer.println("-----+---+---+---+---+");
			writer.println("   1 | "+ kmap[1][0] +" | "+ kmap[1][1] +" | "+ kmap[1][2] +" | "+ kmap[1][3] +" |");
			writer.println("-----+---+---+---+---+");
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
			System.out.println("File Not Found/Can't be Created"); 
		}
	}
}