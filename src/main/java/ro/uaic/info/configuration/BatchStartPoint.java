package ro.uaic.info.configuration;

import ro.uaic.info.utility.FileHandle;
import ro.uaic.info.utility.LoadCommand;

import java.util.Arrays;



public class BatchStartPoint {


    public static void loadCall(String arg){
        System.out.println(new LoadCommand.Builder()
                .withPath(arg)
                .withType(arg.endsWith(".ser") ? FileHandle.SERIALIZED :
                        FileHandle.PLAIN_TEXT)
                .build()
                .execute()
        );
    }

    public static void saveCall(String arg){

    }

    public static void viewCall(String arg){

    }

    public static void main(String[] args) {
        switch(args[0].toLowerCase()){
            case "load": loadCall(args[1]);
            case "save": saveCall(args[1]);
            case "view": viewCall(args[1]);
        }
    }
}
