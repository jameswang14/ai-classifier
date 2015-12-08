import java.util.*;
import java.io.*;
public class MyClassifier extends Classifier{
	ArrayList<Field> fields;
	class Field{
		public String name;
		public boolean numeric;
		public HashMap<String, Integer> typeCounts; 
		public int num;
		public Field(String name, boolean numeric, HashMap<String, Integer> typeCounts, int num)
		{
			this.name = name;
			this.numeric = numeric;
			this.typeCounts = typeCounts;
			this.num = num;

		}

		public String toString()
		{
			String s = "Name: " + this.name;
			if(this.numeric)
				s+= " numeric";
			else{
				s+=" [";
				// to fix
				for(Map.Entry<String, Integer> entry: this.typeCounts.entrySet())
					s+=entry.getKey() + ": " + entry.getValue().toString() + ", ";
				s = s.substring(0, s.length()-2);
				s+= "]";
			}	
			return s;
		}

	}

	public MyClassifier(String namesFilepath) 
	{
		super(namesFilepath);
		fields = new ArrayList<Field>();
		try{
			Scanner sc = new Scanner(new File(namesFilepath));
			ArrayList<String> copiedStrings = new ArrayList<String>();
			ArrayList<String> split = new ArrayList<String>();
			copiedStrings.add(sc.next());
			copiedStrings.add(sc.next());
			HashMap<String, Integer> copy = new HashMap<String, Integer>();
			for(String s: copiedStrings)
				copy.put(new String(s), 0);
			sc.nextLine();
			sc.nextLine();
			Field output = new Field("output", false, copy, -1);
			while(sc.hasNextLine())
			{
				copiedStrings.clear();
				split.clear();
				String nextLine = sc.nextLine();
				Scanner line = new Scanner(nextLine);
				
				while(line.hasNext())
				{
					String next = line.next();
					split.add(next);
				}
				if(split.get(1).equals("numeric"))
					fields.add(new Field(split.get(0), true, null, 0));
				else
				{
					for(int i = 1; i < split.size(); i++)
						copiedStrings.add(split.get(i));
					copy = new HashMap<String, Integer>();
					for(String s: copiedStrings)
						copy.put(new String(s), 0);
					fields.add(new Field(split.get(0), false, copy, -1));

				}

			}
			fields.add(output);
		}	
		catch(FileNotFoundException e){
			System.out.println("FNF");
		}
		


	}

	public void train(String trainingDataFilpath){
		try{
			Scanner sc = new Scanner(new File(trainingDataFilpath));
			while(sc.hasNextLine())
			{
				String[] split = sc.nextLine().split(" +");
				for(int i = 0 ; i < split.length; i++)
				{
					Field toUse = fields.get(i);
					if(!toUse.numeric)
					{
						String key = split[i];
						toUse.typeCounts.put(key, toUse.typeCounts.get(key)+1);
					}

				}
			}
		}
		catch(FileNotFoundException e){
			System.out.println("FNF");
		}
		

	}

	public void makePredictions(String testDataFilepath){

	}

	public String toString()
	{
		String s1 = "";
		for(Field f: fields)
			s1+= f.toString() + "\n";
		return s1;
	}




}