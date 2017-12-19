package com.example.demo;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@SpringBootApplication
public class ServiceAApplication {

	@Value("${my.testprop}")
    String testprop;
	
	@Autowired
	Environment environment;
	
	RabbitMessagingTemplate template;
	
	@Autowired
	ServiceAApplication(RabbitMessagingTemplate template){
		this.template=template;
	}
	
	@Bean
	Queue queue() {
		return new Queue("TestQ",false);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceAApplication.class, args);
	}
	
	@RequestMapping("/servicea")
	public String callServiceA()
	{
		String str = "This is in ServiceA : port : " + environment.getProperty("local.server.port");
		System.out.println(str);
		return str;
	}
	
	@RequestMapping("/serviceab")
	public String callServiceAB()
	{
		String str = callServiceA() + "\n";
		
		RestTemplate resttemplate= new RestTemplate();
		
		str += resttemplate.getForObject("http://localhost:8091/serviceb", String.class);
		
		System.out.println(str);
		
		return str;
	}
	
	@RequestMapping("/sendmessage")
	public String callSendMessage()
	{
		String str = "This is in sendMessage";
		System.out.println(str);
		template.convertAndSend("TestQ", "Hello World!!!");
		
		return str;
	}
	
	@RequestMapping("/testprop")
	public String callTestProp()
	{
		String str = "This is test property : " + testprop;
		System.out.println(str);
		return str;
	}
}
