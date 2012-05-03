/**
 *
 */
package ca.thurn.prelude;

import static ca.thurn.prelude.P.*;
import static org.junit.Assert.*;

import org.junit.*;

import com.google.common.base.Function;

import java.util.*;

/**
 * @author dthurn
 *
 */
public class PreludeTests {

  private Iterable<String> empty;
  private Iterable<String> one;
  private Iterable<String> two;
  private Iterable<String> three;

  /**
   * @throws java.lang.Exception
   */
  @Before public void setUp() throws Exception {
    empty = $();
    one = $("1");
    two = $("1", "2");
    three = $("1", "2", "3");
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private <A,B> void assertElementsEqual(Iterable<A> xs, Iterable<B> ys) {
    Iterator<A> xsIterator = xs.iterator();
    Iterator<B> ysIterator = ys.iterator();
    while (xsIterator.hasNext() && ysIterator.hasNext()) {
      A nextA = xsIterator.next();
      B nextB = ysIterator.next();
      if (nextA instanceof Iterable && nextB instanceof Iterable) {
        assertElementsEqual((Iterable)nextA, (Iterable)nextB);
      } else {
        assertEquals("Comparing " + xs + " to " + ys, nextA, nextB);
      }
    }
    if (xsIterator.hasNext()) fail("Iterable " + xs + " had more elements than " + ys);
    if (ysIterator.hasNext()) fail("Iterable " + ys + " had more elements than " + xs);
   }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private <A,B> void assertPairEquals(Pair<A,B> pair1, Pair<A,B> pair2) {
    if (pair1.first instanceof Iterable && pair2.first instanceof Iterable) {
      assertElementsEqual((Iterable)pair1.first, (Iterable)pair2.first);
    } else {
      assertEquals(pair1.first, pair2.first);
    }
    if (pair1.second instanceof Iterable && pair2.second instanceof Iterable) {
      assertElementsEqual((Iterable)pair1.second, (Iterable)pair2.second);
    } else {
      assertEquals(pair1.second, pair2.second);
    }
  }

  @Test public final void testFilter() {
    assertElementsEqual($(2, 4), filter(even(), $(1,2,3,4)));
    assertElementsEqual($(1, 2), filter(_(lt(), 3), $(1,2,3,4)));
    assertElementsEqual($(), filter(even(), $(1)));
    assertElementsEqual($(), filter(even(), P.<Integer>$()));
  }

  @Test public final void testIsEmpty() {
    assertEquals(true, isEmpty(empty));
    assertEquals(false, isEmpty(one));
  }

  @Test public final void testHead() {
    assertEquals("1", head(one));
    assertEquals("1", head(two));
    assertEquals("1", head(three));
  }

  @Test(expected = NoSuchElementException.class)
  public final void testHeadFail() {
    head(empty);
  }

  @Test public final void testLast() {
    assertEquals("1", last(one));
    assertEquals("2", last(two));
    assertEquals("3", last(three));
  }

  @Test(expected = NoSuchElementException.class)
  public final void testLastFail() {
    last(empty);
  }

  @Test public final void testTail() {
    assertElementsEqual($(), tail(one));
    assertElementsEqual($("2"), tail(two));
    assertElementsEqual($("2", "3"), tail(three));
  }

  @Test(expected = NoSuchElementException.class)
  public final void testTailFail() {
    tail(empty);
  }

  @Test public final void testInit() {
    assertElementsEqual($(), init(one));
    assertElementsEqual($("1"), init(two));
    assertElementsEqual($("1", "2"), init(three));
  }

  @Test(expected = NoSuchElementException.class)
  public final void testInitFail() {
    init(empty);
  }

  @Test public final void testLength() {
    assertEquals(new Integer(0), length(empty));
    assertEquals(new Integer(1), length(one));
    assertEquals(new Integer(2), length(two));
    assertEquals(new Integer(3), length(three));
  }

  @Test public final void testAt() {
    assertEquals("1", at(one, 0));
    assertEquals("1", at(two, 0));
    assertEquals("3", at(three, 2));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAtFail() {
    at(empty, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAtFail2() {
    at(one, 1);
  }

  @Test public final void testReverse() {
    assertElementsEqual($(), reverse(empty));
    assertElementsEqual($("1"), reverse(one));
    assertElementsEqual($("2", "1"), reverse(two));
    assertElementsEqual($("3", "2", "1"), reverse(three));
  }

  @Test public final void testFoldl() {
    assertEquals(new Integer(6), foldl(plus(), 0, $(1, 2, 3)));
    assertElementsEqual($(0, 1, 2, 3), foldl(P.<Integer>append(), $(0), $($(1), $(2), $(3))));
    assertEquals(new Integer(0), foldl(P.<Integer,Integer>constant(), 0, $(1, 2, 3)));
    assertElementsEqual(s("123"), foldl(P.<Character>append(), s(""), $(s("1"), s("2"), s("3"))));
    assertEquals(new Integer(0), foldl(minus(), 10, $(5, 3, 2)));
  }

  @Test public final void testFoldl1() {
    assertEquals(new Integer(6), foldl1(plus(), $(1, 2, 3)));
    assertElementsEqual($(1, 2, 3), foldl1(P.<Integer>append(), $($(1), $(2), $(3))));
    assertEquals(new Integer(1), foldl1(P.<Integer,Integer>constant(), $(1, 2, 3)));
    assertElementsEqual(s("123"), foldl1(P.<Character>append(), $(s("1"), s("2"), s("3"))));
  }

  @Test(expected = NoSuchElementException.class)
  public final void testFoldl1Fail() {
    foldl1(plus(),P.<Integer>$());
  }

  @Test public final void testFoldr() {
    assertEquals(new Integer(-6), foldr(minus(), 10, $(5, 3, 2)));
    assertElementsEqual($(1, 2, 3, 0), foldr(P.<Integer>append(), $(0), $($(1), $(2), $(3))));
    assertEquals(true, foldr(and(), true, $(true, true, true)));
    assertEquals(true, foldr(or(), false, $(false, false, true)));
  }

  @Test public final void testFoldr1() {
    assertEquals(new Integer(-6), foldr1(minus(), $(5, 3, 2, 10)));
    assertElementsEqual($(1, 2, 3, 0), foldr1(P.<Integer>append(), $($(1), $(2), $(3), $(0))));
  }


  @Test(expected = NoSuchElementException.class)
  public final void testFoldr1Fail() {
    foldr1(plus(),P.<Integer>$());
  }

  @Test public final void testAndList() {
    assertEquals(true, andList($(true, true, true)));
    assertEquals(false, andList($(true, false, true)));
    assertEquals(true, andList(P.<Boolean>$()));
  }

  @Test public final void testOrList() {
    assertEquals(true, orList($(true, true, true)));
    assertEquals(true, orList($(true, false, true)));
    assertEquals(false, orList($(false, false, false)));
    assertEquals(false, orList(P.<Boolean>$()));
  }

  @Test public final void testAny() {
    assertEquals(true, any(even(), $(1, 2, 3)));
    assertEquals(false, any(even(), $(1, 3, 5)));
    assertEquals(false, any(even(), P.<Integer>$()));
  }

  @Test public final void testAll() {
    assertEquals(true, all(even(), $(2, 4, 6)));
    assertEquals(false, all(even(), $(1, 2, 3)));
    assertEquals(true, all(even(), P.<Integer>$()));
  }

  @Test public final void testSum() {
    assertEquals(new Integer(6), sum($(1, 2, 3)));
    assertEquals(new Integer(-6), sum($(-1, -2, -3)));
    assertEquals(new Integer(0), sum(P.<Integer>$()));
  }

  @Test public final void testProduct() {
    assertEquals(new Integer(24), product($(1, 2, 3, 4)));
    assertEquals(new Integer(24), product($(-1, 2, -3, 4)));
    assertEquals(new Integer(1), product(P.<Integer>$()));
  }

  @Test public final void testConcat() {
    assertElementsEqual(s("123"), concat($(s("1"), s("2"), s("3"))));
    assertElementsEqual($(1, 2, 3), concat($($(1), $(2), $(3))));
    assertElementsEqual($(), concat($($())));
  }

  @Test public final void testConcatMap() {
    Function<String,Iterable<Character>> foldFn = new Function<String,Iterable<Character>>() {
      @Override public Iterable<Character> apply(String str) {
        return s(str + ",");
      }
    };
    assertElementsEqual(s("foo,bar,baz,"), concatMap(foldFn, $("foo", "bar", "baz")));
    assertElementsEqual(s(), concatMap(foldFn, P.<String>$()));
  }

  @Test public final void testMaximum() {
    assertEquals(new Integer(10), maximum($(1, 3, 10, 2, 8)));
    assertEquals(new Integer(1), maximum($(1)));
    assertEquals(new Integer(1), maximum($(1, -3, -10, -2, -8)));
  }

  @Test(expected = NoSuchElementException.class)
  public final void testMaximumFail() {
    maximum(P.<Integer>$());
  }

  @Test public final void testMinimum() {
    assertEquals(new Integer(1), minimum($(1, 3, 10, 2, 8)));
    assertEquals(new Integer(1), minimum($(1)));
    assertEquals(new Integer(-10), minimum($(1, -3, -10, -2, -8)));
  }

  @Test(expected = NoSuchElementException.class)
  public final void testMinimumFail() {
    minimum(P.<Integer>$());
  }

  @Test public final void testScanl() {
    assertElementsEqual($(10, 5, 2, 0), scanl(minus(), 10, $(5, 3, 2)));
    assertElementsEqual($($(10), $(10, 5), $(10, 5, 3), $(10, 5, 3, 2)),
        scanl(P.<Integer>append(), $(10), $($(5), $(3), $(2))));
    assertElementsEqual($(0), scanl(minus(), 0, P.<Integer>$()));
  }

  @Test public final void testScanl1() {
    assertElementsEqual($(5, 2, 0), scanl1(minus(), $(5, 3, 2)));
    assertElementsEqual($($(5), $(5, 3), $(5, 3, 2)),
        scanl1(P.<Integer>append(), $($(5), $(3), $(2))));
  }

  @Test(expected = NoSuchElementException.class)
  public final void testScanl1Fail() {
    scanl1(minus(), P.<Integer>$());
  }

  @Test public final void testScanr() {
    assertElementsEqual($(-6, 11, -8, 10), scanr(minus(), 10, $(5, 3, 2)));
    assertElementsEqual($($(5, 3, 2, 10), $(3, 2, 10), $(2, 10), $(10)),
        scanr(P.<Integer>append(), $(10), $($(5), $(3), $(2))));
    assertElementsEqual($(0), scanr(minus(), 0, P.<Integer>$()));
  }

  @Test public final void testScanr1() {
    assertElementsEqual($(4, 1, 2), scanr1(minus(), $(5, 3, 2)));
    assertElementsEqual($($(5, 3, 2), $(3, 2), $(2)),
        scanr1(P.<Integer>append(), $($(5), $(3), $(2))));
  }

  @Test(expected = NoSuchElementException.class)
  public final void testScanr1Fail() {
    scanr1(minus(), P.<Integer>$());
  }

  @Test public final void testIterate() {
    Function<Integer, Integer> triple = new Function<Integer, Integer>() {
      @Override public Integer apply(Integer value) {
        return value * 3;
      }
    };
    assertElementsEqual($(1, 3, 9), take(3, iterate(triple, 1)));
    assertEquals(new Integer(10), head(iterate(triple, 10)));
    assertEquals(new Integer(30), head(tail(iterate(triple, 10))));
  }

  @Test public final void testRepeat() {
    assertElementsEqual($(1, 1, 1, 1, 1), take(5, repeat(1)));
    assertEquals(new Integer(1), head(repeat(1)));
    assertEquals(new Integer(1), head(tail(repeat(1))));
  }

  @Test public final void testReplicate() {
    assertElementsEqual(s("aaaaa"), replicate(5, 'a'));
    assertElementsEqual($(3, 3, 3), replicate(3, 3));
    assertElementsEqual($(1), replicate(1, 1));
    assertElementsEqual($(), replicate(-1, 12));
    assertElementsEqual($(), replicate(0, 12));
  }

  @Test public final void testCycle() {
    assertElementsEqual(s("ababab"), take(6, cycle(s("ab"))));
    assertElementsEqual($(3, 3, 3), take(3, cycle($(3))));
    assertEquals(new Integer(1), head(cycle($(1, 2, 3))));
  }

  @Test public final void testTake() {
    assertElementsEqual(s("Hello"), take(5, s("Hello World!")));
    assertElementsEqual($(1, 2, 3), take(3, $(1, 2, 3, 4, 5)));
    assertElementsEqual($(1, 2), take(3, $(1, 2)));
    assertElementsEqual($(), take(-1, $(1, 2)));
    assertElementsEqual($(), take(0, $(1, 2)));
  }

  @Test public final void testDrop() {
    assertElementsEqual(s("World!"), drop(6, s("Hello World!")));
    assertElementsEqual($(4,5), drop(3, $(1,2,3,4,5)));
    assertElementsEqual($(), drop(3, $(1,2)));
    assertElementsEqual($(), drop(3, $()));
    assertElementsEqual($(1,2), drop(-1, $(1,2)));
    assertElementsEqual($(1,2), drop(0, $(1,2)));
  }

  @Test public final void testSplitAt() {
   assertPairEquals(splitAt(6, s("Hello World!")), t(s("Hello "), s("World!")));
   assertPairEquals(splitAt(3, $(1,2,3,4,5)), t($(1,2,3),$(4,5)));
   assertPairEquals(splitAt(1, $(1,2,3)), t($(1),$(2,3)));
   assertPairEquals(splitAt(3, $(1,2,3)), t($(1,2,3),P.<Integer>$()));
   assertPairEquals(splitAt(4, $(1,2,3)), t($(1,2,3),P.<Integer>$()));
   assertPairEquals(splitAt(0, $(1,2,3)), t(P.<Integer>$(),$(1,2,3)));
   assertPairEquals(splitAt(-1, $(1,2,3)), t(P.<Integer>$(),$(1,2,3)));
  }

  @Test public final void testTakeWhile() {
    assertElementsEqual($(1,2), takeWhile(_(lt(), 3), $(1,2,3,4,1,2,3,4)));
    assertElementsEqual($(1,2,3), takeWhile(_(lt(), 9), $(1,2,3)));
    assertElementsEqual($(), takeWhile(_(lt(), 0), $(1,2,3)));
  }

  @Test public final void testDropWhile() {
    assertElementsEqual($(3,4,5,1,2,3), dropWhile(_(lt(), 3), $(1,2,3,4,5,1,2,3)));
    assertElementsEqual($(), dropWhile(_(lt(), 9), $(1,2,3)));
    assertElementsEqual($(1,2,3), dropWhile(_(lt(), 0), $(1,2,3)));
  }










































}
