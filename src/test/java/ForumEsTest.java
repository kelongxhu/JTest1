import com.es.forum.model.PostMeta;
import com.es.forum.model.PostModel;
import com.es.forum.service.PostEsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author kelong
 * @date 10/28/15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class ForumEsTest {
    @Resource
    private PostEsService postEsService;

    @Test
    public void addIndexTest()throws Exception{
        PostMeta postMeta=new PostMeta();
        postMeta.setId("5");
        postMeta.setTitle("wwwwww");
        postMeta.setContent("55555555555555");
        PostModel postModel=new PostModel(postMeta);
        postEsService.addPostDoc(postModel);
    }
}
