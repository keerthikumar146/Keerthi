package com.test.duplicate;

import java.util.*;
import java.util.stream.Collectors;

public class Duplicate {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int num, size;
		System.out.println("enter Size");
		size = sc.nextInt();
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			num = sc.nextInt();
			list.add(num);
		}

		/*
		 * Set<Integer> set=new TreeSet<Integer>();
		 * 
		 * for(int a:list) { set.add(a); } System.out.println(set);
		 */

		/*
		 * Duplicate ds=new Duplicate(); ds.splitlist(set);
		 */
		List<Integer> list2 = new ArrayList<Integer>();
		List<Integer> UniqueNumbers = list.stream().distinct().collect(Collectors.toList());
		for (int i = 0; i < UniqueNumbers.size(); ++i) {
			list2.add(UniqueNumbers.get(i));
		}

		Collections.sort(list2);
		System.out.println(list2);
		List<Integer> newlist = new ArrayList<Integer>();
		// List<Integer> newlist2=new ArrayList<Integer>();

		for (int k = 0; k < list2.size() - 1; k++) {
			/*
			 * if(k==0) { if(list2.get(k)==1) { newlist2.add(list2.get(k)); } }
			 */

			if (list2.get(k) + 1 == list2.get(k + 1)) {

				newlist.add(list2.get(k));

			} else if (list2.get(k) - 1 == list2.get(k - 1)) {
				newlist.add(list2.get(k));

				System.out.println(newlist);
				newlist.clear();

			} else {
				newlist.add(list2.get(k));
				System.out.println(newlist);
				newlist.clear();
			}

		}
		System.out.println(newlist);
		// System.out.println(newlist2);

	}
}