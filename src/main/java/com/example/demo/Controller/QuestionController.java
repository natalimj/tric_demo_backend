package com.example.demo.Controller;

import com.example.demo.Model.Question;
import com.example.demo.Model.Result;
import com.example.demo.Repository.QuestionRepository;
import com.example.demo.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/questionApi")
public class QuestionController {
    private final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SimpMessagingTemplate template;

    @GetMapping("/questions")
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }


    @GetMapping("/questions/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable("id") long id) {
        System.out.println(id);
        try {
            Optional<Question> questionData = questionRepository.findById(id);

            if (questionData.isPresent()) {
                template.convertAndSend("/topic/start",questionData);
                return new ResponseEntity<>(questionData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/next/{id}")
    public ResponseEntity<Question> getNextQuestionById(@PathVariable("id") long id) {
        System.out.println(id);
        try {
            Optional<Question> questionData = questionRepository.findById(id+1); //TODO: find next question id here

            if (questionData.isPresent()) {
                template.convertAndSend("/topic/start",questionData);
                return new ResponseEntity<>(questionData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/start")
    public Question getFirstQuestions() {
        template.convertAndSend("/topic/start",questionRepository.findAll().get(0));
        return questionRepository.findAll().get(0);
    }



    @GetMapping("/results/{id}")
    public ResponseEntity<Result> getResultById(@PathVariable("id") long id) {

        try {
            //TODO : get results here, create DTO
            Result result = new Result(65.6,34.4);
            template.convertAndSend("/topic/result",result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
