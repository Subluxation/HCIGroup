
public class HighScore implements Comparable<HighScore>
{
	private String name;
	private int score;
	private int rank;
	
	public HighScore(String name,int score)
	{
		this.name=name;
		this.score=score;
		rank=0;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public int getRank()
	{
		return rank;
	}
	
	public void setRank(int rank)
	{
		this.rank=rank;
	}

	@Override
	public int compareTo(HighScore hs) 
	{
		return hs.getScore()-score;
	}
}
