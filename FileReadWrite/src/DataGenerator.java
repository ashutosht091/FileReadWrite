import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DataGenerator {


	public static void main(String[] args)  {
		Scanner sc = new Scanner(System.in);
		int fileSize=0;
		int choice = 4;

		System.out.println("Enter Master dataset Location");
		String directory = sc.next();
		System.out.println("Enter file Size");
		fileSize = sc.nextInt();
		System.out.println("Enter directoryDetails");
		String[]  directories = sc.next().split(",");
		int count =0;
		while(count<directories.length){
			generateDirectoryContent(directory+File.separator+directories[count],fileSize, Integer.parseInt(directories[++count]));
			++count;
		}
		sc.close();






	}

	public static void generateDirectoryContent(String directory,int fileSize,int directorySize)
	{

		int fileCount ;
		int leftoverSize=0;
		fileCount = directorySize/fileSize;
		if(directorySize%fileSize !=0)
		{
			leftoverSize=directorySize%fileSize;
			fileCount++;

		}
		String fileName =  File.separator+"file";
		int count=1;
		try{
			do{

				File file = new File(directory);
				if(!file.exists()) 
				{
					file.mkdirs();
				}
				file = new File(file,fileName+count);
				if(count==fileCount &&leftoverSize>0)
				{

					FileUtil.write(leftoverSize,file);
					leftoverSize=0;
				}else{	
					FileUtil.write(fileSize,file);
				}
				count++;
			}while(count<=fileCount);
			System.out.println("Data Generated Successfully");
		}catch(Exception ex)
		{
			System.out.println("some error occurred"+ex.getMessage());
		}

	}





}
