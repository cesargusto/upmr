package com.upmr.c_localsearch;

import java.util.ArrayList;

import com.upmr.core.Solution;

public class InsertIn extends Moviments implements Neighborhood{
	//insert_intra_Maq
	@Override
	public Solution run_moviment(Solution solucao) throws CloneNotSupportedException {
		
		int initial_fo = solucao.makespan();
		
		Solution best_sol_mov = solucao.clone();
		int best_mspan = initial_fo;
		
		ArrayList<Integer> alter_maqs = new ArrayList<>();
		
		int tamanho_sol = solucao.getSizeSol();

		for(int i = 0;i < tamanho_sol;i++){
			int tamanho_maq = solucao.getMaq(i).getSizeMaq();
			if(tamanho_maq > 1) {
				for(int j = 0;j < tamanho_maq -1;j++){
					int a = solucao.getMaq(i).getJob(j);
					solucao.getMaq(i).removeJobToMaq(j);
					for(int w = j+1;w < tamanho_maq;w++){
						solucao.getMaq(i).insertJobToMaq(w, a);
	
						alter_maqs.add(i);
						
						if(avalia_solucao(initial_fo, best_mspan, solucao, best_sol_mov, alter_maqs)){
							best_sol_mov = solucao.clone();
							best_mspan = best_sol_mov.makespan();
						}
						//solucao.print_solution();//avaliação da solution
						solucao.getMaq(i).removeJobToMaq(w);
					}
					solucao.getMaq(i).insertJobToMaq(j, a);
					alter_maqs.clear();
				}
			}
		}
		return best_sol_mov;
	}

}
