/**
 *
 */
package ca.thurn.prelude;

import com.google.common.base.*;
import com.google.common.collect.*;

import java.util.*;


/**
 * @author dthurn
 *
 */
public class P {
  static class Maybe<A> {
    final A value;
    private Maybe(A value) {
      this.value = value;
    }
  }

  public static <A> Maybe<A> Nothing() {
    return new Maybe<A>(null);
  }

  public static <A> Maybe<A> Just(A value) {
    return new Maybe<A>(value);
  }

  public static <A> Function<A, Maybe<A>> Just() {
    return new Function<A, Maybe<A>>() {
      @Override public Maybe<A> apply(A value) {
        return Just(value);
      }
    };
  }

  public static <A,B> B maybe(B defaultValue, Function<A,B> function, Maybe<A> input) {
    if (input.value == null) return defaultValue;
    else return function.apply(input.value);
  }

  public static <A,B> Function<Maybe<A>,B> maybe(final B defaultValue,
      final Function<A,B> function) {
    return new Function<Maybe<A>,B>() {
      @Override public B apply(Maybe<A> input) {
        return maybe(defaultValue, function, input);
      }
    };
  }

  public static <A,B> Function<Function<A,B>,Function<Maybe<A>,B>> maybe(final B defaultValue) {
    return new Function<Function<A,B>,Function<Maybe<A>,B>>() {
      @Override public Function<Maybe<A>, B> apply(Function<A, B> function) {
        return maybe(defaultValue, function);
      }
    };
  }

  public static <A,B> Function<B,Function<Function<A,B>,Function<Maybe<A>,B>>> maybe() {
    return new Function<B,Function<Function<A,B>,Function<Maybe<A>,B>>>() {
      @Override public Function<Function<A, B>, Function<Maybe<A>, B>> apply(B defaultValue) {
        return maybe(defaultValue);
      }
    };
  }

  static class Either<A,B> {
    final A left;
    final B right;
    private Either(A leftVal, B rightVal) {
      this.left = leftVal;
      this.right = rightVal;
    }
  }

  public static <A,B> Either<A,B> Left(A leftVal) {
    return new Either<A,B>(leftVal, null);
  }

  public static <A,B> Function<A,Either<A,B>> Left() {
    return new Function<A,Either<A,B>>() {
      @Override public Either<A,B> apply(A leftVal) {
        return Left(leftVal);
      }
    };
  }

  public static <A,B> Either<A,B> Right(B rightVal) {
    return new Either<A,B>(null, rightVal);
  }

  public static <A,B> Function<B, Either<A,B>> Right() {
    return new Function<B, Either<A,B>>() {
      @Override public Either<A,B> apply(B value) {
        return Right(value);
      }
    };
  }

  public static <A,B,C> C either(Function<A,C> leftFn, Function<B,C> rightFn, Either<A,B> input) {
    if (input.left != null) return leftFn.apply(input.left);
    else return rightFn.apply(input.right);
  }

  public static <A,B,C> Function<Either<A,B>,C> either(
      final Function<A,C> leftFn,
      final Function<B,C> rightFn) {
    return new Function<Either<A,B>, C>() {
      @Override
      public C apply(Either<A,B> value) {
        return either(leftFn, rightFn, value);
      }
    };
  }

  public static <A,B,C> Function<Function<B,C>,Function<Either<A,B>,C>> either(
      final Function<A,C> leftFn) {
    return new Function<Function<B,C>,Function<Either<A,B>,C>>() {
      @Override public Function<Either<A,B>,C> apply(Function<B,C> value) {
        return either(leftFn, value);
      }
    };
  }

  public static <A,B,C> Function<Function<A,C>,Function<Function<B,C>,Function<Either<A,B>,C>>>
  either() {
    return new Function<Function<A,C>,Function<Function<B,C>,Function<Either<A,B>,C>>>() {
      @Override public Function<Function<B,C>,Function<Either<A,B>,C>> apply(Function<A,C> value) {
        return either(value);
      }
    };
  }

  /**
   * Adds two integers.
   *
   * <p><b>Time Complexity:</b> O(1)</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param x First integer.
   * @param y Second integer.
   * @return Result of addition.
   */
  public static Integer plus(Integer x, Integer y) {
    return x + y;
  }

  /**
   * Partially applied version of {@link P#plus(Integer, Integer)}.
   */
  public static Function<Integer,Integer> plus(final Integer x) {
    return new Function<Integer,Integer>() {
      @Override public Integer apply(Integer value) {
        return plus(x, value);
      }
    };
  }

  /**
   * Partially applied version of {@link P#plus(Integer, Integer)}.
   */
  public static Function<Integer,Function<Integer,Integer>> plus() {
    return new Function<Integer,Function<Integer,Integer>>() {
      @Override public Function<Integer,Integer> apply(Integer value) {
        return plus(value);
      }
    };
  }

  /**
   * Multiplies two integers.
   *
   * <p><b>Time Complexity:</b> O(1)</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param x First integer.
   * @param y Second integer.
   * @return Product of x and y.
   */
  public static Integer times(Integer x, Integer y) {
    return x * y;
  }

  /**
   * Partially applied version of {@link P#times(Integer, Integer)}.
   */
  public static Function<Integer,Integer> times(final Integer x) {
    return new Function<Integer,Integer>() {
      @Override public Integer apply(Integer value) {
        return times(x, value);
      }
    };
  }

