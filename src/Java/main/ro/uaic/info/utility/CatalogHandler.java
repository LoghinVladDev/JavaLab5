package ro.uaic.info.utility;

import ro.uaic.info.exception.InvalidCatalogException;
import ro.uaic.info.object.Catalog;

import java.io.*;

public class CatalogHandler {

    public static void save(Catalog catalog) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(catalog.getPath())
        )){
            objectOutputStream.writeObject(catalog);
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
}


