package problems.lists

import scala.annotation.tailrec

/**
  * [[http://aperiodic.net/phil/scala/s-99/#p01]] to [[http://aperiodic.net/phil/scala/s-99/#p28]]
  */
trait Lists {
  /**
    * #01 Find the last element of a list.
    */
  def last[A](list: Seq[A]): Option[A]

  /**
    * #02 Find the last but one element of a list.
    */
  def penultimate[A](list: Seq[A]): Option[A]

  /**
    * #03 Find the Kth element of a list.
    */
  def nth[A](n: Int, list: Seq[A]): Option[A]

  /**
    * #04 Find the number of elements of a list.
    */
  def length(list: Seq[_]): Int

  /**
    * #05 Reverse a list.
    */
  def reverse[A](list: Seq[A]): Seq[A]

  /**
    * #06 Find out whether a list is a palindrome.
    */
  def isPalindrome[A](list: Seq[A]): Boolean

  /**
    * #07 Flatten a nested list structure.
    */
  def flatten(list: Seq[_])

  /**
    * #08 Eliminate consecutive duplicates of list elements.
    */
  def dedupeAdjacents[A](list: Seq[A])

  /**
    * #09 Pack consecutive duplicates of list elements into sublists.
    */
  def packAdjacentDupes[A](list: Seq[A]): Seq[Either[A, Seq[A]]]

  /**
    * #10 Run-length encoding of a list.
    */
  def runLengthEncoded[A](list: Seq[A]): Seq[(A, Int)]

  /**
    * #11 Modified run-length encoding.
    *
    * scala> encodeModified(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e))
    * res0: Seq[Any] = List((4,'a), 'b, (2,'c), (2,'a), 'd, (4,'e))
    */
  def oneOrRunLengthEncoded[A](list: Seq[A]): Seq[Either[A, (A, Int)]]

  /**
    * #12 Decode a run-length encoded list.
    *
    * Given a run-length code list generated as specified in problem P10, construct its uncompressed version.
    *
    * Example:
    * scala> decode(List((4, 'a), (1, 'b), (2, 'c), (2, 'a), (1, 'd), (4, 'e)))
    * res0: Seq[Symbol] = List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)
    */
  def decodeRunLengthEncoded[A](list: Seq[(A, Int)]): Seq[A]

  /**
    * #13 Run-length encoding of a list (direct solution). -- my #10 basically does this already */

  /**
    * #14 Duplicate the elements of a list.
    *
    * scala> duplicate(List('a, 'b, 'c, 'c, 'd))
    * res0: Seq[Symbol] = List('a, 'a, 'b, 'b, 'c, 'c, 'c, 'c, 'd, 'd)
    */
  def duplicateEach[A](list: Seq[A]): Seq[A]

  /**
    * #15 Duplicate the elements of a list a given number of times.
    */
  def duplicateEachN[A](i: Int, list: Seq[A]): Seq[A]

  /**
    * #16 Drop every Nth element from a list.
    */
  def dropEveryN[A](n: Int, list: Seq[A]): Seq[A]

  /**
    * #17 Split a list into two parts.
    * The length of the first part is given. Use a Tuple for your result.
    */
  def splitAt[A](n: Int, list: Seq[A]): (Seq[A], Seq[A])

  /**
    * #18 Extract a slice from a list.
    */
  def slice[A](from: Int, to: Int, list: Seq[A]): Seq[A]

  /**
    * #19 Rotate a list N places to the left.
    */
  def rotate[A](pivot: Int, list: Seq[A]): Seq[A]

  /**
    * #20 Remove the Kth element from a list.
    */
  def remove[A](pos: Int, list: Seq[A]): Seq[A]

  /**
    * #21 Insert an element at a given position into a list.
    */
  def insert[A](post: Int, elem: A, list: Seq[A]): Seq[A]

  /**
    * #22 Create a list containing all integers within a given range.
    */
  def range(from: Int, to: Int): Seq[Int]

  /**
    * #23 Extract a given number of randomly selected elements from a list.
    */
  def randomSelect[A](n: Int, list: Seq[A]): List[A]

  /**
    * #24 Lotto: Draw N different random numbers from the set 1..M.
    */
  def lotto(n: Int, size: Int): Seq[Int]

