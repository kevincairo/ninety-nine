package problems.lists

import problems._
import org.scalactic.TypeCheckedTripleEquals
import org.scalactic.anyvals.{PosInt, PosZInt}
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.prop.GeneratorDrivenPropertyChecks

case class Negative[I](value: I) extends AnyVal

abstract class GenericListsSpec(lists: Lists) extends WordSpec {
  import Matchers._, GeneratorDrivenPropertyChecks._, TypeCheckedTripleEquals._
  import List.concat

  "#01: last" when {
    "empty" should { "be None" in {
      lists.last(Nil) should === (None)
    }}

    "non-empty" should { "return the last element" in forAll { (xs: List[Int], x: Int) =>
      lists.last(xs :+ x) should === (Some(x))
    }}
  }

  "#02: penultimate" when {
    "empty" should { "be None" in {
      lists.penultimate(Nil) should === (None)
    }}

    "length 1" should { "be None" in forAll { x: PosInt =>
      lists.penultimate(List(x)) should === (None)
    }}

    "length >1" should { "be the second-to-last element" in forAll { (xs: List[Int], x: Int, y: Int) =>
      lists.penultimate(xs ++ List(x, y)) should === (Some(x))
    }}
  }

  "#03: nth" when {
    "index is negative" should { "be None" in forAll { (ind: PosInt, xs: List[Int]) =>
      lists.nth(-ind, xs) should === (None)
    }}

    "index is >= list length" should { "be None" in forAll { (ind: PosZInt, xs: List[Int]) =>
      lists.nth(xs.length + ind, xs) should === (None)
    }}

    "index is < list length" should { "be Some" in forAll { (ind: PosZInt, fill: Int, hit: Int, post: List[Int]) =>
      lists.nth(ind, List.fill(ind)(fill) ++ (hit +: post)) should === (Some(hit))
    }}
  }

  "#04: length" when {
    "empty" should { "be zero" in {
      lists.length(Nil) should === (0)
    }}

    "non-empty" should { "be number of elements in the list" in forAll { (len: PosInt) =>
      lists.length(List.fill(len)(len)) should === (len.value)
    }}
  }

  "#05: reverse" when {
    "empty" should { "be Nil" in {
      lists.reverse(Nil) should === (Nil)
    }}

    "non-empty" should { "invert the order of elements" in forAll { (start: Int, len: PosInt) =>
      lists.reverse(start to (start + len)) should === ((start + len.value) to start by -1)
    }}
  }

  "#06: isPalindrome" when {
    "empty" should { "be true for empty list" in {
      lists.isPalindrome(Nil) should === (true)
    }}

    "palindrome" should { "be true for symmetric list" in forAll { (xs: List[Int], mid: Option[Int]) =>
      val symmetric = concat(xs, mid, xs.reverse)

      lists.isPalindrome(symmetric) should === (true)
    }}

    "non-palindrome" should { "be false for asymmetric list" in forAll { (x: Int, pre: List[Int], mid: Option[Int], post: List[Int]) =>
      val asymmetric = concat(pre, List(x), post, mid, post.reverse, List(x + 1), pre.reverse)

      lists.isPalindrome(asymmetric) should === (false)
    }}
  }

  "#07: flatten" when {
    def nest(level: Int, list: Seq[_]): Seq[_] = if (level < 1) list else nest(level - 1, Seq(list))
    def fill(i: Int): Seq[Int] = 0 to i

    "empty lists" should { "be empty list" in { lists.flatten(Nil) should === (Nil) }}

    "nested" should { "bring all elements to one level deep" in forAll { (levels: List[PosZInt]) =>
      val nested = levels.zipWithIndex map { case (level, pos) => nest(level, fill(pos)) }

      lists.flatten(nested) should === (0 until levels.length flatMap fill)
    }}
  }

  "#08: compress" when {
    "empty list" should { "be empty list" in { lists.compress(Nil) should === (Nil) }}

    "non-empty" should { "remove adjacent duplicates" in forAll { (lengths: Set[PosInt]) =>
      val orderedLengths = util.Random shuffle lengths.toSeq
      val expanded = orderedLengths flatMap { i => Seq.fill(i)(i) }

      lists.compress(expanded) should === (orderedLengths)
    }}
  }

  "#09: pack" when {
    def fill(i: PosInt) = Seq.fill(i)(i)

    "empty list" should { "be empty list" in { lists.pack(Nil) should === (Nil) } }

    "non-empty" should { "lift adjacents of equal value into nested list" in forAll { (lengths: Set[PosInt]) =>
      val orderedLengths = util.Random shuffle lengths.toSeq
      val expanded = orderedLengths flatMap fill
      val packed = orderedLengths map { i => Either.cond(i > 1, left = i, right = fill(i)) }

      lists.pack(expanded) should === (packed)
    }}
  }

//  "runLengthEncoded" when {
//    "empty list" should { "be empty" in {} }
//    "non-empty" should { "" in {} }
//  }
//
//  "oneOrRunLengthEncoded" when {
//    "empty list" should { "be empty" in {} }
//    "non-empty" should { "" in {} }
//  }
//
//  "runLengthDecoded" when {
//    "empty list" should { "be empty" in {} }
//    "non-empty" should { "" in {} }
//  }
//
//  "duplicateEach" when {
//    "empty list" should { "be empty" in {} }
//    "non-empty" should { "" in {} }
//  }
//
//  "duplicateEachN" when {
//    "empty list" should { "be empty" in {} }
//    "non-empty" should { "" in {} }
//  }
}

class ListsSpec extends GenericListsSpec(Lists)