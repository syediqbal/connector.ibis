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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.resource.ResourceException;
import org.apache.cxf.common.util.StringUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.teiid.logging.LogManager;
import org.jboss.dmr.ModelNode;
import org.jboss.teiid.resource.adaptor.ibis.IbisManagedConnectionFactory;
import org.teiid.resource.spi.BasicConnection;

/**
 * Rest WebService connection implementation.
 * 
 * TODO: set a handler chain
 */
/**
 * @author student
 * 
 */
public class IbisConnectionImpl extends BasicConnection {
	private HttpClient client = new DefaultHttpClient();
	private IbisManagedConnectionFactory mcf;
	private String url;
	// TODO make this a part of configuration file
	private final String JSON_ROOT_NODE = "rows";

	public IbisConnectionImpl(IbisManagedConnectionFactory mcf) {
		this.mcf = mcf;
	}

	/**
	 * @param params
	 * @return List<String>
	 * @throws java.lang.Exception
	 */
	public List<String> executeQuery(final String params)
			throws java.lang.Exception {

		url = getURL(this.mcf.getEndPoint(), params);
		String json = rsRequest(url, HttpMethod.GET, this.mcf.getTimeout());
		// in case the server answers with 'no-content'

		if (StringUtils.isEmpty(json)) {
			return null;
		}

		LogManager.logTrace("Unmarshalling response\n{}", json);

		// get docNodes from json String
		final ModelNode docNodes = getModelNode(json.toString(),JSON_ROOT_NODE);

		return getDocList(docNodes);
	}

	/**
	 * 
	 * @param docNodes
	 * @return list of documents
	 */
	public List<String> getDocList(ModelNode docNodes) {
		int index = 0;
		List<String> docs = new ArrayList<String>();
		for (ModelNode docNode : docNodes.asList()) {
			docs.add(index, docNode.toString());
			index++;
		}
		return docs;
	}
	
	private String rsRequest(String url, HttpMethod httpMethod, int timeout)
			throws HttpException, ClientProtocolException, IOException {
		LogManager.logInfo("Requesting {} with protocol {} on {}",
				httpMethod.name() + " " + url);

		switch (httpMethod) {
		case GET:
			return getResponse(url, timeout);
		default:
			throw new HttpException("Unexpected HTTP method {0} "
					+ httpMethod.toString());
		}

	}

	/**
	 * description: takes a url and makes a rest call then gets the json back
	 * and parses that into a string
	 * 
	 * @param url
	 * @param timeout
	 * @return String
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String getResponse(String url, int timeout)
			throws ClientProtocolException, IOException {

		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		String line = "";
		StringBuilder content = new StringBuilder();

		while ((line = rd.readLine()) != null) {
			content.append(line);
		}
		return content.toString();
	}
	
	public static String getURL(String endpoint, String param) {
		if(param.isEmpty()){
			return endpoint;
		}
		return endpoint + param;
	}
	
//only using GET at moment, by may want to extend in future
	public enum HttpMethod {
		GET, POST, PUT, DELETE, HEAD
	}

	//TODO leverage this class to root node, w/wo any root parameters
	private ModelNode getModelNode(final String content,final String root)
			throws java.lang.Exception {
		if (content == null) {
			throw new java.lang.Exception(
					"Could not unmarshall response: no content.");
		}
		final ModelNode node = ModelNode.fromJSONString(content).get(root);
		if (!node.isDefined()) {
			throw new java.lang.Exception(
					"Could not unmarshall response: erroneous content.");
		}

		return node;
	}
	
	@Override
	public void close() throws ResourceException {
		// TODO Auto-generated method stub

	}

}
