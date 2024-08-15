package av3.pw.ufrn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotoDto {
    private Long id;
    private String modelo;
    private String marca;
    private int ano;
}
