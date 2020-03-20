package ro.uaic.info.utility;

import ro.uaic.info.exception.InvalidCatalogException;
import ro.uaic.info.object.Catalog;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveCommand implements Command {

    private FileHandle type;
    private Catalog catalog;

    public static class Builder{
        private Catalog catalog;
        private FileHandle type;

        public Builder withCatalog(Catalog catalog){
            this.catalog = catalog;
            return this;
        }

        public Builder withType(FileHandle type){
            this.type = type;
            return this;
        }

        public SaveCommand build(){
            SaveCommand cmd = new SaveCommand();

            cmd.type = this.type;
            cmd.catalog = this.catalog;

            return cmd;
        }
    }

    private SaveCommand(){

    }

    private boolean savePlainText() throws IOException{
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(
                        catalog.getPath().replaceAll(".txt", ".ser"))
        )){
            objectOutputStream.writeObject(catalog);
            return true;
        }
    }

    private boolean saveSerialized() throws IOException{
        try(FileOutputStream outputStream = new FileOutputStream(
                catalog.getPath().replaceAll(".ser", ".txt")
        )){
            StringBuilder sb = new StringBuilder();
            sb.append("Catalog;name = ").append(catalog.getName()).append("\n");
            catalog.getDocumentList().forEach(c -> {
                        sb.append("Doc;name = ")
                                .append(c.getName())
                                .append(";id = ")
                                .append(c.getId())
                                .append(";location = ")
                                .append(c.getLocation())
                                .append(";tags : { ");
                        c.getTags().forEach((key, value) -> sb.append(key)
                                .append(" = ")
                                .append(value)
                                .append(", "));
                        sb.append("} \n");

                    }
            );
            //System.out.println(sb.toString());
            outputStream.write(sb.toString().getBytes());
            return true;
        }
    }

    public Object execute() {
        try{
            switch(this.type) {
                case PLAIN_TEXT:
                    return this.savePlainText();
                case SERIALIZED:
                    return this.saveSerialized();
                default:
                    return false;
            }
        }
        catch (IOException e){
            System.out.println(e.toString());
        }
        return false;
    }

    public boolean hasResult() {
        return false;
    }
}
