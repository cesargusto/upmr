/*******************************************************************
 * 
 * Esta classe contem as instruções de chamadas das heurísticas 
 * implementadas. Inicialmente é apontado para o diretórios com as 
 * intancias e são executadas em séries as heurísticas. Os resultados
 * são armazendados em um objeto com os valores de makespan e no final
 * são gravados em arquivo de texto.
 * 
 * Classe criada em: 19 de out 2017
 * 
 * Alteração da classe para rodar todos os algorítmos acessandos parâmetros
 * via properties. A idéia é ligar/desligar metaheurística via properties
 * 
 * Alteração feita em: 26 de abr 2019
 * 
 * @author cesar
 * 
 *******************************************************************/
package com.upmr.experiment;

import java.io.IOException;
import java.util.Properties;

import com.upmr.metaheuristic.grasp.Grasp;
import com.upmr.metaheuristic.ig.IG;
import com.upmr.metaheuristic.ils.Ils;
import com.upmr.metaheuristic.sa.SA;
import com.upmr.metaheuristic.vns.Vns;
import com.upmr.instances.Instance;
import com.upmr.core.Solution;
import com.upmr.util.Calcs;
import com.upmr.util.Parses;
import com.upmr.util.View;

public class ConfExperiment {
	private Properties prop;
	private Calcs calc;
	private int num_exec; //iterações do experimento - 30
	
	public ConfExperiment(Properties prop) {
		this.prop = prop;
		this.calc = new Calcs();
		this.num_exec = Integer.parseInt(this.prop.getProperty("N_EXEC")); //iterações do experimento - 30
		//this.dir = this.prop.getProperty("INSTANCE_PATH"); //caminho das instâncias
	}
	
