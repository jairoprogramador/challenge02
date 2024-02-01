package com.jairoprogramador;

import com.stripe.Stripe;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OneseventechApplication { //implements CommandLineRunner {

	/*@Autowired
	PasswordEncoder passwordEncoder;*/

	public static void main(String[] args) {
		SpringApplication.run(OneseventechApplication.class, args);
		Stripe.apiKey = "sk_test_51Oe8q9GkDX5Jm5FZk5evnzV2NyHZJYNNyfkJTTVWQvjGHS847yFAm8CA2NS9ZKSnCu6Ex7Lw2iyLkrEKYpwIKAuG00ZDFMzWv4";
	}

	/*@Override
	public void run(String... args) throws Exception {
		//System.out.println(passwordEncoder.encode("secret"));
		//System.out.println(passwordEncoder.encode("12345"));
	}*/
}
