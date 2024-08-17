package av3.pw.ufrn.dto;

import av3.pw.ufrn.controller.ClienteController;
import av3.pw.ufrn.controller.EnderecoController;
import av3.pw.ufrn.domain.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDto extends RepresentationModel<ClienteResponseDto> {
    private Long id; 
    private String nome;
    private String telefone;
    private EnderecoDto endereco;
    private List<AluguelDto> alugueis;


    public void addLinks(Cliente cliente) {
        this.add(linkTo(ClienteController.class).slash(cliente.getId()).withSelfRel());
        if (cliente.getEndereco() != null) {
            this.add(linkTo(EnderecoController.class).slash(cliente.getEndereco().getId()).withRel("endereco"));
        }
    }
}
