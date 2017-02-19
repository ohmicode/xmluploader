package com.example.parser;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"name", "properties"})
@XmlRootElement(name = "property")
public class PropertyXml {

    @XmlAttribute(name = "name", required = true)
    private String name;
    @XmlElement(name = "property")
    protected List<PropertyXml> properties = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PropertyXml> getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return "PropertyXml{" +
                "name='" + name + '\'' +
                ", properties=" + properties +
                '}';
    }
}
