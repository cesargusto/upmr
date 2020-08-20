package com.upmr.metaheuristic.grasp;

import com.upmr.core.Solution;
import com.upmr.experiment.BestResults;
import com.upmr.instances.Instance;
import com.upmr.metaheuristic.pr2.EliteSet;
import com.upmr.metaheuristic.pr2.PR;

public class R_grasp_pr extends Grasp{
	private EliteSet es;
	private PR pr;

	public R_grasp_pr(Instance inst, int grasp_max, BestResults best_results) {
		super(inst, grasp_max, best_results);
		this.es = new EliteSet();
		this.pr = null;
	}

	public Solution run() throws CloneNotSupportedException {
		Solution s = null;
		int fo_aux = -1;
		
		initialize_rgrasp();

		while(grasp_max > 0){
			int i_alpha = choose_alpha(); 
			s = gc.run_gc(alphas.get(i_alpha));
			s = vnd.execute_vnd(s);
			es.setElite(s);
			if(Math.random() <= 0.1 && es.getElite().size()>=5) {
				pr = new PR(s, es);
				s = pr.run_one_pr();
				//s.repair_solution();
				System.out.println("PR >>>> "+s.makespan());
			}
			fo_aux = s.makespan();
			this.FOs.get(i_alpha).add(fo_aux);
			if(fo_aux < fo_star){
				s_star = s.clone();
				fo_star = fo_aux;
				System.out.println("Melhora: "+fo_star);
			}
			grasp_max --;
			if(grasp_max % p == 0) 
				update_rgrasp();
		}
		best_results.setBest_list(fo_star);
		return s_star;
	}
}
