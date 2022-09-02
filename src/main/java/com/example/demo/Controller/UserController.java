package com.example.demo.Controller;

import com.example.demo.Model.Question;
import com.example.demo.Model.User;
import com.example.demo.Model.Vote;
import com.example.demo.Repository.QuestionRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    SimpMessagingTemplate template;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    //add a new user
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User _user = userRepository.save(new User(user.getUsername()));
            template.convertAndSend("/topic/message",userRepository.findAll().size());
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/end")
    public void endSession() {
        //delete all users and user answers
        userRepository.deleteAll();
        voteRepository.deleteAll();
        log.info("session ended...");
    }

    @PostMapping("/vote")
    public ResponseEntity<Vote> addVote(@RequestBody Vote vote) {
        try {
            Vote _vote = voteRepository.save(vote);
            template.convertAndSend("/topic/message",userRepository.findAll().size());
            return new ResponseEntity<>(_vote, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
