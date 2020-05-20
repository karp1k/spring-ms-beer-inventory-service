package guru.sfg.beer.inventory.service.services;

import guru.springframework.springmsbeercommon.web.model.BeerOrderDto;

/**
 * @author kas
 */
public interface AllocationService {

    Boolean allocateOrder(BeerOrderDto beerOrderDto);

    void deallocateOrder(BeerOrderDto beerOrderDto);

}
