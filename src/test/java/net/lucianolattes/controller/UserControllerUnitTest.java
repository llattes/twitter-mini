package net.lucianolattes.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;

import net.lucianolattes.config.AppConfig;
import net.lucianolattes.model.UserInfo;
import net.lucianolattes.model.UserProfile;
import net.lucianolattes.service.UserService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class UserControllerUnitTest {

  private MockMvc mockMvc;

  @Mock
  private UserService userService;

  @Mock
  private SecurityContext mockSecurityContext;

  @InjectMocks
  private UserController userController;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @Test
  public void testGetOwnFollowers() throws Exception {
    List<UserProfile> profiles = new ArrayList<UserProfile>();
    UserInfo userInfo = new UserInfo();
    userInfo.setUsername("luciano");
    userInfo.setPassword("luciano");
    userInfo.setEnabled(true);
    userInfo.setRole("ROLE_ADMIN");
    profiles.add(new UserProfile("Luciano Lattes", "I am.", userInfo));

    User user = new User("luciano", "luciano", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
    TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, null);

    when(userService.getFollowers("luciano")).thenReturn(profiles);

    String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("luciano:luciano").getBytes()));
    this.mockMvc
        .perform(get("/api/followers").header("Authorization", basicDigestHeaderValue)
            .accept(MediaType.APPLICATION_JSON)
            .principal(testingAuthenticationToken))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].displayName", is("Luciano Lattes")));

    verify(userService, times(1)).getFollowers("luciano");
    verifyNoMoreInteractions(userService);
  }

  @Test
  public void testFollow() throws Exception {
    List<UserProfile> profiles = new ArrayList<UserProfile>();
    UserInfo userInfo = new UserInfo();
    userInfo.setUsername("luciano");
    userInfo.setPassword("luciano");
    userInfo.setEnabled(true);
    userInfo.setRole("ROLE_ADMIN");
    profiles.add(new UserProfile("Luciano Lattes", "I am.", userInfo));

    User user = new User("luciano", "luciano", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
    TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, null);

    when(userService.follow("luciano", "thousandeyes")).thenReturn(profiles);

    UserInfo bodyContent = new UserInfo();
    bodyContent.setUsername("thousandeyes");

    Gson gson = new Gson();
    String jsonBodyContent = gson.toJson(bodyContent);

    String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("luciano:luciano").getBytes()));
    this.mockMvc
        .perform(post("/api/follow").header("Authorization", basicDigestHeaderValue)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBodyContent)
            .principal(testingAuthenticationToken))
        .andExpect(status().isCreated());

    verify(userService, times(1)).follow("luciano", "thousandeyes");
    verifyNoMoreInteractions(userService);
  }

  @Test
  public void testAutoFollow() throws Exception {
    List<UserProfile> profiles = new ArrayList<UserProfile>();
    UserInfo userInfo = new UserInfo();
    userInfo.setUsername("luciano");
    userInfo.setPassword("luciano");
    userInfo.setEnabled(true);
    userInfo.setRole("ROLE_ADMIN");
    profiles.add(new UserProfile("Luciano Lattes", "I am.", userInfo));

    User user = new User("luciano", "luciano", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
    TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, null);

    when(userService.follow("luciano", "luciano")).thenReturn(profiles);

    UserInfo bodyContent = new UserInfo();
    bodyContent.setUsername("luciano");

    Gson gson = new Gson();
    String jsonBodyContent = gson.toJson(bodyContent);

    String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("luciano:luciano").getBytes()));
    this.mockMvc
        .perform(post("/api/follow").header("Authorization", basicDigestHeaderValue)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBodyContent)
            .principal(testingAuthenticationToken))
        .andExpect(status().isBadRequest());

    verify(userService, times(0)).follow("luciano", "luciano");
    verifyNoMoreInteractions(userService);
  }
}