  /**
    * #25 Generate a random permutation of the elements of a list.
    */
  def randomPermute[A](list: Seq[A]): Seq[A]

  /**
    * #26 Generate the combinations of K distinct objects chosen from the N elements of a list.
    */
  def combinations[A](n: Int, list: Seq[A]): List[List[A]]

  /**
    * #27a Group the elements of a set into disjoint subsets.
    * In how many ways can a group of 9 people work in 3 disjoint subgroups of 2, 3 and 4 persons?
    */
  def group3[A](list: Seq[A]): List[List[List[A]]]

  /**
    * #27b Group the elements of a set into disjoint subsets.
    * Generalize #27a in a way that we can specify a list of group sizes and the predicate will return a list of groups.
    *
    * NOTE: we do not want permutations of the group members; i.e. ((Aldo, Beat), ...) is the same solution as ((Beat, Aldo), ...).
    * However, we make a difference between ((Aldo, Beat), (Carla, David), ...) and ((Carla, David), (Aldo, Beat), ...).
    */
  def group[A](sizes: List[Int], list: Seq[A]): List[List[List[A]]]

  /**
    * #28a Sorting a list of lists according to length of sublists.
    * We suppose that a list contains elements that are lists themselves. The objective is to sort the elements of the list according to their length.
    * E.g. short lists first, longer lists later, or vice versa
    */
  def lsort[A](lists: List[List[A]]): List[List[A]]

  /**
    * #28b Sorting a list of lists according to length of sublists.
    * We suppose that a list contains elements that are lists themselves. But this time the objective is to sort the elements according to their length frequency;
    * i.e. in the default, sorting is done ascendingly, lists with rare lengths are placed, others with a more frequent length come later.
    */
  def lsortFreq[A](lists: List[List[A]]): List[List[A]]
}

object Lists extends Lists {

  /**
    * #01 Find the last element of a list.
    */
  @tailrec
  final def last[A](list: Seq[A]): Option[A] = list match {
    case x +: Nil => Some(x)
    case _ +: xs  => last(xs)
    case      Nil => None
  }

  /**
    * #02 Find the last but one element of a list.
    */
  @tailrec
  final def penultimate[A](list: Seq[A]): Option[A] = list match {
    case x +: _ +: Nil => Some(x)
    case      _ +: xs  => penultimate(xs)
    case           Nil => None
  }

  /**
    * #03 Find the Kth element of a list.
    */
  @tailrec
  final def nth[A](n: Int, list: Seq[A]): Option[A] = list match {
    case x +: _ if n == 0 => Some(x)
    case _ +: xs          => nth(n - 1, xs)
    case      Nil         => None
  }

  /**
    * #04 Find the number of elements of a list.
    */
  def length(list: Seq[_]): Int = {
    @tailrec def count(acc: Int, list: Seq[_]): Int = if (list.isEmpty) acc else count(acc + 1, list.tail)

    count(0, list)
  }

  /**
    * #05 Reverse a list.
    */
  def reverse[A](list: Seq[A]): Seq[A] = {
    @tailrec def go(acc: Seq[A], list: Seq[A]): Seq[A] = list match {
      case Nil     => acc
      case x +: xs => go (x +: acc, xs)
    }

    go(Nil, list)
  }

  /**
    * #06 Find out whether a list is a palindrome.
    */
  def isPalindrome[A](list: Seq[A]): Boolean = list == reverse(list)

  /**
    * #07 Flatten a nested list structure.
    */
  def flatten(list: Seq[_]) = {
    def go(acc: Seq[_], list: Seq[_]): Seq[_] = list match {
      case                 Nil => reverse(acc)
      case (x: Seq[_]) +: xs  => go(go(acc, x), xs)
      case  x           +: xs  => go(x +: acc, xs)
    }

    go(Nil, list)
  }

  /**
    * #08 Eliminate consecutive duplicates of list elements.
    */
  def dedupeAdjacents[A](list: Seq[A]) = {
    @tailrec def go(list: Seq[A], acc: Seq[A]): Seq[A] = list match {
      case      Nil => reverse(acc)
      case x +: xs  => go(xs, if (acc.headOption contains x) acc else x +: acc)
    }

    go(list, Nil)
  }

  /**
    * #09 Pack consecutive duplicates of list elements into sublists.
    */
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

  /**
    * #10 Run-length encoding of a list.
    */
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

