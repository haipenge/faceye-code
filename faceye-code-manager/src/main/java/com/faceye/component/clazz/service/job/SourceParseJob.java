package com.faceye.component.clazz.service.job;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.clazz.service.parse.impl.JavaParseImpl;
import com.faceye.feature.service.job.impl.BaseJob;

@Service
@DisallowConcurrentExecution
public class SourceParseJob extends BaseJob {
	@Autowired
	private JavaParseImpl javaParse=null;
	@Override
	public void run() {
		this.javaParse.parse();
	}

}
