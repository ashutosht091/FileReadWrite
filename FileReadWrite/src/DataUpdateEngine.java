import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DataUpdateEngine {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Master dataset Location");
		String directory = sc.next();
		System.out.println("Enter directoryDetails");
		String[] directories = sc.next().split(",");
		int count =0;
		Map<String,Integer> dirToSize = new HashMap<String,Integer>();
		while(count<directories.length){
			dirToSize.put(directories[count], Integer.parseInt(directories[++count]));
			++count;

		}
		updateDirectoryContent(directory, dirToSize);
		sc.close();
	}
	
	
	public static void updateDirectoryContent(String directory,Map<String,Integer>dirToSize) 
	{

		File dest = new File(directory);
		String[] listOfDir = dest.list();
		for(String dir:listOfDir)
		{
			if(dirToSize.containsKey(dir))
			{
				try{
					File[] listOfFile = new File(directory+File.separator+dir).listFiles();
					int fileSize = dirToSize.get(dir)/listOfFile.length;
					int leftOver = dirToSize.get(dir)%listOfFile.length;
					FileUtil.writeToFile(fileSize, listOfFile, leftOver);
				}catch(IOException ex)
				{
					System.out.println("problem occured while wrting to dir"+dir+ex.getMessage());
				}
			}
		}
		System.out.println("Job Completed");


	}
}
