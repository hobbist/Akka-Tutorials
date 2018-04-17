package Akka.Demo;

import akka.actor.AbstractActor;
import akka.japi.pf.FI;
import akka.japi.pf.ReceiveBuilder;

public class DemoActor extends AbstractActor {
	@Override
	public Receive createReceive() {
		return ReceiveBuilder.create().match(String.class,new FI.UnitApply<String>() {
			public void apply(String arg0) throws Exception {
				System.out.println(arg0);
				getSender().tell(arg0, getSelf());
			}
		}).build();
	}
}
