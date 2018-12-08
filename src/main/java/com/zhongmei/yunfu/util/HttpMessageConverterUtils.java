package com.zhongmei.yunfu.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class HttpMessageConverterUtils {

    private static final String ENCODING = "UTF-8";

    public static String objectToXml(Object object) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, sw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    public static <T> T xmlToObject(String xml, Class<T> clazz) {
        T object = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader sr = new StringReader(xml);
            object = (T) unmarshaller.unmarshal(sr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
