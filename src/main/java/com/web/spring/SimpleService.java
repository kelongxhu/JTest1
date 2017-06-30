package com.web.spring;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author kelong
 * @date 1/7/16
 */

public class SimpleService {

    private TransactionTemplate transactionTemplate;

    public Object someServiceMethod() {
        return transactionTemplate.execute(new TransactionCallback() {
            public Object doInTransaction(TransactionStatus status) {
                Object object = new Object();
                System.out.println();
                System.out.println();
                return object;
            }
        });
    }
}
