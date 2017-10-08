package com.f2m.common.connection;

import android.util.Log;

import com.f2m.common.utils.Constants;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

/**
 * Created by sev_user on 7/25/2016.
 */
public class XMLFactory {

    private static final String TAG = Constants.F2M_DEBUG_PREFIX + XMLFactory.class.getName();

    private static XMLFactory mInstance = new XMLFactory();
    private XMLFactory() {}

    public static XMLFactory getInstance() {
        return mInstance;
    }

    public Document createDocFromString(String xml) {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            doc = builder.parse(is);

            return doc;
        } catch (Exception e) {
            Log.d(TAG, e.getClass().getName() + " : " + e.getMessage());
        }
        return doc;
    }

    public Document createDocFromFile(String filePath) {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File file = new File(filePath);
            FileInputStream is = new FileInputStream(file);


            doc = builder.parse(is);

            return doc;
        } catch (Exception e) {
            Log.d(TAG, e.getClass().getName() + " : " + e.getMessage());
        }
        return doc;
    }

    public NodeList getNodeList(Document doc, String nodePath) {
        NodeList nodeList = null;

        XPathFactory mXPathFactory = XPathFactory.newInstance();
        XPath mXPath = mXPathFactory.newXPath();
        XPathExpression mXPathExpression = null;
        try {
            mXPathExpression = mXPath.compile(nodePath);
            nodeList = (NodeList) mXPathExpression.evaluate(doc, XPathConstants.NODESET);
        } catch (Exception e) {
            Log.d(TAG, e.getClass().getName() + " : " + e.getMessage());
        }

        return nodeList;
    }
}
