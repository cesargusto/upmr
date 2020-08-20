package com.upmr.c_localsearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.upmr.core.Solution;
import com.upmr.eliteset.EliteSet;

public class EliteSetLocalSearch extends EliteSet{
	
	public EliteSetLocalSearch() {
		super();
	}

	public void setElite(Solution solution) {

		if(super.elite.isEmpty() || super.elite.size() < list_size) {
			super.elite.add(solution);
			//System.out.println("ADD! first");
		}	
		else {
			int makespan_atual = solution.makespan();
			worst_makespan = Collections.max(super.getMakespans_elite());
			if(makespan_atual < worst_makespan) {
				super.elite.add(solution);
				if(super.elite.size() > list_size)
					super.remove_worst();
			}
		}
	}
	
	public Solution elect_best() {
		List<Integer>fos = new ArrayList<>();
		if(!getElite().isEmpty()) {
			for (Solution s : getElite()) {
				//s.print_solution();
				s.repair_solution();
				//s.print_solution();
				fos.add(s.makespan());
			}
			int Best = Collections.min(fos);			
			return getElite().get(fos.indexOf(Best));
		}
		else
			return null;
	}

}
