package com.upmr.c_localsearch;

import java.util.ArrayList;

import com.upmr.core.Solution;

public class TakeOff extends Moviments implements Neighborhood{

	@Override
	public Solution run_moviment(Solution solucao) throws CloneNotSupportedException {
		//O JOB PODE SER INSERIDO EM UMA POSIÇÃO ALEATÓRIA INVEZ DO FINAL

		int initial_fo = solucao.makespan();
		
		Solution best_sol_mov = solucao.clone();
		int best_mspan = initial_fo;
		
		ArrayList<Integer> alter_maqs = new ArrayList<>();
		
		int sol_size = solucao.getSizeSol();
		int maq_mspan = solucao.maior_menor().get(2);
		int machine_mkpan_size = solucao.getMaq(maq_mspan).getSizeMaq();
		
		if(machine_mkpan_size > 1) {
			for(int i = 0;i < machine_mkpan_size;i++){
				int job_pivot = solucao.getMaq(maq_mspan).getJob(i);
				solucao.getMaq(maq_mspan).removeJobToMaq(i);
				alter_maqs.add(maq_mspan);
				for(int j = 0;j < sol_size;j++){
					if(j != maq_mspan){
						solucao.getMaq(j).addJobToMaq(job_pivot);
						alter_maqs.add(j);
						
						if(avalia_solucao(initial_fo, best_mspan, solucao, best_sol_mov, alter_maqs)){
							best_sol_mov = solucao.clone();
							best_mspan = best_sol_mov.makespan();
						}
						//solucao.print_solution();//avaliação da solution
						solucao.getMaq(j).removeLastJob();
					}
				}
				alter_maqs.remove(1);
				solucao.getMaq(maq_mspan).insertJobToMaq(i, job_pivot);
			}
			alter_maqs.clear();
		}
		return best_sol_mov;

	}

}
