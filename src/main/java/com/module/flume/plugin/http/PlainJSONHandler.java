package com.module.flume.plugin.http;

/**
 * @author codethink
 * @date 12/12/16 2:32 PM
 */
import com.google.gson.JsonParser;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.event.JSONEvent;
import org.apache.flume.source.http.HTTPSourceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.*;
/**
 * PlainJSONHandler for HTTPSource that accepts json-based http body.
 *
 * This handler throws exception if the deserialization fails because of bad
 * format or any other reason.
 */
public class PlainJSONHandler implements HTTPSourceHandler {

    private static final String FORWARD_HEADERS = "forwardHeaders";
    private static final Logger LOG =
        LoggerFactory.getLogger(PlainJSONHandler.class);
    private static JsonParser parser = new JsonParser();
    private static Set<String> forwardHeaders = new HashSet<String>();

    public List<Event> getEvents(HttpServletRequest request) throws Exception {
        Map<String,String> eventHeaders = new HashMap<String,String>();
        Enumeration requestHeaders = request.getHeaderNames();
        while (requestHeaders.hasMoreElements()) {
            String headerName = (String) requestHeaders.nextElement();
            if (forwardHeaders.contains(headerName)) {
                eventHeaders.put(headerName, request.getHeader(headerName));
            }
        }
        BufferedReader reader = request.getReader();
        List<Event> eventList = new ArrayList<Event>(1);
        StringBuffer lineBuffer = new StringBuffer();
        boolean tag;
        do {
            lineBuffer.append(reader.readLine());
        } while (tag = reader.read() != -1);
        if (lineBuffer != null) {
            Event event = new JSONEvent();
            event.setBody(lineBuffer.toString().getBytes());
            event.setHeaders(eventHeaders);
            eventList.add(event);

            LOG.info("=Event body:" + new String(event.getBody()));
        }
        return eventList;
    }
    public void configure(Context context) {
        String confForwardHeaders = context.getString(FORWARD_HEADERS);
        if (confForwardHeaders != null) {
            if (forwardHeaders.addAll(Arrays.asList(confForwardHeaders.split(",")))) {
                LOG.debug("forwardHeaders=" + forwardHeaders);
            } else {
                LOG.error("error to get forward headers from " + confForwardHeaders);
            }
        } else {
            LOG.debug("no forwardHeaders");
        }
    }
}
