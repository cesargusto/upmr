package com.upmr.eliteset;

import java.util.ArrayList;
import java.util.Collections;

import com.upmr.core.Solution;

public class EliteSet {

	public static final int list_size = 5;
	protected Solution initial_s;
	protected ArrayList<Solution> elite;//this neighbors have been generated
	protected ArrayList<Integer> makespans_elite;
	protected int worst_makespan;
	
	public EliteSet() {
		this.initial_s = null;
		this.elite = new ArrayList<Solution>(6);
		this.makespans_elite = new ArrayList<Integer>(6);
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
	
	public void remove_worst() {
		
		int pior = Collections.max(this.getMakespans_elite());
		int i_pior = this.makespans_elite.indexOf(pior);
		
		this.elite.remove(i_pior);
	}

	public void setInitial_s(Solution initial_s) {
		this.initial_s = initial_s;
	}

	public Solution getInitial_s() {
		return initial_s;
	}

	
}
