package com.example.demo.Model;

import javax.persistence.*;

@Entity
@Table(name="vote")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long voteId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "questionId")
    private long questionId;

    @Column(name = "answer")
    private String answer;

    public Vote() {
    }

    public Vote(long voteId, long userId, long questionId, String answer) {
        this.voteId = voteId;
        this.userId = userId;
        this.questionId = questionId;
        this.answer = answer;
    }

    public long getVoteId() {
        return voteId;
    }

    public void setVoteId(long voteId) {
        this.voteId = voteId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
