package digitalinnovation.example.restfull.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import digitalinnovation.example.restfull.controller.SoldadoController;
import digitalinnovation.example.restfull.controller.response.SoldadoListResponse;
import digitalinnovation.example.restfull.controller.response.SoldadoResponse;
import digitalinnovation.example.restfull.entity.SoldadoEntity;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class ResourceSoldado {
    private ObjectMapper objectMapper;

    public ResourceSoldado(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public SoldadoListResponse criarLinks(SoldadoEntity soldadoEntity){
       SoldadoListResponse soldadoListResponse = objectMapper.convertValue(soldadoEntity, SoldadoListResponse.class);
       Link link = linkTo(methodOn(SoldadoController.class).buscarSoldado(soldadoEntity.getId())).withSelfRel();
       soldadoListResponse.add(link);
       return soldadoListResponse;
    }

    public SoldadoResponse criarLinksDetalhe(SoldadoEntity soldadoEntity){
        SoldadoResponse soldadoListResponse = objectMapper.convertValue(soldadoEntity, SoldadoResponse.class);
       if (soldadoEntity.getStatus().equals("morto")){
           Link link = linkTo(methodOn(SoldadoController.class).deletarSoldado(soldadoEntity.getId()))
                   .withRel("remover")
                   .withTitle("Deletar soldado")
                   .withType("delete");
           soldadoListResponse.add(link);
       }else if (soldadoEntity.getStatus().equals("vivo")){
           Link link = linkTo(methodOn(SoldadoController.class).frenteCastelo(soldadoEntity.getId()))
                   .withRel("batalhar")
                   .withTitle("Ir para frente do castelo")
                   .withType("put");
           soldadoListResponse.add(link);
       }
        return soldadoListResponse;
    }
}
