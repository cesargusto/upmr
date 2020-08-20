package com.upmr.c_localsearch;

import com.upmr.core.Solution;

public class VND {
	
	private Moviments moviment;
	private Solution melhor_s;
	private int melhor_fo;
	private EliteSetLocalSearch elite;
	
	public VND(){
		this.moviment = new Moviments();
		this.melhor_s = null;
		this.melhor_fo = Integer.MAX_VALUE;
		this.elite = null;
	}

	public Solution execute_vnd(Solution s) throws CloneNotSupportedException{
		melhor_s = s.clone();
		melhor_fo = melhor_s.makespan();
		Solution s_aux = null;
		int fo_aux = Integer.MAX_VALUE;
		this.elite = new EliteSetLocalSearch();
		//int n_v = this.moviment.getN_vizinhanca();
		int n_v = 5;
		LocalSearch lc = null;
		int k = 1;
		while(k < n_v){
			switch(k){
				case 1:
					TakeOff tk_o = new TakeOff();
					lc = new LocalSearch(s, tk_o);
					s_aux = lc.find_best_neighbor();
					fo_aux = s_aux.makespan();
					if(fo_aux < melhor_fo){
						melhor_fo = fo_aux;
						melhor_s = s_aux.clone();
						this.elite.setElite(melhor_s);
						k = 1;
					}else
						k = k + 1;
					break;
				case 2:
					SwapOut sw_o = new SwapOut();
					lc = new LocalSearch(s, sw_o);
					s_aux = lc.find_best_neighbor();
					fo_aux = s_aux.makespan();
					if(fo_aux < melhor_fo){
						melhor_fo = fo_aux;
						melhor_s = s_aux.clone();
						this.elite.setElite(melhor_s);
						k = 2;
					}else
						k = k + 1;
					break;
				case 3:
					SwapIn sw_i = new SwapIn();
					lc = new LocalSearch(s, sw_i);
					s_aux = lc.find_best_neighbor();
					fo_aux = s_aux.makespan();
					if(fo_aux < melhor_fo){
						melhor_fo = fo_aux;
						melhor_s = s_aux.clone();
						this.elite.setElite(melhor_s);
						k = 3;
					}else
						k = k + 1;
					break;
				case 4:
					InsertIn ins_i = new InsertIn();
					lc = new LocalSearch(s, ins_i);
					s_aux = lc.find_best_neighbor();
					fo_aux = s_aux.makespan();
					if(fo_aux < melhor_fo){
						melhor_fo = fo_aux;
						melhor_s = s_aux.clone();
						this.elite.setElite(melhor_s);
						k = 4;
					}else
						k = k + 1;
					break;
				case 5:
					ShuffleIndex sh_i = new ShuffleIndex();
					s_aux = sh_i.change_Maq(s);
					fo_aux = s_aux.makespan();
					if(fo_aux < melhor_fo){
						melhor_fo = fo_aux;
						melhor_s = s_aux.clone();
						this.elite.setElite(melhor_s);
						k = 5;
					}else
						k = k + 1;
					break;					
			}
		}
		Solution e = this.elite.elect_best();
		if(e!=null)
			return e;
		else
			return melhor_s;
	}
}
