package com.upmr.c_localsearch;

import java.util.ArrayList;
import java.util.Collections;

import com.upmr.core.Solution;

public class SwapIn extends Moviments implements Neighborhood{
	//troca_intra_Maq
	@Override
	public Solution run_moviment(Solution solucao) throws CloneNotSupportedException{
		
		int initial_fo = solucao.makespan();
		
		Solution best_sol_mov = solucao.clone();
		int best_mspan = initial_fo;
		
		ArrayList<Integer> alter_maqs = new ArrayList<>();
		
		int tamanho_sol = solucao.getSizeSol();

		for(int i = 0;i < tamanho_sol;i++){
			int tamanho_maq = solucao.getMaq(i).getSizeMaq();
			if(tamanho_maq > 1) {
				for(int j = 0;j < tamanho_maq -1;j++){
					for(int w = j+1;w < tamanho_maq;w++){
	
						int a = solucao.getMaq(i).getJob(j);
	
						solucao.getMaq(i).setJobToMaq(j, solucao.getMaq(i).getJob(w));
						solucao.getMaq(i).setJobToMaq(w, a);
						
						alter_maqs.add(i);
	
						if(avalia_solucao(initial_fo, best_mspan, solucao, best_sol_mov, alter_maqs)){
							best_sol_mov = solucao.clone();
							best_mspan = best_sol_mov.makespan();
						}
						//solucao.print_solution();//avaliação da solution
	
						a = solucao.getMaq(i).getJob(j);
	
						solucao.getMaq(i).setJobToMaq(j, solucao.getMaq(i).getJob(w));
						solucao.getMaq(i).setJobToMaq(w, a);
						alter_maqs.clear();
					}
				}
			}
		}
		return best_sol_mov;

	}

	
}
