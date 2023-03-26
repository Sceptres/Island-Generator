package ca.mcmaster.cas.se2aa4.a2.island.biome;

import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;

import java.util.Objects;
import java.util.function.BiFunction;

public class Rule {
    private final TileType type;
    private final int lowerHumidity;
    private final int upperHumidity;
    private final int lowerTemperature;
    private final int upperTemperature;
    private final BiFunction<Float, Double, Boolean> ruleFunction;

    public Rule(int lowerHumidity, int upperHumidity, int lowerTemperature, int upperTemperature, TileType type) {
        this.lowerHumidity = lowerHumidity;
        this.upperHumidity = upperHumidity;
        this.lowerTemperature = lowerTemperature;
        this.upperTemperature = upperTemperature;
        this.type = type;

        this.ruleFunction = (humidity, temperature) -> {
            boolean humidityMatches = this.lowerHumidity <= humidity && humidity <= this.upperHumidity;
            boolean temperatureMatches = this.lowerTemperature <= temperature && temperature <= this.upperTemperature;
            return humidityMatches && temperatureMatches;
        };
    }

    /**
     *
     * @return The {@link TileType} of this rule
     */
    public TileType getType() {
        return this.type;
    }

    /**
     *
     * @param humidity The humidity to check
     * @param temperature The temperature to check
     * @return True if the humidity and temperature fall within the range. False otherwise
     */
    public boolean matches(float humidity, double temperature) {
        return this.ruleFunction.apply(humidity, temperature);
    }

    @Override
    public String toString() {
        return "Rule{" +
                "type=" + type +
                ", lowerHumidity=" + lowerHumidity +
                ", upperHumidity=" + upperHumidity +
                ", lowerTemperature=" + lowerTemperature +
                ", upperTemperature=" + upperTemperature +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rule rule)) return false;
        return lowerHumidity == rule.lowerHumidity && upperHumidity == rule.upperHumidity && lowerTemperature == rule.lowerTemperature && upperTemperature == rule.upperTemperature && type == rule.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, lowerHumidity, upperHumidity, lowerTemperature, upperTemperature);
    }
}
