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
import java.util.Random;

import com.upmr.core.Machine;
import com.upmr.instances.Instance;

public class Solution implements Cloneable{
	
	private Instance arquivo;
	private List<Machine> solucao;
	private List<Integer>resource_curve;
	private List<Integer>pending_list;
	
	public Solution(){
		this.solucao = new ArrayList<>();
		this.pending_list = new ArrayList<>();
	}
	public Solution(Instance arq){
		this.arquivo = arq;
		this.solucao = new ArrayList<>();
		this.pending_list = new ArrayList<>();
	}
	public Solution(Machine maq){
		this.solucao = new ArrayList<>();
		this.solucao.add(maq);
	}
	
	@Override
	public Solution clone() throws CloneNotSupportedException {
		Solution solCp = new Solution(arquivo);
		int tam = solucao.size();
		for(int i = 0;i < tam;i++){
			solCp.solucao.add(new Machine());
			int tam_mq = this.solucao.get(i).getSizeMaq();
			for(int j = 0;j < tam_mq;j++){
				int elemento = this.solucao.get(i).getJob(j);
				solCp.getMaq(i).addJobToMaq(elemento);
			}
		}
		return solCp;
	}
	
	public void construction_greedy(){
		
		int num_maq = arquivo.getN_maqs();
		int num_jobs = arquivo.getN_jobs();
		int value = Integer.MAX_VALUE;
		int position_maq = -1;
		int position_job = -1;
		int aux = Integer.MAX_VALUE;
		
		for(int x = 0;x < num_maq;x++){
			this.setMaqSolucao(new Machine());
		}
		
		for(int i = 0;i < num_jobs;i++){
			for(int j = 0;j < num_maq;j++){
				aux = arquivo.getT_exec(j, i);
				if(aux < value){
					value = aux;
					position_maq = j;
					position_job = i;
				}
			}
			this.solucao.get(position_maq).addJobToMaq(position_job);
			aux = Integer.MAX_VALUE;
			value = Integer.MAX_VALUE;
		}
	}
	
	public void ConstroiSolution(){
		List<Integer> candidatos = new ArrayList<>(arquivo.getN_jobs());
		for(int i = 0;i < arquivo.getN_jobs();i++){
			candidatos.add(i);
		}
		for(int x = 0;x < arquivo.getN_maqs();x++){
			this.setMaqSolucao(new Machine());
		}
		
		int k = 0;
		while(k < arquivo.getN_jobs()){
			for(int j = 0;j < arquivo.getN_maqs();j++){
				this.solucao.get(j).addJobToMaq(candidatos.get(k));
				k++;
				if(k >= arquivo.getN_jobs())
					break;
			} 
		}
	}

	public void ConstroiSolution(Boolean random){
		List<Integer> candidatos = new ArrayList<>(arquivo.getN_jobs());
		for(int i = 0;i < arquivo.getN_jobs();i++){
			candidatos.add(i);
		}
		for(int x = 0;x < arquivo.getN_maqs();x++){
			this.setMaqSolucao(new Machine());
		}
		
		if(random)
			Collections.shuffle(candidatos);
		
		int k = 0;
		while(k < arquivo.getN_jobs()){
			for(int j = 0;j < arquivo.getN_maqs();j++){
				this.solucao.get(j).addJobToMaq(candidatos.get(k));
				k++;
				if(k >= arquivo.getN_jobs())
					break;
			} 
		}
	}

	public int makespan(){
		int mspan = 0;
		List<Integer>mspans = new ArrayList<>(arquivo.getN_maqs());
		
		for(int t = 0;t < arquivo.getN_maqs();t++)
			mspans.add(this.getMaq(t).tempoMaq(arquivo, t));

		mspan = Collections.max(mspans);
		
		return mspan;
	}
	
	public int indice_makespan(){
		int makespan = this.makespan();
		int index = -1;
		if(Tempos().contains(makespan))
			index = Tempos().indexOf(makespan);

		return index;
	}
	
