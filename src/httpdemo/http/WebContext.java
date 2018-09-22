package httpdemo.http;

import java.util.HashMap;
import java.util.List;

public class WebContext {
    private List<Entity> entities;
    private List<Mapping> mappings;

    private HashMap<String,String> hashMapEntity=new HashMap<String, String>();
    private HashMap<String,String> hashMapMapping=new HashMap<String, String>();

    public WebContext(List<Entity> entities, List<Mapping> mappings){
        this.entities=entities;
        this.mappings=mappings;
        for (Entity entity:entities){
            hashMapEntity.put(entity.getName(),entity.getClazz());
        }
        for (Mapping mapping:mappings){
            for (String string:mapping.getPattern()){
                hashMapMapping.put(string,mapping.getName());
            }
        }
    }

    //获取Class
    public String getClass(String pattern){
        String name=hashMapMapping.get(pattern);
        return hashMapEntity.get(name);
    }
}
