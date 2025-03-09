package booking_stay.booking_stay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BookingStayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingStayApplication.class, args);
	}

}
