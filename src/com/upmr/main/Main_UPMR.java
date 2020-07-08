package com.upmr.main;

import java.io.IOException;

import com.upmr.core.Solution;
import com.upmr.experiment.BestResults;
import com.upmr.instances.Instance;
import com.upmr.metaheuristic.sa.SA;

public class Main_UPMR {

	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		
		//String path = "experiment_instances/8x2_1_JobCorre_R_uni_.txt";
		
		String path = "instances/25x6_1_U_1_100__R_inter_.txt";
		//String path = "experiment_instances/upmr/8x2_4_JobCorre_R_inter_.txt";
		
		
		Instance inst = new Instance(path);
		
		inst.print_exec_time();
		
		inst.print_resources();
		
		/*
		Machine mc1 = new Machine();
		Machine mc2 = new Machine();
		
		mc1.addJobToMaq(3);
		mc1.addJobToMaq(6);
		mc1.addJobToMaq(1);
		mc1.addJobToMaq(7);
		
		mc2.addJobToMaq(0);
		mc2.addJobToMaq(5);
		mc2.addJobToMaq(2);
		mc2.addJobToMaq(4);
		
		Solution s = new Solution(inst);
		s.setMaqSolucao(mc1);
		s.setMaqSolucao(mc2);*/
		
		
		
		Solution s = new Solution(inst);
		s.ConstroiSolution(false);
		s.print_solution();
		s.repair_solution();
		s.print_solution();
		
		//Non_int_LocalSearch no_ls = new Non_int_LocalSearch();
		//s = no_ls.execute_nonIntLS(s);
		
		//VND vnd = new VND();
		//s = vnd.execute_vnd(s);
		BestResults br = new BestResults();
		SA sa = new SA(s, 1000, br);
		s = sa.execute_sa();
		//s.check_feasibility();
		
		s.print_solution();
		
		
		
		//System.out.println(s.prev_res(0));
		//System.out.println(s.getPending_list());
		//System.out.println(s.getResource_curve());
		
		
		//Repairing Mecanism
		

	}

}
