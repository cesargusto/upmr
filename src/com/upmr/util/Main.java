package com.upmr.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Main {
	
	public static void main(String[] args) {
		ArrayList<Integer>teste = new ArrayList<>();
		teste.add(12);
		teste.add(14);
		teste.add(17);
		teste.add(10);
		teste.add(19);
		teste.add(34);
		teste.add(30);
		Collections.sort(teste);
		teste.forEach(valor->{System.out.println(Math.sqrt(valor));});
	}

}
