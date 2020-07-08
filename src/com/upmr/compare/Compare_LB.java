package com.upmr.compare;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import com.upmr.experiment.WriteResultsFile;
import com.upmr.results.R_ruiz;
import com.upmr.util.RPD;

public class Compare_LB {

	private String file_name;
	private R_ruiz rr;
	private FileInputStream is;
	private InputStreamReader isr;
	private BufferedReader br;
	private String linha;
	private StringTokenizer tk;
	
	public Compare_LB() throws IOException {
		this.file_name = "./bounds/gaps.csv";
		this.rr = new R_ruiz();
		String path = "./bounds/Bounds_LB.csv";
		String delim = ";";
		this.rr.ler(path, delim);
		
		this.is = new FileInputStream(file_name);
		this.isr = new InputStreamReader(this.is);
		this.br = new BufferedReader(this.isr);
		//this.linha = br.readLine();
		//this.tk = new StringTokenizer(linha, ";");
		
		
	}
	
	public void read() throws IOException {
		
		br.readLine();
		
		for(int i = 0;i < 450;i++){	
			
			this.linha = br.readLine();
			this.tk = new StringTokenizer(linha, ";");
			
			String nome_inst = tk.nextToken();
			tk.nextToken();
			double melhor = Double.parseDouble(tk.nextToken());
			double avg = Double.parseDouble(tk.nextToken());
			this.write_gap(nome_inst, avg, melhor);
			
		}
		
	}
	
	public void write_gap(String nome_inst, double value_avg, double value_bst) {

		File arquivo = new File("./results/gaps_lb.csv");
		try{
			if(!arquivo.exists()){
				arquivo.createNewFile();
			}
			
			FileWriter fw = new FileWriter(arquivo, true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			if(arquivo.length() == 0) {
				//bw.write("INSTANCIA\tALGORITMO\tLIT.\tMELHOR\tAVG_T=10\tAVG_T=30\tAVG_T=50\tPIOR\tAVG\tMEDI.\tRPDbst\tRPDavg");
				bw.write("INSTANCIA;LITERATURA;MELHOR;AVG;RPDbst;RPDavg");
				bw.newLine();
			}
			
			//double value_avg = this.best_results.getMedia();//média de todos os resultados obtido pelo algoritmo
			//double value_bst = this.best_results.getMelhor();//melhor de todos resultados obtido pelo algoritmo
			
			//removendo o .txt do final do nome do arquivo
			//String f_name_noE = nome_inst.substring(0, file_name.indexOf("_."));
			String f_name_noE = nome_inst;
			
			//NOME DA INSTANCIA
			bw.write(f_name_noE);//escreve o nome da instancia
			
			//NOME DO ALGORITMO
			//bw.write(prop.getProperty("WRITE_FILE_DELIMIT"));//escreve o delimitador
			//bw.write(nome_algoritmo);
			
			//VALOR DA LITERATURA
			bw.write(";");
			int value_lit = this.rr.getValor(f_name_noE); //valor da literatura
			bw.write(Integer.toString(value_lit));//escreve o valor absoluto da literatura
			
			//MELHOR
			bw.write(";");
			bw.write(Double.toString(value_bst));
			
			//MEDIA
			bw.write(";");
			bw.write(WriteResultsFile.format_med(value_avg));
			
			//MEDIANA
			//bw.write(prop.getProperty("WRITE_FILE_DELIMIT"));
			//bw.write(WriteResultsFile.format_med(this.best_results.getMediana()));
			
			//GAP DO MELHOR DO ALGORITMO COM O VALOR DA LITERATURA
			bw.write(";");
			bw.write(WriteResultsFile.format_gap(RPD.rpd_i(value_bst, value_lit))); //grava o gap
			
			//GAP DA MEDIA DO ALGORITMO COM O VALOR DA LITERATURA
			bw.write(";");
			bw.write(WriteResultsFile.format_gap(RPD.rpd_i(value_avg, value_lit))); //grava o gap
			
            bw.newLine();
			
			bw.close();
			fw.close();
			
			}catch(Exception e){
				System.out.println("Erro ao escrever no arquivo "+e.fillInStackTrace());
		}	
	}
}
