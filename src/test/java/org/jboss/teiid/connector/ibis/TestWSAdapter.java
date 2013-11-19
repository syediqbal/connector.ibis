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

package org.jboss.teiid.connector.ibis;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.jboss.teiid.resource.adaptor.ibis.IbisConnectionImpl;
import org.jboss.teiid.resource.adaptor.ibis.IbisManagedConnectionFactory;
import org.junit.Test;

public class TestWSAdapter {
	@SuppressWarnings("static-access")
	@Test
	public void testURLNOParameters() throws Exception {
		IbisManagedConnectionFactory imcf = new IbisManagedConnectionFactory();
		IbisConnectionImpl conn = (IbisConnectionImpl) imcf
				.createConnectionFactory().getConnection();
		Assert.assertEquals("http://mockibis-1381187054851.rhcloud.com/rest/mockibis/v1/master/content", conn.getURL("http://mockibis-1381187054851.rhcloud.com/rest/mockibis/v1/master/content", ""));
//		conn.executeQuery("");

	}
	@SuppressWarnings("static-access")
	@Test
	public void testURLWithParameters() throws Exception {
		IbisManagedConnectionFactory imcf = new IbisManagedConnectionFactory();
		imcf.setEndPoint("http://mockibis-1381187054851.rhcloud.com/rest/mockibis/v1/master/content");
		IbisConnectionImpl conn = (IbisConnectionImpl) imcf
				.createConnectionFactory().getConnection();
		Assert.assertEquals("http://mockibis-1381187054851.rhcloud.com/rest/mockibis/v1/master/content/q=*%3A*&wt=json&indent=true", conn.getURL("http://mockibis-1381187054851.rhcloud.com/rest/mockibis/v1/master/content/", "q=*%3A*&wt=json&indent=true"));
		conn.executeQuery("");

	}
	
	@Test
	public void testExecuteQueryNoParameters() throws Exception {
		IbisManagedConnectionFactory imcf = new IbisManagedConnectionFactory();
		imcf.setEndPoint("http://mockibis-1381187054851.rhcloud.com/rest/mockibis/v1/master/content");
		IbisConnectionImpl conn = (IbisConnectionImpl) imcf
				.createConnectionFactory().getConnection();
		List<String> docTest = new ArrayList<String>();
				
		Assert.assertEquals(docTest.toString(),conn.executeQuery(""));
		

	}
	@Test
	public void testExecuteQueryWithParameters() throws Exception {
		IbisManagedConnectionFactory imcf = new IbisManagedConnectionFactory();
		imcf.setEndPoint("http://mockibis-1381187054851.rhcloud.com/rest/mockibis/v1/master/content");
		IbisConnectionImpl conn = (IbisConnectionImpl) imcf
				.createConnectionFactory().getConnection();
		List<String> docTest = new ArrayList<String>();
				
		Assert.assertEquals(docTest.toString(),conn.executeQuery(""));
		
	}
	
 }

