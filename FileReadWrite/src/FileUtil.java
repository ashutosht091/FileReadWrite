import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Random;

public class FileUtil {
	
	
	public static void writeToFile(int fileSize, File[] listOfFile,int leftOver) throws IOException
	{
		for(int i =0 ;i<listOfFile.length;i++)
		{
			if(i==listOfFile.length-1 && leftOver>0){
				write(leftOver,listOfFile[i]);
			}else{
				write(fileSize,listOfFile[i]);
			}
		}
	}
	
	static void write(long size,File file) throws IOException
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
	public static String getSaltString(int size) throws IOException {



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


}
