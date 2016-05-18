package com.test;

import com.tcl.mie.userforum.api.client.UserforumClient;
import com.tcl.mie.userforum.api.vo.PostDetailQuery;
import com.tcl.mie.userforum.api.vo.PostResult;

import java.nio.channels.CompletionHandler;

/**
 * @author kelong
 * @date 11/24/15
 */
public class UserforumTest {
    public static void main(String[] args) {
        UserforumTest test=new UserforumTest();
        test.findPostById();
    }


    public void findPostById() {
        PostDetailQuery detailQuery = new PostDetailQuery();
        detailQuery.setId(17l);
        detailQuery.setPlatformId(10000);
        PostResult result = UserforumClient.getInstance().findPostById(detailQuery);
        System.out.print(result.getTitle());
    }

}
