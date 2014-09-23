/**    / \____  _    ______   _____ / \____   ____  _____
 *    /  \__  \/ \  / \__  \ /  __//  \__  \ /    \/ __  \   Javaslang
 *  _/  // _\  \  \/  / _\  \\_  \/  // _\  \  /\  \__/  /   Copyright 2014 Daniel Dietrich
 * /___/ \_____/\____/\_____/____/\___\_____/_/  \_/____/    Licensed under the Apache License, Version 2.0
 */
package javaslang.monad;

import java.io.Serializable;
import java.util.Objects;

public final class Right<L, R> implements Either<L, R>, Serializable {

	private static final long serialVersionUID = 6037923230455552437L;

	final R right;

	public Right(R right) {
		this.right = right;
	}

	@Override
	public boolean isLeft() {
		return false;
	}

	@Override
	public boolean isRight() {
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Right)) {
			return false;
		}
		final Right<?, ?> other = (Right<?, ?>) obj;
		return Objects.equals(right, other.right);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(right);
	}

	@Override
	public String toString() {
		return String.format("Right(%s)", right);
	}

}