  /**
   * Partially applied version of {@link P#times(Integer, Integer)}.
   */
  public static Function<Integer,Function<Integer,Integer>> times() {
    return new Function<Integer,Function<Integer,Integer>>() {
      @Override public Function<Integer,Integer> apply(Integer value) {
        return times(value);
      }
    };
  }

  /**
   * Subtracts two integers.
   *
   * <p><b>Time Complexity:</b> O(1)</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param x First integer
   * @param y Second integer
   * @return x Result of subtraction
   */
  public static Integer minus(Integer x, Integer y) {
    return x - y;
  }

  /**
   * Partially applied version of {@link P#minus(Integer, Integer)}.
   */
  public static Function<Integer,Integer> minus(final Integer x) {
    return new Function<Integer,Integer>() {
      @Override public Integer apply(Integer value) {
        return minus(x, value);
      }
    };
  }

  /**
   * Partially applied version of {@link P#minus(Integer, Integer)}.
   */
  public static Function<Integer,Function<Integer,Integer>> minus() {
    return new Function<Integer,Function<Integer,Integer>>() {
      @Override public Function<Integer,Integer> apply(Integer value) {
        return minus(value);
      }
    };
  }

  /**
   * Checks if x is less than y.
   *
   * <p><b>Time Complexity:</b> O(1)</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param x First comparable value.
   * @param y Second comparable value.
   * @return True if x is less than y.
   */
  public static <A extends Comparable<A>> Boolean lt(A x, A y) {
    return x.compareTo(y) < 0;
  }

  /**
   * Partially applied version of {@link P#lt(Comparable, Comparable)}.
   */
  public static <A extends Comparable<A>> Function<A,Boolean> lt(final A x) {
    return new Function<A,Boolean>() {
      @Override public Boolean apply(A value) {
        return lt(x, value);
      }
    };
  }

  /**
   * Partially applied version of {@link P#lt(Comparable, Comparable)}.
   */
  public static <A extends Comparable<A>> Function<A,Function<A,Boolean>> lt() {
    return new Function<A,Function<A,Boolean>>() {
      @Override public Function<A,Boolean> apply(A value) {
        return lt(value);
      }
    };
  }

  /**
   * The boolean conjunction of two boolean values.
   *
   * <p><b>Time Complexity:</b> O(1)</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param x First boolean.
   * @param y Second boolean.
   * @return The conjunction ("and") of x and y.
   */
  public static Boolean and(Boolean x, Boolean y) {
    return x && y;
  }

  /**
   * Partially applied version of {@link P#and(Boolean, Boolean)}.
   */
  public static Function<Boolean,Boolean> and(final Boolean x) {
    return new Function<Boolean,Boolean>() {
      @Override public Boolean apply(Boolean value) {
        return and(x, value);
      }
    };
  }

  /**
   * Partially applied version of {@link P#and(Boolean, Boolean)}.
   */
  public static <types> Function<Boolean,Function<Boolean,Boolean>> and() {
    return new Function<Boolean,Function<Boolean,Boolean>>() {
      @Override public Function<Boolean,Boolean> apply(Boolean value) {
        return and(value);
      }
    };
  }

  /**
   * The boolean disjunction of two values.
   *
   * <p><b>Time Complexity:</b> O(1)</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param x First boolean.
   * @param y Second boolean.
   * @return The disjunction ("or") of x and y.
   */
  public static Boolean or(Boolean x, Boolean y) {
    return x || y;
  }

  /**
   * Partially applied version of {@link P#or(Boolean, Boolean)}.
   */
  public static Function<Boolean,Boolean> or(final Boolean x) {
    return new Function<Boolean,Boolean>() {
      @Override public Boolean apply(Boolean value) {
        return or(x, value);
      }
    };
  }

  /**
   * Partially applied version of {@link P#or(Boolean, Boolean)}.
   */
  public static Function<Boolean,Function<Boolean,Boolean>> or() {
    return new Function<Boolean,Function<Boolean,Boolean>>() {
      @Override public Function<Boolean,Boolean> apply(Boolean value) {
        return or(value);
      }
    };
  }

  public static <A,B> Function<A,B> _(final Function<A,Function<A,B>> fn, final A x) {
    return new Function<A,B>() {
      @Override public B apply(A value) {
        return fn.apply(value).apply(x);
      }
    };
  }

  public static <A,B> Function<A,B> _(A x, Function<A,Function<A,B>> fn) {
    return fn.apply(x);
  }

  public static <A> Iterable<A> $() {
    return ImmutableList.of();
  }

  public static <A> Iterable<A> $(A a) {
    return ImmutableList.of(a);
  }

  public static <A> Iterable<A> $(A a, A b) {
    return ImmutableList.of(a, b);
  }

  public static <A> Iterable<A> $(A a, A b, A c) {
    return ImmutableList.of(a, b, c);
  }

  public static <A> Iterable<A> $(A a, A b, A c, A d) {
    return ImmutableList.of(a, b, c, d);
  }

  public static <A> Iterable<A> $(A a, A b, A c, A d, A e) {
    return ImmutableList.of(a, b, c, d, e);
  }

  public static <A> Iterable<A> $(A... args) {
    return ImmutableList.copyOf(args);
  }

  static enum To {
    INSTANCE,
  }

  public final static To to = To.INSTANCE;

