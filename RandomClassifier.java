import java.util.*;
import java.io.*;
public class RandomClassifier extends Classifier{

	public RandomClassifier(String namesFilepath) 
	{
		super(namesFilepath);

	}

	public void train(String trainingDataFilpath){
		//dpn't need to train for random
	}

	public void makePredictions(String testDataFilepath){
		try{
			Scanner sc = new Scanner(new File(testDataFilepath));
			while(sc.hasNextLine())
			{
				String s = sc.nextLine();
				double prob = Math.random();
				if(prob > 0.5)
					System.out.println("<=50K");
				else
					System.out.println(">50K");
			}
		}
		catch(FileNotFoundException e){
			System.out.println("FNF");
		}
	}





}