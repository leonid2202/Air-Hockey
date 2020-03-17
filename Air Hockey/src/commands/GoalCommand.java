package commands;

import airHockey.World;

public class GoalCommand implements Command {
    private static final long serialVersionUID = 1L;
    protected boolean isEnemyGoal;

    public GoalCommand(boolean isEnemyGoal) {
        this.isEnemyGoal = isEnemyGoal;
    }

    @Override
    public void perform(World world) {
        world.goal(isEnemyGoal);
    }

}
