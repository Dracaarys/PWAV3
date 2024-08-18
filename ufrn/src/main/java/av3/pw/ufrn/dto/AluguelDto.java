package av3.pw.ufrn.dto;

import av3.pw.ufrn.controller.AluguelController;
import av3.pw.ufrn.controller.ClienteController;
import av3.pw.ufrn.controller.MotoController;
import av3.pw.ufrn.domain.Aluguel;
import av3.pw.ufrn.domain.Moto;
import av3.pw.ufrn.domain.Cliente;
import av3.pw.ufrn.dto.ClienteRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AluguelDto extends RepresentationModel<AluguelDto> {
    private Long id;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private List<MotoDto> motos;

    public void addLinks(Aluguel aluguel) {
        this.add(linkTo(AluguelController.class).slash(aluguel.getId()).withSelfRel());
        if (aluguel.getCliente() != null) {
            this.add(linkTo(ClienteController.class).slash(aluguel.getCliente().getId()).withRel("cliente"));
        }
        if (aluguel.getMotos() != null) {
            for (Moto moto : aluguel.getMotos()) {
                this.add(linkTo(MotoController.class).slash(moto.getId()).withRel("moto"));
            }
        }
    }

}