  public static <A> Iterable<A>$(A start, To upto) {
    return null;
  }

  public static <A> Iterable<A>$(A start, To upto, A end) {
    return null;
  }

  public static Iterable<Character> s() {
    return ImmutableList.of();
  }

  public static Iterable<Character> s(CharSequence input) {
    return Lists.charactersOf(input);
  }


  static class Pair<A,B> {
    final A first;
    final B second;
    private Pair(A first, B second) {
      this.first = first;
      this.second = second;
    }
  }

  public static <A,B> Pair<A,B> t(A first, B second) {
    return new Pair<A,B>(first, second);
  }

  static class Triple<A,B,C> {
    final A first;
    final B second;
    final C third;
    private Triple(A first, B second, C third) {
      this.first = first;
      this.second = second;
      this.third = third;
    }

    public static <A,B,C> Triple<A,B,C> t(A first, B second, C third) {
      return new Triple<A,B,C>(first, second, third);
    }
  }

  static class Tuple4<A,B,C,D> {
    final A first;
    final B second;
    final C third;
    final D fourth;
    private Tuple4(A first, B second, C third, D fourth) {
      this.first = first;
      this.second = second;
      this.third = third;
      this.fourth = fourth;
    }
  }

  public static <A,B,C,D> Tuple4<A,B,C,D> t(A first, B second, C third, D fourth) {
    return new Tuple4<A,B,C,D>(first, second, third, fourth);
  }

  static class Tuple5<A,B,C,D,E> {
    final A first;
    final B second;
    final C third;
    final D fourth;
    final E fifth;
    private Tuple5(A first, B second, C third, D fourth, E fifth) {
      this.first = first;
      this.second = second;
      this.third = third;
      this.fourth = fourth;
      this.fifth = fifth;
    }
  }

  public static <A,B,C,D,E> Tuple5<A,B,C,D,E> t(A first, B second, C third, D fourth, E fifth) {
    return new Tuple5<A,B,C,D, E>(first, second, third, fourth, fifth);
  }

  public static <A,B> A fst(Pair<A,B> pair) {
    return pair.first;
  }

  public static <A,B> Function<Pair<A,B>,A> fst() {
    return new Function<Pair<A,B>,A>() {
      @Override public A apply(Pair<A,B> value) {
        return fst(value);
      }
    };
  }

  public static <A,B> B snd(Pair<A,B> pair) {
    return pair.second;
  }

  public static <A,B> Function<Pair<A,B>,B> snd() {
    return new Function<Pair<A,B>,B>() {
      @Override public B apply(Pair<A,B> value) {
        return snd(value);
      }
    };
  }

  public static <A,B,C> C curry (Function<Pair<A,B>,C> function, A a, B b) {
    return function.apply(t(a,b));
  }

  public static <A,B,C> Function<B,C> curry(final Function<Pair<A,B>,C> function, final A a) {
    return new Function<B,C>() {
      @Override public C apply(B value) {
        return curry(function, a, value);
      }
    };
  }

  public static <A,B,C> Function<A,Function<B,C>> curry(final Function<Pair<A,B>,C> function) {
    return new Function<A,Function<B,C>>() {
      @Override public Function<B,C> apply(A value) {
        return curry(function, value);
      }
    };
  }

  public static <A,B,C> Function<Function<Pair<A,B>,C>,Function<A,Function<B,C>>> curry() {
    return new Function<Function<Pair<A,B>,C>,Function<A,Function<B,C>>>() {
      @Override public Function<A,Function<B,C>> apply(Function<Pair<A,B>,C> value) {
        return curry(value);
      }
    };
  }

  public static <A,B,C> C uncurry(Function<A,Function<B,C>> function, Pair<A,B> pair) {
    return function.apply(pair.first).apply(pair.second);
  }

  public static <A,B,C> Function<Pair<A,B>,C> uncurry(final Function<A,Function<B,C>> function) {
    return new Function<Pair<A,B>,C>() {
      @Override public C apply(Pair<A,B> value) {
        return uncurry(function, value);
      }
    };
  }

  public static <A,B,C> Function<Function<A,Function<B,C>>,Function<Pair<A,B>,C>> uncurry() {
    return new Function<Function<A,Function<B,C>>,Function<Pair<A,B>,C>>() {
      @Override public Function<Pair<A,B>,C> apply(Function<A,Function<B,C>> value) {
        return uncurry(value);
      }
    };
  }

  /**
   * Checks if an integer is even.
   *
   * <p><b>Time Complexity:</b> O(1)</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param x Integer to check
   * @return True if the integer is even.
   */
  public static Boolean even(Integer x) {
    return x % 2 == 0;
  }

  /**
   * Partially applied version of {@link P#even(Integer)}.
   */
  public static Function<Integer,Boolean> even() {
    return new Function<Integer,Boolean>() {
      @Override public Boolean apply(Integer value) {
        return even(value);
      }
    };
  }

  /**
   * Checks if an integer is odd.
   *
   * <p><b>Time Complexity:</b> O(1)</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param x Integer to check
   * @return True if the integer is odd.
   */
  public static Boolean odd(Integer x) {
    return x % 2 != 0;
  }

  /**
   * Partially applied version of {@link P#even(Integer)}.
   */
  public static Function<Integer,Boolean> odd() {
    return new Function<Integer,Boolean>() {
      @Override public Boolean apply(Integer value) {
        return even(value);
      }
    };
  }


