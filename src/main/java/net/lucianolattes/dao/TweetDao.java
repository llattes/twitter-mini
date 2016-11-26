package net.lucianolattes.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.lucianolattes.mapping.TweetRowMapper;
import net.lucianolattes.model.Tweet;

@Component
@Transactional
public class TweetDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Dummy database. Initialize with some dummy values.
    private static List<Tweet> tweets;

    {
	tweets = new ArrayList<Tweet>();
	tweets.add(new Tweet(101L, "John", "Tu vieja en tanga"));
	tweets.add(new Tweet(201L, "Russ", "Smith is on fire!"));
	tweets.add(new Tweet(301L, "Kate", "Williams? You mean white chocolate?"));
	tweets.add(new Tweet(System.currentTimeMillis(), "Viral", "Patel. Mi nombre es Pi Patel!"));
    }

    public List<Tweet> list() {
	return tweets;
    }

    public List<Tweet> getAllTweets() {
	List<Tweet> tweets = jdbcTemplate.query("select * from tweet", new TweetRowMapper());
	return tweets;
    }

    public Tweet get(Long id) {
	for (Tweet tweet : tweets) {
	    if (tweet.getId().equals(id)) {
		return tweet;
	    }
	}
	return null;
    }

    public Tweet create(Tweet tweet) {
	tweets.add(tweet);
	return tweet;
    }

    public Long delete(Long id) {
	for (Tweet tweet : tweets) {
	    if (tweet.getId().equals(id)) {
		tweets.remove(tweet);
		return id;
	    }
	}
	return null;
    }

    public Tweet update(Long id, Tweet customer) {
	for (Tweet tweet : tweets) {
	    if (tweet.getId().equals(id)) {
		customer.setId(tweet.getId());
		tweets.remove(tweet);
		tweets.add(customer);
		return customer;
	    }
	}
	return null;
    }
}
