package av3.pw.ufrn.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDto {
    private String rua;
    private String cidade;
    private String estado;
    private String cep;

}
