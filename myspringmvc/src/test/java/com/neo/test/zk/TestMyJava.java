package com.neo.test.zk;

import java.util.SortedSet;
import java.util.TreeSet;

public class TestMyJava {

	public static void main(String[] args) {
		// [/WRITE_LOCK/x-237682361962463246-0000000034, /WRITE_LOCK/x-93567173870682127-0000000033]
		String s1 = "/WRITE_LOCK/x-93567173870682127-0000000033";
		String s2 = "/WRITE_LOCK/x-237682361962463246-0000000034";
		System.out.println(s1.compareTo(s2));
		SortedSet<String> sortedNames = new TreeSet<String>();
		sortedNames.add(s2);
		sortedNames.add(s1);
		System.out.println(sortedNames.first());
	}

}
