package validated_test

import cats.data.Validated.{Invalid, Valid}
import cats.data.{Validated, ValidatedNel}
import cats.implicits._
import cats.{Applicative, Semigroup}

sealed trait FormValidatorNel {

  type ValidationResult[A] = ValidatedNel[DomainValidation, A]

  // validNel or invalidNel lift the success or failure in their respective container (Valid or Invalid)

  private def validateUserName(userName: String): ValidationResult[String] =
    if (userName.matches("^[a-zA-Z0-9]+$")) userName.validNel
    else UsernameHasSpecialCharacters.invalidNel

  private def validatePassword(password: String): ValidationResult[String] =
    if (password.matches("(?=^.{10,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")) password.validNel
    else PasswordDoesNotMeetCriteria.invalidNel

  private def validateFirstName(firstName: String): ValidationResult[String] =
    if (firstName.matches("^[a-zA-Z]+$")) firstName.validNel
    else FirstNameHasSpecialCharacters.invalidNel

  private def validateLastName(lastName: String): ValidationResult[String] =
    if (lastName.matches("^[a-zA-Z]+$")) lastName.validNel
    else LastNameHasSpecialCharacters.invalidNel

  private def validateAge(age: Int): ValidationResult[Int] =
    if (age >= 18 && age <= 75) age.validNel
    else AgeIsInvalid.invalidNel

  def validateForm(username: String, password: String, firstName: String, lastName: String, age: Int): ValidationResult[RegistrationData] = {
    (validateUserName(username),
      validatePassword(password),
      validateFirstName(firstName),
      validateLastName(lastName),
      validateAge(age)) mapN RegistrationData
  }

  def parallelValidate[E: Semigroup, A, B, C](v1: Validated[E, A], v2: Validated[E, B])(f: (A, B) => C): Validated[E, C] =
    (v1, v2) match {
      case (Valid(a), Valid(b)) => Valid(f(a, b))
      case (Valid(_), i@Invalid(_)) => i
      case (i@Invalid(_), Valid(_)) => i
      case (Invalid(e1), Invalid(e2)) => Invalid(Semigroup[E].combine(e1, e2))
    }

  implicit def validatedApplicative[E: Semigroup]: Applicative[Validated[E, *]] =
    new Applicative[Validated[E, *]] {
      def ap[A, B](f: Validated[E, A => B])(fa: Validated[E, A]): Validated[E, B] =
        (fa, f) match {
          case (Valid(a), Valid(fab)) => Valid(fab(a))
          case (i@Invalid(_), Valid(_)) => i
          case (Valid(_), i@Invalid(_)) => i
          case (Invalid(e1), Invalid(e2)) => Invalid(Semigroup[E].combine(e1, e2))
        }

      def pure[A](x: A): Validated[E, A] = Validated.valid(x)
    }

}

object FormValidatorNel extends FormValidatorNel {
//  import cats.Monad
//
//  implicit def validatedMonad[E]: Monad[Validated[E, *]] =
//    new Monad[Validated[E, *]] {
//      def flatMap[A, B](fa: Validated[E, A])(f: A => Validated[E, B]): Validated[E, B] =
//        fa match {
//          case Valid(a)     => f(a)
//          case i@Invalid(_) => i
//        }
//
//      def pure[A](x: A): Validated[E, A] = Valid(x)
//
//      @annotation.tailrec
//      def tailRecM[A, B](a: A)(f: A => Validated[E, Either[A, B]]): Validated[E, B] =
//        f(a) match {
//          case Valid(Right(b)) => Valid(b)
//          case Valid(Left(a)) => tailRecM(a)(f)
//          case i@Invalid(_) => i
//        }
//    }
}