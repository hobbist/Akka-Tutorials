package Akka.Demo;

import akka.actor.AbstractActor;
import akka.japi.pf.FI;
import akka.japi.pf.ReceiveBuilder;

public class DemoActor extends AbstractActor {
	@Override
	public Receive createReceive() {
		return ReceiveBuilder.create().match(String.class, arg0 -> {
			System.out.println("Received " + arg0);
			getSender().tell("Acknowledged from Demo "+ arg0, getSelf());
		}).build();
	}
}
