package com.upmr.c_localsearch;

import java.util.ArrayList;

import com.upmr.core.Solution;

public class ShuffleIndex extends Moviments{
	
	public Solution change_Maq(Solution solucao) throws CloneNotSupportedException{
		//Troca todo o sequenciamento de máquinas
		
		int initial_fo = solucao.makespan();
		
		Solution best_sol_mov = solucao.clone();
		
		int best_mspan = initial_fo;
		ArrayList<Integer> alter_maqs = new ArrayList<>();
		
		int sol_size = solucao.getSizeSol();
		int maq_mspan = solucao.maior_menor().get(2);
		
		for(int i = 0;i < sol_size;i++){
			if(i != maq_mspan){
				solucao.swap_Machine(maq_mspan, i);
				alter_maqs.add(maq_mspan);
				alter_maqs.add(i);
				if(avalia_solucao(initial_fo, best_mspan, solucao, best_sol_mov, alter_maqs)){
					best_sol_mov = solucao.clone();
					best_mspan = best_sol_mov.makespan();
				}
				//solucao.print_solution();//avaliação da solution
				solucao.swap_Machine(maq_mspan, i);
			}
			alter_maqs.clear();
		}	
		return best_sol_mov;
	}
}
