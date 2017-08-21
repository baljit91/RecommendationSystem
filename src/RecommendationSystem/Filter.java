package RecommendationSystem;
/**
 * An interface that has a method signature which is implemented by all classes that implement filter
 */
public interface Filter {
	public boolean satisfies(String id);
}
