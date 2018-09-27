package httpdemo.http.httpServer;

import java.util.HashSet;
import java.util.Set;

public class Mapping {
    private String name;
    private Set<String> pattern;

    public Mapping(){
        pattern=new HashSet<String>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPattern(Set<String> pattern) {
        this.pattern = pattern;
    }
    public Set<String> getPattern() {
        return pattern;
    }

    public void addPattern(String pa){
        pattern.add(pa);
    }
}
