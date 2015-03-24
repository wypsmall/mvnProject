package com.neo.test.js;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class CalcString {

	public static void main(String[] args) {
		try {
			
			ScriptEngineManager factory = new ScriptEngineManager();  
			ScriptEngine engine = factory.getEngineByName("JavaScript");  
			String option = "'fdas'<'32'";
			Object o = engine.eval(option);  
			System.out.println(o.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
