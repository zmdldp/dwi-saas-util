package com.dwi.basic.datasource.plugin.component;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.dwi.basic.utils.CommonConstants;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
public class ApplicationEnvironment {
	
	private String applicationName;
	
	private String multiTenantType;
	
	private String contextPath;
	
	public ApplicationEnvironment(Environment env) {
		this.applicationName = env.getProperty(CommonConstants.APPLICATION_NAME_KEY);		
		this.multiTenantType = env.getProperty(CommonConstants.MULTI_TENANT_TYPE_KEY);		
		this.contextPath = env.getProperty(CommonConstants.CONTEXT_PATH_KEY);
	}
	


}
