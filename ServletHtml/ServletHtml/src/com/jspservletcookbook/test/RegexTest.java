package com.jspservletcookbook.test;

public class RegexTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s="PR-20;\r\n24*24*2;\r\nPART#PRP2424-20";
		System.out.println(s);
		System.out.println(s.replaceAll("\r\n", "abc"));

	}

}
