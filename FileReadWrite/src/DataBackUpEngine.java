import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DataBackUpEngine {

	public static void main(String[] args) {
		Scanner sc =  new Scanner(System.in);
		System.out.println("Enter Source location");
		String directory = sc.next();
		System.out.println("Enter Destination");
		String destination = sc.next();
		doBackUp(directory, destination);
		sc.close();

	}

	public static void doBackUp(String source, String destination)
	{

		String destinationPath = destination+File.separator+System.currentTimeMillis();

		try {
			File file = new File(destinationPath);
			if(!file.exists()) 
			{
				file.mkdirs();
			}
			List<Path> sourceFiles = getAllFiles(source);
			for(Path sFile:sourceFiles){
				File desinationFile = new File(sFile.toString().replace(source, destinationPath));
				desinationFile.mkdirs();
				Files.copy(sFile, desinationFile.toPath(),
						StandardCopyOption.ATOMIC_MOVE);
			}
		} catch (IOException e) {
			System.out.println("some thing went wrong"+e.getMessage());
		}


	}
	
	

	public static List<Path> getAllFiles(String sDir) throws IOException {
		return Files.find(Paths.get(sDir), 999, (p, bfa) -> bfa.isRegularFile()).collect(Collectors.toList());
	}
	
	
}
