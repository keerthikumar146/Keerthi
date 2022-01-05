package com.test.app.domain;

import java.util.*;

public class Repeate {
	public static void main(String[] args) {
		int num = 0, size = 30;
		List<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i <= size; i++) {
			list.add(num);
		}
		System.out.println(list);
	}
}