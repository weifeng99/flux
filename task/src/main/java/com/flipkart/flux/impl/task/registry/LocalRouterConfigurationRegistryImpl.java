package com.flipkart.flux.impl.task.registry;

import akka.cluster.routing.ClusterRouterPoolSettings;
import com.flipkart.polyguice.core.ConfigurationProvider;
import com.flipkart.polyguice.core.Initializable;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Option;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Retrieves router configurations from local config
 * Uses the default configurations for routers whose config is not explicitly given
 */
@Singleton
public class LocalRouterConfigurationRegistryImpl implements RouterConfigurationRegistry,Initializable {

    private static final Logger log = LoggerFactory.getLogger(LocalRouterConfigurationRegistryImpl.class);

    Set<String> routerNames;
    ConfigurationProvider configurationProvider;

    private Integer defaultTotalInstances;
    private Integer defaultMaxInstancesPerNode;

    LocalRouterConfigurationRegistryImpl() {
    }

    @Inject
    public LocalRouterConfigurationRegistryImpl(@Named("router.names") Set<String> routerNames,
                                                ConfigurationProvider configurationProvider) {
        this();
        this.routerNames = routerNames;
        this.configurationProvider = configurationProvider;
    }

    @Override
    public Iterable<Pair<String, ClusterRouterPoolSettings>> getConfigurations() {
        Set<Pair<String,ClusterRouterPoolSettings>> routerSettings = new HashSet<>();
        this.routerNames.forEach((rName) -> {
            {
                Integer totalInstances = (Integer) configurationProvider.getValue("routers." + rName + ".totalInstances", Integer.class);
                Integer maxInstances = (Integer) configurationProvider.getValue("routers." + rName + ".maxInstancesPerNode", Integer.class);
                if (totalInstances == null || maxInstances == null) {
                    log.info("Could not find settings for router {}, going with default settings", rName);
                    totalInstances = defaultTotalInstances;
                    maxInstances = defaultMaxInstancesPerNode;
                }
                routerSettings.add(new Pair<>(rName, new ClusterRouterPoolSettings(totalInstances, maxInstances, true, Option.empty())));
            }
        });
        return routerSettings;
    }

    @Override
    public void initialize() {
        this.defaultTotalInstances = (Integer) this.configurationProvider.getValue("routers.default.totalInstances",Integer.class);
        this.defaultMaxInstancesPerNode = (Integer) this.configurationProvider.getValue("routers.default.maxInstancesPerNode",Integer.class);
        if (this.defaultMaxInstancesPerNode == null || this.defaultTotalInstances == null) {
            throw new ConfigurationException("Could not find default router configurations!!");
        }
    }
}