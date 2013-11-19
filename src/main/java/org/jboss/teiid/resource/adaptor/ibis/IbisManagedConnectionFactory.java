/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package org.jboss.teiid.resource.adaptor.ibis;

import java.util.List;

import javax.resource.ResourceException;
import javax.xml.namespace.QName;

import org.apache.cxf.Bus;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;
import org.teiid.resource.spi.BasicConnection;
import org.teiid.resource.spi.BasicConnectionFactory;
import org.teiid.resource.spi.BasicManagedConnectionFactory;

public class IbisManagedConnectionFactory extends BasicManagedConnectionFactory {

	private static final long serialVersionUID = -2998163922934555003L;
	
//	public static final BundleUtil UTIL = BundleUtil.getBundleUtil(WSManagedConnectionFactory.class);

	public enum SecurityType {None,HTTPBasic,WSSecurity}
	
	private String endPoint;
	private String rsVersion;
	private Integer timeout;
	private String securityType = SecurityType.None.name(); // None, HTTPBasic, WS-Security
	private String configFile; // path to the "jbossws-cxf.xml" file
	private String configName; // config name in the above file
	private String authPassword; // httpbasic - password
	private String authUserName; // httpbasic - username

	private Bus bus;
	private QName portQName;
	private List<Interceptor<? extends Message>> outInterceptors;

	@SuppressWarnings("serial")
	@Override
	public BasicConnectionFactory createConnectionFactory() throws ResourceException {
		return new BasicConnectionFactory() {

			@Override
			public BasicConnection getConnection() throws ResourceException {
				return new IbisConnectionImpl(IbisManagedConnectionFactory.this);
			}
		};
	}
	
	public String getAuthPassword() {
		return this.authPassword;
	}

	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}

	public String getAuthUserName() {
		return this.authUserName;
	}

	public void setAuthUserName(String authUserName) {
		this.authUserName = authUserName;
	}

	public String getEndPoint() {
		return this.endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}	
	
	public SecurityType getSecurityType() {
		return SecurityType.valueOf(this.securityType);
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}	

	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String config) {
		this.configFile = config;
	}
	
	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}
	
	public Bus getBus() {
		return bus;
	}
	
	public QName getPortQName() {
		return portQName;
	}
	
	public List<Interceptor<? extends Message>> getOutInterceptors() {
		return outInterceptors;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public String getRsVersion() {
		return rsVersion;
	}

	public void setRsVersion(String rsVersion) {
		this.rsVersion = rsVersion;
	}
	
}
