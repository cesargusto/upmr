package com.upmr.instances;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Instance {
	private FileInputStream  is;
	private InputStreamReader isr;
	private BufferedReader br;
	private StringTokenizer tk;
	private String linha;
	private int n_jobs;
	private int n_maqs;
	private int R_max;
	//private int n_desconhecido; //Valor existente na instancia mas desconhecida sua finalidade
	private int[][] tempo_exec;
	private int[][] resources;
	
	public Instance(String file_name) throws IOException{
		
		this.is = new FileInputStream(file_name);
		this.isr = new InputStreamReader(this.is);
		this.br = new BufferedReader(this.isr);
		this.linha = br.readLine();
		this.tk = new StringTokenizer(linha, "\t");
		this.n_jobs = Integer.parseInt(tk.nextToken());
		this.n_maqs = Integer.parseInt(tk.nextToken());
		this.tempo_exec = new int[n_maqs][n_jobs];
		this.resources = new int[n_maqs][n_jobs];
		this.ler(file_name);
	}
	
	public void ler(String nome) throws IOException{
		br.readLine();
		
		for(int i = 0;i < n_jobs;i++){	
			
			this.linha = br.readLine();
			this.tk = new StringTokenizer(linha, "\t");
			
			for(int j = 0;j < n_maqs; j++){
				tk.nextToken();
				tempo_exec[j][i] = Integer.parseInt(tk.nextToken());
			}
		}
		
		br.readLine();
		br.readLine();
		br.readLine();
		
		this.linha = br.readLine();
		this.tk = new StringTokenizer(linha, "\t");
		this.R_max = Integer.parseInt(tk.nextToken());
					
		for(int i = 0;i < n_jobs;i++){
			this.linha = br.readLine();
			this.tk = new StringTokenizer(linha, "\t");
			
			for(int j = 0;j < n_maqs; j++){
				tk.nextToken();
				resources[j][i] = Integer.parseInt(tk.nextToken());
			}
		}
	}

	public int getN_jobs() {
		return n_jobs;
	}

	public int getN_maqs() {
		return n_maqs;
	}
	
	public int getR_max() {
		return R_max;
	}
	
	public int getT_exec(int n_maq, int n_job){
		return this.tempo_exec[n_maq][n_job];
	}
	
	public int get_resource(int i_maq, int i_job){
		return this.resources[i_maq][i_job];
	}
	
	public int[][] getMatriz_T_exec(){
		return this.tempo_exec;
	}
	
	public int[][] getMatriz_rosources(){
		return this.resources;
	}

	public void print_exec_time(){
		//IMPRIME MATRIZ DE TEMPOS DE EXECUÇAO
		System.out.println();
		for(int i = 0;i < n_maqs;i++){
			for(int j = 0;j < n_jobs;j++){
				System.out.printf("%d ", tempo_exec[i][j]);
			}
			System.out.println();
		}
	}
	public void print_resources(){
		System.out.println();
		for(int i = 0;i < n_maqs;i++){
			for(int j = 0;j < n_jobs;j++){
				System.out.printf("%d ", resources[i][j]);
			}
			System.out.println();
		}
		System.out.println("\nR_Max = "+this.R_max);
	}		
}
