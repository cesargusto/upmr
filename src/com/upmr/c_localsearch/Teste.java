package com.upmr.c_localsearch;

import com.upmr.core.Solution;

public class Teste {
	
	LocalSearch vnd = null;
	public void run(Solution solution) throws CloneNotSupportedException {
		
		SwapIn sw_i = new SwapIn();
		vnd = new LocalSearch(solution, sw_i);
		Solution s1 = vnd.find_best_neighbor();
		s1.print_solution();
		
		SwapOut sw_o = new SwapOut();
		vnd = new LocalSearch(solution, sw_o);
		Solution s2 = vnd.find_best_neighbor();
		s2.print_solution();
	}
	


}
