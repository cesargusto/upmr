/***************************************************************************************
 * 
 * Está classe tem a função de gerar movimentos mais bruscos na solução.
 * Basicamente são retirados de uma só vez da maquina makespan 2, 3 e 4 tarefas
 * de posições aleatórias e distribuídas nas demais máquinas escolhidas aleatóriamente
 * e em posições também aleatórias.
 * 
 * Data de criação: 23 de out de 2017
 * @author cesar
 * 
 ***************************************************************************************/
package com.upmr.c_localsearch;

import java.util.ArrayList;
import java.util.Collections;

import com.upmr.core.Solution;

public class Moviments {
	
	private static final int n_vizinhanca = 4;

	public boolean avalia_solucao(int initial_fo, int best_makespan, Solution s_corrente, Solution s_melhor, ArrayList<Integer> alter_maqs) throws CloneNotSupportedException{
		
		int fo_1  = best_makespan;
		int fo_2 = Collections.max(s_corrente.makespan((ArrayList<Integer>)s_corrente.Tempos(), alter_maqs));
		Solution s2 = s_corrente.clone();
		if(fo_2 < fo_1){
			s2.repair_solution();
			if(s2.makespan() < fo_1)
				return true;
			else 
				return false;
		}else
			return false;
	}
	
	public int getN_vizinhanca() {
		return n_vizinhanca;
	}
}