	/*
	 * Esta função recebe a lista de indices de maquinas alteradas pelo movimeto e a 
	 * lista de makespan anterior, o calculo é feito apenas nas máquinas alteradas
	 * e as duas listas são mescladas e retornadas. Com isso o makespan não é recalculado
	 * completamente, Esta mesma ideia deverá ser aplicada de forma análoga no calculo de 
	 * tempo da máquina
	 * 
	 */
	public ArrayList<Integer> makespan(ArrayList<Integer>tempos, ArrayList<Integer> alter_maqs){
		int mspan = 0;
		ArrayList<Integer>mspans = tempos;
		for(int t = 0;t < alter_maqs.size();t++){
			mspans.set(alter_maqs.get(t), this.getMaq(alter_maqs.get(t)).tempoMaq(arquivo, alter_maqs.get(t)));
		}
		//mspan = Collections.max(mspans);
		return mspans;
	}
	
	public List<Integer> Tempos(){
		
		List<Integer>tempos = new ArrayList<>(arquivo.getN_maqs());
		
		for(int t = 0;t < arquivo.getN_maqs();t++){
			tempos.add(this.getMaq(t).tempoMaq(arquivo, t));
		}
		
		return tempos;
	}
	
	public List<Integer> maior_menor(){
		List<Integer> maior_menor = new ArrayList<>();
		int indice_menor = -1;
		int indice_maior = -1;
		int valor_menor = Integer.MAX_VALUE;
		int valor_maior = Integer.MIN_VALUE;
		int aux = 0;
		
		for(int i = 0;i < arquivo.getN_maqs();i++){
			aux = this.getMaq(i).tempoMaq(arquivo, i);
			if(aux < valor_menor){
				valor_menor = aux;
				indice_menor = i;
				if(valor_menor > valor_maior){
					valor_maior = valor_menor;
					indice_maior = i;
					}
			}
			if(aux > valor_maior){
				valor_maior = aux;
				indice_maior = i;
				if(valor_maior < valor_menor){
					valor_menor = valor_maior;
					indice_maior = i;
				}
			}
		}
		maior_menor.add(indice_menor);
		maior_menor.add(valor_menor);
		maior_menor.add(indice_maior);
		maior_menor.add(valor_maior);
		
		return maior_menor;
	}
	/**
	 * 
	 * Métodos: verifica_nulidade e corrige_nulidade
	 * implementados em: 5/11/17
	 * Objetivos: Verificar e corrigir problemas de máquinas sem alocação de tarefas.
	 *  
	 */
	public void verifica_nulidade(){
		for(int t = 0;t < arquivo.getN_maqs();t++){
			if(this.getMaq(t).getSizeMaq() == 0){
				this.corrige_nulidade(t);
			}
		}
	}
	
	public void corrige_nulidade(int indice){
		Random rnd = new Random();
		int indice_mkspan = this.maior_menor().get(2);
		int size_mkspan = this.getMaq(indice_mkspan).getSizeMaq();
		int pos = rnd.nextInt(size_mkspan);
		this.getMaq(indice).addJobToMaq(this.getMaq(indice_mkspan).getJob(pos));
		this.getMaq(indice_mkspan).removeJobToMaq(pos);
	}
	
	/**
	 * Algorítmo criado na manhã do dia 19 de Maio de 2020 (Quarentena)
	 * Método que verifica a factibilidade da solução. 
	 * Consiste em a partir de uma matriz de unidade de tempo
	 * guardar para cada unidade o quanto de recurso utilizado 
	 * por cada máquina. A soma das linha dessa matriz resultará
	 * em um vetor onde se houver alguma elemento maior que R_max
	 * a solução não é factivel e caso contrário ela será factivel
	 *  
	 * @return Vetor de somatório
	 */
	
	public ArrayList<ArrayList<Integer>> generate_M(){
		//loop na solução
		int exec = 0;
		int rec = 0;
		int job = -1;

		ArrayList<ArrayList<Integer>>matrix = new ArrayList<>();
		ArrayList<Integer>main;
		List<Integer>aux = new ArrayList<>();
		for(int i = 0;i < arquivo.getN_maqs();i++){
			int tam_mq = this.solucao.get(i).getSizeMaq();
			main = new ArrayList<>();
			for(int j = 0;j < tam_mq;j++){
				exec = this.get_T_exec(i, j);
				job = this.solucao.get(i).getJob(j);
				for(int x = 0;x < exec;x++) {
					aux.add(job);
				}
				main.addAll(aux);
				aux.clear();
			}
			matrix.add(main);
		}
		
		return matrix;
	}

