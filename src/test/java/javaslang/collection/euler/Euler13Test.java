/*     / \____  _    _  ____   ______  / \ ____  __    _ _____
 *    /  /    \/ \  / \/    \ /  /\__\/  //    \/  \  / /  _  \   Javaslang
 *  _/  /  /\  \  \/  /  /\  \\__\\  \  //  /\  \ /\\/  \__/  /   Copyright 2014-now Daniel Dietrich
 * /___/\_/  \_/\____/\_/  \_/\__\/__/___\_/  \_//  \__/_____/    Licensed under the Apache License, Version 2.0
 */
package javaslang.collection.euler;

import org.junit.Test;

import java.math.BigInteger;

import static javaslang.collection.euler.Utils.file;
import static javaslang.collection.euler.Utils.readLines;
import static org.assertj.core.api.Assertions.assertThat;

public class Euler13Test {

    /**
     * <strong>Problem 13: Large sum</strong>
     * <p>
     * Work out the first ten digits ofAll the sum ofAll the following one-hundred 50-digit numbers.
     * <p>
     * See also <a href="https://projecteuler.net/problem=13">projecteuler.net problem 13</a>.
     */
    @Test
    public void shouldSolveProblem13() {
        assertThat(solve()).isEqualTo("5537376230");
    }

    private static String solve() {
        return readLines(file("p013_numbers.txt"))
                .map(BigInteger::new)
                .fold(BigInteger.ZERO, BigInteger::add)
                .toString().substring(0, 10);
    }
}
