# JOParser2.0 - Java Object - XML serializer
## Java encapsulated objects serialize into XML format and reverse.

## _Sample Output_
```sh
<?xml version="1.0" encoding="UTF-8"?>
<object class="examples.Doc1">
   <param>
      <name>name</name>
      <object class="java.lang.String">a1</object>
   </param>
   <param>
      <name>doc</name>
      <object class="examples.Doc2">
         <param>
            <name>name</name>
            <object class="java.lang.String">b1</object>
         </param>
         <param>
            <name>doc</name>
            <object class="examples.Doc3">
               <param>
                  <name>name</name>
                  <object class="java.lang.String">c1</object>
               </param>
            </object>
         </param>
      </object>
   </param>
</object>
```

## _Demo on YouTube_
https://youtu.be/zq-frk10nP4

## _Example_
```sh
public class JOParserExample {
    public static void main(String[] args) throws Exception {
        Doc1 dc = new Doc1();
        dc.setName("a1");

        Doc2 dc2 = new Doc2();
        dc.setDoc(dc2);
        dc2.setName("b2");

        Doc3 dc3 = new Doc3();
        dc2.setDoc(dc3);
        dc3.setName("c3");

        String tx = jop.Parser.getParser().createXMLForObject(dc);
        System.out.println(tx);
        # output: 
        # <object class="examples.Doc1"><param><name>name</name><object class="java.lang.String">a1</object></param><param><name>doc</name><object class="examples.Doc2"><param><name>name</name><object class="java.lang.String">b2</object></param><param><name>doc</name><object class="examples.Doc3"><param><name>name</name><object class="java.lang.String">c3</object></param></object></param></object></param></object>      

        Doc1 nw1 = (Doc1) jop.Parser.getParser().createObjectForXML(tx);
        System.out.println(nw1.getName());
        System.out.println(nw1.getDoc().getName());
        System.out.println(nw1.getDoc().getDoc().getName());
        #output: 
        # a1
        # b2
        # c3
    }
}
```

## _Class hierarchy_
Example:
```sh
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

public class Doc3 {
    private String name;
    
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
```