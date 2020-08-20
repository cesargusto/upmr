package com.upmr.c_localsearch;

import java.io.IOException;

import com.upmr.core.Solution;
import com.upmr.instances.Instance;

public class Play {

	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		//String path = "instances/8x2_1_JobCorre_R_uni_.txt";

		//String path = "instances/8x4_1_U_1_100__R_uni_.txt";
		String path = "instances/30x4_3_U_100_200__R_uni_.txt";

		//String path = "experiment_instances/upmr/8x2_4_JobCorre_R_inter_.txt";
		
		
		Instance inst = new Instance(path);
		
		inst.print_exec_time();
		
		inst.print_resources();
		
		Solution sol = new Solution(inst);
		sol.ConstroiSolution();
		sol.print_solution();
		sol.repair_solution();
		sol.print_solution();
		VND vnd = new VND();
		vnd.execute_vnd(sol).print_solution();
		

	}

}
