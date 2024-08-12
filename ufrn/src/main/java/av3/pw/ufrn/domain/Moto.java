package av3.pw.ufrn.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "O modelo não pode estar em branco.")
    private String modelo;

    @NotBlank(message = "A marca não pode estar em branco.")
    private String marca;

    @NotBlank(message = "O ano não pode estar em branco.")
    private int ano;
    private boolean disponivel;

    @ManyToMany(mappedBy = "motos")
    private List<Aluguel> alugueis;

    private boolean isDeleted = false;
}