/***************************************************************
 * Esta classe monta a metaheurística grasp clássica
 * @date 5 de nov de 2017	
 * @author cesar
 */

package com.upmr.metaheuristic.grasp;

import com.upmr.experiment.BestResults;
import com.upmr.instances.Instance;
import com.upmr.c_localsearch.VND;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

//import com.upmsp.structure.Instance;
import com.upmr.core.Solution;

public class Grasp {

	protected float alfa;
	protected int grasp_max;
	protected Instance inst;
	protected GC gc;
	protected VND vnd;
	protected BestResults best_results;
	
	//public static final List<Double>alphas = List.of(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9);
	public static final List<Double>alphas = Arrays.asList(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9);
	public static final int AMP = 10;
	protected List<List<Integer>>FOs;
	protected List<Double>Q_list;
	protected List<Double>P_list;
	protected List<Double>M_list;
	protected Solution s_star;
	protected int fo_star;
	protected int p;
	
	public Grasp(Instance inst, float alfa, int grasp_max, BestResults best_results){
		this.inst = inst;
		this.gc = new GC(this.inst);
		this.vnd = new VND();
		this.best_results = best_results;
		this.alfa = alfa;
		this.grasp_max = grasp_max;
	}
	
	public Grasp(Instance inst, float alfa, BestResults best_results){
		this.inst = inst;
		this.gc = new GC(this.inst);
		this.vnd = new VND();
		this.best_results = best_results;
		this.alfa = alfa;
	}
	
	
	public Solution execute_grasp(long time) throws CloneNotSupportedException{
		Solution s = null;
		Solution s_star = null;
		int fo_melhor = Integer.MAX_VALUE;
		int fo_aux = -1;
		
		long start = 0;
		long end = 0;
		long t = 0;
	
		start = System.currentTimeMillis();
		
		while(t < time){
			s = this.gc.run_gc(this.alfa);
			s = this.vnd.execute_vnd(s);
			fo_aux = s.makespan();
			if(fo_aux < fo_melhor){
				s_star = s.clone();
				fo_melhor = fo_aux;
			}
			
			end = System.currentTimeMillis();
			t = end - start;
		}
		
		this.best_results.setBest_list(fo_melhor);
		return s_star;
	}
	
	public Grasp(Instance inst, int grasp_max, BestResults best_results) {
		this.inst = inst;
		this.gc = new GC(this.inst);
		this.vnd = new VND();
		this.best_results = best_results;
		this.FOs = new ArrayList<>();
		this.Q_list = new ArrayList<>();
		this.P_list = new ArrayList<>();
		this.M_list = new ArrayList<>();
		this.s_star = null;
		this.fo_star = Integer.MAX_VALUE;
		this.grasp_max = grasp_max;
		this.p = grasp_max / 25;
	}
	public Solution c_grasp(double alpha) throws CloneNotSupportedException{
		Solution s = null;
		int fo_aux = -1;
		while(this.grasp_max > 0){
			s = this.gc.run_gc(alpha);
			s = this.vnd.execute_vnd(s);
			fo_aux = s.makespan();
			if(fo_aux < fo_star){
				s_star = s.clone();
				fo_star = fo_aux;
				//System.out.println("Melhora: "+fo_star);
			}
			this.grasp_max --;
		}
		this.best_results.setBest_list(fo_star);
		return s_star;
	}
	
	//initialize equally probability associated each alpha
	public void initialize_rgrasp() {
		for (int i = 0; i < alphas.size(); i++) {
			this.M_list.add(0.0);
			this.Q_list.add(0.0);
			this.P_list.add((double)1/alphas.size());
			this.FOs.add(new ArrayList<>());
		}
	}
	
	public void update_rgrasp() {
		calc_M();
		calc_Q();
		calc_P();
	}
	
	public void calc_P() {
		double p = 0.0;
		for (int j = 0; j < P_list.size(); j++) {
			double sum = Q_list.stream().mapToDouble(val->val).sum();
			if(Q_list.get(j)>0)
				p = Q_list.get(j)/sum;
			else
				p = Math.random()/sum;
			this.P_list.set(j, p);
		}
	}
	
	public void calc_Q() {
		double q = 0.0;
		for (int i = 0; i < Q_list.size(); i++) {
			if(M_list.get(i) > 0) {
				q = fo_star/M_list.get(i);
				q = Math.pow(q, AMP);
				//this.Q_list.set(i, reduct_d(q));
				this.Q_list.set(i, q);
			}else 
				//this.Q_list.set(i, reduct_d(Math.random()));
				this.Q_list.set(i, Math.random());
		}

	}
	
	public void calc_M() {
		for (int i = 0; i < FOs.size(); i++) {
			double avg = FOs.get(i).stream().mapToDouble(v->v).average().orElse(0.0);
			//this.M_list.set(i, reduct_d(avg));
			this.M_list.set(i, avg);
		}
	}
	
	public int choose_alpha() {
		Random rnd = new Random();
		//double r = reduct_d(rnd.nextFloat());
		double r = rnd.nextFloat();
		int chosen_a = 0;
		double sum = 0;
		int count = 0;
		for (Double p : P_list) {
			sum = sum + p;
			
			if(r <= sum) {
				chosen_a = count;
				break;
			}
			count = count + 1;
		}
		return chosen_a;
	}
	
	public double reduct_dx(double value) {
		int decimal = 3;
		BigDecimal bd = new BigDecimal(value).setScale(decimal, RoundingMode.HALF_EVEN);
		return bd.doubleValue();
	}
	
	public Solution r_grasp() throws CloneNotSupportedException {
		Solution s = null;
		int fo_aux = -1;
		
		initialize_rgrasp();

		while(this.grasp_max > 0){
			int i_alpha = choose_alpha(); 
			s = this.gc.run_gc(alphas.get(i_alpha));
			s = this.vnd.execute_vnd(s);
			//s.repair_solution();
			fo_aux = s.makespan();
			this.FOs.get(i_alpha).add(fo_aux);
			if(fo_aux < fo_star){
				s_star = s.clone();
				fo_star = fo_aux;
				System.out.println("Melhora: "+fo_star);
			}
			this.grasp_max --;
			if(grasp_max % p == 0) 
				update_rgrasp();
		}
		this.best_results.setBest_list(fo_star);
		return s_star;
	}
	
	
}
