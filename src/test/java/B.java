/**
 * Created by kelong on 8/27/14.
 */
public class B extends A {
    @Override
    public void a() {
        System.out.println("B");
    }

    public void bb(String a, String... b) {
        System.out.println(a);
        if(b!=null){
            for(String i:b){
                System.out.println(":"+i);
            }
        }
    }

    public static void main(String[] args) {
        B b = new B();
        b.a();
        b.bb("hello");
        b.bb("hello","1");
        b.bb("hello","1","2");
    }

}