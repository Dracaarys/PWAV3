package av3.pw.ufrn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import av3.pw.ufrn.domain.Endereco;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClienteRequestDto {
    String nome;
    String email;
    String senha; 
    String telefone;
    Endereco endereco;
}
