package com.example.parser;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;

@Component
public class XmlParser {

    public PropertyXml fromXml(InputStream inputStream) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(PropertyXml.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            PropertyXml property = (PropertyXml) jaxbUnmarshaller.unmarshal(inputStream);

            return property;
        } catch (JAXBException je) {
            // Parse error
            return null;
        }
    }

    public String toXml(PropertyXml property) {
        if(property == null) return "";

        try {
            StringWriter sw = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(PropertyXml.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            jaxbMarshaller.marshal(property, sw);
            return sw.toString();
        } catch (JAXBException je) {
            // Parse error
            return "";
        }
    }
}
