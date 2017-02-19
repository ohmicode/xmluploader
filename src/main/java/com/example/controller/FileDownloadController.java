package com.example.controller;

import com.example.model.Property;
import com.example.model.PropertyRepository;
import com.example.parser.PropertyXml;
import com.example.parser.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="/")
public class FileDownloadController {

    @Autowired
    private XmlParser xmlParser;

    @Autowired
    private PropertyRepository propertyRepository;

    @RequestMapping(path = "download", method = RequestMethod.GET)
    public ResponseEntity<String> downloadFromBase(Model model) {
        PropertyXml propertyXml = buildTree();
        String body = xmlParser.toXml(propertyXml);

        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_XML);
        return new ResponseEntity<>(body, httpHeaders, HttpStatus.OK);
    }

    private PropertyXml buildTree() {
        List<Property> propertyList = propertyRepository.findAll();
        Property root = propertyList.stream().filter(p -> p.getParentId() == null).findFirst().orElse(null);
        return toXml(root, propertyList);
    }

    private PropertyXml toXml(Property property, List<Property> propertyList) {
        if(property != null) {
            PropertyXml xml = new PropertyXml();
            xml.setName(property.getName());
            List<Property> children = propertyList.stream()
                    .filter(p -> property.getId().equals(p.getParentId()))
                    .collect(Collectors.toList());
            for(Property child : children) {
                PropertyXml childXml = toXml(child, propertyList);
                xml.getProperties().add(childXml);
            }
            return xml;
        } else {
            return null;
        }
    }
}
