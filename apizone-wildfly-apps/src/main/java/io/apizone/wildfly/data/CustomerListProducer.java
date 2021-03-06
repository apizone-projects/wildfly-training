
package io.apizone.wildfly.data;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;
import io.apizone.wildfly.model.Customer;

/**
 * Retrieves the Customer List
 *
 * @author Shoaib R Khan
 */

@RequestScoped
public class CustomerListProducer {

    @Inject
    private Logger log;

    @Inject
    private CustomerRepository customerRepository;

    private List<Customer> customers;

    // @Named provides access the return value via the EL variable name "customers" in the UI (e.g.,
    // Facelets or JSP view)
    @Produces
    @Named
    public List<Customer> getCustomers() {
        return customers;
    }

    public void onCustomerListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Customer customer) {
        retrieveAllCustomersOrderedByName();
    }

    @PostConstruct
    public void retrieveAllCustomersOrderedByName() {
        customers = customerRepository.findAllOrderedByName();
    }
}
