package com.bigdataConcept.akka.serverless.ewallet.domain;

import com.akkaserverless.javasdk.AkkaServerless;
import com.akkaserverless.javasdk.testkit.junit.AkkaServerlessTestkitResource;
import com.bigdataConcept.akka.serverless.ewallet.Main;
import com.bigdataConcept.akka.serverless.ewallet.MainComponentRegistrations;
import org.junit.ClassRule;


public class IntegrationTest {

    @ClassRule
    public static final AkkaServerlessTestkitResource testkit = new AkkaServerlessTestkitResource(MainComponentRegistrations.withGeneratedComponentsAdded(new AkkaServerless()));
}
