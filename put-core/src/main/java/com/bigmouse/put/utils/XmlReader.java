package com.bigmouse.put.utils;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigmouse.put.log.PutLogObserverProxy;

/**
 * Tool for read xml file
 * @author lihaoyuan
 *
 */
public class XmlReader
{
	private transient final Logger log = new PutLogObserverProxy().proxy(LoggerFactory.getLogger(getClass()));
	
	private String xmlPath;
	private Document doc;
	
	public XmlReader(String url)
	{
		this.xmlPath = url;
	}
	
	/**
	 * Find element by XPath
	 * @param xpath xpath path with XPath
	 * @return target element node
	 * @throws XmlReaderException
	 */
	public Node getElementNode(String xpath) throws XmlReaderException
	{
		// check xmlPath and doc
		try
		{
			selfCheck();
		}
		catch (NoSuchFileException | DocumentException e)
		{
			throw new XmlReaderException("Read xml ERROR, " + e.getMessage(), e);
		}
		
		// get target node
		log.debug("Get target node " + xpath);
		return doc.selectSingleNode(xpath);
	}
	
	/**
	 * Find element list by XPath
	 * @param xpath
	 * @return
	 * @throws XmlReaderException
	 */
	@SuppressWarnings("unchecked")
	public List<Node> getElementNodes(String xpath) throws XmlReaderException
	{
		// check xmlPath and doc
		try
		{
			selfCheck();
		}
		catch (NoSuchFileException | DocumentException e)
		{
			throw new XmlReaderException("Read xml ERROR, " + e.getMessage(), e);
		}
		
		// get target nodes
		log.debug("Get target node list " + xpath);
		return doc.selectNodes(xpath);
	}
	
	/**
	 * Find element by XPath and get text in target element
	 * @param xpath path with XPath
	 * @return text in target element
	 * @throws XmlReaderException
	 */
	public String getElementText(String xpath) throws XmlReaderException
	{
		// get target node
		Node node = this.getElementNode(xpath);
		
		// check node and return text
		String text = null;
		if(node != null) text = node.getText();
		log.debug("Got target node text " + xpath + " -> " + text);
		
		return text;
	}
	
	/**
	 * Find all elements by XPath, get text list with all target elements
	 * @param xpath path with XPath
	 * @return text list with all target elements
	 * @throws XmlReaderException
	 */
	public List<String> getElementTextList(String xpath) throws XmlReaderException
	{
		// get target node list
		List<Node> nodes = this.getElementNodes(xpath);
		
		// check node and return text
		if(nodes == null) return null;
		else
		{
			log.debug("Get target node text list " + xpath);
			List<String> textList = new ArrayList<String>();
			for(Node n : nodes)
			{
				String text = n.getText();
				log.debug("Got target node text " + n.getPath() + " -> " + text);
				textList.add(text);
			}
			return textList;
		}
	}
	
	private void selfCheck() throws NoSuchFileException, DocumentException
	{
		if(xmlPath == null || xmlPath.equals("")) throw new NoSuchFileException(xmlPath);
		
		log.debug("Check exist the xml file: " + xmlPath);
		File xmlFile = new File(xmlPath);
		if(!xmlFile.exists()) throw new NoSuchFileException(xmlPath);

		log.debug("Doc is null, read the xml file");
		if(doc == null)
		{
			SAXReader reader = new SAXReader();
			doc = reader.read(xmlPath);
		}
	}
	
	public class XmlReaderException extends Exception
	{
		private static final long serialVersionUID = -8176216897619655668L;
		
		public XmlReaderException(String errorMsg)
		{
			super(errorMsg);
		}
		
		public XmlReaderException(String errorMsg, Exception e)
		{
			super(errorMsg, e);
		}
	}
}
