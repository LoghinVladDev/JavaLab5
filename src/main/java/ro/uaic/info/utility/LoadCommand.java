package ro.uaic.info.utility;

public class LoadCommand implements Command {
    private String path;

    public static class Builder{
        private String path;

        public enum type{
            PLAIN_TEXT,
            SERIALIZED
        }

        public Builder withPath(String path){
            this.path = path;
            return this;
        }

        public LoadCommand build(){
            LoadCommand cmd = new LoadCommand();

            cmd.path = this.path;

            return cmd;
        }
    }

    private LoadCommand(){

    }

    public Object execute(){
        return null;
    }

    public boolean hasResult(){
        return true;
    }
}
