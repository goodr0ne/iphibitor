package goodr0ne.iphibitor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class IphibitorController {
  private static AlwaysAcceptingIphibitor acceptingIphibitor = new AlwaysAcceptingIphibitor();
  private static AlwaysCheckingIphibitor checkingIphibitor = new AlwaysCheckingIphibitor();
  private static AlwaysBlockingIphibitor blockingIphibitor = new AlwaysBlockingIphibitor();

  @RequestMapping(value = {"/", "/index", "/help"}, method = GET)
  public String index() {
    acceptingIphibitor.inhibit();
    return "Welcome to iphibitor!<br>" +
            "run localhost:8080/inhibit request for single operation inhibition check<br>" +
            "or localhost:8080/inhibit/{number_of_requests} for multiple operations check";
  }

  @RequestMapping(value = {"/inhibit/{reqNumber}"}, method = GET)
  public String inhibitRequest(@PathVariable int reqNumber) {
    for (int i = 0; i < reqNumber; i++) {
      checkingIphibitor.inhibit();
    }
    return "Produce inhibition, quantity - " + reqNumber + "...";
  }

  @RequestMapping(value = {"/inhibit"}, method = GET)
  public String inhibitRequest() {
    blockingIphibitor.inhibit();
    return "blank string";
  }
}
