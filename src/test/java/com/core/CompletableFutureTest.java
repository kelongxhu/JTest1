package com.core;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author ke.long
 * @since 2019/6/20 14:36
 */
@Slf4j
public class CompletableFutureTest {
    @Test
    public void thenApplyExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApply(s -> {
            assertFalse(Thread.currentThread().isDaemon());
            return s.toUpperCase();
        });
        assertEquals("MESSAGE", cf.getNow(null));
    }

    @Test
    public void thenApplyAsyncExample() {
        CompletableFuture<String>cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            assertTrue(Thread.currentThread().isDaemon());
            return s.toUpperCase();
        });
        log.info(cf.getNow(null));
        log.info(cf.join());
        //assertNull(cf.getNow(null));
        //assertEquals("MESSAGE", cf.join());
    }

    @Test
    public void thenAcceptExample() {
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture("thenAccept message")
                .thenAccept(s ->result.append(s));
        log.info(result.toString());
        assertTrue("Result was empty", result.length() > 0);
    }

    @Test
    public void allOfAsyncExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(s -> s.toUpperCase()))
                .collect(Collectors.toList());
        CompletableFuture allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
                //.whenComplete((v, th) -> {
                //    futures.forEach(cf -> {
                //        log.info("{}",cf.getNow(null));
                //    });
                //    result.append("done");
                //});
        allOf.join();
        log.info("result:{}",result.toString());
        assertTrue("Result was empty", result.length() > 0);
    }


    @Test
    public void thenCombineAsyncExample() {
        String original = "Message";
        CompletableFuture cf = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> s.toUpperCase())
                .thenCombine(CompletableFuture.completedFuture(original).thenApplyAsync(s -> s.toLowerCase()),
                        (s1, s2) -> s1 + s2);
        assertEquals("MESSAGEmessage", cf.join());
    }
}
