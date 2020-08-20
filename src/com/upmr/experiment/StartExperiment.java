/***********************
 * Esta classe dá o inicio do experimento criando uma array
 * com todos os arquivos existentes em um diretorio passado e então
 * passando seus nomes para a função execute_experiment. A função start executa
 * um número n de vezes (executions_number) o algoritmo para todos os arquivos contidos no
 * diretorio especificado.
 * 
 * Classe criada em: 19 de out 2017
 * @author cesar
 * 
********************************************************************/
package com.upmr.experiment;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import com.upmr.util.View;

public class StartExperiment {
	
	private Properties prop;
	private ConfExperiment conf;
	
	public StartExperiment() throws Exception{
		new ReadFileConf();
		this.prop = ReadFileConf.getProp();
		this.conf = new ConfExperiment(prop);
	}
	
	public String exp_name() {
		String name = prop.getProperty("RESULT_PATH");
		name = name.split("/")[2];
		name = name.split(".csv")[0];		
		return name.split("_")[1].toUpperCase()+" " +name.split("_")[2].toUpperCase();
		
	}
	
	public void start() throws IOException, CloneNotSupportedException{
		
		long Start = System.currentTimeMillis();
		
		String path = prop.getProperty("INSTANCE");
		File[] name_list;
			
		name_list = ReadDirFilesNames.leDir(path);//lista de arquivos contidos no diretorio
		
		View.title_1("EXPERIMENT: "+exp_name());
		
		for(int i = 0;i < name_list.length;i++) {
			View.title_3("[ "+i+" ] - "+name_list[i].getName());
			conf.execute_experiment(name_list[i].getName(), path);
		}
		
		
		long End = System.currentTimeMillis();
		long Time = End - Start;
		Time = Time / 1000;
		View.title_2("Tempo: "+Time+" segundos");
		
		WriteResultsFile write = new WriteResultsFile(prop);
		write.write_resume(Time, name_list.length);

	}

}