  public static <A extends Comparable<A>> Integer compare(A x, A y) {
    return x.compareTo(y);
  }

  public static <A extends Comparable<A>> Function<A,Integer> compare(final A x) {
    return new Function<A,Integer>() {
      @Override public Integer apply(A value) {
        return compare(x, value);
      }
    };
  }

  public static <A extends Comparable<A>> Function<A,Function<A,Integer>> compare() {
    return new Function<A,Function<A,Integer>>() {
      @Override public Function<A,Integer> apply(A value) {
        return compare(value);
      }
    };
  }

  public static <A extends Comparable<A>> A max(A x, A y) {
    if (x.compareTo(y) < 0) return y;
    else return x;
  }

  public static <A extends Comparable<A>> Function<A,A> max(final A x) {
    return new Function<A,A>() {
      @Override public A apply(A value) {
        return max(x, value);
      }
    };
  }

  public static <A extends Comparable<A>> Function<A,Function<A,A>> max() {
    return new Function<A,Function<A,A>>() {
      @Override public Function<A,A> apply(A value) {
        return max(value);
      }
    };
  }

  public static <A extends Comparable<A>> A min(A x, A y) {
    if (x.compareTo(y) < 0) return x;
    else return y;
  }

  public static <A extends Comparable<A>> Function<A,A> min(final A x) {
    return new Function<A,A>() {
      @Override public A apply(A value) {
        return min(x, value);
      }
    };
  }

  public static <A extends Comparable<A>> Function<A,Function<A,A>> min() {
    return new Function<A,Function<A,A>>() {
      @Override public Function<A,A> apply(A value) {
        return min(value);
      }
    };
  }

  public static <A> A id(A a) {
    return a;
  }

  public static <A> Function<A,A> id() {
    return new Function<A,A>() {
      @Override public A apply(A value) {
        return id(value);
      }
    };
  }

  public static <A,B> A constant(A a, B b) {
    return a;
  }

  public static <A,B> Function<B,A> constant(final A a) {
    return new Function<B,A>() {
      @Override public A apply(B value) {
        return constant(a, value);
      }
    };
  }

  public static <A,B> Function<A,Function<B,A>> constant() {
    return new Function<A,Function<B,A>>() {
      @Override public Function<B,A> apply(A value) {
        return constant(value);
      }
    };
  }

  public static <A,B,C> C compose(Function<B,C> fn1, Function<A,B> fn2, A a) {
    return fn1.apply(fn2.apply(a));
  }

  public static <A,B,C> Function<A,C> compose(final Function<B,C> fn1, final Function<A,B> fn2) {
    return new Function<A,C>() {
      @Override public C apply(A value) {
        return compose(fn1, fn2, value);
      }
    };
  }

  public static <A,B,C> Function<Function<A,B>,Function<A,C>> compose(final Function<B,C> fn1) {
    return new Function<Function<A,B>,Function<A,C>>() {
      @Override public Function<A,C> apply(Function<A,B> value) {
        return compose(fn1, value);
      }
    };
  }

  public static <A,B,C> Function<Function<B,C>,Function<Function<A,B>,Function<A,C>>> compose() {
    return new Function<Function<B,C>,Function<Function<A,B>,Function<A,C>>>() {
      @Override public Function<Function<A,B>,Function<A,C>> apply(Function<B,C> value) {
        return compose(value);
      }
    };
  }

  public static <A,B,C> C flip(Function<A,Function<B,C>> fn, B b, A a) {
    return fn.apply(a).apply(b);
  }

  public static <A,B,C> Function<A,C> flip(final Function<A,Function<B,C>> fn, final B b) {
    return new Function<A,C>() {
      @Override public C apply(A value) {
        return flip(fn, b, value);
      }
    };
  }

  public static <A,B,C> Function<B,Function<A,C>> flip(final Function<A,Function<B,C>> fn) {
    return new Function<B,Function<A,C>>() {
      @Override public Function<A,C> apply(B value) {
        return flip(fn, value);
      }
    };
  }

  public static <A,B,C> Function<Function<A,Function<B,C>>,Function<B,Function<A,C>>> flip() {
    return new Function<Function<A,Function<B,C>>,Function<B,Function<A,C>>>() {
      @Override public Function<B,Function<A,C>> apply(Function<A,Function<B,C>> value) {
        return flip(value);
      }
    };
  }

  public static <A> A until(Function<A,Boolean> pred, Function<A,A> fn, A a) {
    A current = a;
    while (true) {
      if (pred.apply(current)) return current;
      current = fn.apply(current);
    }
  }

  public static <A> Function<A,A> until(final Function<A,Boolean> pred, final Function<A,A> fn) {
    return new Function<A,A>() {
      @Override public A apply(A value) {
        return until(pred, fn, value);
      }
    };
  }

  public static <A> Function<Function<A,A>,Function<A,A>> until(final Function<A,Boolean> pred) {
    return new Function<Function<A,A>,Function<A,A>>() {
      @Override public Function<A,A> apply(Function<A,A> value) {
        return until(pred, value);
      }
    };
  }

  public static <A> Function<Function<A,Boolean>,Function<Function<A,A>,Function<A,A>>> until() {
    return new Function<Function<A,Boolean>,Function<Function<A,A>,Function<A,A>>>() {
      @Override public Function<Function<A,A>,Function<A,A>> apply(Function<A,Boolean> value) {
        return until(value);
      }
    };
  }

