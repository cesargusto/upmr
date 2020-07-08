/**
 * Classe criada com base na classe Solution do upmsp
 * refatorada com alterações para atender as restrições
 * do problema UPMR
 * Criação em 10 de Abr de 2020
 * 
 * @author cesar
 * 
 * **/
package com.upmr.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.upmr.instances.Instance;

public class Machine {
	private List<Integer> machine;
	
	public Machine(){
		this.machine = new ArrayList<Integer>();
	}

	//Compute the time execution by Streams.
	public int tempo_maq_2(Instance arq, int nmaq) {
		return this.machine.stream()
		.mapToInt(e -> arq.getT_exec(nmaq, e)).sum();
		
	}
	
	//Compute the time execution of the whole machine
	public int tempoMaq(Instance arq, int nummaq){
		
		int tempo_total = 0;
		int tempo_exec = 0;
		int maq_size = this.machine.size(); 
		
		if(maq_size > 0) {
			for(int t = 0;t < maq_size;t++){
				tempo_exec = arq.getT_exec(nummaq, this.getJob(t));
				tempo_total = tempo_total + tempo_exec;
			}
		}
		return tempo_total;
	}
	
	
	public int getSizeMaq() {
		return machine.size();
	}
	
	public List<Integer> getMaquina() {
		return machine;
	}

	public int getJob(int i){
		return this.machine.get(i);
	}

    public void addJobToMaq(Integer job) {
        this.machine.add(job);
    }
	
	public void setJobMaq(int pos, int job) {
		this.machine.add(pos, job);
	}
	
    public void setJobToMaq(int i, Integer job) {
        this.machine.set(i, job);
    }
    
    public void insertJobToMaq(int i, Integer job) {
        this.machine.add(i, job);
    }
	
    public void removeJobToMaq(int pos){
		this.machine.remove(pos);
	}
    public void removeLastJob() {
        this.machine.remove(this.machine.size() - 1);
    }
    
    public int getLastJob() {
        return this.machine.get(this.machine.size() - 1);
    }

	public void trocaJob(int i, int j){
		Collections.swap(this.machine, i, j);
	}
}
