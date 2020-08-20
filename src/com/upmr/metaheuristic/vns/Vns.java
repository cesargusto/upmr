package com.upmr.metaheuristic.vns;

import com.upmr.experiment.BestResults;
import com.upmr.experiment.Time;
import com.upmr.core.Solution;
import com.upmr.localsearch.Moviments;
import com.upmr.metaheuristic.sa.MovimentosSA;

public class Vns {
	
	private Solution best_solution;
	private Solution solution;
	private BestResults best_results;
	private Moviments mvs;
	private MovimentosSA msa;
	private Time time;
	private final int quant_moviments = 5;
	private int num_it;
	
	public Vns(Solution s, int num_it, BestResults best_results) throws CloneNotSupportedException{
		this.num_it = num_it;
		this.solution = s;
		this.best_results = best_results;
		this.best_solution = s.clone();
		this.msa = new MovimentosSA();
		this.mvs = new Moviments();
		
	}
	
	public Vns(Solution s, BestResults best_results) throws CloneNotSupportedException{
		this.solution = s;
		this.best_results = best_results;
		this.best_solution = s.clone();
		this.msa = new MovimentosSA();
		this.mvs = new Moviments();
	}
	
	public Solution execute_vns(long time) throws CloneNotSupportedException{
		int v = 1;
		long start = 0;
		long end = 0;
		long t = 0;
		Solution sol_aux;
		start = System.currentTimeMillis();
		while(t <= time){
			
			sol_aux = vizinhanca(v, this.solution);
			
			int mspan_1 = sol_aux.makespan();
			int mspan_2 = this.best_solution.makespan();
			
			if(mspan_1 < mspan_2){
				this.best_solution = sol_aux.clone();
				this.solution = sol_aux.clone();
				v = 1;
			}else{
				if(v < quant_moviments){
					v = v + 1;
				}else
					v = 1;	
			}
			//num_it --;
			end = System.currentTimeMillis();
			t = end - start;  
		}
		this.best_results.setBest_list(best_solution.makespan());
		return best_solution;
	}
	
	public Solution execute_vns(boolean shake) throws CloneNotSupportedException{
		int v = 1;
		Solution sol_aux;
		while(num_it > 0){
			sol_aux = vizinhanca(v, this.solution);
			if(shake) {
				sol_aux = msa.Switch(sol_aux);
			}
			if(!sol_aux.check_feasibility()) {
				sol_aux.repair_solution();
			}
			int mspan_1 = sol_aux.makespan();
			int mspan_2 = this.best_solution.makespan();
			
			if(mspan_1 < mspan_2){
				this.best_solution = sol_aux.clone();
				this.solution = sol_aux.clone();
				v = 1;
				//System.out.println("Melhora VNS : "+mspan_1);
			}else{
				if(v < quant_moviments){
					v = v + 1;
				}else
					v = 1;	
			}
			num_it --;
		}
		this.best_results.setBest_list(best_solution.makespan());
		return best_solution;
	}
	
	public Solution vizinhanca(int v, Solution s) throws CloneNotSupportedException{

		switch(v){
		case 1:
			//return mvs.insert_intra_Maq(s);
			return mvs.remove_job_Maq_mspan(s);
		case 2:
			return mvs.swap_job_ExtraMaq(s);
		case 3:
			return mvs.troca_intra_Maq(s);
		case 4:
			return mvs.insert_intra_Maq(s);
			//return mvs.remove_job_Maq_mspan(s);
		case 5:
			return mvs.change_Maq(s);
			//return s;
		default:
			return null;
		}
	}
	

}
