import com.redis.A;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by kelong on 7/10/14.
 */
public class Test1 extends TestCase {
    @Before
    public void before() {
    }

    @Test
    public void test1() {
        A a = new A();
//        System.out.println("======:"+"test".hashCode());
//        assertEquals(5, a.add(2, 3));

        String s = "58.215.204.118 - - [18/Sep/2013:06:51:35 +0000] \"GET /nodejs-socketio-chat/ HTTP/1.1\" 200 10818 \"http://www.google.com/url?sa=t&rct=j&q=nodejs%20%E5%BC%82%E6%AD%A5%E5%B9%BF%E6%92%AD&source=web&cd=1&cad=rja&ved=0CCgQFjAA&url=%68%74%74%70%3a%2f%2f%62%6c%6f%67%2e%66%65%6e%73%2e%6d%65%2f%6e%6f%64%65%6a%73%2d%73%6f%63%6b%65%74%69%6f%2d%63%68%61%74%2f&ei=rko5UrylAefOiAe7_IGQBw&usg=AFQjCNG6YWoZsJ_bSj8kTnMHcH51hYQkAA&bvm=bv.52288139,d.aGc\" \"Mozilla/5.0 (Windows NT 5.1; rv:23.0) Gecko/20100101 Firefox/23.0\"";
        String[] arr = s.split(" ");
        for (String i : arr) {
            System.out.println("value:" + i);
        }


        System.out.println("UUID:"+ UUID.randomUUID().toString().replaceAll("-", ""));
    }
}
