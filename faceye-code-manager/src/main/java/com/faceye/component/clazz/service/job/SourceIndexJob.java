package com.faceye.component.clazz.service.job;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faceye.component.clazz.service.index.SourceIndexService;
import com.faceye.feature.service.job.impl.BaseJob;

@Service
@DisallowConcurrentExecution
public class SourceIndexJob extends BaseJob {

	@Autowired
	private SourceIndexService soureIndexService=null;
	@Override
	public void run() {
		this.soureIndexService.buildIndex();
	}

}
