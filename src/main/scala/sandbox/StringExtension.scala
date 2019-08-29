package sandbox

import cats.data.NonEmptyList
import validated_test.{Config, ConnectionParams, DomainValidation, FormValidator, FormValidatorNel, RegistrationData}

trait StringConverter[A] {
  def convert(value: String): A
}

object StringConverterInstances {
  implicit val stringToIntConverter: StringConverter[Int] = (value: String) => value.head.toInt

  implicit val StringToUpperFirstLetter: StringConverter[Char] = (value: String) => value.toUpperCase.head
}

object StringExtension {

  implicit class StringIntinizer[A](value: String) {
    def headToInt(implicit converter: StringConverter[A]): A = converter.convert(value)
  }

  implicit class StringUpperHeader[A](value: String) {
    def toUpperHead(implicit converter: StringConverter[A]): A = converter.convert(value)
  }

}

//object TestMyExtensionFunction extends App {
//
//  // success
//  println(FormValidator.validateForm(
//    username = "Joe",
//    password = "Passw0r$1234",
//    firstName = "John",
//    lastName = "Doe",
//    age = 21
//  ))
//
//  // fast-fail error
//  println(FormValidator.validateForm(
//    username = "Joe$$$",
//    password = "Passw0r$1234",
//    firstName = "John",
//    lastName = "Doe",
//    age = 21
//  ))
//
//  // success
//  println(FormValidatorNel.validateForm(
//    username = "Joe",
//    password = "Passw0r$1234",
//    firstName = "John",
//    lastName = "Doe",
//    age = 21
//  ))
//
//  // collecting all errors in a NonEmptyList
//  println(FormValidatorNel.validateForm(
//    username = "Joe%%%",
//    password = "password",
//    firstName = "John",
//    lastName = "Doe",
//    age = 21
//  ))
//
//  // success converted to an Either
//  println(
//    FormValidatorNel.validateForm(
//      username = "Joe",
//      password = "Passw0r$1234",
//      firstName = "John",
//      lastName = "Doe",
//      age = 21
//    ).toEither
//  )
//
//  // collecting all errors in a NonEmptyList converted to en Either
//  private val errors: Either[NonEmptyList[DomainValidation], RegistrationData] =
//    FormValidatorNel.validateForm(
//      username = "Joe%%%",
//      password = "password",
//      firstName = "John",
//      lastName = "Doe",
//      age = 21
//    ).toEither
//
//  println(errors)
//
////  val v1 = FormValidatorNel.parallelValidate(config.parse[String]("url").toValidatedNec,
////    config.parse[Int]("port").toValidatedNec)(ConnectionParams.apply)
////  // v1: cats.data.Validated[cats.data.NonEmptyChain[ConfigError],ConnectionParams] = Invalid(Chain(MissingConfig(url), ParseError(port)))
////
////  val v2 = FormValidatorNel.parallelValidate(config.parse[String]("endpoint").toValidatedNec,
////    config.parse[Int]("port").toValidatedNec)(ConnectionParams.apply)
////  // v2: cats.data.Validated[cats.data.NonEmptyChain[ConfigError],ConnectionParams] = Invalid(Chain(ParseError(port)))
////
////  val config = Config(Map(("endpoint", "127.0.0.1"), ("port", "1234")))
////  // config: Config = Config(Map(endpoint -> 127.0.0.1, port -> 1234))
////
////  val v3 = FormValidatorNel.parallelValidate(config.parse[String]("endpoint").toValidatedNec,
////    config.parse[Int]("port").toValidatedNec)(ConnectionParams.apply)
////  // v3: cats.data.Validated[cats.data.NonEmptyChain[ConfigError],ConnectionParams] = Valid(ConnectionParams(127.0.0.1,1234))
//
//
//
//  /*import sandbox.StringConverterInstances._
//  import sandbox.StringExtension._
//
//  def gimmeHeadToInt(s: String): Int = s.headToInt
//
//  def gimmeHeadToUpper(s: String): Char = s.toUpperHead
//
//  val foo = "hello"
//  print(gimmeHeadToInt(foo))
//  print(gimmeHeadToInt(foo))*/
//}