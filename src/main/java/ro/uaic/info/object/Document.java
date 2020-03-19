package ro.uaic.info.object;

import ro.uaic.info.exception.InvalidBuildException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Document implements Serializable {
    private String id;
    private String name;
    private String location;
    private Map<String, Object> tags;

    public static class Builder{
        private String id;
        private String name;
        private String location;
        private Map<String, Object> tags;

        public Builder withID(String id){
            this.id = id;
            return this;
        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withLocation(String location){
            this.location = location;
            return this;
        }

        public Builder withTags(Map<String, Object> tags){
            this.tags = new HashMap<>();
            this.tags.putAll(tags);

            return this;
        }

        public Document build() throws InvalidBuildException{
            Document doc = new Document();

            if(this.id.isEmpty())
                throw new InvalidBuildException("Cannot create Document without id");

            doc.id = this.id;
            doc.location = this.location;
            doc.name = this.name;

            doc.tags = this.tags;
            if(doc.tags == null)
                doc.tags = new HashMap<>();

            return doc;
        }
    }

    private Document(){

    }

    public Document(String id, String name, String location){
        this.id = id;
        this.name = name;
        this.location = location;
        this.tags = new HashMap<>();
    }

    public Document addTag(String key, Object obj){
        this.tags.put(key, obj);
        return this;
    }

    public Document addTags(Map<String, Object> tags){
        this.tags.putAll(tags);
        return this;
    }

    public Object getTag(String key){
        return this.tags.get(key);
    }

    public Map<String, Object> getTags(){
        return this.tags;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setName(String name){
        this.name = name;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder()
                .append("Document \"")
                .append(this.name)
                .append("\"\n\tlocation = \"")
                .append(this.location)
                .append("\"\n\tid = \"")
                .append(this.id)
                .append("\"\n");

        if(!this.tags.isEmpty()) {
            sb.append("\ttags : \n");
            this.tags.forEach((key, value) -> sb.
                    append("\t\t\"")
                    .append(key)
                    .append("\" = \"")
                    .append(value)
                    .append("\"\n")
            );
        }

        return sb.toString();
    }
}