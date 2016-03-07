package com.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kelong
 * @date 12/29/14
 */
public class HbaseHelper {
    static Logger LOG = Logger.getLogger(HbaseHelper.class);
    static HBaseConfiguration cfg = null;

    static {
        Configuration HBASE_CONFIG = HBaseConfiguration.create();
        HBASE_CONFIG.set("hbase.zookeeper.quorum", "172.26.50.24,172.26.50.28,172.26.50.65");
        HBASE_CONFIG.set("hbase.zookeeper.property.clientPort", "4181");
        cfg = new HBaseConfiguration(HBASE_CONFIG);
    }

    /**
     * create Table
     */
    public static void creatTable(String tableName, String[] familyArgs) throws IOException {
        HBaseAdmin admin = new HBaseAdmin(cfg);
        if (admin.tableExists(tableName)) {
            LOG.info("Table Exist.");
        } else {
            HTableDescriptor tableDesc = new HTableDescriptor(tableName);
            if (familyArgs != null && familyArgs.length > 0) {
                for (String family : familyArgs) {
                    tableDesc.addFamily(new HColumnDescriptor(family));
                }
            }
            admin.createTable(tableDesc);
            LOG.info("Create Success.");
        }
    }

    /**
     * insert Row
     *
     * @param tableName
     * @param rowKey
     * @param family
     * @param qualifier
     * @param value
     * @throws java.io.IOException
     */
    public static void insertRecord(String tableName, String rowKey, String family, String qualifier, String value) throws IOException {
        HTable table = new HTable(cfg, tableName);
        Put put = new Put(rowKey.getBytes());
        put.add(family.getBytes(), qualifier.getBytes(), value.getBytes());
        table.put(put);
    }


    /**
     * 批量put
     *
     * @param tableName
     * @param datas
     * @throws Exception
     */
    public static void batchPut(String tableName, List<Map<String, String>> datas) throws Exception {
        HTable table = new HTable(cfg, tableName);
        table.setAutoFlush(false);
        table.setWriteBufferSize(1024*1024*64);
        List<Put> lp = new ArrayList<Put>();
        for (Map<String, String> data : datas) {
            String rowKey = data.get("rowKey");
            String family = data.get("family");
            String qualifier = data.get("qualifier");
            String value = data.get("value");
            Put put = new Put(rowKey.getBytes());
            put.add(family.getBytes(), qualifier.getBytes(), value.getBytes());
            put.setWriteToWAL(false);
            lp.add(put);
        }
        table.put(lp);
    }

    /**
     * delete Record
     *
     * @param tableName
     * @param rowKey
     * @throws java.io.IOException
     */
    public static void deleteRecord(String tableName, String rowKey) throws IOException {
        HTable table = new HTable(cfg, tableName);
        Delete del = new Delete(rowKey.getBytes());
        table.delete(del);
    }

    /**
     * get Record By rowKey
     *
     * @param tableName
     * @param rowKey
     * @return
     * @throws java.io.IOException
     */
    public static Result getResult(String tableName, String rowKey) throws IOException {
        HTable table = new HTable(cfg, tableName);
        Get get = new Get(rowKey.getBytes());
        Result rs = table.get(get);
        return rs;
    }

    /**
     * get All Record
     *
     * @param tableName
     * @return
     * @throws java.io.IOException
     */
    public static List<Result> getResultScann(String tableName) throws IOException {
        HTable table = new HTable(cfg, tableName);
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);
        List<Result> list = new ArrayList<Result>();
        for (Result r : scanner) {
            list.add(r);
        }
        scanner.close();
        return list;
    }

    /**
     * @param tableName
     * @param start_rowkey
     * @param stop_rowkey
     * @return
     * @throws java.io.IOException
     */
    public static List<Result> getResultScann(String tableName, String start_rowkey,
                                              String stop_rowkey) throws IOException {
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(start_rowkey));
        scan.setStopRow(Bytes.toBytes(stop_rowkey));
        ResultScanner scanner = null;
        HTable table = new HTable(cfg, Bytes.toBytes(tableName));
        scanner = table.getScanner(scan);
        List<Result> list = new ArrayList<Result>();
        for (Result r : scanner) {
            list.add(r);
        }
        scanner.close();
        return list;
    }

    /**
     * @param tableName
     * @param rowKey
     * @param familyName
     * @return
     * @throws java.io.IOException
     */
    public static Result getResultByFamily(String tableName, String rowKey,
                                           String familyName) throws IOException {
        HTable table = new HTable(cfg, Bytes.toBytes(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addFamily(Bytes.toBytes(familyName));
        Result result = table.get(get);
        return result;
    }

    /**
     * @param tableName
     * @param rowKey
     * @param familyName
     * @param columnName
     * @return
     * @throws java.io.IOException
     */
    public static String getResultByColumn(String tableName, String rowKey,
                                           String familyName, String columnName) throws IOException {
        HTable table = new HTable(cfg, Bytes.toBytes(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName)); // 获取指定列族和列修饰符对应的列
        Result result = table.get(get);
        byte[] value = result.getValue(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
        return new String(value);
    }
}
