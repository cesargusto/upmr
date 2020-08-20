/********************************************************
 * A Classe de construção grasp consiste em construir uma solução em parte aleatória
 * e em parte gulosa. Esta construção parte de um valor alfa dado que controla as proporções.
 * 
 * Esta construção não considera o tempo de preparação mas apenas o tempo de execução.
 * Uma boa proposta de melhoria seria considerar o também o tempo de preparação.
 * Essa correção é necessária para instancias onde o tempo de preparação e maior que os tempos de execução
 * 
 * @data concluída para primeiro funcionamento em 5 de nov de 2017
 * @author cesar
 * 
 */

package com.upmr.metaheuristic.grasp;

import java.util.ArrayList;
import java.util.Random;

import com.upmr.instances.Instance;
//import com.upmr.structure.Instance;
import com.upmr.core.Machine;
import com.upmr.core.Solution;

public class GraspConstruction {
	
	private Instance instance;
	
	public GraspConstruction(Instance inst){
		this.instance = inst;
	}
	
	public Solution execute_gc(double alfa){

		Random rnd = new Random();
		
		ArrayList<Integer> LC = new ArrayList<>();//lista de jobs ex.: 0, 1, 2, 3..
		ArrayList<Integer> LCR_maq = new ArrayList<>(); //guarda indices das maquinas
		ArrayList<Integer> LCR_job = new ArrayList<>(); //guarda indices dos jobs
		
		Solution solution = new Solution(instance);

		//inicializa lista de candidatos
		for(int t = 0;t < this.instance.getN_jobs();t++){
			LC.add(t);//cria a lista de jobs
		}
		//inicializa solução com maquinas vazias
		for(int x = 0;x < this.instance.getN_maqs();x++){
			solution.setMaqSolucao(new Machine());
		}
		
		int g_min = -1;
		int g_max = -1;
		
		int[]menor_maior = new int[2];
		
		while(!LC.isEmpty()){

			menor_maior = this.extremos(LC);//calcula o maior e menor tempo de execução
			g_min = menor_maior[0];
			g_max = menor_maior[1];
			
			double valor_grasp = Double.MAX_VALUE;
			
			valor_grasp = g_min + alfa * (g_max - g_min);//limitante calculado
			//todo tempo menor que o valor_grasp fará parte do LCR
			int valor_aux = -1;
			//percorre todos os tempos verificando quais entrarão na LCR
			for(int i = 0;i < LC.size();i++){
				for(int j = 0;j < this.instance.getN_maqs();j++){
					valor_aux = this.instance.getT_exec(j, LC.get(i));
					if(valor_aux <= valor_grasp){
						LCR_maq.add(j);
						LCR_job.add(LC.get(i));
						break;
					}
				}
			}
			
			int index = -1;
			//sorteia um elemento da LCR
			if(LCR_job.size() > 1)
				index = rnd.nextInt(LCR_job.size());
			else
				index = 0;
			
			int pos_maq = -1; 
			int job = -1;
			
			pos_maq =LCR_maq.get(index);
			job = LCR_job.get(index);
			//insre o elemento na respectiva maquina onde ele foi alocado
			solution.getMaq(pos_maq).addJobToMaq(job);
			//remove o elemento que foi escolhido da lista de candidatos
			LC.remove(LC.indexOf(job));
			LCR_maq.clear();
			LCR_job.clear();
		}
		//verifica se alguma maquina ficou vazia
		solution.verifica_nulidade();
		return solution;
	}
	
	public int[] extremos(ArrayList<Integer> lc){
		int[]menor_maior = new int[2];
		int maior = -1;
		int menor = Integer.MAX_VALUE;
		int value_aux = Integer.MAX_VALUE;
		
		for(int i = 0;i < lc.size();i++){
			for(int j = 0;j < this.instance.getN_maqs();j++){
				value_aux = this.instance.getT_exec(j, lc.get(i));
				if(value_aux > maior){
					maior = value_aux;
				}else if(value_aux < menor){
					menor = value_aux;
				}
			}
		}
		menor_maior[0] = menor;
		menor_maior[1] = maior;
		
		return menor_maior;
	}

}
