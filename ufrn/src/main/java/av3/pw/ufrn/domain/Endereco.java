package av3.pw.ufrn.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "A rua não pode estar em branco.")
    private String rua;

    @NotBlank(message = "A cidade não pode estar em branco.")
    private String cidade;

    @NotBlank(message = "O estado não pode estar em branco.")
    private String estado;

    @NotBlank(message = "O CEP não pode estar em branco.")
    private String cep;

    @OneToOne(mappedBy = "endereco")
    private Cliente cliente;
}
