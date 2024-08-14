package av3.pw.ufrn.dto;

import av3.pw.ufrn.domain.Moto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//aaa
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AluguelDto {
    private Long id;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Moto moto;
}
