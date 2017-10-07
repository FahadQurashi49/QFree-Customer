package com.qfree.qfree_customer.service;

import com.qfree.qfree_customer.model.Customer;

/**
 * Created by Fahad Qureshi on 10/1/2017.
 */

public class CustomerService {
    private static final CustomerService ourInstance = new CustomerService();
    private static final Customer customerInstance = new Customer();

    public static CustomerService getInstance() {
        return ourInstance;
    }

    private CustomerService() {
    }

    public Customer getCustomerInstance () {
        return customerInstance;
    }
    public void setCustomerInstance (Customer customer) {
        customerInstance.copy(customer);
    }
}