  /**
    * #13 Run-length encoding of a list (direct solution). -- my #10 basically does this already */

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

  /**
    * #15 Duplicate the elements of a list a given number of times.
    */
  def duplicateEachN[A](i: Int, list: Seq[A]): Seq[A] = {
    @tailrec def dup(n: Int, a: A, onto: Seq[A]): Seq[A] = if (n > 0) dup(n - 1, a, a +: onto) else onto

    @tailrec def go(list: Seq[A], acc: Seq[A]): Seq[A] = list match {
      case Nil => reverse(acc)
      case x +: xs => go(xs, acc = dup(n = i, x, acc))
    }

    go(list, Nil)
  }

  /**
    * #16 Drop every Nth element from a list.
    */
  def dropEveryN[A](n: Int, list: Seq[A]): Seq[A] = {
    @tailrec def run(i: Int, curr: Seq[A], acc: Seq[A]): Seq[A] = acc match {
      case head +: tail if i == n => run(1, tail, acc)
      case head +: tail           => run(i + 1, tail, head +: acc)
      case Nil                    => reverse(acc)
    }

    if (n <= 1) Nil
    else run(i = 1, curr = list, acc = Nil)
  }

  /**
    * #17 Split a list into two parts.
    * The length of the first part is given. Use a Tuple for your result.
    */
  def splitAt[A](n: Int, list: Seq[A]): (Seq[A], Seq[A]) = ???

  /**
    * #18 Extract a slice from a list.
    */
  def slice[A](from: Int, to: Int, list: Seq[A]): Seq[A] = ???

  /**
    * #19 Rotate a list N places to the left.
    */
  def rotate[A](pivot: Int, list: Seq[A]): Seq[A] = ???

  /**
    * #20 Remove the Kth element from a list.
    */
  def remove[A](pos: Int, list: Seq[A]): Seq[A] = ???

  /**
    * #21 Insert an element at a given position into a list.
    */
  def insert[A](post: Int, elem: A, list: Seq[A]): Seq[A] = ???

  /**
    * #22 Create a list containing all integers within a given range.
    */
  def range(from: Int, to: Int): Seq[Int] = ???

  /**
    * #23 Extract a given number of randomly selected elements from a list.
    */
  def randomSelect[A](n: Int, list: Seq[A]): List[A] = ???

  /**
    * #24 Lotto: Draw N different random numbers from the set 1..M.
    */
  def lotto(n: Int, size: Int): Seq[Int] = ???

  /**
    * #25 Generate a random permutation of the elements of a list.
    */
  def randomPermute[A](list: Seq[A]): Seq[A] = ???

  /**
    * #26 Generate the combinations of K distinct objects chosen from the N elements of a list.
    */
  def combinations[A](n: Int, list: Seq[A]): List[List[A]] = ???

  /**
    * #27a Group the elements of a set into disjoint subsets.
    * In how many ways can a group of 9 people work in 3 disjoint subgroups of 2, 3 and 4 persons?
    */
  def group3[A](list: Seq[A]): List[List[List[A]]] = ???

  /**
    * #27b Group the elements of a set into disjoint subsets.
    * Generalize #27a in a way that we can specify a list of group sizes and the predicate will return a list of groups.
    *
    * NOTE: we do not want permutations of the group members; i.e. ((Aldo, Beat), ...) is the same solution as ((Beat, Aldo), ...).
    * However, we make a difference between ((Aldo, Beat), (Carla, David), ...) and ((Carla, David), (Aldo, Beat), ...).
    */
  def group[A](sizes: List[Int], list: Seq[A]): List[List[List[A]]] = ???

  /**
    * #28a Sorting a list of lists according to length of sublists.
    * We suppose that a list contains elements that are lists themselves. The objective is to sort the elements of the list according to their length.
    * E.g. short lists first, longer lists later, or vice versa
    */
  def lsort[A](lists: List[List[A]]): List[List[A]] = ???

  /**
    * #28b Sorting a list of lists according to length of sublists.
    * We suppose that a list contains elements that are lists themselves. But this time the objective is to sort the elements according to their length frequency;
    * i.e. in the default, sorting is done ascendingly, lists with rare lengths are placed, others with a more frequent length come later.
    */
  def lsortFreq[A](lists: List[List[A]]): List[List[A]] = ???
}