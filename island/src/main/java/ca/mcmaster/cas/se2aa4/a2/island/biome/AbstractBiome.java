package ca.mcmaster.cas.se2aa4.a2.island.biome;

import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileGroup;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractBiome implements Biome {
    private final List<Rule> biomeRules;

    protected AbstractBiome() {
        this.biomeRules = new ArrayList<>();
    }

    /**
     *
     * @param rule The {@link Rule} to add to the biome
     */
    protected void addRule(Rule rule) {
        if(!this.biomeRules.contains(rule)) {
            this.biomeRules.add(rule);
        } else {
            String message = String.format("Repeated rule %s.", rule);
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public final void takeTile(Tile tile) {
        if(tile.getType().getGroup() == TileGroup.LAND) {
            float humidity = tile.getHumidity();
            double temperature = 50 - (50 * tile.getElevation());

            Optional<Rule> ruleOptional = this.biomeRules.stream()
                    .filter(r -> r.matches(humidity, temperature))
                    .findFirst();

            if(ruleOptional.isPresent()) {
                Rule rule = ruleOptional.get();
                TileType type = rule.getType();
                tile.setType(type);
            } else {
                String message = String.format(
                        "Biome does not cover all graph area. Humidity: %f | Temperature: %f",
                        humidity,
                        temperature
                );
                throw new IllegalStateException(message);
            }
        }
    }
}
