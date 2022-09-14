package Akka.Demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.actor.Props;


public class SupervisorSystem {
	public static void main( String[] args ) throws Exception{
      ActorSystem system=ActorSystem.create("system");
      for(int i=0;i<100;i++){
            ActorRef ref=system.actorOf(Props.create(SupervisorActor.class),"supActor"+i);
            ref.tell("Sending to Supervisor Actor,Message "+ i, ActorRef.noSender());
            ref.tell(i, ActorRef.noSender());
      }

      system.terminate();
    }
}
