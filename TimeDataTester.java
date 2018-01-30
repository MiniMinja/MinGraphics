import java.util.*;
import java.io.*;

public class TimeDataTester{
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("Spring2018.txt"));
		TimeData td = new TimeData();
		String input = br.readLine();
		while(input != null){
			td.addToDays(input,br.readLine());
			input = br.readLine();
		}
		System.out.println(td);
	}
}

