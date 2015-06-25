import com.util.HbaseHelper;
import com.util.StringUtils;
import org.junit.Test;

import java.util.*;

/**
 * @author kelong
 * @date 6/9/15
 */
public class PruductTest {
    @Test
    public void test() throws Exception {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (int i = 1; i <= 1000; i++) {
            String user_id_s = StringUtils.format(i);
            long times = Long.MAX_VALUE - System.currentTimeMillis();
            String product_id = StringUtils.format(100);
            String rowKey = StringUtils.append(user_id_s,"_", times,"_",product_id);
            HbaseHelper.insertRecord("product_sale", rowKey, "name", "prudct", "JAVA");
            Thread.sleep(1000 * 2);
        }
    }
    @Test
    public void weibo()throws Exception{
//        HbaseHelper.creatTable("account_weibo",new String[]{"info"});
        for (int i = 1; i <= 1000; i++) {
            String user_id_s = StringUtils.format(i);
            String rowKey2 = StringUtils.append(user_id_s);
            String rowKey="000000001";
            HbaseHelper.insertRecord("account_weibo", rowKey, "info", "fans_"+rowKey2, rowKey2+"="+1);
//            Thread.sleep(1000 * 2);
        }
    }
}
