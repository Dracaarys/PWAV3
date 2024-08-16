package av3.pw.ufrn.dto;

import av3.pw.ufrn.domain.Moto;
import av3.pw.ufrn.domain.Cliente;
import av3.pw.ufrn.dto.ClienteRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AluguelDto {
    private Long id;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private List<MotoDto> motos;
}
