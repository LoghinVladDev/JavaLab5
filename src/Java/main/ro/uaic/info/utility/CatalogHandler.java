package ro.uaic.info.utility;

import ro.uaic.info.exception.InvalidCatalogException;
import ro.uaic.info.exception.ViewTagException;
import ro.uaic.info.object.Catalog;
import ro.uaic.info.object.Document;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CatalogHandler {

    public static void save(Catalog catalog) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(catalog.getPath())
        )){
            objectOutputStream.writeObject(catalog);
        }
    }

    public static void savePlainText(Catalog catalog) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(catalog.getPath().replaceAll(".ser", ".txt"))
        )){
            StringBuilder sb = new StringBuilder();
            catalog.getDocumentList().forEach(c -> {
                        sb.append("Doc, name = ")
                            .append(c.getName())
                            .append(" id = ")
                            .append(c.getId())
                            .append(" location = ")
                            .append(c.getLocation())
                            .append(" tags = { ");
                            c.getTags().forEach((key, value) -> sb.append(key)
                                .append(" = ")
                                .append(value)
                                .append(", "));
                        sb.append("}\n");

                    }
            );
            System.out.println(sb.toString());
            objectOutputStream.write(sb.toString().getBytes(StandardCharsets.US_ASCII));
        }
    }

    public static Catalog load(String path) throws InvalidCatalogException {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream(path)
        )){
            return (Catalog) objectInputStream.readObject();
        }
        catch (ClassNotFoundException | IOException exception){
            throw new InvalidCatalogException(exception);
        }
    }

    public static void view(Document document) throws ViewTagException {
        try{
            Desktop desktop = Desktop.getDesktop();

            if(document.getLocation().startsWith("http"))
                desktop.browse(new URI(document.getLocation()));
            else if (Tag.isViewAvailable(document))
                desktop.open(new File(document.getLocation()));
            else
                throw new ViewTagException("No available view for document");
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
}


