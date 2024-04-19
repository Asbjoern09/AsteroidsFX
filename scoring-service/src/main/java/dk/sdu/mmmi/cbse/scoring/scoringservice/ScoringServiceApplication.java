package dk.sdu.mmmi.cbse.scoring.scoringservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ScoringServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoringServiceApplication.class, args);
	}


	int score = 0;
	@GetMapping("/score")
	public int addToScore(){
		score++;
		System.out.println(score);
		return score;
	}

	@GetMapping("/getScore")
	public int getScore(){
		return score;
	}

	@GetMapping("/resetScore")
	public void resetScore(){
		score = 0;
	}
}

