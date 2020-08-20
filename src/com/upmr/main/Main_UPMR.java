package com.upmr.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.upmr.core.Machine;
import com.upmr.core.Solution;
import com.upmr.experiment.BestResults;
import com.upmr.instances.Instance;
import com.upmr.metaheuristic.grasp.GC;
import com.upmr.metaheuristic.grasp.Grasp;
import com.upmr.metaheuristic.grasp.R_grasp_pr;
import com.upmr.metaheuristic.ig.IG;
import com.upmr.metaheuristic.ils.Ils;
import com.upmr.metaheuristic.pr2.EliteSet;
import com.upmr.metaheuristic.pr2.PR;
import com.upmr.metaheuristic.sa.SA;
import com.upmr.metaheuristic.vns.Vns;
import com.upmr.reparator.PendingJob;
import com.upmr.reparator.SwapLast;

public class Main_UPMR {

	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		
		//String path = "instances/8x2_1_JobCorre_R_uni_.txt";
		String path = "instances/presentation.txt";
		
		//String path = "instances/8x4_1_U_1_100__R_uni_.txt";
		//String path = "instances/30x4_3_U_100_200__R_uni_.txt";
		//String path = "instances/Large/50x10_1_JobCorre_R_inter_.txt";
		//String path = "instances/8x2_4_JobCorre_R_inter_.txt";
		
		
		Instance inst = new Instance(path);
		
		inst.print_exec_time();
		
		inst.print_resources();
		
		Solution sol = new Solution(inst);
		sol.ConstroiSolution();
		//sol.repair_solution();
		
		EliteSet es = new EliteSet();
		BestResults br = new BestResults();
		
		/*
		R_grasp_pr grasp = new R_grasp_pr(inst, 100, br);
		Solution sol = grasp.run();
		sol.print_solution();
		*/
		/*
		Grasp grasp = new Grasp(inst, 300, br);
		Solution sol = grasp.r_grasp();
		sol.print_solution();
		*/
		
		//Ils ils = new Ils(sol, 5000, br, es);
		//sol = ils.execute_ils();
		sol.print_solution();
		
		
		//IG ig = new IG();
		//sol = ig.ig_sa(sol, 0.5, 5000, br);
		//sol.print_solution();
		
		//PR pr = new PR(sol, es);
		//pr.execute_pr().print_solution();
		
		//System.out.println(gc.extremos(IntStream.range(0, inst.getN_jobs()).boxed().collect(Collectors.toList())));
		
		/*
		Machine mc1 = new Machine();
		Machine mc2 = new Machine();
		//Machine mc3 = new Machine();
		//Machine mc4 = new Machine();
		
		mc1.addJobToMaq(1);
		mc1.addJobToMaq(2);
		mc1.addJobToMaq(4);
		mc1.addJobToMaq(3);
		mc1.addJobToMaq(3);
		mc1.addJobToMaq(1);
		
		mc2.addJobToMaq(3);
		mc2.addJobToMaq(1);
		mc2.addJobToMaq(4);
		mc2.addJobToMaq(4);
		mc2.addJobToMaq(1);
		mc2.addJobToMaq(2);
		
		//mc3.addJobToMaq(1);
		
		//mc4.addJobToMaq(15);
		//mc4.addJobToMaq(9);
		//mc4.addJobToMaq(10);
		//mc4.addJobToMaq(12);
		
		Solution s = new Solution(inst);
		s.setMaqSolucao(mc1);
		s.setMaqSolucao(mc2);
		s.setMaqSolucao(mc3);
		s.setMaqSolucao(mc4);
		
		PendingJob pj = new PendingJob();
		pj.setPending(8);
		pj.setPending(11);
		pj.setPending(14);
		pj.setPending(16);
		pj.setPending(17);
		pj.setPending(18);
		pj.setPending(19);
		
		*/
		//BestResults br = new BestResults();
		//Grasp grasp = new Grasp(inst, 0.6, 1000, br);
		//grasp.execute_grasp().print_solution();
		
		//Solution s = new Solution(inst);
		
		/*
		Solution sol = new Solution(inst);
		sol.ConstroiSolution();
		sol.repair_solution();
		sol.print_solution();*/

		//Construct cons = new Construct();
		//cons.run(des.getPartial_s(), des.getGrupo()).print_solution();
		
		//EliteSet es = new EliteSet();
		
		//IG ig = new IG();
		//ig.ig_sa(sol, 30, 400, 2, br).print_solution();
		
		
		/*
		ArrayList<Integer>fo = new ArrayList<>();
		ArrayList<Float> lista = new ArrayList<>();
		for(int j = 2; j < 12;j++) {
			for(int i = 0;i < 11;i++) {
				IG ig = new IG();
				fo.add(ig.ig_sa(sol, 30, 500, j).makespan());
			}
			float media = fo.stream().reduce(0, (a, b)->a+b);
			lista.add(media/fo.size());
			System.out.println(media/fo.size());
			fo.clear();
		}
		
		System.out.println(lista);
		
		*/
		
		//PR pr = new PR(sol_corrente, es);
		//pr.execute_pr().print_solution();
		
		
		//s.ConstroiSolution(false);
		//s.print_solution();
		//s.repair_solution();
		//s.print_solution();
		
		//s.print_solution();
		
		
		//VND vnd = new VND();
		//s = vnd.execute_vnd(s);
		//BestResults br = new BestResults();
		//SA sa = new SA(s, 1000, br);
		//s = sa.execute_sa();
		
		
		
		//Vns vns = new Vns(s, 5000, br);
		//vns.execute_vns(true).print_solution();
		

	}

}
