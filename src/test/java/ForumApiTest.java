import com.tcl.mie.userforum.api.client.UserforumClient;
import com.tcl.mie.userforum.api.client.UserforumServiceManager;
import com.tcl.mie.userforum.api.vo.PostDetailQuery;
import com.tcl.mie.userforum.api.vo.PostResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author kelong
 * @date 11/24/15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class ForumApiTest {
    @Test
    public void findPostById() {
        PostDetailQuery detailQuery = new PostDetailQuery();
        detailQuery.setId(17l);
        detailQuery.setPlatformId(10000);
        PostResult result = UserforumClient.getInstance().findPostById(detailQuery);
        System.out.print(result.getTitle());
    }

}
