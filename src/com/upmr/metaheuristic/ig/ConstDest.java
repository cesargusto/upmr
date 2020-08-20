package com.upmr.metaheuristic.ig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.upmr.core.Solution;

public class ConstDest {
	private List<Integer> grupo;
	private Solution partial_s;
	
	public ConstDest() {
		this.grupo = new ArrayList<>();
		this.partial_s = null;//right here
	}
	
	public Solution run(Solution s, int t) throws CloneNotSupportedException {
		this.destruct(s, t);
		this.construct();
		return this.partial_s;
	}
	
	public void destruct(Solution s, int t) throws CloneNotSupportedException {
		Random rnd = new Random();
		
		int maq = -1;
		int job = -1;
		
		for(int i = 0;i < t; i++) {
			maq = rnd.nextInt(s.getArquivo().getN_maqs());
			if(s.getMaq(maq).getSizeMaq() > 1) {
				job = rnd.nextInt(s.getMaq(maq).getSizeMaq());
				this.grupo.add(s.getMaq(maq).getJob(job));
				s.getMaq(maq).removeJobToMaq(job);
			}
		}
		this.partial_s = s.clone();
	}
	
	public void construct() throws CloneNotSupportedException{
		Random rnd = new Random();
		partial_s.repair_solution();
		while(!this.grupo.isEmpty()) {
			int s = rnd.nextInt(grupo.size());
			if(add_one(this.grupo.get(s))!= null)
				this.grupo.remove(s);
			else {
				partial_s.getMaq(partial_s.maior_menor().get(2)).addJobToMaq(this.grupo.get(s));
				this.grupo.remove(s);
			}
		}
		
	}

	
	public Solution add_one(int job) throws CloneNotSupportedException{
		int best = -1;
		Solution s_aux = null;
		Solution s_bst = null;
		if(partial_s.check_feasibility()) {
			for(int i = 0;i < partial_s.getSizeSol();i++) {
				for(int j = 0;j <= partial_s.getMaq(i).getSizeMaq();j++) {
					if(j == partial_s.getMaq(i).getSizeMaq())
						partial_s.getMaq(i).addJobToMaq(job);
					else
						partial_s.getMaq(i).insertJobToMaq(j, job);
					if(i == 0 && j == 0)
						best = partial_s.makespan();
					if(partial_s.check_feasibility()) {
						s_aux = partial_s.clone();
						if(s_aux.makespan() < best) {
							s_bst = s_aux.clone();
							best = s_aux.makespan();
						}
					}
					partial_s.getMaq(i).removeJobToMaq(j);
				}
			}
		}
			
		if(s_bst != null) {
			partial_s = s_bst.clone();
			return partial_s;
		}
		else {
			if(s_aux != null) {
				partial_s = s_aux.clone();
				return partial_s;
			}
			else
				return null;
		}
	}

}
