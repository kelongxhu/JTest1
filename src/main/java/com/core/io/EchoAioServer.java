package com.core.io;
/*
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class EchoAioServer {

    private final int port;

    public static void main(String args[]) {
        int port = 8000;
        new EchoAioServer(port);
    }

    public EchoAioServer(int port) {
        this.port = port;
        listen();
    }

    private void listen() {
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            AsynchronousChannelGroup threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
//             try(AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(threadGroup)) {
            AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(threadGroup);
            server.bind(new InetSocketAddress(port));

            System.out.println("Echo listen on " + port);

            server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                final ByteBuffer echoBuffer = ByteBuffer.allocateDirect(1024);

                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    try {
                        System.out.println("waiting ...."+result.getRemoteAddress());
                        echoBuffer.clear();
                        result.read(echoBuffer).get();
                        echoBuffer.flip();
                        // echo data
                        result.write(echoBuffer);//////////////////////////////////////发送数据。。。。。。。。。。。
                        echoBuffer.flip();

                        StringBuilder sb = new StringBuilder();
                        while (echoBuffer.hasRemaining()) {
                            byte c = echoBuffer.get();
                            sb.append((char) c);
                        }
                        System.out.println("accept message:"+sb.toString());

//                        System.out.println("Echoed::::: '" + new String(echoBuffer.array()) + "' to " + result);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e.toString());
                    } finally {
                        try {
                            result.close();
//                            server.accept(null, this);
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    }

                    System.out.println("done...");
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("server failed: " + exc);
                }
            });

            try {
                // Wait for ever
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}*/

