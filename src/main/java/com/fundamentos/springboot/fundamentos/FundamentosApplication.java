package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.bean.MyBean;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;
import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import com.fundamentos.springboot.fundamentos.service.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);
	private ComponentDependency componentDependency;
	private MyBean myBean;

	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;

	private UserService userService;
	private UserRepository userRepository;
	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties, UserPojo userpojo, UserRepository userRepository, UserService userService){
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userpojo;
		this.userRepository = userRepository;
		this.userService = userService;
	}
	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args)  {
		//ejemplosAnteriores();
		saveUsersInDataBase();
		getInformationJpqlFromUser();
		saveWithErrorTransactional();
	}

	private void saveWithErrorTransactional(){
		User test1 = new User("Test1Transactional", "Test1Transactional@domain.com", LocalDate.now());
		User test2 = new User("Test2Transactional", "Test2Transactional@domain.com", LocalDate.now());
		User test3 = new User("Test3Transactional", "Test3Transactional@domain.com", LocalDate.now());
		User test4 = new User("Test4Transactional", "Test4Transactional@domain.com", LocalDate.now());

		List<User> users = Arrays.asList(test1, test2, test3, test4);

		userService.saveTransactional(users);

		userService.getAllUsers().stream()
				.forEach(user -> LOGGER.info("Este es el usuario dentro del metodo transacional"+ user));

	}

	private void getInformationJpqlFromUser(){
		/*LOGGER.info( "El usuario con el metodo findByUserEmail"+
				userRepository.findByUserEmail("andrea@domain.net")
						.orElseThrow(()->new RuntimeException("No se encontro el usuario")));

		userRepository.findAndSort("L", Sort.by("id").descending())
				.stream()
				.forEach(user -> LOGGER.info("Usuario con metodo sort "+user));


		userRepository.findByName("Carlos")
				.stream()
				.forEach(user -> LOGGER.info("Usuario con query method "+ user));


		LOGGER.info("Usuario con query method findByEmailAndName "+userRepository.findByEmailAndName("belia@domain.es", "Beliazar")
				.orElseThrow(()->new RuntimeException("Usuario no encontrado")));


		userRepository.findByNameLike("%L%")
				.stream()
				.forEach(user -> LOGGER.info("Usuario findByNameLike "+ user));


		userRepository.findByNameOrEmail(null, "july@domain.com.co")
				.stream()
				.forEach(user -> LOGGER.info("Usuario findByNameOrEmail "+ user));*/

		userRepository.findBybirthDayBetween(LocalDate.of(2001, 6, 4), LocalDate.of(2001, 7, 11))
				.stream()
				.forEach(user -> LOGGER.info("Usuario con intervalo de fechas: "+ user));

		userRepository.findByNameContainingOrderByIdDesc("L")/*Si hubieran mas datos o nombres liliana, luis, lina, leidys, laura lauren se coloca y cambia*/
				.stream()
				.forEach(user -> LOGGER.info("Usuario encontrado con like y ordenado: "+ user));

		LOGGER.info("El usuario a partir del named parameter es: "+ userRepository.getAllByBirthDateAndEmail(LocalDate.of(2002,8,1),
						"daniela@domain.com")
				.orElseThrow(()-> new RuntimeException("No se encontro el usuario a partir del named parameter ")));
	}

	private void saveUsersInDataBase(){
		User user1=new User("Carlos", "carlos@domain.es", LocalDate.of(1999,11,2));
		User user2=new User("John", "john@domain.com", LocalDate.of(2000,12,14));
		User user3=new User("Daniela", "daniela@domain.com", LocalDate.of(2002,8,1));
		User user4=new User("Lina", "Lina@domain.net", LocalDate.of(1996,12,29));
		User user5=new User("Luis", "luism@domain.test", LocalDate.of(2001,7,10));
		User user6=new User("Sergio", "sergio@domain.test", LocalDate.of(1998,2,22));
		User user7=new User("Andrea", "andrea@domain.net", LocalDate.of(1899,10,19));
		User user8=new User("Liliana", "lily@domain.com", LocalDate.of(1998,4,4));
		User user9=new User("Beliazar", "belia@domain.es", LocalDate.of(1997,5,18));
		User user10=new User("Julio", "july@domain.com.co", LocalDate.of(2005,11,26));
		User user11=new User("Sarith", "sari@domain.test", LocalDate.of(1974,1,12));
		User user12=new User("Angie", "angie@domain.es", LocalDate.of(2001,6,4));
		List<User> list= Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12);
		list.stream().forEach(userRepository::save);

	}
	private void ejemplosAnteriores(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail()+":"+userPojo.getPassword());
		try {
			int value = 10/0;
			LOGGER.debug("Mi valor: "+ value);
		} catch (Exception e){
			LOGGER.error("Error al dividir entre 0 "+e.getMessage());
		}
	}
}
