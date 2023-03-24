package ca.mcmaster.cas.se2aa4.a2.island.geography.generator.generators;

import ca.mcmaster.cas.se2aa4.a2.island.geography.Land;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Ocean;
import ca.mcmaster.cas.se2aa4.a2.island.geography.River;
import ca.mcmaster.cas.se2aa4.a2.island.geography.generator.GeographyGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.path.Path;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileGroup;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.util.*;

public class RiverGenerator implements GeographyGenerator<River> {
    private final Land land;
    private final Ocean ocean;

    public RiverGenerator(Land land, Ocean ocean) {
        this.land = land;
        this.ocean = ocean;
    }

    @Override
    public List<River> generate(int num) {
        List<River> generatedRivers = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            River river = this.generateRiver();
            generatedRivers.add(river);
        }

        List<River> removableRivers = this.findRemovableRivers(generatedRivers);
        generatedRivers.removeAll(removableRivers);

        land.getRivers().forEach(river -> {
            Vertex endVertex = river.getEnd();
            List<Path> riverPaths = river.getRiverPath();

            Path lastPath = riverPaths.stream().filter(p -> p.hasVertex(endVertex)).findFirst().get();

            Optional<Tile> oceanTile = land.getTiles().stream()
                    .filter(t -> t.getVertices().contains(endVertex) && !t.getPaths().contains(lastPath))
                    .findFirst();
            if (oceanTile.isPresent()) {
                Tile tile = oceanTile.get();
                tile.setType(TileType.LAND_WATER_TILE);
            }
        });

        return generatedRivers;
    }

    /**
     *
     * @return A single {@link River}
     */
    private River generateRiver() {
        Random random = new Random();

        List<Vertex> springs = this.land.getSprings();
        Vertex spring = springs.get(random.nextInt(springs.size()));

        River river = new River(spring, random.nextFloat(1f, 1.5f));
        land.addRiver(river);
        this.generateRiverPath(river, this.land, this.ocean, spring);

        return river;
    }

    /**
     *
     * @param generatedRivers The list of generated {@link River}
     * @return The list of {@link River} that should be removed
     */
    private List<River> findRemovableRivers(List<River> generatedRivers) {
        List<River> mergedRivers = new ArrayList<>();

        for(River river : generatedRivers) {
            List<River> intersectingRivers = generatedRivers.stream()
                    .filter(r -> r != river && river.intersect(r))
                    .toList();
            for(River river1 : intersectingRivers) {
                river.merge(river1);
                mergedRivers.add(river1);
            }

            List<Path> riverPaths = river.getRiverPath();

            if(riverPaths.size() == 0) {
                mergedRivers.add(river);
            }
        }

        return mergedRivers;
    }

    /**
     *
     * @param river The {@link River} we are generating path for
     * @param land The {@link Land} that this river belongs to
     * @param ocean The {@link Ocean} of this ocean
     * @param start The {@link Vertex} to start the river at
     */
    private void generateRiverPath(River river, Land land, Ocean ocean, Vertex start) {
        List<Tile> landTiles = land.getTiles();
        List<Tile> oceanTiles = ocean.getTiles();
        List<Tile> tiles = new ArrayList<>();
        tiles.addAll(landTiles);
        tiles.addAll(oceanTiles);

        List<Path> paths = land.getPaths();
        List<Vertex> possibleSprings = land.getSprings();

        Optional<Path> newPath = paths.stream()
                .filter(p -> p.hasVertex(start) && (possibleSprings.contains(p.getV1()) || possibleSprings.contains(p.getV2())))
                .min(Comparator.comparingDouble(Path::getElevation));

        if (newPath.isPresent()) {
            Path path = newPath.get();

            Vertex v1 = path.getV1();
            Vertex v2 = path.getV2();
            Vertex next = v1.equals(start) ? v2 : v1;

            List<Tile> pathTiles = tiles.stream().filter(t -> t.getPaths().contains(path)).toList();
            river.addPath(path, start, pathTiles);

            boolean isLowestElevation = this.isAtLowest(river, path, start);

            boolean reachedWater = tiles.stream()
                    .filter(t -> t.getVertices().contains(next))
                    .anyMatch(t -> t.getNeighbors().stream().anyMatch(t1 -> t1.getType().getGroup() == TileGroup.WATER));

            boolean hasIntersection = land.getRivers().stream()
                    .filter(r -> !r.equals(river))
                    .anyMatch(river::intersect);

            boolean isLooping = this.isLooping(river, path);

            if(hasIntersection || isLooping || isLowestElevation || reachedWater)
                return;

            generateRiverPath(river, land, ocean, next);
        }
    }

    /**
     *
     * @param river The {@link River} to check
     * @param path The current {@link Path} to check elevation
     * @param start The start {@link Vertex} of the path
     * @return True if this {@link Path} is at the lowest point. False otherwise.
     */
    private boolean isAtLowest(River river, Path path, Vertex start) {
        boolean isLowest = false;

        if (!river.getStartVertices().contains(start)) {
            Vertex endVertex = river.getEnd();
            Path lastPath = river.getRiverPath().stream().filter(p -> p.hasVertex(endVertex)).findFirst().get();
            isLowest = lastPath.getElevation() < path.getElevation();
        }

        return isLowest;
    }

    /**
     *
     * @param river The {@link River} to check
     * @param path The current {@link Path} to check loop
     * @return True if the river is looping around the current tile. False otherwise.
     */
    private boolean isLooping(River river, Path path) {
        Tile currentTile = river.getTiles().stream()
                .filter(t -> t.getPaths().contains(path) && t.getElevation() == path.getElevation())
                .findFirst().get();

        List<Path> tilePaths = currentTile.getPaths();
        tilePaths.retainAll(river.getRiverPath());
        int numPaths = tilePaths.size();

        return numPaths > 1;
    }
}