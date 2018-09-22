package httpdemo.http;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

//编写处理器
public class WebHandler extends DefaultHandler {

    private List<Entity> entities;
    private List<Mapping> mappings;
    private Entity entity;
    private Mapping mapping;
    private String tag;
    private boolean isMapping=false;

    public List<Entity> getEntities() {
        return entities;
    }
    public List<Mapping> getMappings() {
        return mappings;
    }

    @Override
    public void startDocument()throws SAXException {
        entities=new ArrayList<Entity>();
        mappings=new ArrayList<Mapping>();
    }

    @Override
    public void characters (char ch[], int start, int length) throws SAXException {
        String contents=new String(ch,start,length).trim();
        if (null!=tag) {
            if (isMapping){
                if (tag.equals("servlet-name")){
                    mapping.setName(contents);
                }else if(tag.equals("url-pattern")){
                    mapping.addPattern(contents);
                }
            }else {
                if (tag.equals("servlet-name")){
                    entity.setName(contents);
                }else if(tag.equals("servlet-class")){
                    entity.setClazz(contents);
                }
            }
        }
    }


    @Override
    public void startElement (String uri, String localName,String qName, Attributes attributes) throws SAXException {
        if (null!=qName) {
            tag = qName;
            if (tag == "servlet") {
                entity=new Entity();
                isMapping=false;
            }else if (tag.equals("servlet-mapping")){
                mapping=new Mapping();
                isMapping=true;
            }
        }
    }

    @Override
    public void endElement (String uri, String localName, String qName)throws SAXException {
        if (null!=qName) {
            if (qName.equals("servlet")) {
                entities.add(entity);
            }else if (qName.equals("servlet-mapping")){
                mappings.add(mapping);
            }
        }
        tag=null;
    }
}

