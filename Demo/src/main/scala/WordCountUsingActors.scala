import Job.{JobIn}
import Mapper.{MapperIn, MapperOut}
import Reducer.{ReducerEnd, ReducerIn, ReducerOut}
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.util.Random

class Job extends Actor {

  val r:ActorRef=context.actorOf(Props[Reducer],s"reducer-${Random.nextInt(100)}")
  var taskCount=0
  var mapOut=0
  override def receive: Receive = {
    case msg:JobIn =>{
      scala.io.Source.fromFile(msg.m).getLines().foreach(l=>{
        taskCount=taskCount+1
        val mapper=context.actorOf(Props[Mapper],s"mapper-${Random.nextInt(100)}")
        mapper ! MapperIn(l)
      })
    }
    case mr:MapperOut=>{
      r ! ReducerIn(mr.o)
      mapOut=mapOut+1
      if(mapOut==taskCount) {
        r ! ReducerEnd()
      }
    }
    case op:ReducerOut=>{
      println(s"final op - ${op.o}")
    }
  }
}

object Job {
  case class JobIn(m:String)
  case class JobOut(o:Map[String,Int])
}



class Mapper extends Actor{

  override def receive: Receive = {
    case msg:MapperIn =>{
      sender() ! MapperOut(msg.m.split(" ").groupBy(s=>s).map(x=>(x._1,x._2.size)))
    }
  }
}

object Mapper {
  case class MapperIn(m:String)
  case class MapperOut(o:Map[String,Int])
}


class Reducer extends Actor {
  var state:Map[String,Int] = Map.empty
  override def receive: Receive = {
    case in:ReducerIn => {
        in.o.foreach(x=>{
          state=state + ((x._1,x._2+state.getOrElse(x._1,0)))
        })
    }
    case s:ReducerEnd => {
      sender() ! ReducerOut(state)
    }
    case _ => println("Failed to process Message")
  }
}

object Reducer{
  case class ReducerIn(o:Map[String,Int])
  case class ReducerOut(o:Map[String,Int])
  case class ReducerEnd()
}


object WordCountUsingActors {
  def main(args: Array[String]): Unit = {
    val system=ActorSystem("mapReduce")
    val actor=system.actorOf(Props[Job],"mr-Job")
    actor ! JobIn("/home/kapil/Downloads/Test_Text.txt")

  }
}