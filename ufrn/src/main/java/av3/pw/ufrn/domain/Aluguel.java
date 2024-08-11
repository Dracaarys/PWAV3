package av3.pw.ufrn.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Aluguel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
            name = "aluguel_moto",
            joinColumns = @JoinColumn(name = "aluguel_id"),
            inverseJoinColumns = @JoinColumn(name = "moto_id")
    )
    private List<Moto> motos;
}
//