	public ArrayList<Integer> sum_M(ArrayList<ArrayList<Integer>> M){
		
		ArrayList<Integer> soma = new ArrayList<Integer>();

		int valor = 0;
		int coluna = 0;
		boolean triger = true;
		
		while(triger){
			for (int i = 0; i < M.size(); i++) {
				if(coluna < M.get(i).size()) { 
					valor = valor + this.get_Resource_d(i, M.get(i).get(coluna));
				}
			}
			if(valor == 0) {
				triger = false;
			}
			else {
				soma.add(valor);
				valor = 0;
				coluna = coluna + 1;
			}
		}
		return soma;
	}
	
	public void generate_curve() {
		this.resource_curve = this.reduce_M(this.matrix_sum_only(this.generate_M()));
	}
	
	public boolean check_feasibility() {
		ArrayList<ArrayList<Integer>> M = this.generate_M();
		ArrayList<Integer> soma = new ArrayList<Integer>();

		int valor = 0;
		int coluna = 0;
		
		boolean triger = true;
		
		while(triger){
			for (int i = 0; i < M.size(); i++) {
				if(coluna < M.get(i).size()) {
					valor = valor + this.get_Resource_d(i, M.get(i).get(coluna));
					if(valor > this.arquivo.getR_max()) {
						return false;
					}
				}
			}
			if(valor == 0) {
				triger = false;
			}
			else {
				soma.add(valor);
				valor = 0;
				coluna = coluna + 1;
			}
		}
		return true;
	}
	
	public ArrayList<Integer> matrix_sum_only(ArrayList<ArrayList<Integer>> M){
		
		ArrayList<Integer> soma = new ArrayList<Integer>();

		int valor = 0;
		int coluna = 0;
		
		boolean triger = true;
		
		while(triger){
			for (int i = 0; i < M.size(); i++) {
				if(coluna < M.get(i).size()) {
					valor = valor + this.get_Resource_d(i, M.get(i).get(coluna));
				}
			}
			if(valor == 0) {
				triger = false;
			}
			else {
				soma.add(valor);
				valor = 0;
				coluna = coluna + 1;
			}
		}
				
		return soma;
	}
	
	public ArrayList<Integer> matrix_sum(ArrayList<ArrayList<Integer>> M){
		
		ArrayList<Integer> soma = new ArrayList<Integer>();
		ArrayList<Integer> remove = new ArrayList<Integer>();
		
		remove.add(0, -2);//full solution factivel
		remove.add(1, -1);
		remove.add(2, -1);
		remove.add(3, -1);

		int valor = 0;
		int coluna = 0;
		int i_rec = 0;
		int coluna_maior = 0;
		int job_maior = -1;
		
		//get the sum of matrix M
		//get the max column index
		//back to M and find the major resource job
		//move the job for pending jobs list
		//generate other matrix M and repeat the rolle process
		
		boolean triger = true;
		
		while(triger){
			for (int i = 0; i < M.size(); i++) {
				if(coluna < M.get(i).size()) {
					if(coluna == 0) {
						i_rec = 0;
					}
					else{
						if((M.get(i).get(coluna)) != (M.get(i).get(coluna-1))) {
							i_rec++;
						}
					} 
					valor = valor + this.get_Resource_d(i, M.get(i).get(coluna));
					if(valor > this.arquivo.getR_max()) {
						if(valor > remove.get(3)) {
							remove.set(0, i); //machine index
							remove.set(1, this.solucao.get(i).getMaquina().indexOf(M.get(i).get(coluna))); //job index *** THROUBLE HERE ****
							remove.set(2, M.get(i).get(coluna)); //job number
							remove.set(3, valor); //resource value
						}
					}
				}
			}
			if(valor == 0) {
				triger = false;
			}
			else {
				soma.add(valor);
				valor = 0;
				coluna = coluna + 1;
			}
		}
		
		int pos_col = soma.indexOf(Collections.max(soma));
		int job = -1;
		int resource = -1;
		int major_resrouce = 0;
		int major_job = -1;
		
		for (int i = 0; i < M.size();i++) {
			job = M.get(i).get(pos_col);
			resource = this.get_Resource_d(i, M.get(i).get(pos_col));
		
			if(resource > major_resrouce) {
				major_resrouce = resource;
				major_job = job;
			}
		}
		
		return remove;
	}
	
