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
			while(sc1.hasNextLine())
			{
				if (sc1.nextLine().equals(lst.get(pos))){
					count++;
				}
				pos++;
			}
			System.out.println(count);
			System.out.println(pos);
			System.out.println("Accuracy is: " + (double)count/pos);
			List<String> al = new ArrayList<>();
			al.addAll(lst);
			Set<String> hs = new HashSet<>();
			hs.addAll(al);
			al.clear();
			al.addAll(hs);
			System.out.println("Class Correct Incorrect");
			for(String s : al){
				count = 0;
				pos = 0;
				int all = 0;
				Scanner sc2 = new Scanner(new File("out.txt"));
				while(sc2.hasNextLine())
				{
					String temp = sc2.nextLine();
					if (temp.equals(s) && s.equals(lst.get(pos))){
						count++;
					}
					if(s.equals(lst.get(pos)))
						all++;
					pos++;
				}
				System.out.println(s + " " + count + " " + (all-count));
			}
		}
		catch(FileNotFoundException e){
			System.out.println("FNF");
		}


	}
}