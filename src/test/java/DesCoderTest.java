import java.util.concurrent.TimeUnit;

/**
 * @author kelong
 * @date 10/8/14
 */
public class DesCoderTest {
    //    private static volatile boolean isStop = false;
    private static boolean isStop = false;
    private static int i = 0;

    public static void main(String[] args) throws Exception {
        //       System.out.println(DESCoder.initKey("longke"));
        //       System.out.println(StringUtils.format(1));
        //       System.out.println(System.currentTimeMillis());
        //       System.out.println(DateUtil.fomartTimeMillisToStr("yyyyMMddHHmmsss",1402128000*1000l));

        new Thread(new Runnable() {
            public void run() {
                while (!isStop) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
        for (; ; ) {
            i++;
            TimeUnit.SECONDS.sleep(3);
        }

        //        isStop = true;
    }
}
