package av3.pw.ufrn;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UfrnApplication {

	public static void main(String[] args) {
		SpringApplication.run(UfrnApplication.class, args);
	}

	@Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    };
}