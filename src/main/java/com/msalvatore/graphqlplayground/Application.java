package com.msalvatore.graphqlplayground;

import com.msalvatore.graphqlplayground.scalar.datetime.GraphQLLocalDate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public GraphQLLocalDate graphQLLocalDate() {
		return new GraphQLLocalDate();
	}

}
