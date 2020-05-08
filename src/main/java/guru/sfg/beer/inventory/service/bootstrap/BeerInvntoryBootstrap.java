package guru.sfg.beer.inventory.service.bootstrap;

import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by jt on 2019-06-07.
 */
@Slf4j
@RequiredArgsConstructor
//@Component
public class BeerInvntoryBootstrap implements CommandLineRunner {
    public static final String BEER_1_UPC = "018200533082";
    public static final String BEER_2_UPC = "18200007712";
    public static final String BEER_3_UPC = "018200113529";
    public static final UUID BEER_1_UUID = UUID.fromString("fba04b35-ad91-4c95-8fea-63202d82f69a");
    public static final UUID BEER_2_UUID = UUID.fromString("a4e9bc4b-75cb-4d1f-b36d-604d60d472db");
    public static final UUID BEER_3_UUID = UUID.fromString("84c8a160-ccd6-4779-9346-3fb94c77ebd1");

    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if(beerInventoryRepository.count() == 0){
            loadInitialInv();
        }
    }

    private void loadInitialInv() {
        beerInventoryRepository.save(BeerInventory
                .builder()
                .upc(BEER_1_UPC)
                .quantityOnHand(50)
                .build());

        beerInventoryRepository.save(BeerInventory
                .builder()
                .upc(BEER_2_UPC)
                .quantityOnHand(50)
                .build());

        beerInventoryRepository.saveAndFlush(BeerInventory
                .builder()
                .upc(BEER_3_UPC)
                .quantityOnHand(50)
                .build());

        log.debug("Loaded Inventory. Record count: " + beerInventoryRepository.count());
    }
}
