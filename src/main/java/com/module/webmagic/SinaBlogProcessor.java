package com.module.webmagic;

/**
 * @author kelong
 * @date 4/5/16
 */
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 */
public class SinaBlogProcessor implements PageProcessor {

    public static final String URL_LIST = "http://blog\\.sina\\.com\\.cn/s/articlelist_1487828712_0_\\d+\\.html";

    public static final String URL_POST = "http://blog\\.sina\\.com\\.cn/s/blog_\\w+\\.html";

    private Site site = Site
        .me()
        .setDomain("blog.sina.com.cn")
        .setSleepTime(3000)
        .setUserAgent(
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    public void process(Page page) {

        System.out.println("==================================================================");
        System.out.println("page Url:"+page.getUrl());
        //列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            List<String>
                s1=page.getHtml().xpath("//div[@class=\"articleList\"]").links().regex(URL_POST).all();
            List<String> s2=page.getHtml().links().regex(URL_LIST).all();
            page.addTargetRequests(s1);
            //page.addTargetRequests(s2);

            //文章页
        } else {
            page.putField("title", page.getHtml().xpath("//div[@class='articalTitle']/h2"));
            page.putField("content", page.getHtml().xpath("//div[@id='articlebody']//div[@class='articalContent']"));
            page.putField("date",
                page.getHtml().xpath("//div[@id='articlebody']//span[@class='time SG_txtc']").regex("\\((.*)\\)"));
        }

        System.out.println("title:"+page.getResultItems().get("title"));
       /// System.out.println("content:"+page.getResultItems().get("content"));
        System.out.println("date"+page.getResultItems().get("date"));

    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new SinaBlogProcessor()).addUrl("http://blog.sina.com.cn/s/articlelist_1487828712_0_1.html")
            .run();
    }
}
