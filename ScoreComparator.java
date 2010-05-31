import java.util.Comparator;

/**
 * A simple class that defines how to snippet objects
 * should be compared using their scores.
 * 
 * @author Chuka Okoye
 */

public class ScoreComparator implements Comparator<Snippet>
{
	public int compare(Snippet snippet1, Snippet snippet2)
	{
		if(snippet1.getScore() < snippet2.getScore())
			return -1;
		else if(snippet1.getScore() == snippet2.getScore())
			return 0;
		else
			return 1;
	}
}
