package com.upmr.metaheuristic.ils;

import com.upmr.localsearch.Moviments;
import com.upmr.localsearch_2.VND;
import com.upmr.experiment.BestResults;
import com.upmr.core.Solution;
//import com.upmr.metaheuristic.pr2.*;

//import simulated_mov.Vnd;

public class Ils {

	private Solution solucao;
	private VND vnd;
	//private Vnd vnd2;
	private BestResults best_results;
	private Pertubations perturbation;
	private Moviments mov;
	private int iter_max;
	//private EliteSet es;

	public Ils(Solution solucao, int iter_max, BestResults br){
		this.solucao = solucao;
		this.vnd = new VND();
		//this.vnd2 = new Vnd();
		this.best_results = br;
		this.perturbation = new Pertubations();
		this.mov = new Moviments();
		this.iter_max = iter_max;
		//this.es = es;
	}
	
	public Ils(Solution solucao, BestResults br){
		this.solucao = solucao;
		this.vnd = new VND();
		this.best_results = br;
		this.perturbation = new Pertubations();
		this.mov = new Moviments();
	}

	public Solution execute_ils(long time) throws CloneNotSupportedException{

		int level = 1;
		int s_fo = solucao.makespan();
		int melhor_s_fo = solucao.makespan();
		Solution melhor_s = null;
		Solution s = this.vnd.execute_vnd(solucao);
		if(s.makespan() < melhor_s_fo){
			melhor_s = s.clone();
			melhor_s_fo = s.makespan();
		}
		
		long start = 0;
		long end = 0;
		long t = 0;
	
		start = System.currentTimeMillis();
		
		while(t < time){
			//iter = iter + 1;
			s = this.perturbation.execute(s, level);
			//s = this.mov.perturbation_hard(s, level);
			int aux = s.makespan();
			s = this.vnd.execute_vnd(s);
			s_fo = s.makespan();
			if(s_fo < melhor_s_fo){
				melhor_s = s.clone();
				melhor_s_fo = s_fo;
				//System.out.println("Melhora ILS : "+melhor_s_fo);
				level = 1;
			}else{
				if(level < this.perturbation.getQuant_levels()){
				//if(level < 4){
					level = level + 1;
					//System.out.println("Nivel: "+level);
				}else{
					//System.out.println("Niveis esgotados sem melhora: "+level);
					level = 1;
				}
			}
			end = System.currentTimeMillis();
			t = end - start;
		}
		this.best_results.setBest_list(melhor_s_fo);
		return melhor_s;
	}
	
	public Solution execute_ils() throws CloneNotSupportedException{

		int iter = 0;
		int level = 1;
		int s_fo = solucao.makespan();
		int melhor_s_fo = solucao.makespan();
		Solution s = this.vnd.execute_vnd(solucao);
		Solution melhor_s = solucao.clone();
		Solution s_linha = null;
		Solution s_t_linha = null;
		
		s.repair_solution();
		if(s.makespan() < melhor_s_fo){
			melhor_s = s.clone();
			melhor_s_fo = s.makespan();
		}
	
		while(iter < iter_max){
			iter = iter + 1;
			s_linha = this.perturbation.execute(s, level).clone();
			s_t_linha = this.vnd.execute_vnd(s_linha);
			s_t_linha.repair_solution();
			s_fo = s_t_linha.makespan();
			if(s_fo < melhor_s_fo){
				melhor_s = s_t_linha.clone();
				melhor_s_fo = s_fo;
				//this.es.setElite(melhor_s);
				//System.out.println("Melhora ILS : "+melhor_s_fo);
				level = 1;
			}else{
				if(level < this.perturbation.getQuant_levels())
					level = level + 1;
				else
					level = 1;
					//move to better solution
					s = melhor_s.clone();
			}
		}
		this.best_results.setBest_list(melhor_s_fo);
		return melhor_s;
	}
}
