package examples;

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

        Doc1 nw1 = (Doc1) jop.Parser.getParser().createObjectForXML(tx);
        System.out.println(nw1.getName());
        System.out.println(nw1.getDoc().getName());
        System.out.println(nw1.getDoc().getDoc().getName());
    }
}
