package net.lucianolattes.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import net.lucianolattes.dao.TweetDao;
import net.lucianolattes.dao.UserDao;
import net.lucianolattes.exception.TweetException;
import net.lucianolattes.exception.UserNotFoundException;
import net.lucianolattes.model.Tweet;
import net.lucianolattes.model.UserProfile;

/**
 * This service acts as a middleware between <tt>TweetController</tt> and DAO
 * layer. Gathers data from controller, performs validation and business logic
 * and calls repositories for data manipulation.
 *
 * @author lucianolattes
 */
@Service
public class TweetService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TweetService.class);

  @Autowired
  private TweetDao tweetDao;

  @Autowired
  private UserDao userDao;

  @Transactional
  public List<Tweet> getTweets(String username, String search) {
    assert TransactionSynchronizationManager.isActualTransactionActive();
    LOGGER.debug("Initializing getTweets transaction...");

    List<UserProfile> following = userDao.getFollowing(username);
    List<String> followingUsernames = following.stream().map(userProfile -> userProfile.getUserInfo().getUsername())
        .collect(Collectors.toList());
    return tweetDao.getUserTweets(username, followingUsernames, search);
  }

  public List<Tweet> getTweetsByGivenUser(String username, String search) throws UserNotFoundException {
    assert TransactionSynchronizationManager.isActualTransactionActive();
    LOGGER.debug("Initializing getTweetsByGivenUser " + (TransactionSynchronizationManager.isActualTransactionActive()
        ? "with active transaction..." : "w/o active transaction..."));

    if (userDao.checkUser(username)) {
      return getTweets(username, search);
    } else {
      throw new UserNotFoundException("The user '" + username + "' does not exist");
    }
  }

  public Tweet insertTweet(String username, Tweet tweet) {
    tweet.setAuthor(username);
    tweetDao.insertTweet(tweet);
    List<Tweet> tweets = getTweets(username, null);
    return tweets.get(tweets.size() - 1);
  }

  public Tweet retweet(String username, Long id) throws TweetException {
    Tweet originalTweet = tweetDao.getTweetById(id);
    if (originalTweet == null) {
      throw new TweetException("Could not find tweet with ID '" + id + "' to retweet");
    }

    if (!tweetDao.retweetAllowed(username, id)) {
      throw new TweetException("The tweet '" + id + "' was already retweeted or it is " + username + "'s own tweet");
    }

    Tweet retweet = new Tweet(null, null, originalTweet.getContent(), true, originalTweet.getId(),
        originalTweet.getAuthor());
    return insertTweet(username, retweet);
  }
}
