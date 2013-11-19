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
	@Test
	public void testURLNOParameters() throws Exception {
		IbisManagedConnectionFactory imcf = new IbisManagedConnectionFactory();
		IbisConnectionImpl conn = (IbisConnectionImpl) imcf
				.createConnectionFactory().getConnection();
		Assert.assertEquals("http://mockibis-1381187054851.rhcloud.com/rest/mockibis/v1/master/content", conn.getURL("http://mockibis-1381187054851.rhcloud.com/rest/mockibis/v1/master/content", ""));
//		conn.executeQuery("");

	}
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
//		IbisManagedConnectionFactory imcf = new IbisManagedConnectionFactory();
//		imcf.setEndPoint("http://mockibis-1381187054851.rhcloud.com/rest/mockibis/v1/master/content");
//		IbisConnectionImpl conn = (IbisConnectionImpl) imcf
//				.createConnectionFactory().getConnection();
//		List<String> docTest = new ArrayList<String>();
				
//		Assert.assertEquals();
	}
	
	// @Test(expected=WebServiceException.class) public void
	// testMissingEndpoint() throws ResourceException {
	// WSManagedConnectionFactory wsmcf = new WSManagedConnectionFactory();
	//
	// WSConnectionImpl conn =
	// (WSConnectionImpl)wsmcf.createConnectionFactory().getConnection();
	// conn.createDispatch(HTTPBinding.HTTP_BINDING,
	// "http://localhost:8983/solr/collection1/select?q=*:*",
	// StreamSource.class, Mode.PAYLOAD);
	// }
	// @Test public void testMissingEndpoint() throws ResourceException {
	// WSManagedConnectionFactory wsmcf = new WSManagedConnectionFactory();
	//
	// WSConnectionImpl conn =
	// (WSConnectionImpl)wsmcf.createConnectionFactory().getConnection();
	// Dispatch dispatch= conn.createDispatch(HTTPBinding.HTTP_BINDING,
	// "http://localhost:8983/solr/collection1/select?", StreamSource.class,
	// Mode.PAYLOAD);
	// dispatch.getRequestContext().put(MessageContext.HTTP_REQUEST_METHOD,
	// "GET");
	// DataSource ds = null;
	// Object source = dispatch.invoke(ds);
	// System.out.println(source.toString());
	// }
	/*@Test
	public void testEndpoint() throws ClientProtocolException, IOException,
			XMLStreamException {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(
				"http://mockibis-1381187054851.rhcloud.com/rest/mockibis/v1/master/content");
		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		// InputStreamReader istream = new
		// InputStreamReader(response.getEntity().getContent());
		String line = "";
		StringBuilder content = new StringBuilder();
		ModelNode rootNode = null;
		while ((line = rd.readLine()) != null) {
			content.append(line);
		}
		String jsonText = content.toString();
		// System.out.println(content.toString());

		// node.set("id");
		rootNode = ModelNode.fromJSONString(content.toString()).get("rows");
		// final String type = rootNode.get("type").asString();
		// final String status = rootNode.get("status").asString();
		// final Messages messages = createMessages(rootNode.get("messages"));
		// rootNode.get("response")
		ModelNode modelNode = rootNode.get("rows");
		// ModelNode docsNode = modelNode.get("docs");
		// ModelNode idNode ;//= docsNode.get("id");
		// System.out.println(modelNode.keys());
		// System.out.println("\n\n\n\n" + rootNode.keys());
		List<String> docs = new ArrayList<String>();
		;
		int index = 0;

		for (ModelNode messageNode : modelNode.asList()) {

			// System.out.println(messageNode);
			// System.out.println(messageNode.toString());
			docs.add(index, messageNode.toString());
			// System.out.println(messageNode.keys());
			// System.out.println(messageNode.);;
			// for (ModelNode node : messageNode.asList())
			// {
			// if(node.get("id").isDefined())
			// System.out.println(node.get("id"));
			// }
			index++;
		}
		File file = new File(
				"/home/student/opt/development/jsonToStringList.txt");

		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(docs.toString());
		bw.close();
		// Mapped convention
		// JSONObject obj = new JSONObject();
		// AbstractXMLStreamReader reader = new MappedXMLStreamReader(obj);

		//
		// System.out.println(reader.getName().getPrefix());
		// System.out.println("=======decode=======");
		//
		// String s="[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
		// Object obj=JSONValue.parse(s);
		// JSONArray array=(JSONArray)obj;
		// System.out.println("======the 2nd element of array======");
		// System.out.println(array.get(1));
		// System.out.println();
		//
		// JSONObject obj2=(JSONObject)array.get(1);
		// System.out.println("======field \"1\"==========");
		// System.out.println(obj2.get("1"));
		//
		//
		// s="{}";
		// obj=JSONValue.parse(s);
		// System.out.println(obj);
		//
		// s="[5,]";
		// obj=JSONValue.parse(s);
		// System.out.println(obj);
		//
		// s="[5,,2]";
		// obj=JSONValue.parse(s);
		// System.out.println(obj);
		//
		// JsonParserFactory factory=JsonParserFactory.getInstance();
		// com.json.parsers.JSONParser parser=factory.newJsonParser();
		// Map jsonData=parser.parseJson(jsonText);
		// String value=(String)jsonData.get("docs");
	}

	private String getString(ModelNode node) {
		if (node == null || !node.isDefined()) {
			return null;
		}
		return node.asString();
	}*/
}

//
// @Test(expected=WebServiceException.class) public void testMissingEndpoint1()
// throws ResourceException {
// WSManagedConnectionFactory wsmcf = new WSManagedConnectionFactory();
//
// WSConnectionImpl conn =
// (WSConnectionImpl)wsmcf.createConnectionFactory().getConnection();
//		conn.createDispatch(HTTPBinding.HTTP_BINDING, "/x", StreamSource.class, Mode.PAYLOAD); //$NON-NLS-1$
// }

