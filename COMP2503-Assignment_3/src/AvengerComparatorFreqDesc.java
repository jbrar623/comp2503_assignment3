import java.util.Comparator;

public class AvengerComparatorFreqDesc implements Comparator <Avenger>{

	@Override
	/**
	 * compares avanger objects: 
	 * Total order:
	 * descending order of total frequency 
	 * in case of tie, in ascending alphabetical order of performer's last name
	 * @return int - A negative integer, zero, or a positive integer as this avenger 
	 * is less, equal to, or greater than the other.
	 * @param two avenger objects to be compared
	 */
	public int compare(Avenger a1, Avenger a2) {
		int diff = a2.getTotalFrequency() - a1.getTotalFrequency();
		if (diff == 0) {
			return a1.getPerformer().compareTo(a2.getPerformer());
		}
		return diff;
	}
}

