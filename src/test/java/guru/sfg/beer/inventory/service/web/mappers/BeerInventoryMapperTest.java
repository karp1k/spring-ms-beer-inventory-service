package guru.sfg.beer.inventory.service.web.mappers;

import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.web.model.BeerInventoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.util.UUID;

import static guru.sfg.beer.inventory.service.bootstrap.BeerInvntoryBootstrap.BEER_1_UUID;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerInventoryMapperTest {

    @Autowired
    BeerInventoryMapper mapper;

    BeerInventoryDto dto;

    static final Integer QUANTITY_ON_HAND = 20;

    @Test
    void beerInventoryDtoToBeerInventory() {
        dto = BeerInventoryDto.builder()
                .beerId(BEER_1_UUID)
                .createdDate(OffsetDateTime.now())
                .id(UUID.randomUUID())
                .lastModifiedDate(OffsetDateTime.now())
                .quantityOnHand(QUANTITY_ON_HAND)
                .build();

        BeerInventory entity = mapper.beerInventoryDtoToBeerInventory(dto);
        assertEquals(BEER_1_UUID, entity.getBeerId());
        assertEquals(QUANTITY_ON_HAND, entity.getQuantityOnHand());
        assertEquals(dto.getId(), entity.getId());
    }
}