package guru.sfg.beer.inventory.service.events;

import guru.springframework.springmsbeercommon.web.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author kas
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -1134240417156196848L;

    private BeerDto beerDto;
}
