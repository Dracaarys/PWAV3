package av3.pw.ufrn.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "O nome do cliente não pode estar em branco.")
    private String nome;

    @Email(message = "O email deve ser válido.")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco.")
    private String senha;  // Adicionando o campo senha

    @NotBlank(message = "O telefone não pode estar em branco.")
    private String telefone;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToMany(mappedBy = "cliente")
    private List<Aluguel> alugueis;
}
