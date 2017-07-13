package com.tao.ioc_aop.xml.document;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tao on 2017/6/3.
 */

/**
 * xml文件的DocumentHolder。
 * 负责解析xml文件，持有Document对象。
 */
public class XmlDocumentHolder implements DocumentHolder {

    //存放多个Document的map
    private Map<String, Document> documentMap = new HashMap<String, Document>();


    /**
     * 读取xml文件，获取对应的Document对象
     *
     * @param filePath
     * @return
     */
    @Override
    public Document getDocument(String filePath) {

        Document document = this.documentMap.get(filePath);

        if (document == null) {
            //map中没有
            Document doc = readDocumentFromXmlFile(filePath);
            this.documentMap.put(filePath, doc);
            return doc;
        } else {
            return document;
        }
    }


    /**
     * 使用dom4j解析xml文件，生成Document对象
     *
     * @param filePath
     * @return
     */
    private Document readDocumentFromXmlFile(String filePath) {

        try {
            //new一个带dtd验证的SAXReader对象
            SAXReader reader = new SAXReader();
            //设置dtd的源
            //reader.setEntityResolver(new XmlEntityResolver());
            //读取xml文件，生成Document对象
            File xmlFile = new File(filePath);
            Document document = reader.read(xmlFile);
            return document;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }


}















