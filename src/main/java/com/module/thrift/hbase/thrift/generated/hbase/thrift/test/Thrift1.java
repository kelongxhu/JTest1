package com.module.thrift.hbase.thrift.generated.hbase.thrift.test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import com.module.thrift.hbase.thrift.generated.Hbase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class Thrift1 {
    public static String byteBufferToString(ByteBuffer buffer) {
        CharBuffer charBuffer = null;
        try {
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer);
            buffer.flip();
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ByteBuffer getByteBuffer(String str) {
        return ByteBuffer.wrap(str.getBytes());
    }

    private void start() {
        try {
            TTransport socket = new TSocket("172.26.50.24", 9090);// 我的虚拟机,线上用的thrift2
            TProtocol protocol = new TBinaryProtocol(socket, true, true);// 注意这里
            Hbase.Client client = new Hbase.Client(protocol);
            socket.open();
            System.out.println("open");
            try {
                System.out.println("scanning tables...");
                for (ByteBuffer name : client.getTableNames()) {
                    System.out.println("find:" + byteBufferToString(name));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            socket.close();
            System.out.println("close");
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thrift1 c = new Thrift1();
        c.start();

    }
}

