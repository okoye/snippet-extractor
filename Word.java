/**
 * Class definition of a Word.
 * @author Chuka Okoye
 *
 */

public class Word 
{
	private String word;
	private int startIndex;
	private final String startTag = "[[HIGHLIGHT]]";
	private final String endTag = "[[ENDHIGHLIGHT]]";
	
	public Word(String word, int index)
	{
		this.word = word;
		this.startIndex = index;
	}
	
	protected String getWord()
	{
		return word;
	}
	
	protected int getStartIndex()
	{
		return startIndex;
	}
	
	protected Word tagWord()
	{
		StringBuffer temp = new StringBuffer();
		temp.append(startTag);
		temp.append(word);
		temp.append(endTag);
		word = temp.toString();
		return this;
	}
}
