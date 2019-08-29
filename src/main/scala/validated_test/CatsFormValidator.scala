package validated_test

class CatsFormValidator {

  import cats.data._
  import cats.implicits._

  def validateUserName(userName: String): Validated[DomainValidation, String] = FormValidator.validateUserName(userName).toValidated

  def validatePassword(password: String): Validated[DomainValidation, String] = FormValidator.validatePassword(password).toValidated

  def validateFirstName(firstName: String): Validated[DomainValidation, String] = FormValidator.validateFirstName(firstName).toValidated

  def validateLastName(lastName: String): Validated[DomainValidation, String] = FormValidator.validateLastName(lastName).toValidated

  def validateAge(age: Int): Validated[DomainValidation, Int] = FormValidator.validateAge(age).toValidated

  //  def validateForm(username: String, password: String, firstName: String, lastName: String, age: Int): Validated[DomainValidation, RegistrationData] = {
  //    for {
  //      validatedUserName <- validateUserName(username)
  //      validatedPassword <- validatePassword(password)
  //      validatedFirstName <- validateFirstName(firstName)
  //      validatedLastName <- validateLastName(lastName)
  //      validatedAge <- validateAge(age)
  //    } yield RegistrationData(validatedUserName, validatedPassword, validatedFirstName, validatedLastName, validatedAge)
  //
  //  }

}

object CatsFormValidator extends CatsFormValidator with App {

//  CatsFormValidator.validateForm(
//    username = "fakeUs3rname",
//    password = "password",
//    firstName = "John",
//    lastName = "Doe",
//    age = 15
//  )

}