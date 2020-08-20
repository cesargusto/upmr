package com.upmr.metaheuristic.pr;

import com.upmr.core.Solution;

import com.upmr.localsearch.VND;

public class pr{
	
	EliteSet elite;
	VND vnd;
	
	public pr() {
		elite = new EliteSet();
		vnd = new VND();
	}
	
public Solution execute_pr(Solution s_guia, Solution s_corrente, int s_star) throws CloneNotSupportedException {
		
	int elemento = 0;
	int s_guia_makespan = s_star;
	Solution s_star_pr = s_corrente;
	
	if(s_guia_makespan == s_corrente.makespan())
		return s_corrente;
	else {
		
		int n_maq = s_guia.getSizeSol();
		int n_job = -1;
		
		for(int i = 0;i < s_guia.getSizeSol();i++) {
			for(int j = 0;j < s_guia.getMaq(i).getSizeMaq();j++) {
				
				elemento = s_guia.getMaq(i).getJob(j);
				
				if(j <= s_corrente.getMaq(i).getSizeMaq()) {
					s_corrente = elite.change_solution(s_corrente, i, elemento, j);
				}
				
				n_job = s_guia.getMaq(n_maq-1).getSizeMaq();
				
				if(j <= n_job) {
					elemento = s_guia.getMaq(n_maq-1).getJob(n_job - 1);
					s_corrente = elite.change_solution(s_corrente, n_maq-1, elemento, n_job - 1);
				}
				//Boolean: first improv, rvnd
				s_corrente = vnd.execute_vnd(s_corrente);
				
				int m_aux = s_corrente.makespan();
				
				if(m_aux < s_guia_makespan) {
					System.out.println("Melhora PATH RELINKING!!! >>> "+ m_aux);
					s_guia_makespan = m_aux;
					s_star_pr = s_corrente.clone();
				}
				//s_corrente.print_solution();
			}
		}
	}
	//System.out.println("Melhora PATH RELINKING!!! >>> "+ s_guia_makespan);
	return s_star_pr;
	}


	public Solution execute_pr_pos(Solution s_guia, EliteSet elite) throws CloneNotSupportedException {
	
		Solution s_star = null;
		Solution s_aux = null;
		int fo_star = s_guia.makespan();
		int fo_aux = Integer.MAX_VALUE;
		
		for(int i = 0;i < elite.getElite().size();i++) {
			s_aux = elite.getElite().get(i);
			s_aux = this.execute_pr(s_guia, s_aux, fo_star);
			fo_aux = s_aux.makespan(); 
			if(fo_aux < fo_star) {
				s_star = s_aux.clone();
				fo_star = fo_aux; 
				System.out.println("Melhor PR!! >> "+ fo_star);
			}else {
				System.out.println("Sem melhora.. =(");
				s_star = s_guia;
			}
		}
	
	return s_star;
	}

}
