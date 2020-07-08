package com.upmr.localsearch;

import com.upmr.core.Solution;

public class VND {
	
	private Moviments moviment;
	
	public VND(){
		this.moviment = new Moviments();
	}
	
	public Solution execute_vnd(Solution s) throws CloneNotSupportedException{
		Solution melhor_s = s.clone();
		int melhor_fo = s.makespan();
		Solution s_aux = null;
		int fo_aux = Integer.MAX_VALUE;
		
		int n_v = this.moviment.getN_vizinhanca();
		int k = 1;
		while(k < n_v){
			switch(k){
				case 1:
					s_aux = this.moviment.remove_job_Maq_mspan(s);
					fo_aux = s_aux.makespan();
					if(fo_aux < melhor_fo){
						melhor_fo = fo_aux;
						melhor_s = s_aux.clone();
						k = 1;
					}else
						k = k + 1;
					break;
				case 2:
					s_aux = this.moviment.swap_job_ExtraMaq(s);
					fo_aux = s_aux.makespan();
					if(fo_aux < melhor_fo){
						melhor_fo = fo_aux;
						melhor_s = s_aux.clone();
					}else
						k = k + 1;
					break;
				case 3:
					s_aux = this.moviment.troca_intra_Maq(s);
					fo_aux = s_aux.makespan();
					if(fo_aux < melhor_fo){
						melhor_fo = fo_aux;
						melhor_s = s_aux.clone();
					}else
						k = k + 1;
					break;
				case 4:
					s_aux = this.moviment.insert_intra_Maq(s);
					fo_aux = s_aux.makespan();
					if(fo_aux < melhor_fo){
						melhor_fo = fo_aux;
						melhor_s = s_aux.clone();
					}else
						k = k + 1;
					break;
			}
		}
		return melhor_s;
	}
	
	public Solution execute_rvnd(Solution s) throws CloneNotSupportedException{
		Solution melhor_s = s.clone();
		int melhor_fo = s.makespan();
		Solution s_aux = null;
		int fo_aux = Integer.MAX_VALUE;
		
		int n_v = this.moviment.getN_vizinhanca();
		int k = 1;
		while(k < n_v){
			switch(k){
				case 1:
					s_aux = this.moviment.remove_job_Maq_mspan(s);
					fo_aux = s_aux.makespan();
					if(fo_aux < melhor_fo){
						melhor_fo = fo_aux;
						melhor_s = s_aux.clone();
						k = 1;
					}else
						k = k + 1;
					break;
				case 2:
					s_aux = this.moviment.swap_job_ExtraMaq(s);
					fo_aux = s_aux.makespan();
					if(fo_aux < melhor_fo){
						melhor_fo = fo_aux;
						melhor_s = s_aux.clone();
					}else
						k = k + 1;
					break;
				case 3:
					s_aux = this.moviment.troca_intra_Maq(s);
					fo_aux = s_aux.makespan();
					if(fo_aux < melhor_fo){
						melhor_fo = fo_aux;
						melhor_s = s_aux.clone();
					}else
						k = k + 1;
					break;
				case 4:
					s_aux = this.moviment.insert_intra_Maq(s);
					fo_aux = s_aux.makespan();
					if(fo_aux < melhor_fo){
						melhor_fo = fo_aux;
						melhor_s = s_aux.clone();
					}else
						k = k + 1;
					break;
			}
		}
		return melhor_s;
	}

}
