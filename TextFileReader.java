import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TextFileReader 
{
	ArrayList<String>lines;
	
	public TextFileReader(String fileName) throws IOException
	{
		FileReader fileReader=new FileReader(fileName);
		BufferedReader bufferedReader=new BufferedReader(fileReader);
		lines=new ArrayList<String>();
		
		readFile(bufferedReader);
	}
	
	private void readFile(BufferedReader br) throws IOException
	{
		String line;
	    
		while ((line = br.readLine()) != null)
	    {
	      lines.add(line);
	    }
	}
	
	public String getLine(int index)
	{
		return lines.get(index);
	}
	
	public int size()
	{
		return lines.size();
	}
	
	public void print()
	{
		for(int i=0;i<lines.size();++i)
		{
			System.out.println(lines.get(i));
		}
	}
	
	public ArrayList<ArrayList<String>> parseBy(char x)
	{
		ArrayList<ArrayList<String>> mainArray=new ArrayList<ArrayList<String>>();
		
		for(int i=0;i<lines.size();++i)
		{
			ArrayList<String> subArray=new ArrayList<String>();
			mainArray.add(subArray);
			
			String line=lines.get(i);
			int startIndex=0;
			
			for(int u=0;u<line.length();++u)
			{
				if(line.charAt(u)==x)
				{
					String phrase=line.substring(startIndex,u);
					
					if(phrase.length()>0)
					{
						subArray.add(phrase);
					}
					
					startIndex=u+1;
				}
			}
			
			if(line.charAt(line.length()-1)!=x)
			{
				String phrase=line.substring(startIndex);
				
				if(phrase.length()>0)
				{
					subArray.add(phrase);
				}
			}
			
		}
		
		return mainArray;
	}
}
