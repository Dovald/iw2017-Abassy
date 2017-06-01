package com.abassy;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.abassy.security.VaadinSessionSecurityContextHolderStrategy;
import com.abassy.services.UsuarioService;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Application {
	
	/*@Autowired
	private LocalRepository repoEst;
	
	@Autowired
	private ZonaRepository repoZona;
	
	@Autowired
	private MesaRepository repoMesa;
	
	@Autowired
	private PedidoRepository repoPedido;
	
	@Autowired
	private ClienteRepository repoCliente;
	
	@Autowired
	private FamiliaProductoRepository repoFam;
	
	@Autowired
	private ProductoRepository repoProd;*/
	
	//private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
	public CommandLineRunner loadData(UsuarioService service) {
		return (args) -> {
			// Local de prueba
			//Zona pruebaZona = new Zona("Erg", "Budapest");
			//repoEst.save(pruebaLocal);
			
			/*Usuario root = new Usuario("root", "toor", 0);
			root.setPassword("root");
			service.save(root);*/
			
			// Pedido de prueba
			/*Pedido prueba = new Pedido (pruebaLocal, root);
			repoPedido.save(prueba);
			
			// Familia Productos de prueba
			FamiliaProducto ingredientes = new FamiliaProducto ("Ingredientes");
			FamiliaProducto bocadillos = new FamiliaProducto ("Bocadillos");
			repoFam.save(ingredientes);
			repoFam.save(bocadillos);
		
			// Productos de prueba
			Producto ing1 = new Producto("Jamon", 0.75f);
			Producto ing2 = new Producto("Queso", 1.0f);
			Producto ing3 = new Producto("Huevo", 1.0f);
			repoProd.save(ing1);
			repoProd.save(ing2);
			repoProd.save(ing3);
			
			// Lista de ingredientes
			List<Producto> ing = new ArrayList<Producto>();
			ing.add(ing1);
			ing.add(ing2);
			ing.add(ing3);
			Producto p1 = new Producto(bocadillos, "Hamburguesa Super IW", 6.8f, true, ing);
			Producto p2 = new Producto(bocadillos, "Super Bocata Vejer&Benalup Fashion", 15.8f, true, ing);
			Producto p3 = new Producto(bocadillos, "Patatas Fritas", 2.2f, true, ing);

			repoProd.save(p1);
			repoProd.save(p2);
			repoProd.save(p3);*/
			
			/*repository.save(new Local("Calle Real", "Cadiz"));
			repository.save(new Local("La Granja", "San Fernando"));
			repository.save(new Local("Calle 4", "Jerez"));
			repository.save(new Local("Candelaria", "Cadiz"));
			
			Usuario root = new Usuario(prueba, "root", "root");
			root.setPassword("root");
			service.save(root);*/

			/*// fetch all Users
			log.info("Local found with findAll():");
			log.info("-------------------------------");
			for (Local Local : repository.findAll()) {
				log.info(Local.toString());
			}
			log.info("");

			// fetch an individual User by ID
			//Local Local = repository.findOne(1);
			log.info("Local found with findOne(1L):");
			log.info("--------------------------------");
			//log.info(Local.toString());
			log.info("");

			// fetch Local by last name
			log.info("Local found with findByLastNameStartsWithIgnoreCase('Bauer'):");
			log.info("--------------------------------------------");
			for (Local bauer : repository
					.findByDireccionStartsWithIgnoreCase("Bauer")) {
				log.info(bauer.toString());
			}
			log.info("");*/
		};
	}
    
    @Configuration
	@EnableGlobalMethodSecurity(securedEnabled = false)
	public static class SecurityConfiguration extends GlobalMethodSecurityConfiguration {

		@Autowired
		private UserDetailsService userDetailsService;

		@Bean
		public PasswordEncoder encoder() {
			return new BCryptPasswordEncoder(11);
		}

		@Bean
		public DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
			authProvider.setUserDetailsService(userDetailsService);
			authProvider.setPasswordEncoder(encoder());
			return authProvider;
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {

			auth.authenticationProvider(authenticationProvider());

			// auth
			// .inMemoryAuthentication()
			// .withUser("admin").password("p").roles("ADMIN", "MANAGER",
			// "USER")
			// .and()
			// .withUser("manager").password("p").roles("MANAGER", "USER")
			// .and()
			// .withUser("user").password("p").roles("USER");
			
		}

		@Bean
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return authenticationManager();
		}

		static {
			// Use a custom SecurityContextHolderStrategy
			SecurityContextHolder.setStrategyName(VaadinSessionSecurityContextHolderStrategy.class.getName());
		}
	}
}