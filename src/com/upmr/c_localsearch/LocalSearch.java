package com.upmr.c_localsearch;

import com.upmr.core.Solution;

public class LocalSearch {
	private Solution s;
	private Neighborhood strategy;
	
	public LocalSearch(Solution s, Neighborhood strategy) {
		super();
		this.s = s;
		this.strategy = strategy;
	}
	
	//Right here is happening the swap of the strategys
	public Solution find_best_neighbor() throws CloneNotSupportedException {
		return this.strategy.run_moviment(s);
	}
	
	

}
