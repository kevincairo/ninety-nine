// http://aperiodic.net/phil/scala/s-99/
// https://gist.github.com/kevincairo/e91ef4e289a1892065d7eb1aef805f0a
import scala.annotation.tailrec
import scala.util.Random.nextInt

def list(range: Range) = range.toList
def rand(range: Range) = range(nextInt(range.length))
def repeat[A](n: Int):   A => List[A] = List.fill(n)
def repeat[A](r: Range): A => List[A] = repeat(rand(r))

implicit class TypedEquality[A](left: A) {
  def ===(right: A) =
    left == right || { println(s"$left does not equal $right"); false }
}

// #01 Find the last element of a list.
@tailrec def last[A](list: List[A]): Option[A] = list match {
  case x :: Nil => Some(x)
  case _ :: xs  => last(xs)
  case      Nil => None
}


assert(last(list(1 to 10)) contains 10)
assert(last(List(1)) contains 1)
assert(last(Nil) === None)


// #02 Find the last but one element of a list.
@tailrec def penultimate[A](list: List[A]): Option[A] = list match {
  case x :: _ :: Nil => Some(x)
  case      _ :: xs  => penultimate(xs)
  case           Nil => None
}


assert(penultimate(list(1 to 10)) contains 9)
assert(penultimate(List(1, 2, 3)) contains 2)
assert(penultimate(List(1)) === None)
assert(penultimate(Nil) === None)


// #03 Find the Kth element of a list.
@tailrec def nth[A](n: Int, list: List[A]): Option[A] = list match {
  case x :: _ if n == 0 => Some(x)
  case _ :: xs          => nth(n - 1, xs)
  case      Nil         => None
}

for (i <- 0 to 10) assert(nth(i, list(0 to 10)) contains i)
assert(nth( 0, Nil)     === None)
assert(nth( 1, List(1)) === None)
assert(nth(-1, List(1)) === None)


// #04 Find the number of elements of a list.
def length(list: List[_]): Int = {
  @tailrec def count(acc: Int, list: List[_]): Int = list match {
    case      Nil => acc
    case _ :: xs  => count(acc + 1, xs)
  }

  count(0, list)
}


assert(length(Nil) === 0)
for (i <- 1 to 10) assert(length(list(0 until i)) === i)


// #05 Reverse a list.
def reverse[A](list: List[A]): List[A] = {
  @tailrec def go(acc: List[A], list: List[A]): List[A] = list match {
    case Nil     => acc
    case x :: xs => go (x :: acc, xs)
  }

  go(Nil, list)
}


assert(reverse(Nil) === Nil)
for (start <- 0 to 10 by 2; end <- 11 to 15)
  assert(reverse(list(start to end)) === list(end to start by -1))


// #06 Find out whether a list is a palindrome.
def isPalindrome[A](list: List[A]): Boolean = list == reverse(list)
assert(isPalindrome(Nil))
assert(isPalindrome(List(1)))
for (start <- 0 to 10 by 2; step <- 1 to 3; length <- 4 to 11 by 3) {
  val half = list(start until (start + length) by step)
  assert(isPalindrome(half ++ reverse(half)))
}


// #07 Flatten a nested list structure.
def flatten(list: List[_]) = {
  def go(acc: List[_], list: List[_]): List[_] = list match {
    case                 Nil => reverse(acc)
    case (x: List[_]) :: xs  => go(go(acc, x), xs)
    case  x           :: xs  => go(x :: acc, xs)
  }

  go(Nil, list)
}

assert(flatten(Nil) === Nil)
assert(flatten(list(1 to 3)) === list(1 to 3))
assert(flatten(List(List(1), 2, List(3, 4, List(5, 6)), 7)) === list(1 to 7))


// #08 Eliminate consecutive duplicates of list elements.
def dedupeAdjacents[A](list: List[A]) = {
  @tailrec def go(list: List[A], acc: List[A]): List[A] = list match {
    case      Nil => reverse(acc)
    case x :: xs  => go(xs, if (acc.headOption contains x) acc else x :: acc)
  }

  go(list, Nil)
}

assert(dedupeAdjacents(Nil) === Nil)
assert(dedupeAdjacents(list(1 to 5) flatMap repeat(1 to 4)) === list(1 to 5))
for (i <- 1 to 10) assert(dedupeAdjacents(list(0 to i)) === list(0 to i))

// #09 Pack consecutive duplicates of list elements into sublists.
def packAdjacentDupes[A](list: List[A]): List[Either[A, List[A]]] = {
  type Grouped = Either[A, List[A]]
  def group[A](a: A, more: List[A]) = if (more.isEmpty) Left(a) else Right(a :: more)

  @tailrec def grouped(elem: A, rest: List[A], acc: List[A] = Nil): (Grouped, List[A]) =
    if (rest.headOption contains elem) grouped(elem, rest.tail, elem :: acc)
    else group(elem, acc) -> rest

  @tailrec def go(list: List[A], acc: List[Grouped]): List[Grouped] = list match {
    case      Nil => reverse(acc)
    case x :: xs  => grouped(x, xs) match { case (dupes, rest) => go(rest, dupes :: acc) }
  }

  go(list, Nil)
}

