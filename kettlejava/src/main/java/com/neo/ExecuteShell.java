package com.neo;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExecuteShell {

	public static void main(String[] args) {
		try {
			//String shpath = "/usr/local/kettle5/mexecute.sh ";
			String shpath = "/usr/local/kettle5/kitchen.sh -rep=gome-pro -job=trans_2015-04-15  -param:exeDate=2015-04-13 -level:Basic";
			Process ps = Runtime.getRuntime().exec(shpath);
			ps.waitFor();
			System.out.println("==============================");
			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append("--").append(line).append("\n");
			}
			String result = sb.toString();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
