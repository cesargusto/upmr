package com.upmr.reparator;


import java.util.ArrayList;
import java.util.List;

public class PendingJob {
	private List<Integer>pending;
	
	public PendingJob() {
		this.pending = new ArrayList<>();
	}

	public List<Integer> getPending() {
		return pending;
	}

	public void setPending(Integer pending) {
		this.pending.add(pending);
	}
	
	public void remove(int i) {
		this.pending.remove(i);
	}
	
	public boolean isEmpty() {
		if(this.pending.isEmpty())
			return true;
		else
			return false;
	}
	
	public int get_pend_job(int i) {
		return this.pending.get(i);
	}
	
	public List<Integer> get_pend_job() {
		return this.pending;
	}
	

}
