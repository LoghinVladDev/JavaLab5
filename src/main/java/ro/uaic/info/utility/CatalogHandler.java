package ro.uaic.info.utility;

import ro.uaic.info.exception.InvalidBuildException;
import ro.uaic.info.exception.InvalidCatalogException;
import ro.uaic.info.exception.ViewTagException;
import ro.uaic.info.object.Catalog;
import ro.uaic.info.object.Document;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.util.Arrays;
import java.util.Scanner;

public class CatalogHandler {

    public static void save(Catalog catalog) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(catalog.getPath())
        )){
            objectOutputStream.writeObject(catalog);
        }
    }

    public static void savePlainText(Catalog catalog) throws IOException {
        try(FileOutputStream outputStream = new FileOutputStream(catalog.getPath().replaceAll(".ser", ".txt")
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
        }
    }

    public static Catalog loadPlainText(String path){
        try{
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            Catalog readCatalog = new Catalog.Builder()
                    .withName(
                            scanner.nextLine().split(";")[1].split("=")[1].trim()
                    )
                    .withPath(path)
                    .build();

            while(scanner.hasNextLine()) {
                String[] tokens = scanner
                        .nextLine()
                        .replaceFirst("Doc;", "")
                        .split(";");

                Document currentDoc = new Document.Builder()
                        .withName(tokens[0].split("=")[1].trim())
                        .withID(tokens[1].split("=")[1].trim())
                        .withLocation(tokens[2].split("=")[1].trim())
                        .build();

                String[] tags = tokens[3]
                        .split(":")[1]
                        .trim()
                        .replaceAll("[{}]+", "")
                        .trim()
                        .split(",");

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


