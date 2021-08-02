package com.smartcontact.core.springrest;

import com.smartcontact.core.springrest.entities.Role;
import com.smartcontact.core.springrest.service.RoleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringRestApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(SpringRestApplication.class, args);

		crearRoles(ctx);


	}


	/**
	 * Metodo para crear Roles
	 */

	public static void crearRoles(ConfigurableApplicationContext ctx) {
		RoleService serv = ctx.getBean(RoleService.class);
		Role rol = new Role((long) 1, "ROLE_USER");
		Role rol2 = new Role((long) 2, "ROLE_ADMIN");
		Role rol3 = new Role((long) 3, "ROLE_CREATOR");
		Role rol4 = new Role((long) 4, "ROLE_EDITOR");

		serv.save(rol);
		serv.save(rol2);
		serv.save(rol3);
		serv.save(rol4);
	}
}