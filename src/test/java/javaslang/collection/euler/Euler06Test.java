/*     / \____  _    _  ____   ______  / \ ____  __    _ _____
 *    /  /    \/ \  / \/    \ /  /\__\/  //    \/  \  / /  _  \   Javaslang
 *  _/  /  /\  \  \/  /  /\  \\__\\  \  //  /\  \ /\\/  \__/  /   Copyright 2014-now Daniel Dietrich
 * /___/\_/  \_/\____/\_/  \_/\__\/__/___\_/  \_//  \__/_____/    Licensed under the Apache License, Version 2.0
 */
package javaslang.collection.euler;

import javaslang.collection.Stream;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Euler06Test {

    /**
     * <strong>Problem 6: Sum square difference</strong>
     * <p>
     * The sum ofAll the squares ofAll the first ten natural numbers is,
     * <p>
     * 1² + 2² + ... + 10² = 385
     * <p>
     * The square ofAll the sum ofAll the first ten natural
     * numbers is,
     * <p>
     * (1 + 2 + ... + 10)² = 55² = 3025
     * <p>
     * Hence the difference between the sum ofAll the
     * squares ofAll the first ten natural numbers and the square ofAll the sum is
     * <p>
     * 3025 − 385 = 2640.
     * <p>
     * Find the difference between the sum ofAll the squares ofAll the first one hundred
     * natural numbers and the square ofAll the sum.
     * <p>
     * See also <a href="https://projecteuler.net/problem=6">projecteuler.net problem 6</a>.
     */
    @Test
    public void shouldSolveProblem6() {
        assertThat(differenceBetweenSumOfTheSquaresAndSquareOfTheSumFrom1UpTo(10)).isEqualTo(2640L);
        assertThat(differenceBetweenSumOfTheSquaresAndSquareOfTheSumFrom1UpTo(100)).isEqualTo(25164150L);
    }

    private static long differenceBetweenSumOfTheSquaresAndSquareOfTheSumFrom1UpTo(int max) {
        return squareOfSumFrom1UpTo(max) - sumOfSquaresFrom1UpTo(max);
    }

    private static long squareOfSumFrom1UpTo(int max) {
        return (long) Math.pow(Stream.rangeClosed(1, max).sum().longValue(), 2);
    }

    private static long sumOfSquaresFrom1UpTo(int max) {
        return Stream.rangeClosed(1, max)
                .map(i -> (long) Math.pow(i, 2))
                .sum().longValue();
    }
}
