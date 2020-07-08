package com.upmr.util;

import java.util.ArrayList;

public class View {
	/*
	public static float media(ArrayList<Integer> lista){
		float media = 0;
		int somador = 0;
		for(int i = 0;i < lista.size();i++){
			somador = somador + lista.get(i);
		}
		media = somador / lista.size();
		return media;
	}*/
	
	public static void title_1(String frase) {
		System.out.println("\n**********************************");
		System.out.println("\t"+frase);
		System.out.println("**********************************\n");
	}
	
	public static void title_2(String frase) {
		System.out.println("\n--------------------------------------------------");
		System.out.println("\t"+frase);
		System.out.println("--------------------------------------------------\n");
	}
	
	public static void title_3(String frase) {
		System.out.println("---------------------------------------");
		//System.out.println("[ 10 ] - 25x6_1_U_1_100__R_inter_.txt");
		System.out.println("\t"+frase);
		//System.out.println("-----------------------------------\n");
	}

}
