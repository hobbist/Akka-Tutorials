package Akka.Demo;

import java.util.concurrent.CompletionStage;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.pattern.PatternsCS;
import akka.util.Timeout;
import scala.Function1;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.util.Try;

public class App 
{
	public static void main( String[] args ) throws Exception
    {
      ActorSystem system=ActorSystem.create("system");
      ActorRef ref=system.actorOf(Props.create(DemoActor.class),"demoActor");
      Timeout timeout= new Timeout(Duration.create(5, "seconds"));
      Future<Object> ack=Patterns.ask(ref,"hello from ask", timeout);
      CompletionStage<String> result = PatternsCS
    		  .ask(ref,"hello from ask", timeout)
    		  .thenApply((msg)->(String) msg);
      	result.whenComplete((response,throwable)->{
      		System.out.println("Completion Stage :"+response);
      	});
      ack.onComplete(new Function1<Try<Object>, String>() {
		public String apply(Try<Object> v1) {
			System.out.println(v1.get().toString());
			return v1.get().toString();
		}
	}, system.dispatcher());
    }
}
