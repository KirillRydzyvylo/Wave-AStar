import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiFunction;

public class NavigationAStar implements Navigator {
	private static final char WALL = '#';
	private final static char ENTER = '@';
	private final static char EXIT = 'X';
	private int M ;
	private int N ;
	private char[][] map;
	private int exitX;
	private int exitY;
	
	
	
	@Override
	public char[][] searchRoute(char[][] map) {
		M = map.length;
		N = map[0].length;
		this.map = map;
		
		try {
			HashMap<Integer, Position> way = new HashMap<>();
			ArrayList<Position> suitableWay  = new ArrayList<>(); 
			Integer [] coordinates = findEnterExitCoordinates(map);
			exitX = coordinates[2];
			exitY = coordinates[3];
			Position route = new Position( coordinates[0], coordinates[1]);
			way.put(route.getPosition(), route);
			suitableWay = findNeighbords(route);
			Collections.sort(suitableWay);
			
			while(suitableWay.size() != 0) {
				route = suitableWay.remove(0);
				if(route.getX() == exitX && route.getY() == exitY) {
					way.put(route.getPosition(), route);
					break;
				}
				else {
					if(way.get(route.getPosition()) == null) {
						way.put(route.getPosition(), route);
						suitableWay.addAll(findNeighbords(route));
						Collections.sort(suitableWay);
					}
					
				}
			}
			
			int exitPosition = exitX * N + exitY; 
			if( way.get(exitPosition) == null ) {
				return null;
			}
			else {
				HashSet<Integer> bestWay = new HashSet<>();
				Position startPosition = new Position( coordinates[0], coordinates[1]);
				bestWay.add(route.getPosition());				
				ArrayList<Position> neigbords = findNeighbords(route); 				
				do {
					Collections.sort(neigbords);
					for( int i = neigbords.size() - 1 ; i > -1  ; i-- ) {
						if( way.get(neigbords.get(i).getPosition()) != null &&  bestWay.contains(neigbords.get(i).getPosition()) == false ) {
							route = neigbords.get(i);
							bestWay.add(route.getPosition());
							map[route.getX()][route.getY()]='+';
							break;
						}
					}
					neigbords=findNeighbords(route); 
				}
 				while( startPosition.getPosition() != route.getPosition() );
				map[route.getX()][route.getY()]='@';
				return map;
			}			
		}
		catch (IllegalArgumentException e) {
			return null;
		}		
	}
	
	
	
	
	private Integer[] findEnterExitCoordinates(char[][] map) throws IllegalArgumentException {
		Integer [] enterExitCoordinates = new Integer[4];//координаты x и y
		int flagEnter = 0; // Флаг поиска входа
		int flagExit = 0; // Флаг поиска выхода
		for(int i = 0; i < map.length; i++) {
			for( int j = 0; j < map[0].length; j++) {
				// поиск входа
				if(map[i][j] == ENTER ) { 
					enterExitCoordinates[0]=i;
					enterExitCoordinates[1]=j;
					flagEnter++ ; 
					continue;
				}
				// поиск выхода
				if(map[i][j] == EXIT ) { 
					enterExitCoordinates[2]=i;
					enterExitCoordinates[3]=j;
					flagExit++ ; 
				}
			}
		}
		
		if( flagEnter == 0 ) {
			throw new IllegalArgumentException("Нет ни одного входа");
		}
		if( flagExit == 0 ) {
			throw new IllegalArgumentException("Нет ни одного Выхода");
		}
		
		if( flagEnter > 1 ) { 
			throw new IllegalArgumentException("Входов больше 1");
		}
		if( flagExit > 1 ) { 
			throw new IllegalArgumentException("Выходов больше 1");
		}
		
		return enterExitCoordinates;
	}
	
	
	
	
	public ArrayList<Position> findNeighbords(Position p){
		BiFunction<Integer,Integer,Boolean> isWall = ( x, y )->( map[x][y] == WALL );

		ArrayList<Position> neighbords = new ArrayList<>();
		if(p.getX() > 0) {
			if(!isWall.apply( p.getX() - 1, p.getY() )) {
				neighbords.add(new Position(p.getX() - 1, p.getY()));
			}
		}
		if(p.getX() < M - 1) {
			if(!isWall.apply(  p.getX() + 1, p.getY() )) {
				neighbords.add(new Position(p.getX() + 1, p.getY() ));
			}
		}
		if(p.getY() > 0) {
			if(!isWall.apply( p.getX(), p.getY() - 1 )) {
				neighbords.add(new Position(p.getX(), p.getY() - 1 ));
			}
		}
		if(p.getY() < N - 1) {
			if(!isWall.apply( p.getX(), p.getY() + 1 )) {
				neighbords.add(new Position(p.getX(), p.getY() + 1 ));
			}
		}
		return neighbords;
	}
	

	
	
	public class Position implements Comparable<Position>{
		private int x;
		private int y;
		private int position;
		private int distance;
		
		public Position(int x, int y) {
			
			
			this.x = x;
			this.y = y;
			position = x * N + y ;
			distance = (int)(Math.pow( exitX - x , 2) + Math.pow( exitY - y , 2));
		}

		public int getPosition() {
			return position;
		}

		public void setPosition(int position) {
			this.position = position;
		}

		public int getDistance() {
			return distance;
		}

		public void setDistance(int x , int y) {
			this.distance = (int)(Math.pow( x - this.x , 2) + Math.pow( y - this.y , 2));
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		
		
		@Override
		public int compareTo(Position p) {
			return distance > p.getDistance() ? 1 : distance < p.getDistance() ? -1 : 0 ;
		}

		
		
		@Override
		public String toString() {
			return "Position [x=" + x + ", y=" + y + ", position=" + position + ", distance=" + distance + "]";
		}
		
		
		
				
	}
	
	public void drawMap() {
		for(int i = 0 ; i < map.length ; i++) {
			for(int j = 0 ; j < map[0].length ; j++) {
				System.out.print(map[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	
}


