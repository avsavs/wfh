package guestbook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {

	private static final String FILENAME = "C:\\searchallcraigslist\\filename.html";

	public static void writeFile(String content) {

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			//String content = "This is the content to write into file\n";
			
			File file= new File (FILENAME);
		
			//if (file.exists())
		//	{
			//   fw = new FileWriter(file,true);//if file exists append to file. Works fine.
		//	}
			//else
			{
			   file.createNewFile();
			   fw = new FileWriter(file);
			}

			//fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			bw.write(content);

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

}
