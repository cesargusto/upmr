package com.upmr.metaheuristic.grasp;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.upmr.core.Machine;
import com.upmr.core.Solution;
import com.upmr.instances.Instance;

public class GC {
	
	private Instance instance;
	private List<Integer> LC = new ArrayList<>();
	private List<Integer> LCR_maq = new ArrayList<>(); //guarda indices das maquinas
	private List<Integer> LCR_job = new ArrayList<>(); //guarda indices dos jobs
	
	public GC(Instance inst){
		this.instance = inst;
	}
	
	public Solution run_gc(double alfa) {
		int n_jobs = this.instance.getN_jobs();
		int n_maqs = this.instance.getN_maqs();
		
		Random rnd = new Random();

		Solution solution = new Solution(instance);

		LC = IntStream.range(0, n_jobs).boxed().collect(Collectors.toList());
				
		for(int x = 0;x < n_maqs;x++)
			solution.setMaqSolucao(new Machine());

		int g_min = -1;
		int g_max = -1;
		
		while(!LC.isEmpty()) {
			
			g_min = extremos(LC).get(0);
			g_max = extremos(LC).get(1);
			
			double valor_grasp;
			valor_grasp = g_min + alfa * (g_max - g_min);
			
			find_better(valor_grasp, true);
			
			if(!LCR_job.isEmpty()) {

				int index = -1;
				if(LCR_job.size() > 1)
					index = rnd.nextInt(LCR_job.size());
				else
					index = 0;
				
				int pos_maq = -1; 
				int job = -1;
				
				pos_maq =LCR_maq.get(index);
				job = LCR_job.get(index);
				
				solution.getMaq(pos_maq).addJobToMaq(job);
				
				LC.remove(LC.indexOf(job));
				LCR_maq.clear();
				LCR_job.clear();
			}			
		}
		//solution.repair_solution();
		return solution;
	}
	
	public int find_b_res(List<Integer>index, int job) {
		int aux = -1;
		List<Integer>col_r = new ArrayList<>();
		List<Integer>col_i = new ArrayList<>();
		for (int i = 0; i < index.size(); i++) {
			aux = this.instance.get_resource(index.get(i), job);
			col_r.add(aux);
			col_i.add(index.get(i));
		}
		int smaller = Collections.min(col_r);
		int better_index_res = col_i.get(col_r.indexOf(smaller)); 
		return better_index_res;
	}
	
	public void find_better(double valor_grasp, boolean res) {
		List<Integer>col_t = new ArrayList<>();
		List<Integer>col_j = new ArrayList<>();
		List<Integer>col_i = new ArrayList<>();
		int b_time = Integer.MAX_VALUE;
		for(int i = 0;i < LC.size();i++){
			for(int j = 0;j < this.instance.getN_maqs();j++){
				b_time = this.instance.getT_exec(j, LC.get(i));
				if(b_time <= valor_grasp){
					col_t.add(b_time);
					col_j.add(LC.get(i));
					col_i.add(j);
				}
			}
			if(!col_t.isEmpty()) {
				if(res) {
					int indice = find_b_res(col_i, LC.get(i));
					LCR_maq.add(indice);
					LCR_job.add(LC.get(i));
				}else {
					int menor = Collections.min(col_t);
					int indice = col_t.indexOf(menor);
					
					LCR_maq.add(col_i.get(indice));
					LCR_job.add(col_j.get(indice));
					
				}
				col_i.clear();
				col_j.clear();
				col_t.clear();
			}
		}
	}
	
	public List<Integer> extremos(List<Integer> lc){
		List<Integer>menor_maior = new ArrayList<>();
		int maior = -1;
		int menor = Integer.MAX_VALUE;
		int value_aux = Integer.MAX_VALUE;
		
		for(int i = 0;i < lc.size();i++){
			for(int j = 0;j < this.instance.getN_maqs();j++){
				value_aux = this.instance.getT_exec(j, lc.get(i));
				if(value_aux > maior){
					maior = value_aux;
				}else if(value_aux < menor){
					menor = value_aux;
				}
			}
		}
		menor_maior.add(menor);
		menor_maior.add(maior);
		
		return menor_maior;
	}

}
