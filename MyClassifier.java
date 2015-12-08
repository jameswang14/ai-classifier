import java.util.*;
import java.io.*;
public class MyClassifier extends Classifier{
	ArrayList<Field> fields;
	class Field{
		public String name;
		public boolean numeric;
		public HashMap<String, Integer> typeCounts1; // <=50k 
		public HashMap<String, Integer> typeCounts2; // > 50k
		public int num;
		public Field(String name, boolean numeric, HashMap<String, Integer> typeCounts1, HashMap<String, Integer> typeCounts2, int num)
		{
			this.name = name;
			this.numeric = numeric;
			this.typeCounts1 = typeCounts1;
			this.typeCounts2 = typeCounts2;
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
				for(Map.Entry<String, Integer> entry: this.typeCounts1.entrySet())
					s+=entry.getKey() + ": " + entry.getValue().toString() + ", ";
				for(Map.Entry<String, Integer> entry: this.typeCounts2.entrySet())
					s+=entry.getKey() + " 2: " + entry.getValue().toString() + ", ";
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
			HashMap<String, Integer> copy2 = new HashMap<String, Integer>();			
			for(String s: copiedStrings){
				copy.put(new String(s), 0);
				copy2.put(new String(s), 0);
			}
			sc.nextLine();
			sc.nextLine();
			Field output = new Field("output", false, copy, copy2, -1);
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
					fields.add(new Field(split.get(0), true, null, null, 0));
				else
				{
					for(int i = 1; i < split.size(); i++)
						copiedStrings.add(split.get(i));
					copy = new HashMap<String, Integer>();
					copy2 = new HashMap<String, Integer>();			
					for(String s: copiedStrings){
						copy.put(new String(s), 0);
						copy2.put(new String(s), 0);
					}
					fields.add(new Field(split.get(0), false, copy, copy2, -1));

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
				Field toUse = fields.get(split.length-1);	
				String key = split[split.length-1];
				boolean gt = false;
				if(key.equals(">50K"))
					gt = true;
				System.out.println(gt + " " + key);
				if(gt){
					System.out.println("Added to 2");
					toUse.typeCounts2.put(key, toUse.typeCounts2.get(key)+1);
				}
				else{
					System.out.println("Added to 1");
					toUse.typeCounts1.put(key, toUse.typeCounts1.get(key)+1);
				}
				for(int i = 0 ; i < split.length-1; i++)
				{
					toUse = fields.get(i);
					if(!toUse.numeric)
					{
						key = split[i];
						if(gt)
							toUse.typeCounts2.put(key, toUse.typeCounts2.get(key)+1);
						else
							toUse.typeCounts1.put(key, toUse.typeCounts1.get(key)+1);
				
					}

				}
			}
		}
		catch(FileNotFoundException e){
			System.out.println("FNF");
		}
		

	}

	public void makePredictions(String testDataFilepath){
		try{
			Scanner sc = new Scanner(new File(testDataFilepath));
			while(sc.hasNextLine())
			{
				String[] split = sc.nextLine().split(" +");
				for(int i = 0 ; i < split.length; i++)
				{
					Field toUse = fields.get(i);
					if(!toUse.numeric)
					{
						String key = split[i];
						//int occurences = toUse.typeCounts.get(key)+1;

					}

				}
			}
		}
		catch(FileNotFoundException e){
			System.out.println("FNF");
		}
	}

	public String toString()
	{
		String s1 = "";
		for(Field f: fields)
			s1+= f.toString() + "\n";
		return s1;
	}




}