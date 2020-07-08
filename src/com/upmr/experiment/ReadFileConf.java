package com.upmr.experiment;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadFileConf {
	
	public static Properties getProp() throws IOException{
		Properties props = new Properties();
		FileInputStream file = new FileInputStream("../conf/conf.properties");
		//FileInputStream file = new FileInputStream("conf/conf.properties");
		props.load(file);
		return props;
	}

}
