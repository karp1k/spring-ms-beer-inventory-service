package guru.sfg.beer.inventory.service.services.listeners;

import guru.sfg.beer.inventory.service.services.AllocationService;
import guru.springframework.springmsbeercommon.web.config.Constants;
import guru.springframework.springmsbeercommon.web.events.AllocationOrderRequest;
import guru.springframework.springmsbeercommon.web.events.AllocationOrderResponse;
import guru.springframework.springmsbeercommon.web.model.BeerOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * @author kas
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AllocationOrderListener {

    private final JmsTemplate jmsTemplate;
    private final AllocationService allocationService;

    @JmsListener(destination = Constants.ALLOCATE_ORDER_QUEUE)
    public void listen(AllocationOrderRequest allocationOrderRequest) {
        BeerOrderDto beerOrderDto = allocationOrderRequest.getBeerOrderDto();
        boolean inventoryPending = false;
        boolean allocationError = false;
        try {
            inventoryPending = !allocationService.allocateOrder(beerOrderDto);

        } catch (Exception e) {
            log.error(e.getMessage());
            allocationError = true;
        }

        AllocationOrderResponse response = AllocationOrderResponse.builder()
                .allocationError(allocationError)
                .beerOrderDto(beerOrderDto)
                .inventoryPending(inventoryPending)
                .build();
        jmsTemplate.convertAndSend(Constants.ALLOCATE_ORDER_RESULT_QUEUE, response);

    }
}
