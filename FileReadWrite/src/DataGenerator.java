import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Random;

public class DataGenerator {


	static ByteArrayOutputStream out;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub   
		generateDirectoryContent("destination", 15, 25);

	}

	public static boolean generateDirectoryContent(String directory,int filesize,int directorySize) throws IOException
	{
		int fileCount = 0;
		int leftoverSize=0;
		fileCount = directorySize/filesize;
		if(directorySize%filesize !=0)
		{
			leftoverSize=directorySize%filesize;
			fileCount++;

		}
		String fileName =  File.separator+"file";
		int count=1;
		do{

			File file = new File(directory);
			if(!file.exists()) file.mkdirs();
			file = new File(file,fileName+count);
			if(count==fileCount &&leftoverSize>0)
			{

				writeFile(leftoverSize,file);
				leftoverSize=0;
			}else{
				writeFile(filesize,file);
			}
			count++;
		}while(count<=fileCount);


		return true;
	}
	public static void updateDirectoryContent(String directory,int size,Map<String,Integer>dirToSize) throws IOException
	{
		
		File dest = new File(directory);
		String[] listOfDir = dest.list();
		for(String dir:listOfDir)
		{
			if(dirToSize.containsKey(dir))
			{
				File[] listOfFile = new File(dir).listFiles();
				int fileSize = size/listOfFile.length;
				int leftOver = size%listOfFile.length;
				writeToFile(fileSize, listOfFile, leftOver);
			}
		}
		
	}
	
	public static void writeToFile(int fileSize, File[] listOfFile,int leftOver) throws IOException
	{
		for(int i =0 ;i<listOfFile.length;i++)
		{
			if(i==listOfFile.length-1 && leftOver>0){
				writeFile(leftOver,listOfFile[i]);
			}
			writeFile(fileSize,listOfFile[i]);
		}
	}
	


	protected static String getSaltString(int size) throws IOException {



		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();

		Random rnd = new Random();
		while (salt.length() < size) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}




	static void writeFile(long size,File file) throws IOException
	{
		boolean append = true;
		boolean autoFlush = true;
		String charset = "UTF-8";
		long writtenChunk = 0;
		FileOutputStream fos = new FileOutputStream(file, append);
		OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
		BufferedWriter bw = new BufferedWriter(osw);
		PrintWriter pw = new PrintWriter(bw, autoFlush);
		Random rnd = new Random();
		while(writtenChunk<size*1000000){
			int linesize =(int)(rnd.nextFloat()*50);
			byte[] bb =  getSaltString(linesize).getBytes();
			writtenChunk+=bb.length;
			pw.println(new String(bb));
		}

		pw.flush();
		pw.close();


	}


}
