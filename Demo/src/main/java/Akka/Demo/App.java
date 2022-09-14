package Akka.Demo;

import java.util.concurrent.CompletionStage;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class App 
{
	public static void main( String[] args ) throws Exception
    {
      ActorSystem system=ActorSystem.create("system");
      ActorRef ref=system.actorOf(Props.create(DemoActor.class),"demoActor");
      Timeout timeout= new Timeout(Duration.create(5, "seconds"));
      Future<Object> ack=Patterns.ask(ref,"hello from Main", timeout);
      ack.onComplete(v1 -> {
		  System.out.println(v1.get().toString());
		  return v1.get().toString();
	  }, system.dispatcher());
    }
}
