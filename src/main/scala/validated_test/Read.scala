package validated_test

trait Read[A] {
  def read(s: String): Option[A]
}

object Read {

  def apply[A](implicit A: Read[A]): Read[A] = A

  implicit val stringRead: Read[String] = (s: String) => Some(s)

  implicit val intRead: Read[Int] = { s: String =>
    if (s.matches("-?[0-9]+")) Some(s.toInt)
    else None
  }

}