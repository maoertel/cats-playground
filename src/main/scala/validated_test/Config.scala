package validated_test

import cats.{Semigroup, SemigroupK}
import cats.data.{NonEmptyChain, Validated}
import cats.data.Validated.{Invalid, Valid}

case class ConnectionParams(url: String, port: Int)

case class Config(map: Map[String, String]) {

  def parse[A: Read](key: String): Validated[ConfigError, A] =
    map.get(key) match {
      case None => Invalid(MissingConfig(key))
      case Some(value) =>
        Read[A].read(value) match {
          case None => Invalid(ParseError(key))
          case Some(a) => Valid(a)
        }
    }

  val config = Config(Map(("endpoint", "127.0.0.1"), ("port", "not an int")))

  implicit val necSemigroup: Semigroup[NonEmptyChain[ConfigError]] =
    SemigroupK[NonEmptyChain].algebra[ConfigError]

  implicit val readString: Read[String] = Read.stringRead
  implicit val readInt: Read[Int] = Read.intRead

}

