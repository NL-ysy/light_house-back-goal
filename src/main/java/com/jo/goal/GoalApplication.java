package com.jo.goal;

import com.jo.goal.service.GoalServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class GoalApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoalApplication.class, args);
	}

	@Scheduled(cron = "30 * * * * *") // 매분 30초 마다 실행
	public void testScheduler() {
		System.out.println("test");
	}

}
