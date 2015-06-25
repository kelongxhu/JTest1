package com.thrift;

import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author kelong
 * @date 11/26/14
 */
public class ThreadPoolClientTest {
    public Logger LOG = Logger.getLogger(ThreadPoolClientTest.class);
    final static ExecutorService service = Executors.newCachedThreadPool();

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                for (int i = 0; i < 5; i++) {
                    service.execute(new TFramedTransportClient(i));
                }
                System.out.println("总耗时:" + (System.currentTimeMillis() - start));
            }
        }).start();
    }
}


class client implements Runnable {
    private int i;

    public client(int i) {
        this.i = i;
    }

    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()+"====================");
            TTransport transport = new TSocket("0.0.0.0", 9813);
            long start = System.currentTimeMillis();
            TProtocol protocol = new TBinaryProtocol(transport);
            IndexNewsOperatorServices.Client client = new IndexNewsOperatorServices.Client(protocol);
            transport.open();
//            client.deleteArtificiallyNews(123456);
            NewsModel newsModel = new NewsModel();
            newsModel.setId(789456);
            newsModel.setTitle("this from java client");
            newsModel.setContent("content..");
            newsModel.setAuthor("ddc");
            newsModel.setMedia_from("新华=================>"+i);
            boolean flag=client.indexNews(newsModel);
            transport.close();
            System.out.println((System.currentTimeMillis() - start) + "client sucess!" + i+",flag:"+flag);
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}

class TFramedTransportClient implements Runnable {
    private int i;

    public TFramedTransportClient(int i) {
        this.i = i;
    }

    public void run() {
        try {
            long start = System.currentTimeMillis();
            TTransport transport = new TFramedTransport(new TSocket("0.0.0.0", 9813));
            transport.open();
            TProtocol protocol = new TCompactProtocol(transport);
            IndexNewsOperatorServices.Client client = new IndexNewsOperatorServices.Client(protocol);
//            client.deleteArtificiallyNews(123456);
            NewsModel newsModel = new NewsModel();
            newsModel.setId(789456);
            newsModel.setTitle("this from java client");
            newsModel.setContent("content..");
            newsModel.setAuthor("ddc");
            newsModel.setMedia_from("新华"+i);
            boolean flag=client.indexNews(newsModel);
            transport.close();
            System.out.println(Thread.currentThread().getName()+"====>"+(System.currentTimeMillis() - start) + "client sucess!" + i+",flag:"+flag);
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