	public ArrayList<Integer> get_major(ArrayList<ArrayList<Integer>> M){
		
		ArrayList<Integer> soma = new ArrayList<Integer>();
		ArrayList<Integer> major = new ArrayList<Integer>();

		int valor = 0;
		int coluna = 0;
		
		boolean triger = true;
		
		while(triger){
			for (int i = 0; i < M.size(); i++) {
				if(coluna < M.get(i).size()) {
					valor = valor + this.get_Resource_d(i, M.get(i).get(coluna));
				}
			}
			if(valor == 0) {
				triger = false;
			}
			else {
				soma.add(valor);
				valor = 0;
				coluna = coluna + 1;
			}
		}
		
		int max_resource = Collections.max(soma);
		
		if(max_resource > this.getArquivo().getR_max()) {
			int pos_col = soma.indexOf(max_resource);
			
			int job = -1;
			int resource = -1;
			int major_resrouce = 0;
			int major_job = -1;
			int major_indice = -1;
			
			int size = M.size();
			for (int i = 0; i < size;i++) {
				if(pos_col <= M.get(i).size() - 1) {
					job = M.get(i).get(pos_col);
					resource = this.get_Resource_d(i, M.get(i).get(pos_col));
				
					if(resource > major_resrouce) {
						major_resrouce = resource;
						major_job = job;
						major_indice = i;
					}
				}
			}
			major.add(major_indice);
			major.add(major_job);
			major.add(major_resrouce);
		}
		else {
			major.add(0, -1);
			major.add(1, -1);
			major.add(2, -1);
		}

		this.resource_curve = this.reduce_M(soma);
				
		return major;
		
	}

	public boolean remove_major() {
		//get_major -> index 0 = machine index
		//			   index 1 = major job
		//			   index 3 = major resource
		ArrayList<Integer> major_list = this.get_major(this.generate_M());
		
		int i_s = 0;  
		//check if list is not empty		
		if(major_list.get(0) >= 0) {
			//check if machine is bigger then one
			if(this.getMaq(major_list.get(0)).getSizeMaq() > 1) {
				//Add the major element on the pending list
				this.setPending_list(major_list.get(1));
				//Get the index of the element on the solution
				i_s = this.getMaq(major_list.get(0)).getMaquina().indexOf(major_list.get(1));
				//Remove the job of the solution
				this.getMaq(major_list.get(0)).removeJobToMaq(i_s);
				//return false until major list get empty
				return false;
			}else {
				//identify makespan machine
				//swap current job with last one of the makespan machine
				//if solution not get feasible then swap with previous one
				this.swap_last(major_list.get(0));
			}
		}
		//major list empty
		//there is no job to remove anymore
		return true;
	}
	/**
	 * This method is called when the solution have only one job in the target machine
	 * and that solution does not feasible
	 * @param index
	 */
	public void swap_last(int index) {
		// faz uma copia da solução para ficar mais facil de desfazer se for necessario
		int index_makespan = this.maior_menor().get(2);
		for (int i = 0; i < this.getMaq(index_makespan).getSizeMaq(); i++) {
			int job1 = this.getMaq(index_makespan).getJob(i);
			int job2 = this.getMaq(index).getJob(0);
			if(index != index_makespan) {
				//take the last job of the makespan and swap
				this.getMaq(index).addJobToMaq(job1);
				//remove the job
				this.getMaq(index_makespan).insertJobToMaq(i, job2);
				this.getMaq(index_makespan).removeJobToMaq(i+1);				
				
				this.getMaq(index).removeJobToMaq(0);
				
			}else {
				Random rnd = new Random();
				int size = this.getSizeSol();
				int other_m = rnd.nextInt(size);
				
				while(other_m == index){
					other_m = rnd.nextInt(size);
				}
				//swap with a pending list element
				this.getMaq(index).addJobToMaq(this.getMaq(other_m).getJob(0));
				int e = this.getMaq(index).getJob(0);
				this.getMaq(index).removeJobToMaq(0);
				this.getMaq(other_m).removeJobToMaq(0);
			
				if(this.check_feasibility()) {
					this.setPending_list(e);
					break;
				}else {
					System.out.println("non-feasible - Implement inner mechanism");
				}
			}
			if(this.check_feasibility())
				break;
			else {
				this.getMaq(index).addJobToMaq(job2);
				this.getMaq(index_makespan).removeJobToMaq(i);
				this.getMaq(index_makespan).insertJobToMaq(i, job1);
				this.getMaq(index).removeJobToMaq(0);
			
			}
		}
	}
	
