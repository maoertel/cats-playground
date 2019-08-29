trait Monad[A, F[_]] {

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  def pure[A](x: A): F[A]

  def map[A, B](fa: F[A])(f: A => B): F[B] =
    flatMap(fa)(f.andThen(pure))

  def ap[A, B](fa: F[A])(f: F[A => B]): F[B] =
    flatMap(fa)(a => map(f)(fab => fab(a)))

}



object X {
  sealed trait MathiasOption[+A]
  object MathiasOption {

    implicit val mathiasOptionMonad = new Monad[MathiasOption] {

      override def flatMap[A, B](fa: MathiasOption[A])(f: A => MathiasOption[B]): MathiasOption[B] = {
        fa match {
          case MathiasSome(value) => f(value)
          case MathiasNone => MathiasNone
        }
      }

      override def pure[A](x: A): MathiasOption[A] = MathiasSome(x)

    }

  }

  case class MathiasSome[+A](value: A) extends MathiasOption[A]

  case object MathiasNone extends MathiasOption[Nothing]
}

import X._


val aaa = implicitly[Monad[Int, MathiasOption[Int]]].map(MathiasSome(2))(_*5)

aaa