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
		public ArrayList<Integer> nums;
		public ArrayList<Double> thetas;
		public double theta;
		public Field(String name, boolean numeric, HashMap<String, Integer> typeCounts1, HashMap<String, Integer> typeCounts2, int num)
		{
			this.name = name;
			this.numeric = numeric;
			this.typeCounts1 = typeCounts1;
			this.typeCounts2 = typeCounts2;
			this.num = num;
			nums = new ArrayList<Integer>();
			thetas = new ArrayList<Double>();
			theta = 0.0;
		}

		public void train(Field outputs)
		{
			double rate = 1;
			for(int n = 0; n < nums.size(); n++)
				thetas.add(0.0);
			for(int i = 0; i < nums.size(); i++)
			{
				double z = theta * nums.get(i);
				double h = sigmoid(z);

				double gradient = (1.0/nums.size());
				double gsum = 0.0;
				for(int n = 0; n < nums.size(); n++)
				{
					gsum += (h-(double)(outputs.nums.get(n))) * (double)nums.get(n);
				}
				gradient *= gsum;
				// double hess  = (1.0/nums.size);
				// double hsum = 0.0;
				// for(int n = 0; n < nums.size(); n++)
				// {

				// }
				theta = theta - rate*(gsum);
				

			}

		}

		public double classify(double data)
		{
			return sigmoid(theta * data);
		}

		private double sigmoid(double x)
		{
			double d = 1.0 + Math.pow(Math.E,(-1.0 * x));
			d = 1.0/d;
			return d;

		}
		public String toString()
		{
			String s = "Name: " + this.name;
			if(this.name.equals("output"))
			{
				s+=" [";
				// to fix
				for(Map.Entry<String, Integer> entry: this.typeCounts1.entrySet())
					s+=entry.getKey() + ": " + entry.getValue().toString() + ", ";
				for(Map.Entry<String, Integer> entry: this.typeCounts2.entrySet())
					s+=entry.getKey() + " 2: " + entry.getValue().toString() + ", ";
				s = s.substring(0, s.length()-2);
				s+="] " + this.nums;
			}
			else if(this.numeric)
				s+= " numeric" + this.nums;
			else
			{
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

		public void printThetas()
		{
			for(int i = 0; i < thetas.size(); i++)
				System.out.print("Theta " + Integer.toString(i) + ": " + Double.toString(thetas.get(i)));
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
			ArrayList<Integer> outputNums = new ArrayList<Integer>();
			while(sc.hasNextLine())
			{
				String[] split = sc.nextLine().split(" +");
				Field toUse = fields.get(split.length-1);	
				String key = split[split.length-1];
				boolean gt = false;
				if(key.equals(">50K"))
					gt = true;
				//System.out.println(gt + " " + key);
				if(gt){
					//System.out.println("Added to 2");
					toUse.typeCounts2.put(key, toUse.typeCounts2.get(key)+1);
				}
				else{
					//System.out.println("Added to 1");
					toUse.typeCounts1.put(key, toUse.typeCounts1.get(key)+1);
				}
				for(int i = 0 ; i < split.length; i++)
				{
					toUse = fields.get(i);
					if(i == split.length-1)
					{
						key = split[i];
						if(key.equals(">50K"))
							outputNums.add(1);
						else
							outputNums.add(0);
						toUse.typeCounts1.put(key, toUse.typeCounts1.get(key)+1);

					}
					else if(!toUse.numeric)
					{
						key = split[i];
						if(gt)
							toUse.typeCounts2.put(key, toUse.typeCounts2.get(key)+1);
						else
							toUse.typeCounts1.put(key, toUse.typeCounts1.get(key)+1);
				
					}
					else
					{
						int val = Integer.parseInt(split[i]);
						toUse.nums.add(val);
					}

				}

			}
			fields.get(fields.size()-1).nums = outputNums;
			for(int i = 0; i < fields.size(); i++)
			{
				if(fields.get(i).numeric)
				{
					fields.get(i).train(fields.get(fields.size()-1));
					//System.out.println(fields.get(i).theta);
				}
			}
		}
		catch(FileNotFoundException e){
			System.out.println("FNF");
		}
		

	}

	public void makePredictions(String testDataFilepath){
		double gtprob = 1.0;
		double ltprob = 1.0;
		double probNum = 1.0;
		try{
			Scanner sc = new Scanner(new File(testDataFilepath));
			while(sc.hasNextLine())
			{
				String[] split = sc.nextLine().split(" +");
				Field toUse = fields.get(split.length);
				int lttotal = toUse.typeCounts1.get("<=50K")+2;
				int gttotal = toUse.typeCounts2.get(">50K")+2;
		
				for(int i = 0 ; i < split.length; i++)
				{
					toUse = fields.get(i);
					if(!toUse.numeric)
					{
						String key = split[i];
						int ltoccurences = toUse.typeCounts1.get(key)+1;
						int gtoccurences = toUse.typeCounts2.get(key)+1;
						gtprob = gtprob*(double)(gtoccurences)/gttotal;
						//System.out.println("gtprob " + gtprob + " " + (double)(gtoccurences)/gttotal);
						ltprob = ltprob*(double)(ltoccurences)/lttotal;
						//System.out.println("ltprob " + ltprob + " " + (double)(ltoccurences)/lttotal);
					}
					else
					{
						System.out.println(toUse.classify(Double.parseDouble(split[i])));



					}

				}
				if(ltprob > gtprob)
					System.out.println("<=50K");
				else
					System.out.println(">50K");
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