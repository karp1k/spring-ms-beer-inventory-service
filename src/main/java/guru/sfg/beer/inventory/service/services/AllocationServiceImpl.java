package guru.sfg.beer.inventory.service.services;

import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import guru.springframework.springmsbeercommon.web.model.BeerOrderDto;
import guru.springframework.springmsbeercommon.web.model.BeerOrderLineDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * @author kas
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class AllocationServiceImpl implements AllocationService {

    private final BeerInventoryRepository beerInventoryRepository;


    @Override
    public Boolean allocateOrder(BeerOrderDto beerOrderDto) {
        log.info("Allocating the order {}...", beerOrderDto.getId());
        AtomicInteger totalOrdered = new AtomicInteger();
        AtomicInteger totalAllocated = new AtomicInteger();

        beerOrderDto.getBeerOrderLines().stream().filter(isAllocationRequired()).forEach(beerOrderLineDto -> {
            allocateBeerOrderLine(beerOrderLineDto);
            totalOrdered.set(totalOrdered.get() + beerOrderLineDto.getOrderQuantity());
            totalAllocated.set(totalAllocated.get() + (beerOrderLineDto.getQuantityAllocated() != null ? beerOrderLineDto.getQuantityAllocated() : 0));
        });
        log.info("Total ordered: {} Total allocated: {}", totalOrdered.get(), totalAllocated.get());
        return totalOrdered.get() == totalAllocated.get();
    }

    @Override
    public void deallocateOrder(BeerOrderDto beerOrderDto) {
        beerOrderDto.getBeerOrderLines().forEach(beerOrderLineDto -> {
            BeerInventory beerInventory = BeerInventory.builder()
                    .beerId(beerOrderLineDto.getBeerId())
                    .upc(beerOrderLineDto.getUpc())
                    .quantityOnHand(beerOrderLineDto.getQuantityAllocated())
                    .build();

            BeerInventory savedBeerInventory = beerInventoryRepository.save(beerInventory);
            log.debug("saved inventory for beer upc {} in inventory {}", savedBeerInventory.getUpc(), savedBeerInventory.getId());
        });


    }

    private void allocateBeerOrderLine(BeerOrderLineDto beerOrderLineDto) {
        List<BeerInventory> beerInventoryList = beerInventoryRepository.findAllByUpc(beerOrderLineDto.getUpc());
        beerInventoryList.forEach(beerInventory -> {

            int inventory = beerInventory.getQuantityOnHand() != null ? beerInventory.getQuantityOnHand() : 0;
            int orderQuantity = beerOrderLineDto.getOrderQuantity() != null ? beerOrderLineDto.getOrderQuantity() : 0;
            int allocatedQuantity = beerOrderLineDto.getQuantityAllocated() != null ? beerOrderLineDto.getQuantityAllocated() : 0;
            int quantityToAllocate = orderQuantity - allocatedQuantity;

            if (inventory >= quantityToAllocate) { // partial allocation
                inventory = inventory - quantityToAllocate;
                beerOrderLineDto.setQuantityAllocated(orderQuantity);
                beerInventory.setQuantityOnHand(inventory);
                beerInventoryRepository.save(beerInventory);
            } else if (inventory > 0) { // full allocation
                beerOrderLineDto.setQuantityAllocated(allocatedQuantity + inventory);
                beerInventory.setQuantityOnHand(0);
                beerInventoryRepository.delete(beerInventory);
            }
        });
    }

    private static Predicate<BeerOrderLineDto> isAllocationRequired() {
        return beerOrderLineDto -> ((beerOrderLineDto.getOrderQuantity() != null ? beerOrderLineDto.getOrderQuantity() : 0)
                - (beerOrderLineDto.getQuantityAllocated() != null ? beerOrderLineDto.getQuantityAllocated() : 0))
                > 0;
    }
}
