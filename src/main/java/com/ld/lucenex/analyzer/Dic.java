package com.ld.lucenex.analyzer;





import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ld.lucenex.thread.LoggerFactory;


public class Dic {

	private String extDictPath;
	private String extStopwordPath;
	private Map<String, Set<String>> thesaurus = new HashMap<>();
	
	private Logger log = LoggerFactory.getLogger(Dic.class); 
	
	/**
	 * @Title:Dic
	 * @Description:TODO
	 */
	public Dic(String extDictPath) {
		this.extDictPath = extDictPath;
	}
	/**
	 * @Title:Dic
	 * @Description:TODO
	 */
	public Dic(String extDictPath,String extStopwordPath) {
		this.extDictPath = extDictPath;
		this.extStopwordPath = extStopwordPath;
	}
	
	public String getExtDictPath() {
		return extDictPath;
	}

	public void setExtDictPath(String extDictPath) {
		this.extDictPath = extDictPath;
	}

	public String getExtStopwordPath() {
		return extStopwordPath;
	}

	public void setExtStopwordPath(String extStopwordPath) {
		this.extStopwordPath = extStopwordPath;
	}

	public Map<String, Set<String>> getThesaurus() {
		return thesaurus;
	}

	public void setThesaurus(Map<String, Set<String>> thesaurus) {
		this.thesaurus = thesaurus;
	}

	/**
	 * 初始化 词库 (自动执行 无需手动)
	 */
	public void init() {
		log.info("dic init start");
		if(extDictPath != null) {
			log.info("dic init exeDic path "+extDictPath);
			List<File> extDicFileList = new ArrayList<>();
			getExtend(extDicFileList, extDictPath);
			if(extDicFileList.isEmpty()) {
				log.warning(extDictPath+" is null .dic file");
			}else {
				thesaurus.put("ext_dict", exe(extDicFileList));
			}
		}else {
			log.warning("dic init extDic is null");
		}
		if(extStopwordPath != null) {
			log.info("dic init extStopwordPath path "+extStopwordPath);
			List<File> extStopwordPathFileList = new ArrayList<>();
			getExtend(extStopwordPathFileList, extStopwordPath);
			if(extStopwordPathFileList.isEmpty()) {
				log.warning(extStopwordPath+" is null .dic file");
			}else {
				thesaurus.put("ext_stopwords", exe(extStopwordPathFileList));
			}
		}else {
			log.warning("dic init extStopwordPath is null");
		}
	}

	private Set<String> exe(List<File> extDicFileList){
		Set<String> set = new HashSet<>();
		extDicFileList.forEach(e->{
			List<String> readLines = null;
			try {
				readLines = Files.readAllLines(e.toPath());
			} catch (IOException e1) {
				log.log(Level.SEVERE, "dic exe read error "+e.getAbsolutePath(), e1);
			}
			if(readLines != null) {
				int size = readLines.size();
				for (int i = 0; i < size; i++) {
					set.add(readLines.get(i));
				}
			}
		});
		return set;
	}

	/**
	 * 获取文件夹 下所有 dic 结尾文件
	 * @param list
	 * @param path
	 */
	private void getExtend(List<File> list, String path){
		File f = new File(path);
		//列出所有文件 及目录
		if(f.exists()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if(file.isDirectory()) {
					//目录
					getExtend(list,file.getAbsolutePath());
				}else {
					//文件不等于空  并且 是 .dic 结尾 文件
					if(file.exists() && file.getName().endsWith(".dic")) {
						list.add(file);
					}
				}
			}
		}
	}

}
