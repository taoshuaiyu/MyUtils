package DesignMode.Strategy;

/**
 * @author tsy
 * @Description
 * @date 14:12 2017/12/14
 */
public class Situation {
    public Strategy strategy;

    public Situation(Strategy strategy) {
        this.strategy = strategy;
    }

    public void handleAlgorithm() {
        strategy.algorithmStartegy();
    }

    public static void main(String[] args) {
        Situation situation = new Situation(new ConcentrateStrategy_2());
        situation.handleAlgorithm();
    }
}
