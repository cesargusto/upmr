/*
 * Classe criada para fazer tratamento de texto, como extração de partes
 * e valores
 * 
 * @autor Cesar
 * 
 * Classe criada em um domingo em Tapira dia 12 de Maio de 2019 / dia das mães
 * 
 * */
package com.upmr.util;

public class Parses {
	
	public static int[] split_nmaq(String nome) {
		int nm[] = new int[2];
		String temp[] = nome.split("_");
		nm[0] = Integer.parseInt(temp[1]);
		nm[1] = Integer.parseInt(temp[2]);
		return nm;
	}
	
	public static int[] split_nmaq_rabadi(String nome) {
		int nm[] = new int[2];
		String temp[] = nome.split("Rp");
		temp = temp[0].split("on");
		nm[0] = Integer.parseInt(temp[0]);
		nm[1] = Integer.parseInt(temp[1]);
		return nm;
	}

}