  public static Void error(String message) {
    throw new RuntimeException(message);
  }

  public static Function<String,Void> error() {
    return new Function<String,Void>() {
      @Override public Void apply(String value) {
        return error(value);
      }
    };
  }

  public static <A,B> Iterable<B> map(Function<A,B> fn, Iterable<A> xs) {
    return Iterables.transform(xs, fn);
  }

  public static <A,B> Function<Iterable<A>,Iterable<B>> map(final Function<A,B> fn) {
    return new Function<Iterable<A>,Iterable<B>>() {
      @Override public Iterable<B> apply(Iterable<A> value) {
        return map(fn, value);
      }
    };
  }

  public static <A,B> Function<Function<A,B>,Function<Iterable<A>,Iterable<B>>> map() {
    return new Function<Function<A,B>,Function<Iterable<A>,Iterable<B>>>() {
      @Override public Function<Iterable<A>,Iterable<B>> apply(Function<A,B> value) {
        return map(value);
      }
    };
  }

  public static <A> Iterable<A> append(Iterable<A> xs, Iterable<A> ys) {
    return Iterables.concat(xs, ys);
  }

  public static <A> Function<Iterable<A>,Iterable<A>> concat(final Iterable<A> xs) {
    return new Function<Iterable<A>,Iterable<A>>() {
      @Override public Iterable<A> apply(Iterable<A> value) {
        return append(xs, value);
      }
    };
  }

  public static <A> Function<Iterable<A>,Function<Iterable<A>,Iterable<A>>> concat() {
    return new Function<Iterable<A>,Function<Iterable<A>,Iterable<A>>>() {
      @Override public Function<Iterable<A>,Iterable<A>> apply(Iterable<A> value) {
        return concat(value);
      }
    };
  }

  public static <A> Iterable<A> filter(final Function<A,Boolean> pred, Iterable<A> xs) {
    return Iterables.filter(xs, new Predicate<A>() {
      @Override public boolean apply(A arg) {
        return pred.apply(arg);
      }
    });
  }

  public static <A> Function<Iterable<A>,Iterable<A>> filter(final Function<A,Boolean> pred) {
    return new Function<Iterable<A>,Iterable<A>>() {
      @Override public Iterable<A> apply(Iterable<A> value) {
        return filter(pred, value);
      }
    };
  }

  public static <A> Function<Function<A,Boolean>,Function<Iterable<A>,Iterable<A>>> filter() {
    return new Function<Function<A,Boolean>,Function<Iterable<A>,Iterable<A>>>() {
      @Override public Function<Iterable<A>,Iterable<A>> apply(Function<A,Boolean> value) {
        return filter(value);
      }
    };
  }

  public static <A> Boolean isEmpty(Iterable<A> xs) {
    return Iterables.isEmpty(xs);
  }

  public static <A> Function<Iterable<A>,Boolean> isEmpty() {
    return new Function<Iterable<A>,Boolean>() {
      @Override public Boolean apply(Iterable<A> value) {
        return isEmpty(value);
      }
    };
  }

  private static <A> void checkNotEmpty(Iterable<A> xs) {
    if (isEmpty(xs)) {
      throw new NoSuchElementException("Iterable was empty: " + xs);
    }
  }

  /**
   * Extract the first element of an Iterable, which must be non-empty.
   *
   * <p><b>Time Complexity:</b> O(1)</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param xs Iterable to extract from.
   * @return First element.
   */
  public static <A> A head(Iterable<A> xs) {
    checkNotEmpty(xs);
    return xs.iterator().next();
  }

  /**
   * Partially applied version of {@link P#head(Iterable)}.
   */
  public static <A> Function<Iterable<A>,A> head() {
    return new Function<Iterable<A>,A>() {
      @Override public A apply(Iterable<A> value) {
        return head(value);
      }
    };
  }

  /**
   * Extract the last element of an Iterable, which must be finite and non-empty.
   *
   * <p><b>Time Complexity:</b> O(length(xs))</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param xs Iterable to extract from.
   * @return Last element.
   */
  public static <A> A last(Iterable<A> xs) {
    checkNotEmpty(xs);
    return Iterables.getLast(xs);
  }

  /**
   * Partially applied version of {@link P#last(Iterable)}.
   */
  public static <A> Function<Iterable<A>,A> last() {
    return new Function<Iterable<A>,A>() {
      @Override public A apply(Iterable<A> value) {
        return last(value);
      }
    };
  }

  /**
   * Extract the elements after the head of an Iterable, which must be
   * non-empty. The resulting Iterable is unmodifiable. Mutating xs after
   * passing it to this function causes undefined behavior.
   *
   * <p><b>Time Complexity:</b> O(1)</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param xs Iterable to extract from.
   * @return Elements after the head.
   */
  public static <A> Iterable<A> tail(final Iterable<A> xs) {
    checkNotEmpty(xs);
    return new Iterable<A>() {
      @Override public Iterator<A> iterator() {
        Iterator<A> iterator = xs.iterator();
        iterator.next();
        return Iterators.unmodifiableIterator(iterator);
      }
    };
  }

  /**
   * Partially applied version of {@link P#tail(Iterable)}.
   */
  public static <A> Function<Iterable<A>,Iterable<A>> tail() {
    return new Function<Iterable<A>,Iterable<A>>() {
      @Override public Iterable<A> apply(Iterable<A> value) {
        return tail(value);
      }
    };
  }

