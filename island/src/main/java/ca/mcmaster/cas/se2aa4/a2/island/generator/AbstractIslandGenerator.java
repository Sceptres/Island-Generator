package ca.mcmaster.cas.se2aa4.a2.island.generator;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.AltimeterProfile;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Land;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Ocean;
import ca.mcmaster.cas.se2aa4.a2.island.geography.River;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.mesh.IslandMesh;
import ca.mcmaster.cas.se2aa4.a2.island.path.Path;
import ca.mcmaster.cas.se2aa4.a2.island.path.type.PathType;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileGroup;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.util.*;

public abstract class AbstractIslandGenerator implements IslandGenerator {

    private final Land land;
    private final Ocean ocean;
    private final int numLakes;
    private final int numRivers;
    private final int numAquifers;
    private final Shape shape;
    private final AltimeterProfile altimeterProfile;
    protected final IslandMesh mesh;

    protected AbstractIslandGenerator(
            IslandMesh mesh,
            Shape shape,
            AltimeterProfile altimeterProfile,
            int numLakes,
            int numAquifers,
            int numRivers
    ) {
        this.land = new Land();
        this.ocean = new Ocean();
        this.mesh = mesh;
        this.shape = shape;
        this.altimeterProfile = altimeterProfile;
        this.numLakes = numLakes;
        this.numRivers = numRivers;
        this.numAquifers = numAquifers;
    }

    @Override
    public final void generate() {
        List<Tile> tiles = this.mesh.getTiles();

        this.generateIsland(tiles, this.ocean, this.land, this.shape);
        this.generateElevation(this.land);
        this.generateAquifers(this.land, this.numAquifers);
        this.generateLakes(this.land, this.numLakes);
        this.generateRivers(this.land, this.ocean, this.numRivers);

    }

    /**
     * Generates the land tiles and ocean tiles
     *
     * @param tiles The {@link List} of tiles given by the mesh
     */
    protected abstract void generateIsland(List<Tile> tiles, Ocean ocean, Land land, Shape shape);

    /**
     * Generates lakes in the island
     *
     * @param land All the land {@link Tile} since lakes can only be in land
     */
    protected abstract void generateLakes(Land land, int numLakes);

    private void generateRivers(Land land, Ocean ocean, int numRivers) {
        Random random = new Random();

        for (int i = 0; i < numRivers; i++) {
            List<Vertex> springs = land.getSprings();
            Vertex spring = springs.get(random.nextInt(springs.size()));

            River river = new River(spring, random.nextFloat(1f, 1.5f));
            land.addRiver(river);
            this.generateRiverPath(river, land, ocean, spring);
        }

        List<River> mergedRivers = new ArrayList<>();

        List<River> rivers = land.getRivers();
        for(River river : rivers) {
            List<River> intersectingRivers = rivers.stream()
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

        mergedRivers.forEach(land::removeRiver);

        land.getRivers().forEach(river -> {
            Vertex endVertex = river.getEnd();
            List<Path> riverPaths = river.getRiverPath();

            river.getStartVertices().forEach(v -> {
                List<Path> startPaths = riverPaths.stream().filter(p -> p.hasVertex(v)).toList();
                startPaths.forEach(p -> p.setType(PathType.TEST));
            });

            Path lastPath = riverPaths.stream().filter(p -> p.hasVertex(endVertex)).findFirst().get();

            Optional<Tile> oceanTile = land.getTiles().stream()
                    .filter(t -> t.getVertices().contains(endVertex) && !t.getPaths().contains(lastPath))
                    .findFirst();
            if (oceanTile.isPresent()) {
                Tile tile = oceanTile.get();
                tile.setType(TileType.LAND_WATER_TILE);
            }
        });
    }

    /**
     * Sets the elevation of land tiles. Ocean tiles are left at 0.
     */
    private void generateElevation(Land land) {
        land.getTiles().forEach(t -> {
            double elevation = this.altimeterProfile.generateElevation(this.shape, t);
            t.setElevation(elevation);
        });
    }

    private void generateAquifers(Land land, int numAquifers) {
        Random random = new Random();

        List<Tile> tiles = land.getTiles();

        for(int i=0; i < numAquifers; i++) {
            int randIdx = random.nextInt(0, tiles.size());
            Tile aquifer_tile = tiles.get(randIdx);
            aquifer_tile.putAquifer();

            tiles = tiles.stream().filter(t -> !t.hasAquifer()).toList();
        }
    }

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

            boolean isLowestElevation = false;

            if (!river.getStartVertices().contains(start)) {
                Vertex endVertex = river.getEnd();
                Path lastPath = river.getRiverPath().stream().filter(p -> p.hasVertex(endVertex)).findFirst().get();
                isLowestElevation = lastPath.getElevation() < path.getElevation();
            }

            boolean reachedWater = tiles.stream()
                    .filter(t -> t.getVertices().contains(next))
                    .anyMatch(t -> t.getNeighbors().stream().anyMatch(t1 -> t1.getType().getGroup() == TileGroup.WATER));


            if(isLowestElevation || reachedWater)
                return;

            List<Tile> pathTiles = tiles.stream().filter(t -> t.getPaths().contains(path)).toList();
            river.addPath(path, start, pathTiles);

            boolean hasIntersection = land.getRivers().stream()
                    .filter(r -> !r.equals(river))
                    .anyMatch(river::intersect);

            Tile currentTile = river.getTiles().stream()
                    .filter(t -> t.getPaths().contains(path) && t.getElevation() == path.getElevation())
                    .findFirst().get();
            List<Path> tilePaths = currentTile.getPaths();
            tilePaths.retainAll(river.getRiverPath());
            int numPaths = tilePaths.size();
            boolean isLooping = numPaths > 2;

            if(hasIntersection || isLooping)
                return;

            generateRiverPath(river, land, ocean, next);
        }
    }
}