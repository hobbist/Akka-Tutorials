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
		System.out.println("stooping supervisor actor");
		super.postStop();
	}
	public ActorRef childRef;
	public SupervisorActor() {
		childRef=getContext().actorOf(Props.create(SupervisorChildActor.class),"supChildActor");
	}
	
	@Override
	public SupervisorStrategy supervisorStrategy() {
		return stratergy;
	}
	private static SupervisorStrategy stratergy=new OneForOneStrategy(10, 
			Duration.create(2,TimeUnit.SECONDS), new Function<Throwable, SupervisorStrategy.Directive>() {
				@Override
				public Directive apply(Throwable param) throws Exception {
					if(param instanceof NullPointerException) {
						return SupervisorStrategy.restart();
					}
					else {
						return SupervisorStrategy.restart();
					}
				}
			}
			);
	
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, str ->{
			childRef.tell(str, getSender());
			//sender().tell(str, getSelf());
		}).match(Integer.class, ints->{
			childRef.tell(ints, getSelf());
		}).	build();
	}

}
