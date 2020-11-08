package funsets

import org.junit._

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite {

  import FunSets._

  @Test def `contains is implemented`: Unit = {
    println(contains(x => true, 100))
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val s5 = singletonSet(5)
    val s7 = singletonSet(7)
    val s1000 = singletonSet(1000)
  }

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remvoe the
   * @Ignore annotation.
   */
  @Ignore("not ready yet") @Test def `singleton set one contains one`: Unit = {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      println(contains(s1, 1))
      assert(contains(s1, 1), "Singleton")
    }
  }

  @Test def `union contains all elements of each set`: Unit = {
    new TestSets {
      val s = union(s1, s2)
      printSet(s)
      println(contains(s, 1))
      println(contains(s, 2))
      println(contains(s, 3))
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
      val t = union(union(union(union(union(s1, s3), s4), s5), s7), s1000)
      printSet(t)
    }
  }

  @Test def `intersect_test`: Unit = {
    new TestSets {
      val s = intersect(s2, s2)
      printSet(s)
    }
  }

  @Test def `diff_test`: Unit = {
    new TestSets {
      val s = diff(s1, s2)
      printSet(s)
      val t = diff(union(s2, s3), s3)
      printSet(t)
    }
  }

  @Test def `filter_test`: Unit = {
    new TestSets {
      val s = filter(s1, x => x % 2 == 0)
      printSet(s)
      val t = filter(union(s2, s3), x => x % 2 == 0)
      printSet(t)
    }
  }

  @Test def `forall_test`: Unit = {
    new TestSets {
      val s = forall(s1, x => x % 2 == 0)
      println(s)
      val t = forall(s2, x => x % 2 == 0)
      println(t)
      val u = forall(s3, x => x % 2 == 0)
      println(u)
      val v = forall(union(union(s1, s2), s3), x => x % 2 == 0)
      println(v)
    }
  }

  @Test def `exists_test`: Unit = {
    new TestSets {
      val s = exists(s1, x => x % 2 == 0)
      println(s)
      val t = exists(s2, x => x % 2 == 0)
      println(t)
      val u = exists(s3, x => x % 2 == 0)
      println(u)
      val v = exists(union(union(s1, s2), s3), x => x % 2 == 0)
      println(v)
    }
  }

  @Test def `map_test`: Unit = {
    new TestSets {
      val v = map(union(union(s1, s2), s3), x => x * 2)
      printSet(v)
    }
  }

  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}
