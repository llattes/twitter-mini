package net.lucianolattes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.lucianolattes.dao.TweetDao;
import net.lucianolattes.model.Tweet;

@RestController
@RequestMapping(value = "/api")
public class TweetController {

  @Autowired
  private TweetDao tweetDao;

  @RequestMapping(value = "/tweets", produces = { "application/xml", "application/json" }, method = RequestMethod.GET)
  public List<Tweet> getTweets() {
    return tweetDao.list();
  }

  @RequestMapping(value = "/db-tweets", produces = { "application/xml",
      "application/json" }, method = RequestMethod.GET)
  public List<Tweet> getDBTweets() {
    return tweetDao.getAllTweets();
  }

  @GetMapping("/tweets/{id}")
  public ResponseEntity getTweet(@PathVariable("id") Long id) {

    Tweet tweet = tweetDao.get(id);

    if (tweet == null) {
      return new ResponseEntity("No Tweet found for ID " + id, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity(tweet, HttpStatus.OK);
  }

  @PostMapping(value = "/tweets")
  public ResponseEntity createTweet(@RequestBody Tweet tweet) {

    tweetDao.create(tweet);

    return new ResponseEntity(tweet, HttpStatus.OK);
  }

  @DeleteMapping("/tweets/{id}")
  public ResponseEntity deleteTweet(@PathVariable Long id) {

    if (null == tweetDao.delete(id)) {
      return new ResponseEntity("No Tweet found for ID " + id, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity(id, HttpStatus.OK);
  }

  @PutMapping("/tweets/{id}")
  public ResponseEntity updateTweet(@PathVariable Long id, @RequestBody Tweet tweet) {

    tweet = tweetDao.update(id, tweet);

    if (null == tweet) {
      return new ResponseEntity("No Tweet found for ID " + id, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity(tweet, HttpStatus.OK);
  }
}
