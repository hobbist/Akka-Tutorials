package Akka.Demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class SupervisorSystem {
	public static void main( String[] args ) throws Exception{
      ActorSystem system=ActorSystem.create("system");
      ActorRef ref=system.actorOf(Props.create(SupervisorActor.class),"supActor");
      ref.tell("sdf", ActorRef.noSender());
      //Future<Object> sck=Patterns.ask(ref, "Hello", 2000);
      //String result=(String) Await.result(sck, Duration.create(3, TimeUnit.SECONDS));
      Thread.sleep(3000);
      ref.tell(1, ActorRef.noSender());
      system.terminate();
    }
}