  /**
   * Return all the elements of an Iterable except the last one. The Iterable
   * must be non-empty. The resulting Iterable is unmodifiable. Mutating xs
   * after passing it to this function causes undefined behavior.
   *
   * <p><b>Time Complexity:</b> O(1)</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param xs The Iterable to extract from.
   * @return Elements except the last.
   */
  public static <A> Iterable<A> init(final Iterable<A> xs) {
    checkNotEmpty(xs);
    return new Iterable<A>() {
      @Override public Iterator<A> iterator() {
        return new SkipLastIterator<A>(xs.iterator());
      }
    };
  }

  /**
   * Partially applied version of {@link P#init(Iterable)}.
   */
  public static <A> Function<Iterable<A>,Iterable<A>> init() {
    return new Function<Iterable<A>,Iterable<A>>() {
      @Override public Iterable<A> apply(Iterable<A> value) {
        return init(value);
      }
    };
  }

  /**
   * Return the length of an Iterable, which must be finite.
   *
   * <p><b>Time Complexity:</b> O(length(xs)) unless the Iterable implements
   *     {@link Collection#size()}, in which case that implementation is
   *     used.</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param xs The Iterable to count the elements of
   * @return
   */
  public static <A> Integer length(Iterable<A> xs) {
    return Iterables.size(xs);
  }

  /**
   * Partially applied version of {@link P#length(Iterable)}.
   */
  public static <A> Function<Iterable<A>,Integer> length() {
    return new Function<Iterable<A>,Integer>() {
      @Override public Integer apply(Iterable<A> value) {
        return length(value);
      }
    };
  }

  /**
   * List index (subscript) operator, starting from 0.
   *
   * <p><b>Time Complexity:</b>O(1)</p>
   * <p><b>Space Complexity:</b>O(1)</p>
   *
   * @param xs
   * @param index
   * @return
   */
  public static <A> A at(Iterable<A> xs, Integer index) {
    return Iterables.get(xs, index);
  }

  /**
   * Partially applied version of {@link P#at(Iterable, Integer)}.
   */
  public static <A> Function<Integer,A> at(final Iterable<A> xs) {
    return new Function<Integer,A>() {
      @Override public A apply(Integer value) {
        return at(xs, value);
      }
    };
  }

  /**
   * Partially applied version of {@link P#at(Iterable, Integer)}.
   */
  public static <A> Function<Iterable<A>,Function<Integer,A>> at() {
    return new Function<Iterable<A>,Function<Integer,A>>() {
      @Override public Function<Integer,A> apply(Iterable<A> value) {
        return at(value);
      }
    };
  }

  /**
   * reverse(xs) returns the elements of xs in reverse order. xs must be finite.
   *
   * <p><b>Time Complexity: O(length(xs))</b></p>
   * <p><b>Space Complexity: O(length(xs))</b></p>
   *
   * @param xs
   * @return
   */
  public static <A> Iterable<A> reverse(Iterable<A> xs) {
    return Lists.reverse(ImmutableList.copyOf(xs));
  }

  /**
   * Partially applied version of {@link P#reverse(Iterable)}.
   */
  public static <A> Function<Iterable<A>,Iterable<A>> reverse() {
    return new Function<Iterable<A>,Iterable<A>>() {
      @Override public Iterable<A> apply(Iterable<A> value) {
        return reverse(value);
      }
    };
  }

  /**
   * foldl, applied to a binary operator, a starting value (typically the
   * left-identity of the operator), and a list, reduces the list using the
   * binary operator, from left to right. The list must be finite.
   *
   * <p><b>Time Complexity: O(length(xs))</b></p>
   * <p><b>Space Complexity: O(1)</b></p>
   *
   * @param fn The binary operator to apply between each element of xs.
   * @param a The initial value.
   * @param xs The list.
   * @return (...((a `fn` x1) `fn` x2) `fn`...) `f` xn - where `fn` is the
   *     infix invocation of the binary operator function.
   */
  public static <A,B> A foldl(Function<A,Function<B,A>> fn, A a, Iterable<B> xs) {
    A result = a;
    for (B x : xs) {
      result = fn.apply(result).apply(x);
    }
    return result;
  }

  /**
   * Partially applied version of {@link P#foldl(Function, Object, Iterable)}.
   */
  public static <A,B> Function<Iterable<B>,A> foldl(final Function<A,Function<B,A>> fn, final A a) {
    return new Function<Iterable<B>,A>() {
      @Override public A apply(Iterable<B> value) {
        return foldl(fn, a, value);
      }
    };
  }

  /**
   * Partially applied version of {@link P#foldl(Function, Object, Iterable)}.
   */
  public static <A,B> Function<A,Function<Iterable<B>,A>> foldl(
      final Function<A,Function<B,A>> fn) {
    return new Function<A,Function<Iterable<B>,A>>() {
      @Override public Function<Iterable<B>,A> apply(A value) {
        return foldl(fn, value);
      }
    };
  }

  /**
   * Partially applied version of {@link P#foldl(Function, Object, Iterable)}.
   */
  public static <A,B> Function<Function<A,Function<B,A>>,Function<A,Function<Iterable<B>,A>>>
  foldl() {
    return new Function<Function<A,Function<B,A>>,Function<A,Function<Iterable<B>,A>>>() {
      @Override public Function<A,Function<Iterable<B>,A>> apply(Function<A,Function<B,A>> value) {
        return foldl(value);
      }
    };
  }

