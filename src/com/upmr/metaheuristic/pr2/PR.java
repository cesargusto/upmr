package com.upmr.metaheuristic.pr2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.upmr.core.Solution;

//import simulated_mov.Vnd;
import com.upmr.localsearch.*;

public class PR {
	
	//Solution s_guia;
	Solution s_corrente;
	EliteSet es;
	VND vnd;
	
	public PR(Solution s_corrente, EliteSet es) {
		//this.s_guia = s_guia;
		this.s_corrente = s_corrente;
		this.es = es;
		this.vnd = new VND();
	}
	
	public Solution execute_pr() throws CloneNotSupportedException {
		Solution s_aux = null;
		Solution s_star = null;
		int fo_star = Collections.min(es.getMakespans_elite());
		
		for(Solution s: es.getElite()) {
			s_aux = troca_sol_pr(s).clone();
			if(s_aux.makespan() < fo_star) {
				fo_star = s_aux.makespan();
				s_star = s_aux.clone();
				System.out.println("MELHORA DE PR: "+fo_star);
			}
			//else 
				//System.out.println("NO IMPROVEMENT");
		}
		if(s_star != null)
			return s_aux;
		else
			return s_corrente;
	}
	
	public Solution run_one_pr() throws CloneNotSupportedException {

		return troca_sol_pr(es.getRandomSolElite());
	}
	
	public Solution troca_sol_pr(Solution s_guia) throws CloneNotSupportedException {
		
		Solution s;
		Solution s_aux;
		Solution s_star = s_guia.clone();
		
		for (int indice = 0; indice < s_guia.getSizeSol(); indice++) {
			s = this.troca_maq_pr(s_guia, indice);
			s_aux = this.vnd.execute_vnd(s);
			s_aux.repair_solution();
			if(s_aux.makespan() < s_star.makespan()) {
				s_star = s_aux.clone();
			}
		}
		
		return s_star;
	}
	
	public Solution troca_maq_pr(Solution s_guia, int indice) {
		//SORTEIA UMA MÁQUINA NA S_GUIA
		//MOVE TODA ELA PARA A MESMA MÁQUINA NA S_CORRENTE
		//CORRIGE A SOLUÇÃO
		Random rnd = new Random();
		int qt_maq = s_guia.getArquivo().getN_maqs();
		//int maq_rnd = rnd.nextInt(qt_maq);
		
		List<Integer> maquina_out = new ArrayList<>();
		List<Integer> maquina_in = new ArrayList<>();
		List<Integer> s_remove = new ArrayList<>();
		List<Integer> s_add = new ArrayList<>();
		
		//maquina que sai da solução
		maquina_out.addAll(s_corrente.getMaq(indice).getMaquina());
		//máquina que entra na solução
		maquina_in.addAll(s_guia.getMaq(indice).getMaquina());
		//remoção da máquina descartada
		s_corrente.getMaq(indice).getMaquina().clear();
		//inserção da nova máquina no lugar
		s_corrente.getMaq(indice).getMaquina().addAll(maquina_in);
		
		//System.out.println(maquina_in);
		//System.out.println(maquina_out);
		
		//corrige solução
		for(int e: maquina_out) {
			if(!maquina_in.contains(e))
				s_add.add(e);
		}
		
		for(int e: maquina_in) {
			if(!maquina_out.contains(e))
				s_remove.add(e);
		}
		
		//System.out.println(s_add);
		//System.out.println(s_remove);
		
		int indice_menor = -1;
		
		//reposicionar elementos não reinseridos
		if(!s_add.isEmpty()) {
			for(int job: s_add) {
				indice_menor = s_corrente.maior_menor().get(0);
				if(indice_menor != indice)
					s_corrente.getMaq(indice_menor).addJobToMaq(job);
				else {
					//sorteia uma maquina aleatória que seja diferente da maq
					//makespan e tambem da maquina escolhida
					int ran = -1;
					if(qt_maq > 2) {
						ran = rnd.nextInt(qt_maq);
						while(ran == indice && ran == s_corrente.maior_menor().get(2)) {
							ran = rnd.nextInt(qt_maq);
						}
						s_corrente.getMaq(ran).addJobToMaq(job);
					}else {
						ran = rnd.nextInt(qt_maq);
						while(ran == indice) {
							ran = rnd.nextInt(qt_maq);
						}
						s_corrente.getMaq(ran).addJobToMaq(job);
					}
				}
			}
		}
		
		//remove elmentos reinseridos
		if(!s_remove.isEmpty()) {
			for(int job: s_remove) {
				for(int m = 0;m < s_corrente.getSolucao().size();m++) {
					if(m != indice) {
						if(s_corrente.getMaq(m).getMaquina().contains(job)) {
							s_corrente.getMaq(m).getMaquina().remove(s_corrente.getMaq(m).getMaquina().indexOf(job));
							break;
						}
					}
				}
			}
		}
		//System.out.println("retornou solucao: "+s_corrente.makespan());
		return s_corrente;
	}
}
