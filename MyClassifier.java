import java.util.*;
import java.io.*;
public class MyClassifier extends Classifier{
	ArrayList<Field> fields;
	class Field{
		public String name;
		public boolean numeric;
		public HashMap<String, Integer> typeCounts; 
		public int num;
		public ArrayList<Integer> nums;
		public double theta; 
		public Field(String name, boolean numeric, HashMap<String, Integer> typeCounts, int num)
		{
			this.name = name;
			this.numeric = numeric;
			this.typeCounts = typeCounts;
			this.num = num;
			nums = new ArrayList<Integer>();
			theta = 0.0;

		}

		public void train(Field outputs)
		{
			double rate = 1;

			for(int i = 0; i < nums.size(); i++)
			{
				theta = theta + rate;


			}

		}

		public double sigmoid(double x)
		{
			double d = 1.0 + Math.pow(Math.E,(-1.0 * x));
			d = 1.0/d;
			return d;

		}
		public String toString()
		{
			String s = "Name: " + this.name;

			if(this.numeric || name.equals("output"))
				s+= " numeric" + this.nums;
			else
			{
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
			ArrayList<Integer> outputNums = new ArrayList<Integer>();
			while(sc.hasNextLine())
			{
				String[] split = sc.nextLine().split(" +");
				for(int i = 0 ; i < split.length; i++)
				{
					Field toUse = fields.get(i);
					if(i == split.length-1)
					{
						String key = split[i];
						if(key.equals(">50K"))
							outputNums.add(1);
						else
							outputNums.add(0);
						toUse.typeCounts.put(key, toUse.typeCounts.get(key)+1);

					}
					else if(!toUse.numeric)
					{
						String key = split[i];
						toUse.typeCounts.put(key, toUse.typeCounts.get(key)+1);
					}
					else
					{
						int val = Integer.parseInt(split[i]);
						toUse.nums.add(val);
					}

				}
			}
			fields.get(fields.size()-1).nums = outputNums;
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