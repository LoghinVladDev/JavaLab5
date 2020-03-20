package ro.uaic.info.utility;

import ro.uaic.info.exception.InvalidBuildException;
import ro.uaic.info.exception.InvalidCatalogException;
import ro.uaic.info.object.Catalog;
import ro.uaic.info.object.Document;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class LoadCommand implements Command {

    private FileHandle type;
    private String path;

    public static class Builder{
        private String path;
        private FileHandle type;

        public Builder withPath(String path){
            this.path = path;
            return this;
        }

        public Builder withType(FileHandle type){
            this.type = type;
            return this;
        }

        public LoadCommand build(){
            LoadCommand cmd = new LoadCommand();

            cmd.path = this.path;
            cmd.type = this.type;

            return cmd;
        }
    }

    public Catalog loadPlainText(){
        try{
            File file = new File(this.path);
            Scanner scanner = new Scanner(file);

            Catalog readCatalog = new Catalog.Builder()
                    .withName(
                            scanner.nextLine().split(";")[1].split("=")[1].trim()
                    )
                    .withPath(this.path)
                    .build();

            while(scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().replaceFirst("Doc;", "").split(";");

                Document currentDoc = new Document.Builder()
                        .withName(tokens[0].split("=")[1].trim())
                        .withID(tokens[1].split("=")[1].trim())
                        .withLocation(tokens[2].split("=")[1].trim())
                        .build();

                String[] tags = tokens[3].split(":")[1].trim().replaceAll("[{}]+", "").trim().split(",");

                Arrays.stream(tags).forEach(e -> {e = e.trim(); currentDoc.addTag(
                        e.split("=")[0].trim(), e.split("=")[1].trim()
                ); } );

                readCatalog.add(currentDoc);

            }

            return readCatalog;
        } catch (FileNotFoundException | InvalidBuildException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Catalog loadSerialized() throws InvalidCatalogException {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream(this.path)
        )){
            return (Catalog) objectInputStream.readObject();
        }
        catch (ClassNotFoundException | IOException exception){
            throw new InvalidCatalogException(exception);
        }
    }

    private LoadCommand(){

    }

    public Object execute(){
        try {
            switch (this.type) {
                case PLAIN_TEXT: return this.loadPlainText();
                case SERIALIZED: return this.loadSerialized();
                default: return null;
            }
        }
        catch(InvalidCatalogException e){
            System.out.println(e.toString());
        }

        return null;
    }

    public boolean hasResult(){
        return true;
    }
}
