
/**
 * @author Chuka Okoye
 *
 */
public class ScoreComputer 
{
	String[] searchTerms;
	public ScoreComputer(String[] searchTerm)
	{
		this.searchTerms = searchTerm;
	}
	public Snippet computeScore(Snippet value)
	{
		int tempScore = 0;
		Word aWord = null;
		Snippet snippet = value;
		
		for(int i=0; i<snippet.getWordList().size(); i++)
		{
			aWord = snippet.getWordList().get(i);
			tempScore = score(aWord);
			if(tempScore != 0)
			{
				snippet.setScore(snippet.getScore()+tempScore);
				snippet.setWord(i, aWord.tagWord());
			}
		}
		return snippet;
	}
	
	private int score(Word word)
	{
		String[] wordArray = word.getWord().split("\\W"); //Ignore all non-word characters when scoring.
		int score = 0;
		
		for(int j=0; j<wordArray.length; j++)
			for(int i=0; i<searchTerms.length; i++)
				if(wordArray[j].equalsIgnoreCase(searchTerms[i]))
					score++;
		return score;
	}
}
