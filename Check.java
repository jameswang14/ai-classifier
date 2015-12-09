import java.util.*;
import java.io.*;
public class Check
{
	public static void main(String[] args)
	{
		ArrayList<String> lst = new ArrayList<String>();
		try{
			Scanner sc = new Scanner(new File("ans.txt"));
			while(sc.hasNextLine())
			{
				String[] split = sc.nextLine().split(" +");
				String key = split[split.length-1];
				lst.add(key);
			}
			Scanner sc1 = new Scanner(new File("out.txt"));
			int pos = 0;
			int count = 0;
			while(sc.hasNextLine())
			{
				if (sc.nextLine().equals(lst.get(pos))){
					count++;
				}
				pos++;
			}
			System.out.println("Accuracy is: " + (double)count/pos);
		}
		catch(FileNotFoundException e){
			System.out.println("FNF");
		}


	}
}