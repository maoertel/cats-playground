package sandbox

import cats.instances.string._
import cats.syntax.semigroup._

object Main extends App {
  // combine of Semigroup
  println("Hello " |+| "Cats!")
}