// http://aperiodic.net/phil/scala/s-99/
// https://gist.github.com/kevincairo/e91ef4e289a1892065d7eb1aef805f0a

// #01 Find the last element of a list.
@tailrec def last[A](list: List[A]): Option[A] = list match {
  case x :: Nil => Some(x)
  case _ :: xs  => last(xs)
  case      Nil => None
}









// #02 Find the last but one element of a list.
@tailrec def penultimate[A](list: List[A]): Option[A] = list match {
  case x :: _ :: Nil => Some(x)
  case      _ :: xs  => penultimate(xs)
  case           Nil => None
}









// #03 Find the Kth element of a list.
@tailrec def nth[A](n: Int, list: List[A]): Option[A] = list match {
  case x :: _ if n <= 0 => Some(x)
  case _ :: xs          => nth(n - 1, xs)
  case      Nil         => None
}









// #04 Find the number of elements of a list.
def length(list: List[_]): Int = {
  @tailrec def count(acc: Int, list: List[_]): Int = list match {
    case      Nil => acc
    case _ :: xs  => count(acc + 1, xs)
  }

  count(0, list)
}









// #05 Reverse a list.
def reverse[A](list: List[A]): List[A] = {
  @tailrec def go(acc: List[A], list: List[A]): List[A] = list match {
    case Nil     => acc
    case x :: xs => go (x :: acc, xs)
  }

  go(Nil, list)
}









// #06 Find out whether a list is a palindrome.
def isPalindrome[A](list: List[A]): Boolean = list == reverse(list)









// #07 Flatten a nested list structure.
// List(1), 2, List(3, 4, List(5)), 6) ->
def flatten(list: List[_]) = {
  def go(acc: List[_], list: List[_]): List[_] = list match {
    case                 Nil => reverse(acc)
    case (x: List[_]) :: xs  => go(go(acc, x), xs)
    case  x           :: xs  => go(x :: acc, xs)
  }

  go(Nil, list)
}









// #08 Eliminate consecutive duplicates of list elements.
def dedupeAdjacents[A](list: List[A]) = {
  @tailrec def go(list: List[A], acc: List[A]): List[A] = list match {
    case      Nil => reverse(acc)
    case x :: xs  => go(xs, if (acc.headOption contains x) acc else x :: acc)
  }

  go(list, Nil)
}









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

// #10 Run-length encoding of a list.
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




// #46 Truth tables for logical expressions.
// #47 Truth tables for logical expressions (2).
// #48 Truth tables for logical expressions (3).
// #49 Gray code.
// #50 Huffman code.



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
