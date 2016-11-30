package net.lucianolattes.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;

import net.lucianolattes.config.AppConfig;
import net.lucianolattes.model.Tweet;
import net.lucianolattes.service.TweetService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class TweetControllerUnitTest {

  private MockMvc mockMvc;

  @Mock
  private TweetService tweetService;

  @Mock
  private SecurityContext mockSecurityContext;

  @InjectMocks
  private TweetController tweetController;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(tweetController).build();
  }

  @Test
  public void testGetOwnTweets() throws Exception {
    List<Tweet> tweets = new ArrayList<Tweet>();
    tweets.add(new Tweet(1L, "luciano", "Tweet #1", false, null, null));
    tweets.add(new Tweet(2L, "luciano", "Tweet #2", false, null, null));

    User user = new User("luciano", "luciano", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
    TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, null);

    when(tweetService.getTweets("luciano", null)).thenReturn(tweets);

    String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("luciano:luciano").getBytes()));
    this.mockMvc
        .perform(get("/api/tweets").header("Authorization", basicDigestHeaderValue)
            .accept(MediaType.APPLICATION_JSON)
            .principal(testingAuthenticationToken))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].content", is("Tweet #1")))
        .andExpect(jsonPath("$[1].content", is("Tweet #2")));

    verify(tweetService, times(1)).getTweets("luciano", null);
    verifyNoMoreInteractions(tweetService);
  }

  @Test
  public void testGetUserTweets() throws Exception {
    List<Tweet> tweets = new ArrayList<Tweet>();
    tweets.add(new Tweet(1L, "luciano", "Tweet #1", false, null, null));
    tweets.add(new Tweet(2L, "luciano", "Tweet #2", false, null, null));

    User user = new User("luciano", "luciano", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
    TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, null);

    when(tweetService.getTweetsByGivenUser("luciano", null)).thenReturn(tweets);

    String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("luciano:luciano").getBytes()));
    this.mockMvc
        .perform(get("/api/tweets/luciano").header("Authorization", basicDigestHeaderValue)
            .accept(MediaType.APPLICATION_JSON)
            .principal(testingAuthenticationToken))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].content", is("Tweet #1")))
        .andExpect(jsonPath("$[1].content", is("Tweet #2")));

    verify(tweetService, times(1)).getTweetsByGivenUser("luciano", null);
    verifyNoMoreInteractions(tweetService);
  }

  @Test
  public void testGetUserTweetsWithSearch() throws Exception {
    List<Tweet> tweets = new ArrayList<Tweet>();
    tweets.add(new Tweet(1L, "luciano", "Tweet #1", false, null, null));
    tweets.add(new Tweet(2L, "luciano", "Tweet #2", false, null, null));

    User user = new User("luciano", "luciano", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
    TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, null);

    when(tweetService.getTweetsByGivenUser("luciano", "tweet")).thenReturn(tweets);

    String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("luciano:luciano").getBytes()));
    this.mockMvc
        .perform(get("/api/tweets/luciano?search=tweet").header("Authorization", basicDigestHeaderValue)
            .accept(MediaType.APPLICATION_JSON)
            .principal(testingAuthenticationToken))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].content", is("Tweet #1")))
        .andExpect(jsonPath("$[1].content", is("Tweet #2")));

    verify(tweetService, times(1)).getTweetsByGivenUser("luciano", "tweet");
    verifyNoMoreInteractions(tweetService);
  }

  @Test
  public void testGetUserTweetsWithSearchXML() throws Exception {
    List<Tweet> tweets = new ArrayList<Tweet>();
    tweets.add(new Tweet(1L, "luciano", "Tweet #1", false, null, null));
    tweets.add(new Tweet(2L, "luciano", "Tweet #2", false, null, null));

    User user = new User("luciano", "luciano", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
    TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, null);

    when(tweetService.getTweetsByGivenUser("luciano", "tweet")).thenReturn(tweets);

    String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("luciano:luciano").getBytes()));
    this.mockMvc
        .perform(get("/api/tweets/luciano?search=tweet").header("Authorization", basicDigestHeaderValue)
            .accept(MediaType.APPLICATION_XML)
            .principal(testingAuthenticationToken))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/xml;charset=UTF-8"));

    verify(tweetService, times(1)).getTweetsByGivenUser("luciano", "tweet");
    verifyNoMoreInteractions(tweetService);
  }

  @Test
  public void testAddTweet() throws Exception {
    Tweet tweet = new Tweet(1L, "luciano", "Tweet #1", false, null, null);
    User user = new User("luciano", "luciano",
        AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
    TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, null);

    when(tweetService.insertTweet(eq("luciano"), any())).thenReturn(tweet);

    Tweet bodyContent = new Tweet();
    bodyContent.setContent("Tweet #1");
    bodyContent.setTimestamp(null);
    Gson gson = new Gson();
    String jsonBodyContent = gson.toJson(bodyContent);

    String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("luciano:luciano").getBytes()));
    this.mockMvc
        .perform(post("/api/tweets/create").header("Authorization", basicDigestHeaderValue)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBodyContent)
            .principal(testingAuthenticationToken))
        .andExpect(status().isCreated());

    verify(tweetService, times(1)).insertTweet(eq("luciano"), any());
    verifyNoMoreInteractions(tweetService);
  }

  @Test
  public void testAddTweetUltraLong() throws Exception {
    Tweet tweet = new Tweet(1L, "luciano", "Tweet #1", false, null, null);
    User user = new User("luciano", "luciano",
        AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
    TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, null);

    when(tweetService.insertTweet(eq("luciano"), any())).thenReturn(tweet);

    Tweet bodyContent = new Tweet();
    bodyContent.setContent(
        "Java is a general-purpose computer programming language that is concurrent, "
            + "class-based, object-oriented,[14] and specifically designed to have as few "
            + "implementation dependencies as possible. It is intended to let application "
            + "developers 'write once, run anywhere' (WORA),[15] meaning that compiled "
            + "Java code can run on all platforms that support Java without the need for "
            + "recompilation.[16] Java applications are typically compiled to bytecode that "
            + "can run on any Java virtual machine (JVM) regardless of computer architecture.");
    bodyContent.setTimestamp(null);
    Gson gson = new Gson();
    String jsonBodyContent = gson.toJson(bodyContent);

    String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("luciano:luciano").getBytes()));
    this.mockMvc
        .perform(post("/api/tweets/create").header("Authorization", basicDigestHeaderValue)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBodyContent)
            .principal(testingAuthenticationToken))
        .andExpect(status().isBadRequest());

    // No interactions, fails before.
    verify(tweetService, times(0)).insertTweet(eq("luciano"), any());
    verifyNoMoreInteractions(tweetService);
  }

  @Test
  @WithUserDetails(value = "luciano") // User that exists in the populated DB.
  public void testRetweet() throws Exception {
    Tweet tweet = new Tweet(1L, "luciano", "Tweet #1", false, null, null);
    User user = new User("luciano", "luciano",
        AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
    TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, null);

    when(tweetService.retweet("luciano", 1L)).thenReturn(tweet);

    String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("luciano:luciano").getBytes()));
    this.mockMvc
        .perform(post("/api/tweets/retweet/1").header("Authorization", basicDigestHeaderValue)
            .accept(MediaType.APPLICATION_JSON)
            .principal(testingAuthenticationToken))
        .andExpect(status().isCreated());

    verify(tweetService, times(1)).retweet("luciano", 1L);
    verifyNoMoreInteractions(tweetService);
  }
}
