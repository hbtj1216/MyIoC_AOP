package com.tao.ioc_aop.xml.document;

import org.dom4j.Document;

/**
 * Created by tao on 2017/6/3.
 */
public interface DocumentHolder {

    /**
     * 根据文件的路径读取文件，并返回Document对象
     * @param xmlFilePath
     * @return
     */
    Document getDocument(String filePath);
}
