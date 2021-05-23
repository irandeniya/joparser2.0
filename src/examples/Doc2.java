package examples;

public class Doc2 {
    private String name;
    private Doc3 doc;
    
    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setDoc(Doc3 doc){
        this.doc = doc;
    }

    public Doc3 getDoc(){
        return doc;
    }
}
