import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileIO{
	public static ArrayList<String> readfile(String filename){
		ArrayList<String> fileContent = new ArrayList<String>();  
		FileReader fr = null;
		BufferedReader br = null;
		try{
			String currentLine = null;
			br = new BufferedReader(new FileReader(filename));
			while ((currentLine = br.readLine()) != null){
				fileContent.add(currentLine);
			}
			 
		}catch(IOException e){
			System.out.println("File Not Found/Can not be Created");
			System.out.println("Please provide the file input.txt and try again");
			System.exit(0);
		}
		
		return fileContent;
	}
}