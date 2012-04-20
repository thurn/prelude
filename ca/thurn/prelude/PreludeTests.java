/**
 *
 */
package ca.thurn.prelude;

import static ca.thurn.prelude.P.*;
import static org.junit.Assert.*;

import org.junit.*;

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

  private <A,B> void assertElementsEqual(Iterable<A> xs, Iterable<B> ys) {
    Iterator<A> xsIterator = xs.iterator();
    Iterator<B> ysIterator = ys.iterator();
    while (xsIterator.hasNext() && ysIterator.hasNext()) {
      assertEquals(xsIterator.next(), ysIterator.next());
    }
    if (xsIterator.hasNext()) fail("Iterable " + xs + " had too many elements");
    if (ysIterator.hasNext()) fail("Iterable " + ys + " had too many elements");
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
    assertEquals(new Integer(6),
        foldl(
            plus(),
            0,
            $(1, 2, 3)));
    assertElementsEqual($(0, 1, 2, 3),
        foldl(
            P.<Integer>concat(),
            $(0),
            $($(1), $(2), $(3))));
    assertEquals(new Integer(0),
        foldl(
            P.<Integer,Integer>constant(),
            0,
            $(1, 2, 3)));
    assertElementsEqual(s("123"),
        foldl(
            P.<Character>concat(),
            s(""),
            $(s("1"), s("2"), s("3"))));
  }

  @Test public final void testFoldl1() {
    assertEquals(new Integer(6),
        foldl1(
            plus(),
            $(1, 2, 3)));
    assertElementsEqual($(1, 2, 3),
        foldl1(
            P.<Integer>concat(),
            $($(1), $(2), $(3))));
    assertEquals(new Integer(1),
        foldl1(
            P.<Integer,Integer>constant(),
            $(1, 2, 3)));
    assertElementsEqual(s("123"),
        foldl1(
            P.<Character>concat(),
            $(s("1"), s("2"), s("3"))));
  }






















}