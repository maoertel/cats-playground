package validated_test

trait Monad[F[_]] {

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  def pure[A](x: A): F[A]

  def map[A, B](fa: F[A])(f: A => B): F[B] =
    flatMap(fa)(f.andThen(pure))

  def ap[A, B](fa: F[A])(f: F[A => B]): F[B] =
    flatMap(fa)(a => map(f)(fab => fab(a)))

}


sealed trait MathiasOption[+A]

case class MathiasSome[+A](value: A) extends MathiasOption[A]

case object MathiasNone extends MathiasOption[Nothing]

object MathiasOption {

  implicit val mathiasOptionMonad: Monad[MathiasOption] = new Monad[MathiasOption] {

    override def flatMap[A, B](fa: MathiasOption[A])(f: A => MathiasOption[B]): MathiasOption[B] = {
      fa match {
        case MathiasSome(value) => f(value)
        case MathiasNone => MathiasNone
      }
    }

    override def pure[A](x: A): MathiasOption[A] = MathiasSome(x)

  }

}