	public void clean_solution() {
		while(true) {
			if(this.remove_major())
				break;
		}
	}
	
	//Check if the job added made solution unfeasible
	
	public boolean check_add(int i_mac, int job, boolean last) {
		
		ArrayList<ArrayList<Integer>> M = this.generate_M();
		
		int time_job = this.getArquivo().getT_exec(i_mac, job);
		int col_M = M.get(i_mac).indexOf(job);
		int r_sum = 0;

		if(last) {
			int last_job = this.getMaq(i_mac).getLastJob();
			int time_last_job = this.getArquivo().getT_exec(i_mac, last_job);
			time_job = time_job + time_last_job; 
		}
		
		for (int i = col_M; i < col_M + time_job; i++) {
			r_sum = 0;
			for(int j = 0; j < this.getSizeSol();j++) {
				if(i >= M.get(j).size())
					break;
				else
					r_sum = r_sum + this.get_Resource_d(j, M.get(j).get(i));
			}
			if(r_sum > this.arquivo.getR_max())
				return false;
		}
		return true;
	}
	
	public boolean add_job(boolean M_m) {
		boolean key = false;
		if(!this.getPending_list().isEmpty()) {
			
			int i_m = -1;
			
			if(M_m)
				i_m = this.maior_menor().get(0); //smaller machine index
			else
				i_m = this.maior_menor().get(2); //bigger machine index
			
			int job = this.getPending_list().get(0); //pending job
			
			//resource of the last job
			int prev_res = this.getPrev_res(i_m);
			//resource of the job in the new position
			int actual_res = this.get_Resource_d(i_m, job); 
			
			if(prev_res < actual_res) {
				
				this.getMaq(i_m).addJobToMaq(job);
				
				int size_maq = this.getMaq(i_m).getSizeMaq();
				int l_job = size_maq - 1;
				int a_job = size_maq - 2;
				
				this.getMaq(i_m).trocaJob(l_job, a_job);
				
				//check feasible of the job adition
				if(this.check_add(i_m, job, true)) {
					this.pending_list.remove(0);
					key = true;
				}else {
					//swap undone 
					this.getMaq(i_m).trocaJob(l_job, a_job);
					//check if feasible
					if(this.check_add(i_m, job, false)) {
						this.pending_list.remove(0);
						key = true;
					}else {
						//return false
						this.getMaq(i_m).removeLastJob();
						key = false;
					}
				}
				
			}else {
				this.getMaq(i_m).addJobToMaq(job);
				
				if(this.check_add(i_m, job, false)) {
					this.pending_list.remove(0);
					key = true;
				}else {
					this.getMaq(i_m).removeLastJob();
					key = false;
				}
			}
		}
		return key;
	}
	
	public void add_job() {
		
		//ArrayList<ArrayList<Integer>> M = this.generate_M();
		
		if(!this.getPending_list().isEmpty()) {
			
			int menor = this.maior_menor().get(0); //smaller machine index
			int maior = this.maior_menor().get(2); //bigger machine index
			
			int job = this.getPending_list().get(0); //pending job
			
			int prev_res = this.getPrev_res(menor);
			int actual_res = this.get_Resource_d(menor, job);
			
			this.getMaq(menor).addJobToMaq(job);

			if(prev_res < actual_res) {
				int size_maq = this.getMaq(menor).getSizeMaq();
				int l_job = size_maq - 1;
				int a_job = size_maq - 2;
				
				this.getMaq(menor).trocaJob(l_job, a_job);
				
				if(this.check_add(menor, job, true)) {
					this.pending_list.remove(0);
				}else {
					//swap again 
					this.getMaq(menor).trocaJob(l_job, a_job);
					//check if feasible
					if(this.check_add(menor, job, false)) {
						this.pending_list.remove(0);
					}else {
						//remove the job
						this.getMaq(menor).removeLastJob();
						// try on the bigger one
						this.getMaq(maior).addJobToMaq(job);
						if(this.check_add(maior, job, false)) {
							this.pending_list.remove(0);
						}else {
							this.getMaq(maior).removeLastJob();
						}
					}
				}
				
			}else {
				this.getMaq(maior).addJobToMaq(job);
				
				if(this.check_add(maior, job, false)) {
					this.pending_list.remove(0);
				}else {
					this.getMaq(maior).removeLastJob();
				}
			}
		}
	}

