package com.upmr.metaheuristic.ils;

import com.upmr.localsearch_2.LocalSearch;
import com.upmr.localsearch.Moviments;
import com.upmr.core.Solution;

public class LocalSearchILS extends LocalSearch{
	
	private Moviments mvs;
	
	public LocalSearchILS(){
		this.mvs = new Moviments();
	}
	
	public Solution local_search_ils(Solution s) throws CloneNotSupportedException{
		Solution sol_bst = s.clone();
		Solution sol_aux = null;
		int fo_bst = s.makespan();
		int fo_aux = Integer.MAX_VALUE;
		
		
		sol_aux = mvs.remove_job_Maq_mspan(s);
		fo_aux = sol_aux.makespan(); 
		if(fo_aux < fo_bst){
			sol_bst = sol_aux.clone();
			fo_bst = fo_aux;
		}
		
		sol_aux = mvs.swap_job_ExtraMaq(s);
		fo_aux = sol_aux.makespan(); 
		if(fo_aux < fo_bst){
			sol_bst = sol_aux.clone();
			fo_bst = fo_aux;
		}
		
		sol_aux = mvs.troca_intra_Maq(s);
		fo_aux = sol_aux.makespan(); 
		if(fo_aux < fo_bst){
			sol_bst = sol_aux.clone();
			fo_bst = fo_aux;
		}
		
		sol_aux = mvs.insert_intra_Maq(s);
		fo_aux = sol_aux.makespan(); 
		if(fo_aux < fo_bst){
			sol_bst = sol_aux.clone();
			fo_bst = fo_aux;
		}

		return sol_aux;
	}
	
	public Solution local_search_ils_2(Solution s) throws CloneNotSupportedException{
		Solution sol_aux = mvs.remove_job_Maq_mspan(s);
		sol_aux = mvs.swap_job_ExtraMaq(sol_aux);
		sol_aux = mvs.troca_intra_Maq(sol_aux);
		sol_aux = mvs.insert_intra_Maq(sol_aux);
		
		return sol_aux;
	}

}
