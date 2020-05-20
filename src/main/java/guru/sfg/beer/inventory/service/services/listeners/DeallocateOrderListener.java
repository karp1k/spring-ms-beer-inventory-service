package guru.sfg.beer.inventory.service.services.listeners;

import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import guru.sfg.beer.inventory.service.services.AllocationService;
import guru.springframework.springmsbeercommon.web.config.Constants;
import guru.springframework.springmsbeercommon.web.events.DeallocateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author kas
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DeallocateOrderListener {

    private final AllocationService allocationService;

    @JmsListener(destination = Constants.DEALLOCATE_ORDER_QUEUE)
    public void listen(DeallocateOrderRequest request) {
        allocationService.deallocateOrder(request.getBeerOrderDto());

    }
}
