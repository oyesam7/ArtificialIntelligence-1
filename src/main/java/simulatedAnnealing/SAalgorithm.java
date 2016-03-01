package simulatedAnnealing;

/**
 * A common variant of the SA algorithm is as follows:<br>
 * <li>
 * 1. Begin at a start point P (either user-selected or randomly-generated).<li>
 * 2. Set the temperature, T, to it’s starting value: Tmax<li>
 * 3. Evaluate P with an objective function, F. This yields the value F(P).<li>
 * 4. If F (P ) ≥ Ftarget then EXIT and return P as the solution; else continue.
 * <li>
 * 5. Generate n neighbors of P in the search space: (P1, P2, ..., Pn).<li>
 * 6. Evaluate each neighbor, yielding (F (P1), F (P2), ..., F (Pn)).<li>
 * 7. Let Pmax be the neighbor with the highest evaluation.<li>
 * 8. Let q = { F(Pmax)−F(P) } / F(P)<li>
 * 9. Letp=min[1,e**(-q/T) ]<li>
 * 10. Generate x, a random real number in the closed range [0,1].<li>
 * 11. Ifx>pthenP ←Pmax ;;(Exploiting)<li>
 * 12. else P ← a random choice among the n neighbors. ;; (Exploring) <li>
 * 13. T ← T − dT<li>
 * 14. GOTO Step 4<br>
 * 
 * @author GuoJunjun
 *
 */
public class SAalgorithm {

}
