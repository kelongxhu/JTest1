package com.thrift;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportFactory;

import java.net.InetSocketAddress;

/**
 * @author kelong
 * @date 11/26/14
 */
public class TThreadedSelectorServerTest {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        IndexNewsOperatorServices.Processor processor = new IndexNewsOperatorServices.Processor(new IndexNewsOperatorServicesImpl());
        try {
            TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(new InetSocketAddress("0.0.0.0", 9813));
            TTransportFactory transportFactory = new TFramedTransport.Factory();
            TProtocolFactory proFactory = new TCompactProtocol.Factory();
            TThreadedSelectorServer.Args trArgs = new TThreadedSelectorServer.Args(serverTransport);
            trArgs.processor(processor);
            trArgs.protocolFactory(proFactory);
            trArgs.transportFactory(transportFactory);
//            trArgs.selectorThreads(10);
//            trArgs.workerThreads(10);
            TServer server = new TThreadedSelectorServer(trArgs);
            System.out.println("server begin ......................");
            server.serve();
            System.out.println("---------------------------------------");
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException("index thrift server start failed!!" + "/n" + e.getMessage());
        }

    }
}
