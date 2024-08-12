package av3.pw.ufrn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import org.springframework.hateoas.RepresentationModel;
import av3.pw.ufrn.domain.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDto extends RepresentationModel<ClienteResponseDto> {
    String nome;
    String telefone;
    Endereco endereco;
    List<Aluguel> alugueis;
}
