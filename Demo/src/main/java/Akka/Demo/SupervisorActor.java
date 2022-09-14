package Akka.Demo;

import java.util.concurrent.TimeUnit;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

public class SupervisorActor extends AbstractActor {
	@Override
	public void postStop() throws Exception {
		System.out.println("Stoping supervisor actor");
		super.postStop();
	}
	public ActorRef childRef;
	public SupervisorActor() {
		System.out.println("Creating child Actor for Current Supervisor");
		childRef=getContext().actorOf(Props.create(SupervisorChildActor.class),"supChildActor");
	}
	
	@Override
	public SupervisorStrategy supervisorStrategy() {
		return stratergy;
	}
	private static SupervisorStrategy stratergy=new OneForOneStrategy(10, 
			Duration.create(2,TimeUnit.SECONDS), param -> {
				if(param instanceof NullPointerException) {
					return SupervisorStrategy.restart();
				}
				else {
					return SupervisorStrategy.restart();
				}
			}
	);
	
	@Override
	public Receive createReceive() {
		System.out.println("Starting Supervisor Actor");
		return receiveBuilder().match(String.class, str -> childRef.tell(str, getSender())).match(Integer.class, ints-> childRef.tell(ints, getSelf())).build();
	}

}
