package av3.pw.ufrn;

import av3.pw.ufrn.core.security.RsaKeyProperties;
import av3.pw.ufrn.domain.Cliente;
import av3.pw.ufrn.domain.Endereco;
import av3.pw.ufrn.domain.SecurityUser;
import av3.pw.ufrn.repository.SecurityUserRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

	@Autowired
	SecurityUserRepository securityUserRerpository;

	@Autowired
	BCryptPasswordEncoder encoder;


	@PostConstruct
	public void started() {
		Cliente p = new Cliente();

		p.setNome("Joao");
		p.setAdmin(true);


		Endereco e = new Endereco();
		e.setRua("rua grnade");
		e.setCidade("natal");
		e.setEstado("amazonas");
		e.setCep("3223452");


		p.setEndereco(e);

		SecurityUser securityUser = new SecurityUser();
		securityUser.setCliente(p);
		securityUser.setEmail("admin@gmail.com");
		securityUser.setPassword(encoder.encode("admin"));

		securityUserRerpository.save(securityUser);
	}
}