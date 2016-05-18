import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author codethink
 * @date 5/12/16 1:35 PM
 */
public class GuavaTest {
    @Test
    public void test() {
        Joiner joiner = Joiner.on("; ").skipNulls();
        String s = joiner.join("Harry", null, "Ron", "Hermione");
        System.out.println(s);
        System.out.println(Joiner.on(",").join(Arrays.asList(1, 5, 7)));
        System.out.println(Joiner.on("-").join("2011", "08", "04"));
        Iterator<String> it=Splitter.on(',').split("a,b").iterator();
        while (it.hasNext()){
            System.out.print("="+it.next());
        }
    }

    @Test
    public void guavaCacheTest() throws Exception {
        LoadingCache<String, String> cahceBuilder = CacheBuilder
            .newBuilder()
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    String strProValue = "hello " + key + "!";
                    return strProValue;
                }

            });

        System.out.println("jerry value:" + cahceBuilder.apply("jerry"));
        System.out.println("jerry value:" + cahceBuilder.get("jerry"));
        System.out.println("peida value:" + cahceBuilder.get("peida"));
        System.out.println("peida value:" + cahceBuilder.apply("peida"));
        System.out.println("lisa value:" + cahceBuilder.apply("lisa"));
        cahceBuilder.put("harry", "ssdded");
        System.out.println("harry value:" + cahceBuilder.get("harry"));
    }

    @Test
    public void collectionTest() {
        Set<String> wordsWithPrimeLength =
            ImmutableSet.of("one", "two", "three", "six", "seven", "eight");
        Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");
        Sets.SetView<String> intersection = Sets.intersection(primes, wordsWithPrimeLength);
        // intersection包含"two", "three", "seven"
        //        ImmutableSet<String> result=intersection.immutableCopy();

        Sets.SetView<String> x = Sets.difference(wordsWithPrimeLength, primes);
        Iterator<String> ss = x.iterator();
        while (ss.hasNext()) {
            System.out.println("============"+ss.next());
        }
    }

    public String createGuava() {
        LoadingCache<String, String> graphs = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .build(
                new CacheLoader<String, String>() {
                    public String load(String key) throws Exception {
                        return "";
                        //return createExpensiveGraph(key);
                    }
                });
        try {
            return graphs.get("");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }
}
