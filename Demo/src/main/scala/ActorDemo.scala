import akka.actor.{Actor, ActorSystem, Props}

import scala.util.Random

case class Greet(msg:String)
case class Reply(msg:String)


class Greeter extends Actor{

  override def receive: Receive = {
    case m:Greet => {
      println("message received in greeter")
      sender() ! s"Greeting from $self"
    }
    case x => {
      println(x)
      sender() ! s"Wrong Message Received"
    }
    }
  }



class ActorDemo extends Actor {
  override def receive: Receive = {

    case s:String => println(s"Received Message as $s")
    case m:Greet => {
      println("message received in Actor Demo")
      val child=context.actorOf(Props[Greeter],s"Actor-${Random.nextInt(100)}")
      child ! m
    }
    case _=> println(s"Dirty Val")
  }
}

object ActorDemo {
  def main(args: Array[String]): Unit = {
    val system=ActorSystem("greetingSystem")
    val parent=system.actorOf(Props[ActorDemo],"starting")
    parent ! Greet("This is inital comm")

  }


}