	public void repair_solution() {
		try {
			if(!this.check_feasibility()){
				this.clean_solution();
				this.add_pending_job();
			}
			
		} catch (Exception e) {
			System.out.println("An exception was captured!");
		}
	}
	
	//start the repairing process
	public void add_pending_job() {
		while(!this.pending_list.isEmpty()) {
			if(!this.add_job(true))		//try to put on smaller machine
				this.add_job(false);	//try to put on the bigguer machine
			
		}
	}
	
	public void add_pending_job_2() {
		while(!this.pending_list.isEmpty()) {
			this.add_job();
				
			
		}
	}
	//Method used to make the swap of the las job
	//this method return the last resource of the last job
	public int getPrev_res(int maq) {
		int prev = this.getMaq(maq).getLastJob();
		return this.get_Resource_d(maq, prev);
	}
	
	public ArrayList<Integer>reduce_M(ArrayList<Integer> mat){
		ArrayList<Integer> reduced = new ArrayList<>();
		for (Integer valor : mat) {
			if(reduced.isEmpty())
				reduced.add(valor);
			else {
				if(reduced.get(reduced.size()-1) != valor)
					reduced.add(valor);
			}
		}
		return reduced;
	}

	public List<Integer> getPending_list() {
		return pending_list;
	}
	
	public void setPending_list(int job) {
		this.pending_list.add(job);
	}
	
	public List<Integer> getResource_curve() {
		if(this.resource_curve == null) {
			this.generate_curve();
		}
		return resource_curve;
	}
	
	public List<Machine> getSolucao() {
		return solucao;
	}
	
	public int getSizeSol(){
		return this.solucao.size();
	}
	
	public Machine getMaq(int i) {
		return solucao.get(i);
	}
	
	public void setMaqSolucao(Machine maq) {
		this.solucao.add(maq);
	}
	
    public void swap_Machine(int i, int j){
    	Collections.swap(this.solucao, i, j);
    }
	
	public Instance getArquivo() {
		return arquivo;
	}
	
	public void setArquivo(Instance arquivo) {
		this.arquivo = arquivo;
	}
	
	public int get_T_exec(int i_maq, int i_job){
		return this.arquivo.getT_exec(i_maq, this.getMaq(i_maq).getJob(i_job));
	}
	public int get_Resource(int i_maq, int i_job){
		return this.arquivo.get_resource(i_maq, this.getMaq(i_maq).getJob(i_job));
	}
	
	public int get_Resource_d(int i_maq, int i_job){
		return this.arquivo.get_resource(i_maq, i_job);
	}
	
	public void print_solution(){
		System.out.println();
		for(int x = 0;x < arquivo.getN_maqs();x++){
			System.out.printf("Maq-%d\tTempo: %d\n",
					x,this.solucao.get(x).tempoMaq(arquivo, x));
			for(int y = 0;
					y < this.getMaq(x).getSizeMaq();
					y++){
				System.out.printf("%d ",this.solucao.get(x).getJob(y));
			}
			//System.out.printf("\nTempo:\t%d \n",this.solucao.get(x).tempoMaq(arquivo, x));
			System.out.println();
		}
		System.out.println("Makespan .........: "+this.makespan());
		System.out.println("\n"+this.getResource_curve());
		System.out.println();
	}
	
	public void print_makespan(){
		System.out.println("Makespan .........: "+this.makespan());
	}

}
