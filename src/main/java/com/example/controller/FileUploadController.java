package com.example.controller;

import com.example.model.Property;
import com.example.model.PropertyRepository;
import com.example.parser.PropertyXml;
import com.example.parser.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping(path="/")
public class FileUploadController {

    @Autowired
    private XmlParser xmlParser;

    @Autowired
    private PropertyRepository propertyRepository;

    @RequestMapping(path = "upload", method = RequestMethod.GET)
    public String redirect2uploadForm(Model model) throws IOException {
        return "upload";  //upload.jsp
    }

    @RequestMapping(path = "upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            PropertyXml xml = xmlParser.fromXml(file.getInputStream());
            save2base(xml);
        } catch (IOException ioe) {
            // ignored
        }

        return "redirect:/download";
    }

    private Property save2base(PropertyXml xml) {
        Property property = new Property();
        property.setName(xml.getName());
        property = propertyRepository.save(property);

        for(PropertyXml childXml : xml.getProperties()) {
            Property child = save2base(childXml);
            child.setParentId(property.getId());
            propertyRepository.save(child);
        }
        return property;
    }
}

