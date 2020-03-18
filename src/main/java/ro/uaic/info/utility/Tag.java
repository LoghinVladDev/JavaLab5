package ro.uaic.info.utility;

import ro.uaic.info.object.Document;

public class Tag {
    public static boolean isViewAvailable(Document document){
        if(document.getTags() == null)
            return false;
        switch(document.getTags().get("type").toString()){
            case "PowerPoint" :
            case "html" :
            case "pdf" : return true;
            default: return false;
        }
    }
}
