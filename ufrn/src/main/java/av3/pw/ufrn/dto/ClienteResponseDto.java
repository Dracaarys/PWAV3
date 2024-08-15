package av3.pw.ufrn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDto extends RepresentationModel<ClienteResponseDto> {
    private Long id;  // Adiciona o campo id
    private String nome;
    private String telefone;
    private EnderecoDto endereco;
    private List<AluguelDto> alugueis;
}