  /**
   * foldl1 is a variant of {@link P#foldl(Function, Object, Iterable)} that
   * has no starting value argument, and thus must be applied to finite
   * non-empty lists.
   *
   * <p><b>Time Complexity: O(length(xs))</b></p>
   * <p><b>Space Complexity: O(1)</b></p>
   *
   * @param fn The binary operator to apply between each element of xs.
   * @param xs The list.
   * @return (...((x1 `fn` x2) `fn` x3) `fn`...) `f` xn - where `fn` is the
   *     infix invocation of the binary operator function.
   */
  public static <A> A foldl1(Function<A,Function<A,A>> fn, Iterable<A> xs) {
    checkNotEmpty(xs);
    return foldl(fn, head(xs), tail(xs));
  }

  /**
   * Partially applied version of {@link P#foldl1(Function, Iterable)}.
   */
  public static <A> Function<Iterable<A>,A> foldl1(final Function<A,Function<A,A>> fn) {
    return new Function<Iterable<A>,A>() {
      @Override public A apply(Iterable<A> value) {
        return foldl1(fn, value);
      }
    };
  }

  /**
   * Partially applied version of {@link P#foldl1(Function, Iterable)}.
   */
  public static <A> Function<Function<A,Function<A,A>>,Function<Iterable<A>,A>> foldl1() {
    return new Function<Function<A,Function<A,A>>,Function<Iterable<A>,A>>() {
      @Override public Function<Iterable<A>,A> apply(Function<A,Function<A,A>> value) {
        return foldl1(value);
      }
    };
  }

  /**
   * foldr, applied to a binary operator, a starting value (typically the
   * right-identity of the operator), and an Iterable, reduces the Iterable
   * using the binary operator, from right to left. This is a strict
   * implementation, so it must be applied to a finite list.
   *
   * <p><b>Time Complexity:</b> O(length(xs))</p>
   * <p><b>Space Complexity:</b> O(length(xs))</p>
   *
   * @param fn The binary operator to apply between the elements of xs
   * @param b The initial value (conceptually placed after the last binary
   *     operator)
   * @param xs The list.
   * @return x1 `f` (x2 `f` ... (xn `f` a)...) - where `fn` is the infix
   *     invocation of the binary operator function.
   */
  public static <A,B> B foldr(Function<A,Function<B,B>> fn, B b, Iterable<A> xs) {
    B result = b;
    for (A x : reverse(xs)) {
      result = fn.apply(x).apply(result);
    }
    return result;
  }

  /**
   * Partially applied version of {@link P#foldr(Function, Object, Iterable)}.
   */
  public static <A,B> Function<Iterable<A>,B> foldr(final Function<A,Function<B,B>> fn, final B b) {
    return new Function<Iterable<A>,B>() {
      @Override public B apply(Iterable<A> value) {
        return foldr(fn, b, value);
      }
    };
  }

  /**
   * Partially applied version of {@link P#foldr(Function, Object, Iterable)}.
   */
  public static <A,B> Function<B,Function<Iterable<A>,B>> foldr(
      final Function<A,Function<B,B>> fn) {
    return new Function<B,Function<Iterable<A>,B>>() {
      @Override public Function<Iterable<A>,B> apply(B value) {
        return foldr(fn, value);
      }
    };
  }

  /**
   * Partially applied version of {@link P#foldr(Function, Object, Iterable)}.
   */
  public static <A,B> Function<Function<A,Function<B,B>>,Function<B,Function<Iterable<A>,B>>>
  foldr() {
    return new Function<Function<A,Function<B,B>>,Function<B,Function<Iterable<A>,B>>>() {
      @Override public Function<B,Function<Iterable<A>,B>> apply(Function<A,Function<B,B>> value) {
        return foldr(value);
      }
    };
  }

  /**
   * foldr1 is a variant of foldr that has no starting value argument, and thus
   * must be applied to finite non-empty lists.
   *
   * <p><b>Time Complexity:</b> O(length(xs))</p>
   * <p><b>Space Complexity:</b> O(length(xs))</p>
   *
   * @param fn The binary operator to apply between the elements of xs
   * @param xs The list.
   * @return x1 `f` (x2 `f` ... (xn-1 `f` xn)...) - where `fn` is the infix
   *     invocation of the binary operator function.
   */
  public static <A> A foldr1(Function<A,Function<A,A>> fn, Iterable<A> xs) {
    checkNotEmpty(xs);
    return foldr(fn, last(xs), init(xs));
  }

  /**
   * Partially applied version of {@link P#foldr1(Function, Iterable)}.
   */
  public static <A> Function<Iterable<A>,A> foldr1(final Function<A,Function<A,A>> fn) {
    return new Function<Iterable<A>,A>() {
      @Override public A apply(Iterable<A> value) {
        return foldr1(fn, value);
      }
    };
  }

  /**
   * Partially applied version of {@link P#foldr1(Function, Iterable)}.
   */
  public static <A> Function<Function<A,Function<A,A>>,Function<Iterable<A>,A>> foldr1() {
    return new Function<Function<A,Function<A,A>>,Function<Iterable<A>,A>>() {
      @Override public Function<Iterable<A>,A> apply(Function<A,Function<A,A>> value) {
        return foldr1(value);
      }
    };
  }

