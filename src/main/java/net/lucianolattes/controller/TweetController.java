package net.lucianolattes.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import net.lucianolattes.dao.TweetDao;
import net.lucianolattes.model.Tweet;

@RestController
@RequestMapping(value = "/api")
public class TweetController {

  private static final Logger LOGGER = LoggerFactory.getLogger(TweetController.class);

  @Autowired
  private TweetDao tweetDao;

  @ApiOperation(value = "", notes = "Get tweets for the authenticated user")
  @RequestMapping(value = "/tweets", produces = { "application/xml", "application/json" }, method = RequestMethod.GET)
  public List<Tweet> getDBTweets(Authentication authentication) {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser != null) {
      LOGGER.debug("Authenticated username: " + authenticatedUser.getUsername());
    }

    return tweetDao.getAllTweets();
  }

  @RequestMapping(value = "/tweets", produces = { "application/xml", "application/json" }, method = RequestMethod.POST)
  public Tweet addTweet(@RequestBody Tweet tweet, Authentication authentication) {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser != null) {
      LOGGER.debug("Authenticated username: " + authenticatedUser.getUsername());
    }

    return tweet;
  }
  
  @RequestMapping(value = "/tweets/{userId}", produces = { "application/xml",
      "application/json" }, method = RequestMethod.GET)
  public List<Tweet> getDBTweets(@PathVariable String userId, Authentication authentication) {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser != null) {
      LOGGER.debug("Authenticated username: " + authenticatedUser.getUsername());
    }

    return tweetDao.getAllTweets();
  }
}
