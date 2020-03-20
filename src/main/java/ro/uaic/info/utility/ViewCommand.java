package ro.uaic.info.utility;

import ro.uaic.info.exception.ViewTagException;
import ro.uaic.info.object.Document;

import java.awt.*;
import java.io.File;
import java.net.URI;

public class ViewCommand implements Command{
    private Document document;

    public static class Builder{
        private Document document;

        public Builder withDocument(Document document){
            this.document = document;
            return this;
        }

        public ViewCommand build(){
            ViewCommand cmd = new ViewCommand();

            cmd.document = this.document;

            return cmd;
        }
    }

    private ViewCommand(){

    }

    private boolean view() throws ViewTagException {
        try{
            Desktop desktop = Desktop.getDesktop();

            if(this.document.getLocation().startsWith("http"))
                desktop.browse(new URI(this.document.getLocation()));
            else if(Tag.isViewAvailable(this.document))
                desktop.open(new File(this.document.getLocation()));
            else
                throw new ViewTagException("No available view for document");
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        return false;
    }

    public Object execute() {
        try{
            return this.view();
        }
        catch (ViewTagException e){
            System.out.println(e.toString());
        }
        return false;
    }

    public boolean hasResult() {
        return false;
    }
}
