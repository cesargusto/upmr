/*****************************************************
 * 
 * Esta classe é responsável por gravar um array de valores de makespan
 * em um arquivo de texto para posterior análise. o atributo factor_reduction
 * é responsável por amostrar esses valores de modo que não seja necessário
 * a gravação de todos os valores quando uma execução tiver um valor alto
 * de iterações.
 * 
 * @author cesar
 * Esta classe foi criada em: 19 de out 2017.
 * 
 *****************************************************/

package com.upmr.experiment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.upmr.results.R_ruiz;
import com.upmr.util.Parses;
import com.upmr.util.RPD;

public class WriteResultsFile {
	
	private BestResults best_results;
	private String file_name;
	//private String file_path;
	private Properties prop;
	//private int factor_reduction = 0;
	private R_ruiz rr;
	
	public WriteResultsFile(BestResults best_results, String file_name, Properties prop) throws IOException {
		this.best_results = best_results;
		this.prop = prop;
		this.file_name = file_name;
		this.rr = new R_ruiz();
		this.rr.ler(prop.getProperty("BOUNDS"), prop.getProperty("BOUNDS_FILE_DELIMIT"));
		
	}
	
	public WriteResultsFile(Properties prop) throws IOException {
		this.prop = prop;
		//this.file_path = this.prop.getProperty("RESUME_PATH");
	}

	public void write_resume(long time, int quant_arqs) {
		
		File arquivo = new File(prop.getProperty("RESUME_PATH"));
		try{
			if(!arquivo.exists()){
				arquivo.createNewFile();
			}
			
			FileWriter fw = new FileWriter(arquivo, true);
			BufferedWriter bw = new BufferedWriter(fw);
			 
			bw.write(" - ARQUIVOS PROCESSADOS ..........: ");
			bw.write(Integer.toString(quant_arqs));
			bw.newLine();
			bw.write(" - TEMPO DE PROCESSAMENTO(SEC.)...: ");
			bw.write(Float.toString(time));
			
            bw.newLine();
			
			bw.close();
			fw.close();
			
			}catch(Exception e){
				System.out.println("Erro ao escrever no arquivo");
		}	
	}
	/*
	 * O metodo write_gap cria um arquivo com o nome da instancia, 
	 * os valores absolutos de função objetivo
	 * do algoritmo executado e o da literatura, calcula e 
	 * escreve o gap metodo finalizado em 18 de maio de 2018
	 * 
	 * Alteração deste método para gravar Melhor, Pior, Media e Mediana feita em 26/abr de 2019
	 */
	public void write_gap(String nome_algoritmo) {

		//File arquivo = new File("../results/gaps");
		File arquivo = new File(prop.getProperty("RESULT_PATH"));
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
			
			double value_avg = this.best_results.getMedia();//média de todos os resultados obtido pelo algoritmo
			double value_bst = this.best_results.getMelhor();//melhor de todos resultados obtido pelo algoritmo
			
			//removendo o .txt do final do nome do arquivo
			String f_name_noE = file_name.substring(0, file_name.indexOf("_."));
			
			//NOME DA INSTANCIA
			bw.write(f_name_noE);//escreve o nome da instancia
			
			//NOME DO ALGORITMO
			//bw.write(prop.getProperty("WRITE_FILE_DELIMIT"));//escreve o delimitador
			//bw.write(nome_algoritmo);
			
			//VALOR DA LITERATURA
			bw.write(prop.getProperty("WRITE_FILE_DELIMIT"));
			int value_lit = this.rr.getValor(f_name_noE); //valor da literatura
			bw.write(Integer.toString(value_lit));//escreve o valor absoluto da literatura
			
			//MELHOR
			bw.write(prop.getProperty("WRITE_FILE_DELIMIT"));
			bw.write(Double.toString(value_bst));
			
			//MEDIA T=10
			//bw.write(prop.getProperty("WRITE_FILE_DELIMIT"));
			//bw.write(format_gap(this.best_results.getMediaValuesT(0)));
			//MEDIA T=30
			//bw.write(prop.getProperty("WRITE_FILE_DELIMIT"));
			//bw.write(format_gap(this.best_results.getMediaValuesT(1)));
			//MEDIA T=50
			//bw.write(prop.getProperty("WRITE_FILE_DELIMIT"));
			//bw.write(format_gap(this.best_results.getMediaValuesT(2)));
			
			//PIOR
			//bw.write(prop.getProperty("WRITE_FILE_DELIMIT"));
			//bw.write(Integer.toString(this.best_results.getPior()));
			
			//MEDIA
			bw.write(prop.getProperty("WRITE_FILE_DELIMIT"));
			bw.write(WriteResultsFile.format_med(this.best_results.getMedia()));
			
			//MEDIANA
			//bw.write(prop.getProperty("WRITE_FILE_DELIMIT"));
			//bw.write(WriteResultsFile.format_med(this.best_results.getMediana()));
			
			//GAP DO MELHOR DO ALGORITMO COM O VALOR DA LITERATURA
			bw.write(prop.getProperty("WRITE_FILE_DELIMIT"));
			bw.write(WriteResultsFile.format_gap(RPD.rpd_i(value_bst, value_lit))); //grava o gap
			
			//GAP DA MEDIA DO ALGORITMO COM O VALOR DA LITERATURA
			bw.write(prop.getProperty("WRITE_FILE_DELIMIT"));
			bw.write(WriteResultsFile.format_gap(RPD.rpd_i(value_avg, value_lit))); //grava o gap
			
            bw.newLine();
			
			bw.close();
			fw.close();
			
			}catch(Exception e){
				System.out.println("Erro ao escrever no arquivo "+e.fillInStackTrace());
		}	
	}
	
	public void write(String algoritmo_name, Time time) {
		
	}
	
	public static String format_gap(double x) {
		String t = String.format("%.2f", x);
		t = t.replace(",", ".");
	    return t;
	}
	
	public static String format_med(double x) {
		String t = String.format("%.1f", x);
		t = t.replace(",", ".");
	    return t;
	}
}
