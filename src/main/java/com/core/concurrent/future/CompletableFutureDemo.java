package com.core.concurrent.future;

import com.google.common.base.Joiner;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ke.long
 * @since 2019/4/29 22:32
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws Exception {
        //supplyAsync();
        testAllOf();
        //testAnyOf();
        //testRunAsync();
        //thenApply();
        //testExceptionally();
        //testWhenComplete();
    }

    public static void supplyAsync() {

        Random random = new Random();
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "from future1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {

            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "from future2";
        });

        CompletableFuture<Void> future = future1.acceptEither(future2,
                str -> System.out.println("The future is " + str));

        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testAllOf() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "tony");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "cafei");
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "aaron");

        //System.out.println(future1.join());

        CompletableFuture.allOf(future1, future2, future3)
                .thenApply(v ->
                        Stream.of(future1, future2, future3)
                                .map(CompletableFuture::join)
                                .collect(Collectors.joining("   ")))
                .thenAccept(System.out::println);
    }


    public static void allOf(){

    }


    public static void testAnyOf() {
        Random rand = new Random();
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "from future1";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "from future2";
        });
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "from future3";
        });

        CompletableFuture<Object> future = CompletableFuture.anyOf(future1, future2, future3);
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testRunAsync() throws Exception {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().isDaemon());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("done=" + cf.isDone());
        TimeUnit.SECONDS.sleep(4);
        System.out.println("done=" + cf.isDone());
    }

    public static void thenApply() throws Exception {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message")
                .thenApplyAsync(s -> {
                    System.out.println(Thread.currentThread().isDaemon() + "_" + s);
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s.toUpperCase();
                })
                .thenApplyAsync(s->{
                    System.out.println("============================="+s);
                    return Joiner.on("=").join(s,"test");
                });

        System.out.println("done=" + cf.isDone());
        //TimeUnit.SECONDS.sleep(4);
        System.out.println("done=" + cf.isDone());
        // 一直等待成功，然后返回结果
        System.out.println(cf.join());
    }

    public static void testExceptionally() {
        CompletableFuture.supplyAsync(() -> "hello world")
                .thenApply(s -> {
                    // s = null;
                    int length = s.length();
                    return length;
                }).thenAccept(i -> System.out.println(i))
                .exceptionally(t -> {
                    System.out.println("Unexpected error!!!:" + t);
                    return null;
                });
    }

    public static void testWhenComplete() {
        CompletableFuture.supplyAsync(() -> "hello world")
                .thenApply(s -> {
                    // s = null;
                    int length = s.length();
                    return length;
                }).thenAccept(i -> System.out.println(i))
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        System.out.println("Unexpected error:" + throwable);
                    } else {
                        System.out.println(result);
                    }
                });
    }
}
