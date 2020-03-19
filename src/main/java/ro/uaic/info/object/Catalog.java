package ro.uaic.info.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Catalog implements Serializable {
    private String name;
    private String path;
    private List<Document> documentList;

    public static class Builder{
        private String name;
        private String path;
        private List<Document> documentList;

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withPath(String path){
            this.path = path;
            return this;
        }

        public Builder withDocuments(Document ... documents){
            this.documentList = new ArrayList<>();
            this.documentList.addAll(Arrays.asList(documents));
            return this;
        }

        public Builder withDocuments(List<Document> documents){
            this.documentList = new ArrayList<>();
            this.documentList.addAll(documents);
            return this;
        }

        public Catalog build(){
            Catalog catalog = new Catalog();
            catalog.name = this.name;
            catalog.path = this.path;
            if(this.documentList == null)
                catalog.documentList = new ArrayList<>();
            else
                catalog.documentList = this.documentList;

            return catalog;
        }
    }

    private Catalog(){

    }

    public String getName() {
        return this.name;
    }

    public List<Document> getDocumentList() {
        return this.documentList;
    }

    public List<String> getIDs() {
        return this.documentList.stream().map(Document::getId).collect(Collectors.toList());
    }

    public Catalog(String name, String path){
        this.name = name;
        this.path = path;
        this.documentList = new ArrayList<>();
    }

    public void add(Document doc){
        this.documentList.add(doc);
    }

    public Document findById(String id){
        return this.documentList
                .stream()
                .filter(d -> d.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public String toString(){
        return "Catalog \""
                + this.name
                + "\"\n\tpath = "
                + this.path
                + "\n\t"
                + "documents :\n"
                + this.documentList.toString();
    }

    public String getPath(){
        return this.path;
    }
}
