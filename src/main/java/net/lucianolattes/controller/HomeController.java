package net.lucianolattes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;

/**
 * Very simple {@link Controller Controller} that performs a redirect to the
 * Swagger UI when a request to the root path arrives.
 *
 * @author lucianolattes
 */
@Controller
@ApiIgnore
public class HomeController {

  @RequestMapping("/")
  public String home() {
    return "redirect:swagger-ui.html";
  }
}
