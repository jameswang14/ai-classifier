import java.util.*;
import java.io.*;
public class MyClassifier extends Classifier{
	ArrayList<Field> fields;
	class Field{
		public String name;
		public boolean numeric;
		public ArrayList<String> types; 
		public int num;
		public Field(String name, boolean numeric, ArrayList<String> types, int num)
		{
			this.name = name;
			this.numeric = numeric;
			this.types = types;
			this.num = num;

		}

		public String toString()
		{
			String s = "Name: " + this.name;
			if(this.numeric)
				s+= " numeric";
			else{
				s+=" [";
				for(String type: types)
					s+=type + " ";
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
			ArrayList<String> temp = new ArrayList<String>();
			ArrayList<String> split = new ArrayList<String>();
			temp.add(sc.next());
			temp.add(sc.next());
			sc.nextLine();
			sc.nextLine();
			Field output = new Field("output", false, temp, -1);
			while(sc.hasNextLine())
			{
				temp.clear();
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
						temp.add(split.get(i));
					ArrayList<String> copy = new ArrayList<String>();
					for(String s: temp)
						copy.add(new String(s));
					fields.add(new Field(split.get(0), false, copy, -1));

				}

			}
		}	
		catch(FileNotFoundException e){
			System.out.println("FNF");
		}
		


	}

	public void train(String trainingDataFilpath){

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