assert(packAdjacentDupes(Nil) === Nil)
assert(packAdjacentDupes(List(1)) === List(Left(1)))
assert(packAdjacentDupes(List(1, 1)) === List(Right(List(1, 1))))
assert(packAdjacentDupes(List(1, 2, 2, 3, 2, 1, 1)) ===
  List(Left(1), Right(List(2, 2)), Left(3), Left(2), Right(List(1, 1))))


// #10 Run-length encoding of a list.
def runLengthEncoded[A](list: List[A]): List[(A, Int)] = {
  @tailrec def go(list: List[A], acc: List[(A, Int)]): List[(A, Int)] = list match {
    case Nil =>
      reverse(acc)

    case x :: xs =>
      acc match {
        case ((`x`, n) :: last) => go(xs, (x, n + 1) :: last)
        case _                  => go(xs, (x,     1) :: acc)
      }
  }

  go(list, Nil)
}

assert(runLengthEncoded(Nil) === Nil)
assert(runLengthEncoded(List(1, 2, 2, 3, 2, 1, 1)) === List(1 -> 1, 2 -> 2, 3 -> 1, 2 -> 1, 1 -> 2))
for (i <- 1 to 5) assert(runLengthEncoded(repeat(0 to i)(42)) === List(42 -> i))

// #11 Modified run-length encoding.

// #12 Decode a run-length encoded list.

// #13 Run-length encoding of a list (direct solution).

// #14 Duplicate the elements of a list.

// #15 Duplicate the elements of a list a given number of times.

// #16 Drop every Nth element from a list.

// #17 Split a list into two parts.

// #18 Extract a slice from a list.

// #19 Rotate a list N places to the left.

// #20 Remove the Kth element from a list.

// #21 Insert an element at a given position into a list.

// #22 Create a list containing all integers within a given range.

// #23 Extract a given number of randomly selected elements from a list.

// #24 Lotto: Draw N different random numbers from the set 1..M.

// #25 Generate a random permutation of the elements of a list.

// #26 Generate the combinations of K distinct objects chosen from the N elements of a list.

// #27 Group the elements of a set into disjoint subsets.

// #28 Sorting a list of lists according to length of sublists.
//

// #31 Determine whether a given integer number is prime.

// #32 Determine the greatest common divisor of two positive integer numbers.

// #33 Determine whether two positive integer numbers are coprime.

// #34 Calculate Euler's totient function phi(m).

// #35 Determine the prime factors of a given positive integer.

// #36 Determine the prime factors of a given positive integer (2).

// #37 Calculate Euler's totient function phi(m) (improved).

// #38 Compare the two methods of calculating Euler's totient function.

// #39 A list of prime numbers.

// #40 Goldbach's conjecture.

// #41 A list of Goldbach compositions.
//

// #46 Truth tables for logical expressions.

// #47 Truth tables for logical expressions (2).

// #48 Truth tables for logical expressions (3).

// #49 Gray code.

// #50 Huffman code.
//

// #54 Omitted; our tree representation will only allow well-formed trees.

// #55 Construct completely balanced binary trees.

// #56 Symmetric binary trees.

// #57 Binary search trees (dictionaries).

// #58 Generate-and-test paradigm.

// #59 Construct height-balanced binary trees.

// #60 Construct height-balanced binary trees with a given number of nodes.

// #61 Count the leaves of a binary tree.

// #62 Collect the internal nodes of a binary tree in a list | #62B Collect the nodes at a given level in a list.

// #63 Construct a complete binary tree.

// #64 Layout a binary tree (1).

// #65 Layout a binary tree (2).

// #66 Layout a binary tree (3).

// #67 A string representation of binary trees.

// #68 Preorder and inorder sequences of binary trees.

// #69 Dotstring representation of binary trees.

// #70 Tree construction from a node string. | #70C Count the nodes of a multiway tree.

// #71 Determine the internal path length of a tree.

// #72 Construct the postorder sequence of the tree nodes.

// #73 Lisp-like tree representation.
//

// #80 Conversions.

// #81 Path from one node to another one.

// #82 Cycle from a given node.

// #83 Construct all spanning trees.

// #84 Construct the minimal spanning tree.

// #85 Graph isomorphism.

// #86 Node degree and graph coloration.

// #87 Depth-first order graph traversal.

// #88 Connected components.

// #89 Bipartite graphs.

// #90 Eight queens problem

// #91 Knight's tour.

// #92 Von Koch's conjecture.

// #93 An arithmetic puzzle.

// #94 Generate K-regular simple graphs with N nodes.

// #95 English number words.

// #96 Syntax checker.

// #97 Sudoku. (alternate solution)

// #98 Nonograms.

// #99 Crossword puzzle.
