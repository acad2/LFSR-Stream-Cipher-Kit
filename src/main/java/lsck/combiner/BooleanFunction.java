package lsck.combiner;

import lsck.bitwise.BitVector;

/**
 * Represents a Boolean function.
 *
 * <p>A {@code BooleanFunction} object represents and computes the values of a Boolean function with
 * a fixed number of parameters. Underlying details of the function are made available both as a
 * truth table and as a "term table" - a list of algebraic terms describing a Boolean expression.
 *
 * @see TruthTable
 * @see TermTable
 */
public interface BooleanFunction {

  /**
   * Returns the number of arguments taken by this function.
   *
   * @return the number of arguments taken by this function.
   */
  int getArity();

  /**
   * Returns the value of the function given the specified arguments.
   *
   * <p>The provided bit vector indicates which arguments to evaluate this function for. The least
   * significant bit is treated as the first argument "x0", and the remaining bits correspond to the
   * remaining consecutive arguments.
   *
   * <p>E.g. 01101 implies x0 = 1, x1 = 0, x2 = 1, x3 = 1, and x4 = 0.
   *
   * @param An argument vector of length {@code getArity()}.
   * @return The value of this function evaluated for the specified argument.
   */
  byte at(BitVector args);

  /**
   * Returns the value of the function given the specified arguments.
   *
   * <p>The length of the {@code long} vector is assumed to be equal to arity of the function - any
   * higher order bits are truncated. Behavior-wise, this method should be equivalent to calling
   * {@link #at(BitVector)} with a {@link BitVector} generated by calling {@link
   * BitVector#fromInteger(int,long)}.
   *
   * @param A {@code long} representing an argument vector of length {@code getArity()}.
   * @return The value of this function evaluated for the specified argument.
   */
  byte at(long args);

  /**
   * Returns the value of the function given the specified arguments.
   *
   * <p>The length of the {@code int} vector is assumed to be equal to arity of the function - any
   * higher order bits are truncated. Behavior-wise, this method should be equivalent to calling
   * {@link #at(BitVector)} with a {@link BitVector} generated by calling {@link
   * BitVector#fromInteger(int,int)}.
   *
   * @param An {@code int} representing an argument vector of length {@code getArity()}.
   * @return The value of this function evaluated for the specified argument.
   */
  byte at(int args);

  /**
   * Returns the value of the function given the specified arguments.
   *
   * <p>The length of the {@code short} vector is assumed to be equal to arity of the function - any
   * higher order bits are truncated. Behavior-wise, this method should be equivalent to calling
   * {@link #at(BitVector)} with a {@link BitVector} generated by calling {@link
   * BitVector#fromInteger(int,short)}.
   *
   * @param A {@code short} representing an argument vector of length {@code getArity()}.
   * @return The value of this function evaluated for the specified argument.
   */
  byte at(short args);

  /**
   * Returns the value of the function given the specified arguments.
   *
   * <p>The length of the {@code byte} vector is assumed to be equal to arity of the function - any
   * higher order bits are truncated. Behavior-wise, this method should be equivalent to calling
   * {@link #at(BitVector)} with a {@link BitVector} generated by calling {@link
   * BitVector#fromInteger(int,byte)}.
   *
   * @param A {@code byte} representing an argument vector of length {@code getArity()}.
   * @return The value of this function evaluated for the specified argument.
   */
  byte at(byte args);

  /**
   * Returns a copy of the truth table for this function.
   *
   * @return a copy of the truth table for this function.
   */
  TruthTable getTruthTable();

  /**
   * Returns a copy of the term table for this function.
   *
   * @see TermTable#buildTruthTable()
   */
  TermTable getTermTable();

  /**
   * Creates a Boolean function from a string representation, with variables indexed from 1.
   *
   * <p>Currently, only functions of fewer than {@link IntegerTermTable#MAX_ARITY} variables can be
   * created from string representations.
   *
   * <p>See {@link BooleanFunction#fromString(int, String, boolean)} for details on the string
   * representation of Boolean functions.
   *
   * @param arity The number of variables for the represented function.
   * @param expression A string representation of the desired function.
   * @return A {@link BooleanFunction} corresponding to the given string representation.
   */
  public static BooleanFunction fromString(int arity, String expression) {
    return fromString(arity, expression, false);
  }

  /**
   * Creates a Boolean function from a string representation.
   *
   * <p>Currently, only functions of fewer than {@link IntegerTermTable#MAX_ARITY} variables can be
   * created from string representations.
   *
   * <p>Consider the example string "1 + x1 x2 + (x1 + 1) x3". Allowable symbols are the constants
   * '0' and '1', variables of the form xN (where N is the index of the variable), parentheses for
   * grouping terms, and the symbol '+' for adding terms. Immediately adjacent variables, constants,
   * and grouped terms are taken to be multiplied together. Whitespace is ignored.
   *
   * @param arity The number of variables for the represented function.
   * @param expression A string representation of the desired function.
   * @param indexFromZero If {@code true}, variables in the string representation are assumed to be
   *     indexed from zero. Otherwise, they are assumed to be indexed from 1.
   * @return A {@link BooleanFunction} corresponding to the given string representation.
   */
  public static BooleanFunction fromString(int arity, String expression, boolean indexFromZero) {
    return new SimpleBooleanFunction(new IntegerTermTable(arity, expression, indexFromZero));
  }
}
