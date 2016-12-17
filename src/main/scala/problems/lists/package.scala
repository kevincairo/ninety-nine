package problems

import scala.annotation.tailrec

package object lists {
  /** #01 Find the last element of a list. */
  @tailrec def last[A](list: Seq[A]): Option[A] = list match {
    case x +: Nil => Some(x)
    case _ +: xs  => last(xs)
    case      Nil => None
  }

  /** #02 Find the last but one element of a list. */
  @tailrec def penultimate[A](list: Seq[A]): Option[A] = list match {
    case x +: _ +: Nil => Some(x)
    case      _ +: xs  => penultimate(xs)
    case           Nil => None
  }

  /** #03 Find the Kth element of a list. */
  @tailrec def nth[A](n: Int, list: Seq[A]): Option[A] = list match {
    case x +: _ if n == 0 => Some(x)
    case _ +: xs          => nth(n - 1, xs)
    case      Nil         => None
  }

  /** #04 Find the number of elements of a list. */
  def length(list: Seq[_]): Int = {
    @tailrec def count(acc: Int, list: Seq[_]): Int = if (list.isEmpty) acc else count(acc + 1, list.tail)

    count(0, list)
  }

  /** #05 Reverse a list. */
  def reverse[A](list: Seq[A]): Seq[A] = {
    @tailrec def go(acc: Seq[A], list: Seq[A]): Seq[A] = list match {
      case Nil     => acc
      case x +: xs => go (x +: acc, xs)
    }

    go(Nil, list)
  }

  /** #06 Find out whether a list is a palindrome. */
  def isPalindrome[A](list: Seq[A]): Boolean = list == reverse(list)

  /** #07 Flatten a nested list structure. */
  def flatten(list: Seq[_]) = {
    def go(acc: Seq[_], list: Seq[_]): Seq[_] = list match {
      case                 Nil => reverse(acc)
      case (x: Seq[_]) +: xs  => go(go(acc, x), xs)
      case  x           +: xs  => go(x +: acc, xs)
    }

    go(Nil, list)
  }

  /** #08 Eliminate consecutive duplicates of list elements. */
  def dedupeAdjacents[A](list: Seq[A]) = {
    @tailrec def go(list: Seq[A], acc: Seq[A]): Seq[A] = list match {
      case      Nil => reverse(acc)
      case x +: xs  => go(xs, if (acc.headOption contains x) acc else x +: acc)
    }

    go(list, Nil)
  }

  /** #09 Pack consecutive duplicates of list elements into sublists. */
  def packAdjacentDupes[A](list: Seq[A]): Seq[Either[A, Seq[A]]] = {
    type Grouped = Either[A, Seq[A]]
    def group[A](a: A, more: Seq[A]) = if (more.isEmpty) Left(a) else Right(a +: more)

    @tailrec def grouped(elem: A, rest: Seq[A], acc: Seq[A] = Nil): (Grouped, Seq[A]) =
      if (rest.headOption contains elem) grouped(elem, rest.tail, elem +: acc)
      else group(elem, acc) -> rest

    @tailrec def go(list: Seq[A], acc: Seq[Grouped]): Seq[Grouped] = list match {
      case      Nil => reverse(acc)
      case x +: xs  => grouped(x, xs) match { case (dupes, rest) => go(rest, dupes +: acc) }
    }

    go(list, Nil)
  }

  /** #10 Run-length encoding of a list. */
  def runLengthEncoded[A](list: Seq[A]): Seq[(A, Int)] = {
    // packAdjacentDuplicates(list).map {
    //   case Left(x)            => x -> 1
    //   case Right(xs @ x +: _) => x -> xs.length
    // }

    @tailrec def go(list: Seq[A], acc: Seq[(A, Int)]): Seq[(A, Int)] = list match {
      case Nil =>
        reverse(acc)

      case x +: xs =>
        acc match {
          case (`x`, n) +: _ => go(xs, (x, n + 1) +: acc.tail)
          case _             => go(xs, (x,     1) +: acc)
        }
    }

    go(list, Nil)
  }

  /**
    * #11 Modified run-length encoding.
    *
    * scala> encodeModified(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e))
    * res0: Seq[Any] = List((4,'a), 'b, (2,'c), (2,'a), 'd, (4,'e))
    */
  def oneOrRunLengthEncoded[A](list: Seq[A]): Seq[Either[A, (A, Int)]] = {
    // packAdjacentDuplicates(list).map {
    //   case Left(x) => Left(x)
    //   case Right(xs @ x +: _) => Right(x -> xs.lenght)
    // }

    type OneOrEncoded = Either[A, (A, Int)]

    @tailrec def go(list: Seq[A], acc: Seq[OneOrEncoded]): Seq[OneOrEncoded] = list match {
      case Nil =>
        reverse(acc)

      case x +: xs =>
        acc match {
          case Right((`x`, n)) +: _ => go(xs, Right((x, n + 1)) +: acc.tail)
          case       Left(`x`) +: _ => go(xs, Right((x,     2)) +: acc.tail)
          case _                    => go(xs,           Left(x) +: acc)
        }
    }

    go(list, Nil)
  }

  /**
    * #12 Decode a run-length encoded list.
    *
    * Given a run-length code list generated as specified in problem P10, construct its uncompressed version.
    *
    * Example:
    * scala> decode(List((4, 'a), (1, 'b), (2, 'c), (2, 'a), (1, 'd), (4, 'e)))
    * res0: Seq[Symbol] = List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)
    */
  def decodeRunLengthEncoded[A](list: Seq[(A, Int)]): Seq[A] = {
    @tailrec def fill(a: A, n: Int, acc: Seq[A]): Seq[A] =
      if (n <= 0) acc else fill(a, n - 1, a +: acc)

    @tailrec def go(list: Seq[(A, Int)], acc: Seq[A]): Seq[A] = list match {
      case Nil => reverse(acc)
      case (a, n) +: rest => go(rest, fill(a, n, acc))
    }

    go(list, Nil)
  }

  /** #13 Run-length encoding of a list (direct solution). -- my #10 basically does this already */

  /**
    * #14 Duplicate the elements of a list.
    *
    * scala> duplicate(List('a, 'b, 'c, 'c, 'd))
    * res0: Seq[Symbol] = List('a, 'a, 'b, 'b, 'c, 'c, 'c, 'c, 'd, 'd)
    */
  def duplicateEach[A](list: Seq[A]): Seq[A] = {
    @tailrec def go(list: Seq[A], acc: Seq[A]): Seq[A] = list match {
      case Nil => reverse(acc)
      case x +: xs => go(xs, x +: x +: acc)
    }

    go(list, Nil)
  }

  /** #15 Duplicate the elements of a list a given number of times. */
  def duplicateEachN[A](i: Int, list: Seq[A]): Seq[A] = {
    @tailrec def dup(n: Int, a: A, onto: Seq[A]): Seq[A] = if (n > 0) dup(n - 1, a, a +: onto) else onto

    @tailrec def go(list: Seq[A], acc: Seq[A]): Seq[A] = list match {
      case Nil => reverse(acc)
      case x +: xs => go(xs, acc = dup(n = i, x, acc))
    }

    go(list, Nil)
  }


  /** #16 Drop every Nth element from a list. */

  /** #17 Split a list into two parts. */

  /** #18 Extract a slice from a list. */

  /** #19 Rotate a list N places to the left. */

  /** #20 Remove the Kth element from a list. */

  /** #21 Insert an element at a given position into a list. */

  /** #22 Create a list containing all integers within a given range. */

  /** #23 Extract a given number of randomly selected elements from a list. */

  /** #24 Lotto: Draw N different random numbers from the set 1..M. */

  /** #25 Generate a random permutation of the elements of a list. */

  /** #26 Generate the combinations of K distinct objects chosen from the N elements of a list. */

  /** #27 Group the elements of a set into disjoint subsets. */

  /** #28 Sorting a list of lists according to length of sublists. */
}
