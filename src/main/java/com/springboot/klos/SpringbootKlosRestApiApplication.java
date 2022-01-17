package com.springboot.klos;

import com.springboot.klos.dto.request.ParticipantRequestDto;
import com.springboot.klos.exception.KLOSApiException;
import com.springboot.klos.service.ParticipantService;
import com.springboot.klos.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SpringbootKlosRestApiApplication implements CommandLineRunner {
	@Autowired
	private RoleService roleService;

	@Autowired
	private ParticipantService participantService;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootKlosRestApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		roleService.createDefaultRoles();

		ParticipantRequestDto admin = new ParticipantRequestDto();
		admin.setName("admin");
		admin.setEmail("klos@gmail.com");
		admin.setPassword("Kl0$admin");

		try {
			participantService.createAdmin(admin);
		} catch (KLOSApiException e) {
			System.out.println("Default admin user is already created.");
		}
	}
}
