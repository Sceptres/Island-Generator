package ca.mcmaster.cas.se2aa4.a2.island.geography.generator.generators;

import ca.mcmaster.cas.se2aa4.a2.island.geography.Lake;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Land;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Ocean;
import ca.mcmaster.cas.se2aa4.a2.island.geography.River;
import ca.mcmaster.cas.se2aa4.a2.island.geography.generator.GeographyGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.path.Path;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileGroup;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.util.*;
import java.util.stream.Collectors;

public class RiverGenerator implements GeographyGenerator<River> {
    private final Land land;
    private final Ocean ocean;

    public RiverGenerator(Land land, Ocean ocean) {
        this.land = land;
        this.ocean = ocean;
    }

    @Override
    public List<River> generate(Random random, int num) {
        List<River> generatedRivers = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            List<Vertex> springs = this.getSprings(generatedRivers);

            float flow = random.nextFloat(1f, 1.5f);
            int randIdx = random.nextInt(springs.size()-1);
            Vertex start = springs.get(randIdx);

            this.generateRiver(generatedRivers, start, flow);
        }

        List<River> removableRivers = this.findRemovableRivers(generatedRivers);
        generatedRivers.removeAll(removableRivers);

        generatedRivers.forEach(river -> {
            Vertex endVertex = river.getEnd();
            List<Path> riverPaths = river.getRiverPath();

            Path lastPath = riverPaths.stream().filter(p -> p.hasVertex(endVertex)).findFirst().get();

            Optional<Tile> waterTile = this.land.getTiles().stream()
                    .filter(t -> {
                        boolean hasEndVertex = t.getVertices().contains(endVertex);
                        boolean noLastPath = !t.getPaths().contains(lastPath);
                        boolean isLand = t.getType().getGroup() == TileGroup.LAND;
                        return hasEndVertex && noLastPath && isLand;
                    })
                    .findFirst();
            if (waterTile.isPresent()) {
                Tile tile = waterTile.get();
                Lake lake = new Lake(tile);
                lake.setElevation(lastPath.getElevation());
                this.land.addLake(lake);
            }
        });

        return generatedRivers;
    }


    /**
     *
     * @param rivers The already existing {@link River}
     * @param start The start {@link Vertex} of the river
     * @param flow The flow of the river
     */
    private void generateRiver(List<River> rivers, Vertex start, float flow) {
        River river = new River(start, flow);
        rivers.add(river);
        this.generateRiverPath(rivers, river, start);
    }

    /**
     *
     * @param generatedRivers The list of generated {@link River}
     * @return The list of {@link River} that should be removed
     */
    private List<River> findRemovableRivers(List<River> generatedRivers) {
        List<River> mergedRivers = new ArrayList<>();

        for(River river : generatedRivers) {
            List<Path> riverPaths = river.getRiverPath();

            if(riverPaths.size() == 0) {
                mergedRivers.add(river);
                continue;
            }

            List<River> intersectingRivers = generatedRivers.stream()
                    .filter(r -> r != river && river.intersect(r) && !mergedRivers.contains(r))
                    .toList();
            for(River river1 : intersectingRivers) {
                river.merge(river1);
                mergedRivers.add(river1);
            }
        }

        return mergedRivers;
    }

    /**
     *
     * @param rivers The list of rivers generated so far
     * @param river The {@link River} we are generating path for
     * @param start The {@link Vertex} to start the river at
     */
    private void generateRiverPath(List<River> rivers, River river, Vertex start) {
        List<Tile> landTiles = this.land.getTiles();
        List<Tile> oceanTiles = this.ocean.getTiles();
        List<Tile> tiles = new ArrayList<>();
        tiles.addAll(landTiles);
        tiles.addAll(oceanTiles);

        List<Path> paths = this.land.getPaths();

        Optional<Path> newPath = paths.stream()
                .filter(p -> p.hasVertex(start))
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
                    .anyMatch(t -> t.getType().getGroup() == TileGroup.WATER);

            boolean hasIntersection = rivers.stream()
                    .filter(r -> !r.equals(river))
                    .anyMatch(r -> r.intersect(river));

            boolean isLooping = this.isLooping(river, path);

            if(hasIntersection || isLooping || isLowestElevation || reachedWater)
                return;

            generateRiverPath(rivers, river, next);
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
        Optional<Tile> currentTile = river.getTiles().stream()
                .filter(t -> t.getPaths().contains(path) && t.getElevation() == path.getElevation())
                .findFirst();
        if(currentTile.isPresent()) {
            Tile tile = currentTile.get();
            List<Path> tilePaths = tile.getPaths();
            tilePaths.retainAll(river.getRiverPath());
            int numPaths = tilePaths.size();
            return numPaths > 2;
        }

        return true;
    }

    /**
     *
     * @param rivers The list of already existing rivers
     * @return A list with all the available springs
     */
    private List<Vertex> getSprings(List<River> rivers) {
        List<Vertex> usedSprings = rivers.stream()
                .flatMap(r -> r.getVertices().stream())
                .distinct()
                .toList();

        List<Vertex> springs =  this.land.getTiles().stream()
                .filter(t -> t.getType().getGroup() == TileGroup.LAND)
                .filter(t -> t.getNeighbors().stream().noneMatch(t1 -> t1.getType().getGroup() == TileGroup.WATER))
                .flatMap(t -> t.getVertices().stream())
                .distinct()
                .collect(Collectors.toList());

        springs.removeAll(usedSprings);
        return springs;
    }
}