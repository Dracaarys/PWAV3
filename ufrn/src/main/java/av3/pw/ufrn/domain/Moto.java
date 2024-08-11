package av3.pw.ufrn.domain;

import jakarta.persistence.*;
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

    private String modelo;
    private String marca;
    private int ano;
    private boolean disponivel;

    @ManyToMany(mappedBy = "motos")
    private List<Aluguel> alugueis;
}