  /**
   * andList returns the conjunction of a Boolean list, which must be finite.
   * This function is called 'and' in Haskell, but that name was used here for
   * the function Haskell calls (&&).
   *
   * <p><b>Time Complexity:</b> O(length(xs))</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param xs List of booleans.
   * @return x1 && x2 && ... xn
   */
  public static Boolean andList(Iterable<Boolean> xs) {
    return foldl(and(), true, xs);
  }

  /**
   * Partially applied version of {@link P#andList(Iterable)}.
   */
  public static Function<Iterable<Boolean>,Boolean> andList() {
    return new Function<Iterable<Boolean>,Boolean>() {
      @Override public Boolean apply(Iterable<Boolean> value) {
        return andList(value);
      }
    };
  }

  /**
   * orList returns the disjunction of a Boolean list, which must be finite.
   * This function is called 'or' in Haskell, but that name was used here for
   * the function Haskell calls (||).
   *
   * <p><b>Time Complexity:</b> O(length(xs))</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param xs List of booleans.
   * @return x1 || x2 || ... xn
   */
  public static Boolean orList(Iterable<Boolean> xs) {
    return foldl(or(), false, xs);
  }

  /**
   * Partially applied version of {@link P#orList(Iterable)}.
   */
  public static Function<Iterable<Boolean>,Boolean> orList() {
    return new Function<Iterable<Boolean>,Boolean>() {
      @Override public Boolean apply(Iterable<Boolean> value) {
        return orList(value);
      }
    };
  }

  /**
   * Applied to a predicate and a list, any determines if any element of the
   * list satisfies the predicate.
   *
   * <p><b>Time Complexity:</b> O(length(xs))</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param predicate Predicate to apply to each element of the list.
   * @param xs The list.
   * @return True if any element of the list satisfies the predicate.
   */
  public static <A> Boolean any(Function<A,Boolean> predicate, Iterable<A> xs) {
    return orList(map(predicate, xs));
  }

  /**
   * Partially applied version of {@link P#any(Function, Iterable)}.
   */
  public static <A> Function<Iterable<A>,Boolean> any(final Function<A,Boolean> predicate) {
    return new Function<Iterable<A>,Boolean>() {
      @Override public Boolean apply(Iterable<A> value) {
        return any(predicate, value);
      }
    };
  }

  /**
   * Partially applied version of {@link P#any(Function, Iterable)}.
   */
  public static <A> Function<Function<A,Boolean>,Function<Iterable<A>,Boolean>> any() {
    return new Function<Function<A,Boolean>,Function<Iterable<A>,Boolean>>() {
      @Override public Function<Iterable<A>,Boolean> apply(Function<A,Boolean> value) {
        return any(value);
      }
    };
  }

  /**
   * Applied to a predicate and a list, all determines if all elements of the
   * list satisfy the predicate.
   *
   * <p><b>Time Complexity:</b> O(length(xs))</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param predicate Predicate to apply to each element of the list.
   * @param xs The list.
   * @return True if every element of the list satisfies the predicate.
   */
  public static <A> Boolean all(Function<A,Boolean> predicate, Iterable<A> xs) {
    return andList(map(predicate, xs));
  }

  /**
   * Partially applied version of {@link P#all(Function, Iterable)}.
   */
  public static <A> Function<Iterable<A>,Boolean> all(final Function<A,Boolean> predicate) {
    return new Function<Iterable<A>,Boolean>() {
      @Override public Boolean apply(Iterable<A> value) {
        return all(predicate, value);
      }
    };
  }

  /**
   * Partially applied version of {@link P#all(Function, Iterable)}.
   */
  public static <A> Function<Function<A,Boolean>,Function<Iterable<A>,Boolean>> all() {
    return new Function<Function<A,Boolean>,Function<Iterable<A>,Boolean>>() {
      @Override public Function<Iterable<A>,Boolean> apply(Function<A,Boolean> value) {
        return all(value);
      }
    };
  }

  /**
   * The sum function computes the sum of a finite list of numbers.
   *
   * <p><b>Time Complexity:</b> O(length(xs))</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param xs The list to sum.
   * @return The sum of the elements of xs.
   */
  public static Integer sum(Iterable<Integer> xs) {
    return foldl(plus(), 0, xs);
  }

  /**
   * Partially applied version of {@link P#sum(Iterable)}.
   */
  public static Function<Iterable<Integer>,Integer> sum() {
    return new Function<Iterable<Integer>,Integer>() {
      @Override public Integer apply(Iterable<Integer> value) {
        return sum(value);
      }
    };
  }

  /**
   * The product function computes the product of a finite list of numbers.
   *
   * <p><b>Time Complexity:</b> O(length(xs))</p>
   * <p><b>Space Complexity:</b> O(1)</p>
   *
   * @param xs The list to multiply.
   * @return The product of the elements of xs.
   */
  public static Integer product(Iterable<Integer> xs) {
    return foldl(times(), 1, xs);
  }

  /**
   * Partially applied version of {@link P#product(Iterable)}.
   */
  public static Function<Iterable<Integer>,Integer> product() {
    return new Function<Iterable<Integer>,Integer>() {
      @Override public Integer apply(Iterable<Integer> value) {
        return product(value);
      }
    };
  }









}
