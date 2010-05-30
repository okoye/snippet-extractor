
public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String doc1 = "test test test document chuka" +
				"hellow wpr;d reddit clone engadget" +
				"andriod ping twitter ; 9 ( zz yelp search" +
				"pizza deep dish reddit clone master ninja" +
				"dart owlcity san francisco hello";
		HighlightDocument highlighter = new HighlightDocument(doc1);
		highlighter.search("san&francisco");
	}

}
