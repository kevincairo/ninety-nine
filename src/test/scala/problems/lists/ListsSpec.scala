package problems.lists

import problems._

import org.scalactic.TypeCheckedTripleEquals
import org.scalactic.anyvals.{PosInt, PosZInt}
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.prop.GeneratorDrivenPropertyChecks

case class Negative[I](value: I) extends AnyVal

class ListsSpec extends WordSpec {
  import Matchers._, GeneratorDrivenPropertyChecks._, TypeCheckedTripleEquals._
  import List.concat

  "last" when {
    "empty" should { "be None" in {
      lists.last(Nil) should === (None)
    }}

    "non-empty" should { "return the last element" in forAll { (xs: List[Int], x: Int) =>
      lists.last(xs :+ x) should === (Some(x))
    }}
  }

  "penultimate" when {
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

  "nth" when {
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

  "length" when {
    "empty" should { "be zero" in {
      lists.length(Nil) should === (0)
    }}

    "non-empty" should { "be number of elements in the list" in forAll { (len: PosInt) =>
      lists.length(List.fill(len)(len)) should === (len.value)
    }}
  }

  "reverse" when {
    "empty" should { "be Nil" in {
      lists.reverse(Nil) should === (Nil)
    }}

    "non-empty" should { "be invert the order of elements" in forAll { (start: Int, len: PosInt) =>
      lists.reverse(start to (start + len)) should === ((start + len.value) to start by -1)
    }}
  }

  "isPalindrome" when {
    "empty" should { "be true" in {
      lists.isPalindrome(Nil) should === (true)
    }}

    "palindrome" should { "be true" in forAll { (xs: List[Int], mid: Option[Int]) =>
      lists.isPalindrome(concat(xs, mid, xs.reverse)) should === (true)
    }}

    "non-palindrome" should { "be false" in forAll { (x: Int, pre: List[Int], mid: Option[Int], post: List[Int]) =>
      lists.isPalindrome(concat(pre, List(x), post, mid, post.reverse, List(x + 1), pre.reverse)) should === (false)
    }}
  }

//  "flatten" should {
//    "" in {}
//  }
//
//  "dedupeAdjacents" should {
//    "" in {}
//  }
//
//  "packAdjacentDupes" should {
//    "" in {}
//  }
//
//  "runLengthEncoded" should {
//    "" in {}
//  }
//
//  "oneOrRunLengthEncoded" should {
//    "" in {}
//  }
//
//  "decodeRunLengthEncoded" should {
//    "" in {}
//  }
//
//  "duplicateEach" should {
//    "" in {}
//  }
//
//  "duplicateEachN" should {
//    "" in {}
//  }
}
