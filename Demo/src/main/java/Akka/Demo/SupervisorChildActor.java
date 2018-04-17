package Akka.Demo;

import akka.actor.AbstractActor;

public class SupervisorChildActor extends AbstractActor {
	@Override
	public void postStop() throws Exception {
		System.out.println("stopping child actor");
		super.postStop();
	}

	@Override
	public Receive createReceive() {
		System.out.println("starting childActor");
		return receiveBuilder().match(String.class,(str)->{System.out.println(str);throw new NullPointerException();}).
				match(Integer.class, (ints)-> System.out.println(ints)).
				build();
	}

}
