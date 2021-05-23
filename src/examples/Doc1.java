package examples;

public class Doc1 {
    private String name;
    private Doc2 doc;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    
    public void setDoc(Doc2 doc){
        this.doc = doc;
    }

    public Doc2 getDoc(){
        return doc;
    }
}
