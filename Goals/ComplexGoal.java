package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class ComplexGoal implements Goal {
	private boolean and; 
	private List<Goal> goals;
	
	/**
	 * 
	 * @param and - boolean to indicate if the complex is AND (true) or OR (false)
	 * @param goals
	 */
	public ComplexGoal(boolean and, List<Goal> goals) {
		this.and = and; 
		this.goals = goals;
	}
	
	public ComplexGoal(boolean and) {
		this.and = and; 
		this.goals = new ArrayList<>(); 
	}
	
	/**
	 * 
	 * @param g Goal is added in the dungeon loader 
	 */
	public void addgoal(Goal g) {
		goals.add(g);
	}
	
	public boolean getType() {
		return and;
	}
	
	/**
	 * Returns false or true depending on the conditions
	 */
	@Override
	public boolean isDone() {
		if (and) {
			for (Goal g : goals) {
				if (!g.isDone()) return false;
			}
			return true; 
		} else {
			for (Goal g : goals) {
				if (g.isDone()) return true;
			}
			return false; 
		}
	}

	/**
	 * 
	 * @returns a list of goals 
	 */
	public List<Goal> getGoals() {
		return goals;
	}

	

	
	
}
