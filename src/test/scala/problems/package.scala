import org.scalacheck.{Arbitrary, Gen}
import org.scalactic.anyvals.{PosInt, PosZInt}

import Numeric.Implicits._

package object problems {
  def zero[N](implicit n: Numeric[N]) = n.zero
  def one[N](implicit n: Numeric[N]) = n.one

  implicit def arbPosInt  = Arbitrary(Gen.posNum[Int] map (n => PosInt.from(n).get))
  implicit def arbPosZInt = Arbitrary(Gen.posNum[Int] map (n => PosZInt.from(n - 1).get))
}
