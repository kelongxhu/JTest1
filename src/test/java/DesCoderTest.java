import com.util.DESCoder;
import com.util.DateUtil;
import com.util.StringUtils;

/**
 * @author kelong
 * @date 10/8/14
 */
public class DesCoderTest {
    public static void main(String[] args)throws Exception{
       System.out.println(DESCoder.initKey("longke"));
       System.out.println(StringUtils.format(1));
       System.out.println(System.currentTimeMillis());
//       System.out.println(DateUtil.fomartTimeMillisToStr("yyyyMMddHHmmsss",1402128000*1000l));
    }
}
