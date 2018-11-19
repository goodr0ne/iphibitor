package goodr0ne.iphibitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class IphibitorController {
  private final IphibitorStrategiesFactory strategyFactory;

  @Autowired
  public IphibitorController(IphibitorStrategiesFactory strategyFactory) {
    this.strategyFactory = strategyFactory;
  }

  @RequestMapping(value = {"/", "/index", "/help"}, method = GET)
  public String index() {
    strategyFactory.getStrategy(Iphibitionable.class,
            IphibitorStrategyUser.SOME_OTHER_SOURCE).inhibit("");
    return "Welcome to iphibitor!<br>" +
            "run localhost:8080/inhibit request for single operation inhibition check<br>" +
            "or localhost:8080/inhibit/{number_of_requests} for multiple operations check";
  }

  @RequestMapping(value = {"/inhibit/{reqNumber}"}, method = GET)
  String inhibitRequest(@PathVariable int reqNumber,
                        HttpServletRequest request) {
    String ip = IphibitorWebUtils.getClientIpAddress(request);
    for (int i = 0; i < reqNumber; i++) {
      strategyFactory.getStrategy(Iphibitionable.class,
              IphibitorStrategyUser.IPHIBITOR_CONTROLLER).inhibit(ip);
    }
    return "Produce inhibition, quantity - " + reqNumber + ", from ip address - " + ip;
  }

  @RequestMapping(value = {"/inhibit"}, method = GET)
  public String inhibitRequestBlank() {
    strategyFactory.getStrategy(Iphibitionable.class,
            IphibitorStrategyUser.SOME_BLOCKING_CONTROLLER).inhibit("");
    return "blank string";
  }
}
