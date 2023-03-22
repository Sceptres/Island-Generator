package ca.mcmaster.cas.se2aa4.a2.island.geography;

import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class River {

    private Vertex start;

    public Vertex getStart() {
        return start;
    }

    public List<Segment> getRiverPath() {
        return riverPath;
    }

    private List<Segment> riverPath = new ArrayList<>();

    public River(Vertex start){
        this.start = start;
    }

    public static void createRiver(River river, List<Segment> riverPath, Vertex vertex, Mesh mesh, List<Tile> tiles, Land land){

        List<Segment> possibleSegments = new ArrayList<>();
        findSegments(vertex, mesh.getSegments(), possibleSegments);

        List<List<Tile>> possibleTiles = new ArrayList<>();

        possibleSegments.forEach(segment -> {
            possibleTiles.add(findSegmentTiles(segment, mesh.getPolygons(), tiles));
        });

        List<Double> lowestElevations = new ArrayList<>();

        possibleSegments.forEach(segment -> {
            lowestElevations.add(findLowestElevation(segment, mesh, tiles));
        });

        double lowestElevation = lowestElevations.stream().min(Double::compare).get();

        double currentLowestElevation = findLowestElevation(vertex, mesh, tiles);

        List<Tile> currentVertexTiles = findVertexTiles(vertex, mesh, tiles);

        if(currentLowestElevation > lowestElevation){

            if(!currentVertexTiles.stream().anyMatch(tile -> (tile.getType() == TileType.OCEAN_TILE) || (tile.getType() == TileType.LAND_WATER_TILE))){
                Tile lakeTile = null;

                for(int i = 0; i < currentVertexTiles.size(); i++){
                    if(Double.compare(currentVertexTiles.get(i).getElevation(), currentLowestElevation) == 0){
                        lakeTile = currentVertexTiles.get(i);
                        break;
                    }
                }

                land.addLake(new Lake(lakeTile));
            }
            return;
        }

        Segment nextSegment = null;

        for(int i = 0; i < possibleTiles.size(); i++){
            for(int j = 0; j < possibleTiles.get(i).size(); j++){
                if(Double.compare(possibleTiles.get(i).get(j).getElevation(), lowestElevation) == 0){
                    nextSegment = possibleSegments.get(i);
                }
            }
        }

        riverPath.add(nextSegment);

        Vertex endVertex = null;

        if(nextSegment.getV1() == vertex){
            endVertex = nextSegment.getV2();
        }
        else {
            endVertex = nextSegment.getV1();
        }

        List<Tile> endVertexTiles = findVertexTiles(endVertex, mesh, tiles);

        if(endVertexTiles.stream().anyMatch( tile -> (tile.getType() == TileType.OCEAN_TILE) || (tile.getType() == TileType.LAND_WATER_TILE))){
            return;
        }

        createRiver(river,riverPath, endVertex, mesh, tiles, land);



    }

    public static void drawRiver(List<Segment> riverPath, Mesh mesh){
        mesh.getSegments().forEach(segment -> {
            if(riverPath.contains(segment)){
                segment.setColor(new Color(14,240,37));
            }
        });
    }

    private static void findSegments(Vertex vertex, List<Segment> segments, List<Segment> segments2){
        segments.forEach(segment -> {
            if(segment.getV1() == vertex || segment.getV2() == vertex){
                segments2.add(segment);
            }
        });
    }

    private static void findPolygons(Segment segment, List<Polygon> polygons, List<Polygon> polygons2){
        polygons.forEach(polygon -> {
            if(polygon.getSegments().contains(segment)){
                polygons2.add(polygon);
            }
        });
    }

    private static void findPolygons(Vertex vertex, List<Polygon> polygons, List<Polygon> polygons2){
        polygons.forEach(polygon -> {
            if(polygon.getVertices().contains(vertex)){
                polygons2.add(polygon);
            }
        });
    }

    private static double findLowestElevation(Segment segment, Mesh mesh, List<Tile> tiles){

        List<Double> elevationList = new ArrayList<>();

        List<Polygon> polygons = mesh.getPolygons();

        List<Tile> segmentTiles = new ArrayList<>();

        segmentTiles = findSegmentTiles(segment, polygons, tiles);

        segmentTiles.forEach(tile -> {
            elevationList.add(tile.getElevation());
        });

        return elevationList.stream().min(Double::compare).get();
    }

    private static double findLowestElevation(Vertex vertex, Mesh mesh, List<Tile> tiles){

        List<Double> elevationList = new ArrayList<>();

        List<Tile> vertexTiles = new ArrayList<>();

        vertexTiles = findVertexTiles(vertex, mesh, tiles);

        vertexTiles.forEach(tile -> {
            elevationList.add(tile.getElevation());
        });

        return elevationList.stream().min(Double::compare).get();
    }

    private static List<Tile> findSegmentTiles(Segment segment, List<Polygon> polygons, List<Tile> tiles){
        List<Polygon> segmentPolygons = new ArrayList<>();

        List<Tile> segmentTiles = new ArrayList<>();

        findPolygons(segment, polygons, segmentPolygons);

        for(int i = 0; i < segmentPolygons.size(); i++){
            for(int j = 0; j < tiles.size(); j++){
                if(Tile.hasPolygon(tiles.get(j), segmentPolygons.get(i))){
                    segmentTiles.add(tiles.get(j));
                }
            }
        }

        return segmentTiles;
    }

    private static List<Tile> findVertexTiles(Vertex vertex, Mesh mesh, List<Tile> tiles){
        List<Polygon> vertexPolygons = new ArrayList<>();

        List<Tile> vertexTiles = new ArrayList<>();

        findPolygons(vertex, mesh.getPolygons(), vertexPolygons);

        for(int i = 0; i < vertexPolygons.size(); i++){
            for(int j = 0; j < tiles.size(); j++){
                if(Tile.hasPolygon(tiles.get(j), vertexPolygons.get(i))){
                    vertexTiles.add(tiles.get(j));
                }
            }
        }

        return vertexTiles;
    }

}
