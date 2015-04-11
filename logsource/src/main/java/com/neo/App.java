package com.neo;

import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
	private final static Logger logger = LoggerFactory.getLogger("MPSP");
    public static void main( String[] args )
    {
    	try {
			Random rand = new Random(System.currentTimeMillis());
    		for (int i = 0; i < Integer.MAX_VALUE; i++) {
    			logger.info("{},{}", new Object[]{"t-"+i, rand.nextInt(Integer.valueOf(args[0]))} );
    			Thread.sleep(Integer.valueOf(args[1]));
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
