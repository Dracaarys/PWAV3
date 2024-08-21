package av3.pw.ufrn;

import av3.pw.ufrn.core.security.RsaKeyProperties;
import av3.pw.ufrn.domain.Cliente;
import av3.pw.ufrn.domain.Endereco;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class UfrnApplication {

	public static void main(String[] args) {
		SpringApplication.run(UfrnApplication.class, args);
	}

	@Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    };


}