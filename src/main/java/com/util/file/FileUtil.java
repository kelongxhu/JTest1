package com.util.file;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;


/**
 * @describe 文件操作, 主要针对日志和统计信息
 */

public class FileUtil {

    private final static Logger LOG = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 向文件中增量写文件
     *
     * @param path
     * @param value
     */
    public static void writeToFileByLock(String path, String value) {
        RandomAccessFile rf = null;
        FileChannel fc = null;
        FileLock lock = null;
        ByteBuffer byteBuffer = null;
        try {
            File temp = new File(path);
            if (!temp.exists()) {
                File dir=temp.getParentFile();
                if(!dir.exists()){
                    dir.mkdirs();
                }
            }
            rf = new RandomAccessFile(path, "rw");
            fc = rf.getChannel(); // 获得文件通道
            byteBuffer = ByteBuffer.wrap(StringUtil.append(value, "\n")
                .getBytes());
            while (true) {
                try {
                    lock = fc.lock(fc.size() + 1, byteBuffer.limit(), false);// 获取阻塞锁
                    break;
                } catch (OverlappingFileLockException e) {
                    Thread.sleep(1 * 1000);
                    LOG.info("file locked");
                }
            }
            if (null != lock) {
                fc.position(fc.size());
                fc.write(byteBuffer);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                if (lock != null && lock.isValid()) {
                    lock.release();
                }
                if (fc != null && fc.isOpen()) {
                    fc.close();
                    fc = null;
                }
                if (rf != null) {
                    rf.close();
                    rf = null;
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    /**
     * write log to day file
     *
     * @param logPath:file path
     * @param jsonData:file data
     */
    public static void writeDayLog(String logPath, String jsonData) {
        logPath =
            StringUtil.append(logPath, DateUtil.fomartDateToStr("yyyyMMdd", new Date()), ".log");
        writeToFileByLock(logPath, jsonData);
    }

    public static String getPath(String filename) {
        String baseDir = FileUtil.class.getResource("/").getPath();
        String fullPath = StringUtil.append(baseDir, filename);
        fullPath =
            System.getProperty("os.name").contains("indow") ? fullPath.substring(1) : fullPath;
        return fullPath;
    }

    public static File getConfigFile(String filename) {
        Path path = Paths.get(getPath(filename));
        if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
            path = path.getParent().resolveSibling(StringUtil.append("classes/", filename));
        }

        return path.toFile();
    }

}
