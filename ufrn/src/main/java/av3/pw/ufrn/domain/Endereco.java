package av3.pw.ufrn.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@SQLDelete(sql = "UPDATE Endereco SET deleted_at = CURRENT_TIMESTAMP where id=?")
@SQLRestriction("deleted_at is null")
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
    @JsonIgnore
    private Cliente cliente;

    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    LocalDateTime deletedAt;
}
