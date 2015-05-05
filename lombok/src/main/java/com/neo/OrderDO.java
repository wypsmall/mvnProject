package com.neo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 *
 */
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class OrderDO 
{
	private String id;
	private String name;
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        log.info("out {} {} !", "test", 2);
        log.info("");
        log.info("");
    }
}
