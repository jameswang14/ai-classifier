public class driver
{
	public static void main(String[] args)
	{
		MyClassifier cl = new MyClassifier("census.names");
		cl.train("census.train");
		//System.out.println(cl.toString());
		cl.makePredictions("census.test");
		
	}
}