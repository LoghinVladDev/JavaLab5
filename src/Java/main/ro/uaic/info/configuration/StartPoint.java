package ro.uaic.info.configuration;

import ro.uaic.info.object.Catalog;
import ro.uaic.info.object.Document;
import ro.uaic.info.utility.CatalogHandler;

import java.util.HashMap;

public class StartPoint {
    public static void main(String[] args) {
        try {
            Catalog c1 = new Catalog.Builder()
                    .withName("Java Resources")
                    .withPath("D:/LFuck/java/resources/l5.ser")
                    .withDocuments(
                            new Document.Builder()
                                    .withID("JC0")
                                    .withLocation("https://profs.info.uaic.ro/~acf/java/slides/en/intro_slide_en.pdf")
                                    .withName("JavaCourse0")
                                    .build()
                                    .addTag("type", "PowerPoint"),
                            new Document.Builder()
                                    .withID("JL5")
                                    .withLocation("https://profs.info.uaic.ro/~acf/java/labs/lab_05.html")
                                    .withName("Lab 5")
                                    .build()
                                    .addTag("type", "html")
                                    .addTag("size", 16)
                    )
                    .build();

            CatalogHandler.save(c1);
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

        try {
            Catalog c2 =CatalogHandler.load("D:/Lfuck/java/resources/l5.ser");

            for(String id : c2.getIDs()){
                System.out.println(c2.findById(id));
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
}
