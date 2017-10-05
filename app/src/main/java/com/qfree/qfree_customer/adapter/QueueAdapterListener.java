package com.qfree.qfree_customer.adapter;

import com.qfree.qfree_customer.model.Queue;

/**
 * Created by Fahad Qureshi on 10/5/2017.
 */

public interface QueueAdapterListener {
    void onQueueSelected(int position, Queue queue);
}
