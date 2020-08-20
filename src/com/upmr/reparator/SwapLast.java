package com.upmr.reparator;

import com.upmr.core.Solution;
/**
 * This method is called when the solution have only one job in the target machine
 * and that solution does not feasible
 * @param index
 */
public class SwapLast {
	
	private Solution part_s;
	private PendingJob pending;
	
	public SwapLast(Solution part_s, PendingJob pending) {
		this.part_s = part_s;
		this.pending = pending;
	}
	
	public Solution swap_last_pending(int index) throws CloneNotSupportedException {
		Solution s_better = null;
		boolean fact = false;
		if(!this.pending.isEmpty()) {
			s_better = this.part_s.clone();
			int job_target = this.part_s.getMaq(index).getJob(0);
			for(int i = 0 ;i < this.pending.getPending().size();i++) {
				int aux = job_target;
				job_target = this.pending.get_pend_job(i);
				this.pending.getPending().set(i, aux);
				this.part_s.getMaq(index).addJobToMaq(job_target);
				this.part_s.getMaq(index).removeJobToMaq(0);
				
				if(this.part_s.check_feasibility()) {
					if(!fact) {
						s_better = this.part_s.clone();
						fact = true;
					}else {
						int m1 = this.part_s.makespan();
						int m2 = s_better.makespan();
						//if(this.part_s.indice_makespan() < s_better.makespan()) {
						if(m1 < m2) {	
							s_better = this.part_s.clone();
						}
					}
				}
				aux = job_target;
				job_target = this.pending.get_pend_job(i);
				this.pending.getPending().set(i, aux);
				this.part_s.getMaq(index).addJobToMaq(job_target);
				this.part_s.getMaq(index).removeJobToMaq(0);
				
			}
		}
		if(!fact) {
			//System.out.println("IMPOSSIBLE GET FEASIBLE");
			return s_better;
		}else {
			//System.out.println("Got it!! Step #2");
			return s_better;
		}
	}
	public Solution execute(int index) throws CloneNotSupportedException {
		Solution s_better = this.part_s.clone();
		boolean fact = false;
		for(int w = 0;w < this.part_s.getSizeSol();w++) {
			if(w != index) {
				int job1 = this.part_s.getMaq(index).getJob(0);
				int job2 = -1;
				for(int x = 0;x < this.part_s.getMaq(w).getSizeMaq();x++) {
					
					job2 = this.part_s.getMaq(w).getJob(x);
					
					this.part_s.getMaq(index).addJobToMaq(job2);
					this.part_s.getMaq(w).insertJobToMaq(x, job1);
					this.part_s.getMaq(w).removeJobToMaq(x+1);
					this.part_s.getMaq(index).removeJobToMaq(0);
					
					if(this.part_s.check_feasibility()) {
						if(!fact) {
							s_better = this.part_s.clone();
							fact = true;
						}else {
							if(this.part_s.makespan() < s_better.makespan())
								s_better = this.part_s.clone();
						}
					}
					this.part_s.getMaq(index).addJobToMaq(job1);
					this.part_s.getMaq(w).insertJobToMaq(x, job2);
					this.part_s.getMaq(w).removeJobToMaq(x+1);
					this.part_s.getMaq(index).removeJobToMaq(0);
					
				}
			}
		}
		if(fact) {
			//System.out.println("got it!! Step #1");
			return s_better;
		}
		else 
			return swap_last_pending(index);

		}
	}
