import java.util.ArrayList;

/**A class definition of the term 'Snippet'. It contains
 * a sequence of words extracted from the document. The first
 * element is always the beginning index while the last element
 * forms the last index.
 * 
 * @author Chuka Okoye
 */
public class Snippet
{
	private ArrayList<Word> wordList;
	private int score;
	
	public Snippet()
	{
		this.wordList = new ArrayList<Word>();
		this.score = 0;
	}
	
	/**
	 * @return int, The pre-computed score for this snippet
	 */
	protected int getScore()
	{
		return score;
	}
	
	/**
	 * @return int, The index of the first element. If empty,
	 * it returns -1. 
	 */
	protected int getStartIndex()
	{
		if(wordList.size() != 0)
			return wordList.get(0).getStartIndex();
		else
			return -1;
	}
	
	/**
	 * @return int, The index of the last element plus length of 
	 * last element to include all characters in last string.
	 * Returns -1 if empty.
	 */
	public int getEndIndex()
	{
		if(wordList.size() != 0)
			return (wordList.get(wordList.size()-1).getStartIndex() + (wordList.get(wordList.size()-1).getWord().length()-1));
		else
			return -1;
	}
	
	protected void setScore(int score)
	{
		this.score = score;
	}
	
	protected void addWord(Word aWord)
	{
		wordList.add(aWord);
	}
	
	protected ArrayList<Word> getWordList()
	{
		return wordList;
	}
	
	protected boolean setWord(int index, Word word)
	{
		wordList.set(index, word);
		return true;
	}

	public String toString()
	{
		StringBuffer temp = new StringBuffer();
		if(wordList.size() == 0)
			return null;
		for(int i=0; i<wordList.size(); i++)
		{
			temp.append(wordList.get(i).getWord());
			temp.append(" ");
		}
		//temp.append(" [score: "+score+"]");
		return temp.toString().trim();
	}
}
