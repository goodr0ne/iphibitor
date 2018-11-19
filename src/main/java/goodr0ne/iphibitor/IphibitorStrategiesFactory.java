package goodr0ne.iphibitor;

import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.*;

@SuppressWarnings("ALL")
@Repository
public class IphibitorStrategiesFactory {

  @Autowired
  private ApplicationContext applicationContext;
  private Map<Class, List<Object>> annotatedTypes = new HashMap<>();
  private Map<Class, IphibitorStrategy> strategyCache = new HashMap<>();

  @PostConstruct
  public void init() {
    Map<String, Object> annotatedBeanClasses =
            applicationContext.getBeansWithAnnotation(IphibitorStrategy.class);
    sanityCheck(annotatedBeanClasses.values());
    for (Object bean : annotatedBeanClasses.values()) {
      IphibitorStrategy strategyAnnotation = strategyCache.get(bean.getClass());
      getBeansWithSameType(strategyAnnotation).add(bean);
    }
  }

  private void sanityCheck(Collection<Object> annotatedBeanClasses) {
    Set<String> usedStrategies = new HashSet<>();
    for (Object bean : annotatedBeanClasses) {
      IphibitorStrategy strategyAnnotation =
              AnnotationUtils.findAnnotation(bean.getClass(), IphibitorStrategy.class);
      if (strategyAnnotation == null){
        try {
          Object target = ((Advised) bean).getTargetSource().getTarget();
          strategyAnnotation = AnnotationUtils.findAnnotation(target.getClass(), IphibitorStrategy.class);
        } catch (Exception ignored) {}
      }
      strategyCache.put(bean.getClass(), strategyAnnotation);
      if (isDefault(strategyAnnotation)) {
        ifNotExistAdd(strategyAnnotation.type(), "default", usedStrategies);
      }
      for (IphibitorStrategyUser profile : strategyAnnotation.users()) {
        ifNotExistAdd(strategyAnnotation.type(), profile, usedStrategies);
      }
    }
  }

  private List<Object> getBeansWithSameType(IphibitorStrategy strategyAnnotation) {
    List<Object> beansWithSameType = annotatedTypes.get(strategyAnnotation.type());
    if (beansWithSameType != null) {
      return beansWithSameType;
    } else {
      List<Object> newBeansList = new ArrayList<>();
      annotatedTypes.put(strategyAnnotation.type(), newBeansList);
      return newBeansList;
    }
  }

  private boolean isDefault(IphibitorStrategy strategyAnnotation) {
    return (strategyAnnotation.users().length == 0);
  }

  private void ifNotExistAdd(Class type, IphibitorStrategyUser profile, Set<String> usedStrategies) {
    ifNotExistAdd(type, profile.name(), usedStrategies);
  }

  private void ifNotExistAdd(Class type, String user, Set<String> usedStrategies) {
    if (usedStrategies.contains(createKey(type, user))) {
      throw new RuntimeException("There can only be a single strategy for each type, " +
              "found multiple for type '" + type + "' and user '" + user + "'");
    }
    usedStrategies.add(createKey(type, user));
  }

  private String createKey(Class type, String profile) {
    return (type+"_"+profile).toLowerCase();
  }

  public <T> T getStrategy(Class<T> strategyType, IphibitorStrategyUser user) {
    List<Object> strategyBeans = annotatedTypes.get(strategyType);
    Assert.notEmpty(strategyBeans, "No strategies found of type '"
            + strategyType.getName()+ "'");
    Object strategy = findStrategyMatchingProfile(strategyBeans, user);
    if (strategy == null) {
      throw new RuntimeException("No strategy found for type '" + strategyType + "'");
    }
    return (T)strategy;
  }

  private Object findStrategyMatchingProfile(List<Object> strategyBeans,
                                             IphibitorStrategyUser user) {
    Object defaultStrategy = null;
    for (Object bean : strategyBeans) {
      IphibitorStrategy strategyAnnotation = strategyCache.get(bean.getClass());
      if(user != null) {
        for (IphibitorStrategyUser possibleUser : strategyAnnotation.users()) {
          if (possibleUser == user) {
            return bean;
          }
        }
      }
      if (isDefault(strategyAnnotation)) {
        defaultStrategy = bean;
        if(user == null) {
          return defaultStrategy;
        }
      }
    }
    return defaultStrategy;
  }
}
