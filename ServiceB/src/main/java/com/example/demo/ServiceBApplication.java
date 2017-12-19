package com.example.demo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@SpringBootApplication
public class ServiceBApplication {

	@Autowired
	Environment environment;
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceBApplication.class, args);
	}
	
	@RequestMapping("/serviceb")
	public String callServiceB()
	{
		String str = "This is in ServiceB : port : " + environment.getProperty("local.server.port");
		System.out.println(str);
		return str;
	}
	
	@RequestMapping("/serviceba")
	public String callServiceBA()
	{
		String str = callServiceB() + "\n";
		
		RestTemplate resttemplate= new RestTemplate();
		
		str += resttemplate.getForObject("http://localhost:8090/servicea", String.class);
		
		System.out.println(str);
		
		return str;
	}
	
	@RabbitListener(queues="TestQ")
	public void processMessage(String content) {
		System.out.println("Received Message in Listener :" + content);
	}
}