	public void execute_experiment(String file_name, String dir) throws IOException, CloneNotSupportedException {
		
		String complete_path = dir + file_name;
		Instance inst = new Instance(complete_path);
		Time time;
		
		Solution sol;
		BestResults best_results;
		String algoritmo_name;
		WriteResultsFile write_file;
		
		if(this.prop.getProperty("GRASP").equals("ON")) {
			
			//VNS
			algoritmo_name = "GRASP";
			best_results = new BestResults(this.prop);
			write_file = new WriteResultsFile(best_results, file_name, this.prop);
			time = new Time();

			if(this.prop.getProperty("TIME_EXEC").equals("ON")) {
				
				String ts = this.prop.getProperty("VALUES_T");
				String[] t_s = ts.split(",");
				long limite = 0;
				
				for(int i = 0;i < this.num_exec;i++) {
					for(int j = 0;j < t_s.length;j++) {
						limite = this.calc.tempoExec(Parses.split_nmaq(file_name), Integer.parseInt(t_s[j]));
						sol = new Solution(inst);
						sol.ConstroiSolution();
						Vns vns = new Vns(sol, best_results);
						sol = vns.execute_vns(limite);
						best_results.setValueT(sol.makespan());
					}
					best_results.setTabelaT(best_results.getValueT());
					best_results.clean_valueT();
				}
				best_results.calc_media_valuesT(best_results.soma_valuesT());
				write_file.write_gap(algoritmo_name);
				
			}else {

				int grasp_max = Integer.parseInt(this.prop.getProperty("GRASP_MAX"));//iterações do algoritmo
				//double T = Double.parseDouble(this.prop.getProperty("T"));
				
				long start = 0;
				long end = 0;
				long t = 0;
				
				for(int i = 0;i < num_exec;i++) {
					start = System.currentTimeMillis();
					
					Grasp grasp = new Grasp(inst, grasp_max, best_results);
					grasp.r_grasp();
					
					end = System.currentTimeMillis();
					t = end - start;
					time.setTempo(t);
				}
				write_file.write_gap(algoritmo_name);
			}
		}

		if(this.prop.getProperty("IG").equals("ON")) {
			
			//VNS
			algoritmo_name = "IG";
			best_results = new BestResults(this.prop);
			write_file = new WriteResultsFile(best_results, file_name, this.prop);
			time = new Time();

			if(this.prop.getProperty("TIME_EXEC").equals("ON")) {
				
				String ts = this.prop.getProperty("VALUES_T");
				String[] t_s = ts.split(",");
				long limite = 0;
				
				for(int i = 0;i < this.num_exec;i++) {
					for(int j = 0;j < t_s.length;j++) {
						limite = this.calc.tempoExec(Parses.split_nmaq(file_name), Integer.parseInt(t_s[j]));
						sol = new Solution(inst);
						sol.ConstroiSolution();
						Vns vns = new Vns(sol, best_results);
						sol = vns.execute_vns(limite);
						best_results.setValueT(sol.makespan());
					}
					best_results.setTabelaT(best_results.getValueT());
					best_results.clean_valueT();
				}
				best_results.calc_media_valuesT(best_results.soma_valuesT());
				write_file.write_gap(algoritmo_name);
				
			}else {

				int ig_max = Integer.parseInt(this.prop.getProperty("IG_MAX"));//iterações do algoritmo
				double T = Double.parseDouble(this.prop.getProperty("T"));
				
				long start = 0;
				long end = 0;
				long t = 0;
				
				for(int i = 0;i < num_exec;i++) {
					start = System.currentTimeMillis();
					
					sol = new Solution(inst);
					sol.ConstroiSolution();
					IG ig = new IG();
					sol = ig.run(sol, T, ig_max, best_results);
					
					end = System.currentTimeMillis();
					t = end - start;
					time.setTempo(t);
				}
				write_file.write_gap(algoritmo_name);
			}
		}
		
		if(this.prop.getProperty("VNS").equals("ON")) {
			
			//VNS
			algoritmo_name = "VNS";
			best_results = new BestResults(this.prop);
			write_file = new WriteResultsFile(best_results, file_name, this.prop);
			time = new Time();

			if(this.prop.getProperty("TIME_EXEC").equals("ON")) {
				
				String ts = this.prop.getProperty("VALUES_T");
				String[] t_s = ts.split(",");
				long limite = 0;
				
				for(int i = 0;i < this.num_exec;i++) {
					for(int j = 0;j < t_s.length;j++) {
						limite = this.calc.tempoExec(Parses.split_nmaq(file_name), Integer.parseInt(t_s[j]));
						sol = new Solution(inst);
						sol.ConstroiSolution();
						Vns vns = new Vns(sol, best_results);
						sol = vns.execute_vns(limite);
						best_results.setValueT(sol.makespan());
					}
					best_results.setTabelaT(best_results.getValueT());
					best_results.clean_valueT();
				}
				best_results.calc_media_valuesT(best_results.soma_valuesT());
				write_file.write_gap(algoritmo_name);
				
			}else {

				int vns_max = Integer.parseInt(this.prop.getProperty("VNS_MAX"));//iterações do algoritmo
				
				long start = 0;
				long end = 0;
				long t = 0;
				
				for(int i = 0;i < num_exec;i++) {
					start = System.currentTimeMillis();
					sol = new Solution(inst);
					sol.ConstroiSolution();
					Vns vns = new Vns(sol, vns_max, best_results);
					sol = vns.execute_vns(true);
					end = System.currentTimeMillis();
					t = end - start;
					time.setTempo(t);
				}
				write_file.write_gap(algoritmo_name);
			}
		}
		
		if(this.prop.getProperty("ILS").equals("ON")) {
			//ILS
			algoritmo_name = "ILS";
			best_results = new BestResults(this.prop);
			write_file = new WriteResultsFile(best_results, file_name, this.prop);
			time = new Time();
			
			if(this.prop.getProperty("TIME_EXEC").equals("ON")) {
				String ts = this.prop.getProperty("VALUES_T");
				String[] t_s = ts.split(",");
				long limite = 0;
				
				for(int i = 0;i < this.num_exec;i++) {
					for(int j = 0;j < t_s.length;j++) {
						limite = this.calc.tempoExec(Parses.split_nmaq(file_name), Integer.parseInt(t_s[j]));
						sol = new Solution(inst);
						sol.ConstroiSolution();
						Ils ils = new Ils(sol, best_results);
						sol = ils.execute_ils(limite);
						best_results.setValueT(sol.makespan());
					}
					best_results.setTabelaT(best_results.getValueT());
					best_results.clean_valueT();
				}
				best_results.calc_media_valuesT(best_results.soma_valuesT());
				write_file.write_gap(algoritmo_name);
			}else {
				int ils_max = Integer.parseInt(this.prop.getProperty("ILS_MAX"));
				
				long start = 0;
				long end = 0;
				long t = 0;
				
				for(int i = 0;i < num_exec;i++) {//nºexec vs. Time
					start = System.currentTimeMillis();
					
					sol = new Solution(inst);
					sol.ConstroiSolution();
					Ils ils = new Ils(sol, ils_max, best_results);
					sol = ils.execute_ils();
					
					end = System.currentTimeMillis();
					t = end - start;
					time.setTempo(t);
				}
				write_file.write_gap(algoritmo_name);
			}
		}
		
		if(this.prop.getProperty("SA").equals("ON")) {
			
			//SA
			algoritmo_name = "SA";
			best_results = new BestResults(this.prop);
			write_file = new WriteResultsFile(best_results, file_name, this.prop);
			time = new Time();
			
			if(this.prop.getProperty("TIME_EXEC").equals("ON")) {
				
				String ts = this.prop.getProperty("VALUES_T");
				String[] t_s = ts.split(",");
				long limite = 0;
				
				int sa_max = Integer.parseInt(this.prop.getProperty("SA_MAX"));
				double t_inicial = Double.parseDouble(this.prop.getProperty("SA_T_INICIAL"));
				float alfa = Float.parseFloat(this.prop.getProperty("SA_ALF"));
				
				for(int i = 0;i < this.num_exec;i++) {
					for(int j = 0;j < t_s.length;j++) {
						limite = this.calc.tempoExec(Parses.split_nmaq(file_name), Integer.parseInt(t_s[j]));
						sol = new Solution(inst);
						sol.ConstroiSolution();
						SA sa = new SA(sol, t_inicial, alfa, sa_max, best_results);
						sol = sa.execute_sa(limite);
						best_results.setValueT(sol.makespan());
					}
					best_results.setTabelaT(best_results.getValueT());
					best_results.clean_valueT();
				}
				best_results.calc_media_valuesT(best_results.soma_valuesT());
				write_file.write_gap(algoritmo_name);
			}else {
				
				int sa_max = Integer.parseInt(this.prop.getProperty("SA_MAX"));
				double t_inicial = Double.parseDouble(this.prop.getProperty("SA_T_INICIAL"));
				float alfa = Float.parseFloat(this.prop.getProperty("SA_ALF"));
				
				long start = 0;
				long end = 0;
				long t = 0;
				
				for(int i = 0;i < num_exec;i++) {
					start = System.currentTimeMillis();
					
					sol = new Solution(inst);
					sol.ConstroiSolution();
					sol.repair_solution();
					SA sa = new SA(sol, t_inicial, alfa, sa_max, best_results);
					sol = sa.execute_sa();
					
					end = System.currentTimeMillis();
					t = end - start;
					time.setTempo(t);
				}
				//write_file.write(algoritmo_name, time);
				write_file.write_gap(algoritmo_name);
			}
		}
	}
}
