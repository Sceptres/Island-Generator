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

            River river = new River(spring, random.nextFloat(0.1f));
            this.generateRiverPath(river, land, ocean, river.getStart());

            List<Path> riverPaths = river.getRiverPath();

//            river.getTiles().forEach(t -> t.setType(TileType.LAND_WATER_TILE));
            if(riverPaths.size() != 0)
                land.addRiver(river);
        }

        List<River> rivers = land.getRivers();
        for(River river : rivers) {
            List<Path> riverPath = river.getRiverPath();
            Path start = riverPath.get(0);
            start.setType(PathType.TEST);

            List<River> intersectingRivers = rivers.stream()
                    .filter(r -> r != river && river.intersect(r))
                    .toList();
            for(River river1 : intersectingRivers) {
//                river.getFlow()+river1.getFlow()
                river.update(river1.getEnd(), 3f);
            }

            boolean doesIntersectWithOther = rivers.stream()
                    .anyMatch(r -> r != river && r.intersect(river));

            if(!doesIntersectWithOther) {
                Path lastPath = riverPath.get(riverPath.size() - 1);
                Vertex endVertex = river.getEnd();

                Optional<Tile> oceanTile = land.getTiles().stream()
                        .filter(t -> t.getVertices().contains(endVertex) && !t.getPaths().contains(lastPath))
                        .findFirst();
                if (oceanTile.isPresent()) {
                    Tile tile = oceanTile.get();
                    tile.setType(TileType.LAND_WATER_TILE);
                }
            }
        }
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

        Optional<Path> newPath = paths.stream()
                .filter(p -> p.hasVertex(start))
                .min(Comparator.comparingDouble(Path::getElevation));

        if (newPath.isPresent()) {
            Path path = newPath.get();

            Vertex v1 = path.getV1();
            Vertex v2 = path.getV2();
            Vertex next = v1.equals(start) ? v2 : v1;

            boolean isLowestElevation = false;

            if (!river.getStart().equals(start)) {
                List<Path> riverPath = river.getRiverPath();
                Path lastPath = riverPath.get(riverPath.size() - 1);
                isLowestElevation = lastPath.getElevation() < path.getElevation();
            }

            boolean reachedWater = tiles.stream()
                    .filter(t -> t.getVertices().contains(next))
                    .anyMatch(t -> t.getType().getGroup() == TileGroup.WATER);

            boolean isLooping = river.intersect(next);

            if (isLowestElevation || reachedWater || isLooping) {
                river.setEnd(next);
                return;
            }

            boolean hasIntersection = land.getRivers().stream().anyMatch(river::intersect);
            if(hasIntersection)
                return;

            List<Tile> pathTiles = tiles.stream().filter(t -> t.getPaths().contains(path)).toList();
            river.addPath(path, pathTiles);

            generateRiverPath(river, land, ocean, next);
        }
    }
}