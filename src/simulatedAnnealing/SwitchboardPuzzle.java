package simulatedAnnealing;

/**
 * The Switchboard Puzzle
 * <p>
 * An electrician encounters a rectangular switchboard consisting of M rows and
 * N columns of pegs. Given a pre-defined starting and ending peg, the task is
 * to connect all of the pegs with one wire of the shortest possible length. The
 * key constraints are:
 * <li>1. The wire cannot cross any peg more than once.
 * <li>2. The distance between neighboring pegs in the vertical or horizontal
 * direction is D.
 * <li>3. Anytime the wire turns at a peg P, it needs to be wound completely
 * around P before turning. This uses W additional units of wire.
 * <li>4. The wire is wound around the first (but not the last) peg of the
 * sequence.
 * 
 * <p>
 * Puzzle Variants (all of which you must attempt:)
 * <p>
 * 
 * <li>1. M = N = 4, D=3 and W = 2. Start position = 1 peg below the upper right
 * corner. End position = bottom left peg.
 * <li>2. M = 6, N = 5, D=3 and W = 2. Start position = upper right peg. End
 * position = bottom left peg.
 * <li>3. M = N = 8, D=3 and W = 2. Start position = 1 peg below the upper right
 * corner. End position = bottom left peg. <br>
 * Note: This is not an easy puzzle, so your SA might have problems with one or
 * more of the 3 variants. If you are really ambitious, try using your A*
 * algorithm to solve the problem instead. This is not a requirement, just a
 * suggestion for those who either a) enjoy puzzles or b) hate puzzles and like
 * to get their computers to do the work for them.
 * 
 * @author GuoJunjun
 *
 */
public class SwitchboardPuzzle {

}
