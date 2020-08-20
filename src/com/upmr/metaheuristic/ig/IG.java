package com.upmr.metaheuristic.ig;

import java.util.Random;

import com.upmr.core.Solution;
import com.upmr.experiment.BestResults;
import com.upmr.localsearch_2.VND;

public class IG {


	public int d_max(Solution s) {
		return (int)Math.floor(s.getArquivo().getN_jobs()/7);
	}
	
	public double valor_T(Solution s, double T) {

		int n = s.getArquivo().getN_jobs();
		int m = s.getArquivo().getN_maqs();
		int sum = 0;
		
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				sum += s.getArquivo().getT_exec(i, j);
			}
		}

		return T * sum/(n * m * 10);
	}

	public Solution run(Solution s, double T, int ig_max, BestResults br) throws CloneNotSupportedException {
		
		ConstDest cd = new ConstDest();
		double valor_T = valor_T(s, T);
		
		VND vnd = new VND();
		s = vnd.execute_vnd(s);

		s.repair_solution();

		Solution s_best = s;

		Solution s_part;
		Solution s_pivot;
		int fo_s_best = s_best.makespan();
		int fo_s_part = -1;
		int fo_s = -1;
		int d = d_max(s);
		int t = 1;
		int iter = 0;
		while(iter < ig_max) {
			s_pivot = s.clone();
			s_part = cd.run(s_pivot, t);
			s_part = vnd.execute_vnd(s_part);
			s_part.repair_solution();
			fo_s_part = s_part.makespan();
			fo_s = s.makespan();
			if(fo_s_part < fo_s) {
				t = 1;
				s = s_part.clone();
				fo_s = s.makespan();
				if(fo_s < fo_s_best) {
					s_best = s.clone();
					fo_s_best = s_best.makespan();
					//System.out.println("Melhora IG: "+s.makespan());
				}
			}
			else {
				t = t + 1;
				if (t > d)
					t = 1;

				long Alfa = fo_s_part - fo_s;

				Random rnd = new Random();
				Double x = rnd.nextDouble();
				Double exp = Math.pow(Math.E, (-1*Alfa)/valor_T);

				if(x <= exp) 
					s = s_part.clone();
			}	
		
			iter = iter + 1;
		}
		br.setBest_list(fo_s_best);
		return s_best;
	}

}
