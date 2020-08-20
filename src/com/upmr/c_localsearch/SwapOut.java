package com.upmr.c_localsearch;

import java.util.ArrayList;

import com.upmr.core.Solution;

public class SwapOut extends Moviments implements Neighborhood {
	//swap_job_ExtraMaq
	@Override
	public Solution run_moviment(Solution solucao) throws CloneNotSupportedException {
		//SERÁ FEITO NA MAQUINA MAKESPAN COM AS DEMAIS

		int initial_fo = solucao.makespan();
		
		Solution best_sol_mov = solucao.clone();
		int best_mspan = initial_fo;
		
		ArrayList<Integer> alter_maqs = new ArrayList<>();
		
		int sol_size = solucao.getSizeSol();
		int maq_mspan = solucao.maior_menor().get(2);
		int machine_mkpan_size = solucao.getMaq(maq_mspan).getSizeMaq();
		
		for(int i = 0;i < machine_mkpan_size;i++){
			int job_pivot = solucao.getMaq(maq_mspan).getJob(i);
			alter_maqs.add(maq_mspan);
			for(int j = 0;j < sol_size;j++){
				if(j != maq_mspan){
					for(int w = 0;w < solucao.getMaq(j).getSizeMaq();w++){
						int job_pivot_2 = solucao.getMaq(j).getJob(w);
						solucao.getMaq(maq_mspan).setJobToMaq(i, job_pivot_2);
						solucao.getMaq(j).setJobToMaq(w, job_pivot);
						
						alter_maqs.add(j);
						
						if(avalia_solucao(initial_fo, best_mspan, solucao, best_sol_mov, alter_maqs)){
							best_sol_mov = solucao.clone();
							best_mspan = best_sol_mov.makespan();
						}
						solucao.getMaq(maq_mspan).setJobToMaq(i, job_pivot);
						solucao.getMaq(j).setJobToMaq(w, job_pivot_2);
						
						alter_maqs.remove(1);
					}
				}
			}
			alter_maqs.clear();
		}
		return best_sol_mov;

	}

}
