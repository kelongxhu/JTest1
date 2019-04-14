package com.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class WristBandDao {
    public static Logger LOG = Logger.getLogger(AmoebaDao.class);

    @Resource
    private JdbcTemplate jdbcTemplate;

    public static String tableName = "activity";

    public void put() {
        try {
            LOG.info("start...");
            String countSql = "select count(1) from activity where end_time<>0 and start_time<>0";
            int count = this.jdbcTemplate.queryForObject(countSql,Integer.class);
            Executor service = Executors.newFixedThreadPool(4);
            long start = System.currentTimeMillis();
            int pageSize = 10000;
            int size = count / pageSize;
            LOG.info("split size:" + size);
            for (int j = 0; j < size + 1; j++) {
                int startCount = j * pageSize;
                String sql =
                    "select * from activity where end_time<>0 and start_time<>0 limit " + startCount
                        + "," + pageSize;
                List activityMap = this.jdbcTemplate.queryForList(sql);
                LOG.info("query OK." + j);
                Thread.sleep(1000 * 2);
                service.execute(new Task(activityMap, startCount));
            }
            long end = System.currentTimeMillis();
            LOG.info("END," + (end - start) / 1000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int n = 0;


    class Task implements Runnable {
        private List<Map<String, Object>> maps;
        private int start;

        public Task(List<Map<String, Object>> maps, int start) {
            this.maps = maps;
            this.start = start;
        }

        @Override
        public void run() {
            try {
//                long time = putActivitys(maps);
//                WristBandDao.LOG.info("====finish:" + start + ",耗时:" + time + ",N:" + n);
                n++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    public long putActivitys(List<Map<String, Object>> activitys) throws Exception {
//        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
//        for (Map<String, Object> object : activitys) {
//            long activityId = StringUtils.obj2long(object.get("activity_id"));
//            int credential_id = StringUtils.obj2int(object.get("credential_id"));
//            String credential_id_s = StringUtils.format(credential_id);
//            long start_time_l = StringUtils.obj2long(object.get("start_time"));
////            String start_time = DateUtil.fomartTimeMillisToStr("yyyyMMddHHmmsss",
//                start_time_l * 1000);
//            long end_time_l = StringUtils.obj2long(object.get("end_time"));
////            String end_time = DateUtil.fomartTimeMillisToStr("yyyyMMddHHmmsss", end_time_l * 1000);
//            String type = StringUtils.obj2Str(object.get("type"));
//            String rowKey =
//                StringUtils.append(
////                    getActivityType(type), credential_id_s, end_time, start_time);
//            String family = "activity";
//            object.remove("id");
//            object.remove("created_timestamp");
//            object.remove("updated_timestamp");
//            for (Map.Entry entry : object.entrySet()) {
//                String column = (String) entry.getKey();
//                String value = StringUtils.obj2Str(entry.getValue());
//                data.add(getData(tableName, rowKey, family, column, value));
//            }
//            //            String pointSql = "select * from activity_point where activity_id=" + activityId;
//            //            List<Map<String, Object>> points = this.jdbcTemplate.queryForList(pointSql);
//            //            if (points != null) {
//            //                int i = 0;
//            //                for (Map map : points) {
//            //                    String pointJosn = JSON.toJSONString(map);
//            //                    data.add(getData(tableName, rowKey, family, "point" + i, pointJosn));
//            //                    i++;
//            //                }
//            //            }
//            //            String slotSql = "select * from activity_slot where activity_id=" + activityId;
//            //            List<Map<String, Object>> slots = this.jdbcTemplate.queryForList(slotSql);
//            //            if (slots != null) {
//            //                int j = 0;
//            //                for (Map map : slots) {
//            //                    String slotJosn = JSON.toJSONString(map);
//            //                    data.add(getData(tableName, rowKey, family, "slots" + j, slotJosn));
//            //                    j++;
//            //                }
//            //            }
//        }
//        long start = System.currentTimeMillis();
//        HbaseHelper.batchPut(tableName, data);
//        long end = System.currentTimeMillis();
//        return end - start;
//    }

    public String getActivityType(String str) {
        String type = "00";
        if ("walking".equals(str)) {
            type = "01";
        } else if ("cycling".equals(str)) {
            type = "02";
        } else if ("hiking".equals(str)) {
            type = "03";
        } else if ("running".equals(str)) {
            type = "04";
        } else if ("boxer".equals(str)) {
            type = "05";
        } else if ("custom_activity".equals(str)) {
            type = "06";
        } else if ("activity".equals(str)) {
            type = "07";
        } else if ("yoga".equals(str)) {
            type = "08";
        } else if ("soccer".equals(str)) {
            type = "09";
        } else if ("basket_ball".equals(str)) {
            type = "10";
        } else if ("swimming".equals(str)) {
            type = "11";
        }
        return type;
    }

    private Map<String, String> getData(String tableName, String rowKey, String family, String qualifier, String value) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("tableName", tableName);
        data.put("rowKey", rowKey);
        data.put("family", family);
        data.put("qualifier", qualifier);
        data.put("value", value);
        return data;
    }
}
