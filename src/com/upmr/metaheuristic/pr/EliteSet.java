package com.upmr.metaheuristic.pr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.upmr.core.Solution;

public class EliteSet {
	
	private ArrayList<Solution> elite;
	private ArrayList<Integer> makespans_elite;
	private int makespan_pior;
	private int tamanho_lista;
	private double min_diferenca;
	
	public EliteSet() {
		this.elite = new ArrayList<Solution>(6);
		this.makespans_elite = new ArrayList<Integer>(6);
		this.tamanho_lista = 5;
		this.min_diferenca = 10.0;
	}

	public ArrayList<Integer> getMakespans_elite() {
		this.atualiza_makespans();
		return makespans_elite;
	}

	public void atualiza_makespans() {
		this.makespans_elite.clear();
		for(int i = 0;i < this.elite.size();i++) {
			this.makespans_elite.add(this.elite.get(i).makespan());
		}
	}
	public ArrayList<Solution> getElite() {
		return elite;
	}
	
	public void remove_pior() {
		
		int pior = Collections.max(this.getMakespans_elite());
		int i_pior = this.makespans_elite.indexOf(pior);
		
		this.elite.remove(i_pior);
	}
	
	public void setElite(Solution solution) {

		if(this.elite.isEmpty() || this.elite.size() < this.tamanho_lista) {
			this.elite.add(solution);
			//System.out.println("ADD! first");
		}	
		else {
			int makespan_atual = solution.makespan();
			makespan_pior = Collections.max(this.getMakespans_elite());
			if(makespan_atual < makespan_pior ) {
				if(this.diff(solution)) {
					this.elite.add(solution);
					//System.out.println("ADD!");
					if(this.elite.size() > tamanho_lista) {
						this.remove_pior();
					}	
				}
			}
		}
	}
	
	public Boolean diff(Solution s_1) {
		
		int size = this.getElite().size();
		double diff;
		for(int i = 0;i < size;i++) {
			diff = s_comparation(s_1, this.getElite().get(i));
			if(diff < this.min_diferenca) {
				return false;
			}
		}
		return true;
	}
	
	public double s_comparation(Solution s_guia, Solution s_corrente) {
		
		ArrayList<Integer>d_s_guia = new ArrayList<>();
		ArrayList<Integer>d_s_corrente = new ArrayList<>();
		
		double result = -1.0;
		
		if(s_guia.makespan() != s_corrente.makespan()) {
			for(int x = 0;x < s_guia.getSizeSol();x++) {
				d_s_guia.add(s_guia.getMaq(x).getSizeMaq());
			}
			for(int y = 0;y < s_corrente.getSizeSol();y++) {
				d_s_corrente.add(s_corrente.getMaq(y).getSizeMaq());
			}
			
			for(int i = 0;i < s_guia.getSizeSol();i++) {
				
				int t1 = d_s_guia.get(i);
				int t2 = d_s_corrente.get(i);
				
				int maior = 0;
				int menor = 0;
				
				if(t1 >= t2) {
					maior = t1;
					menor = t2;
					}
				else {
					maior = t2;
					menor = t1;
				}
					
				for(int j = 0;j < maior;j++) {
					
					int v;
					int p;
					
					if(j < menor) {
						p = s_guia.getMaq(i).getJob(j);
						v = s_corrente.getMaq(i).getJob(j);
						if(p == v) {
							d_s_guia.add(-1);//elemntos iguais
							d_s_corrente.add(-1);
						}
						else {
							d_s_guia.add(p);//elementos diferentes
							d_s_corrente.add(v);
						}
					}else {
						if(t1 < t2) {
							p = s_corrente.getMaq(i).getJob(j);
							d_s_corrente.add(p);
						}
						else {
							p = s_guia.getMaq(i).getJob(j);
							d_s_guia.add(p);
						}
					}
				}
			}
			
			int contador = 0;
			
			
			for(int w = s_guia.getSizeSol(); w < d_s_guia.size();w++) {
				if(d_s_guia.get(w).equals(-1))
					contador ++;
			}
			if(contador == 0) {
				result = 100.0;	
			}
				
			else {
				int divisor = d_s_guia.size() - s_guia.getSizeSol();
				result = ((double)contador / divisor) * 100;
				result = 100 - result;
			}
		}
		
		return result;
	}
	
	public Solution change_solution(Solution s, int maq, int elemento, int position) {
		
		int pos_atual = -1;
		
		if(s.getMaq(maq).getMaquina().contains(elemento)) {
			pos_atual = s.getMaq(maq).getMaquina().indexOf(elemento);

			if(pos_atual != position) {
				if(position < s.getMaq(maq).getSizeMaq()) {
					s.getMaq(maq).removeJobToMaq(pos_atual);
					s.getMaq(maq).insertJobToMaq(position, elemento);
				}else {
					s.getMaq(maq).addJobToMaq(elemento);
					s.getMaq(maq).removeJobToMaq(pos_atual);
				}
			}
		}else {
			if(position < s.getMaq(maq).getSizeMaq())
				s.getMaq(maq).insertJobToMaq(position, elemento);
			else {
				s.getMaq(maq).addJobToMaq(elemento);
			}
				
			for(int i = 0;i < s.getSizeSol();i++) {
				if(i != maq) {
					if(s.getMaq(i).getMaquina().contains(elemento)) {
						pos_atual = s.getMaq(i).getMaquina().indexOf(elemento);
						s.getMaq(i).removeJobToMaq(pos_atual);
						if(s.getMaq(i).getSizeMaq() == 0) {
							s.corrige_nulidade(i);
							//System.out.println("---- Nulidade corrigida");
						}
					}
				}
			}
		}
		return s;
	}
	
	public Solution getRandomSolElite() {
		return elite.get(new Random().nextInt(5));
	}
	
	public float prob_pr() {
		return new Random().nextFloat();
	}
}
