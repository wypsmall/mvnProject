package neo.ce.rebate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URI;
import java.util.Arrays;

public class RWFile {
	public static void main(String[] args) {
		try {
			URI uri= RWFile.class.getClassLoader().getResource("param.dat").toURI();
			System.out.println(uri.getPath());
			FileWriter fw=new FileWriter(uri.getPath()+".csv");
			BufferedWriter bw=new BufferedWriter(fw);
			
			File fr = new File(uri);
			BufferedReader br=new BufferedReader(new FileReader(fr));
			String line = "";
			while ((line=br.readLine())!=null) {
				System.out.println(line);
				String param[] = line.split(",");
				System.out.println(Arrays.toString(param));
				line += ",20000," + new String("你好".getBytes(), "utf-8"); 
				bw.write(line+"\t\n");
			}
			
			br.close();
			
			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
