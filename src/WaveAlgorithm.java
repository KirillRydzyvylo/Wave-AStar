

import java.util.ArrayList;
import java.util.HashMap;


public class WaveAlgorithm implements Navigator{
	private final static int ZERO = 0 ; 
	private final static char ENTER = '@';
	private final static char EXIT = 'X';
	private final static char WAY = '+';
	private final static char WALL = '#';
	private int rows; 
	private int lines; 
	private char[][] map;
	
	@Override
	public char[][] searchRoute(char[][] map) {
		rows = map.length;
		lines = map[ZERO].length;
		this.map = map;
		int mapSize = rows * lines;		
		int minWave;
		Integer neighborWave;
		
		HashMap<Integer,Integer> positions = new HashMap<>();// карта позиция-волна

		try {
			Integer [] coordinates = findEnterExitCoordinates(map);
			
			int exitPosition = getNumPosition(coordinates[2], coordinates[3]);
			int position = getNumPosition(coordinates[0], coordinates[1]);		
			positions.put(position, ZERO);
			ArrayList<Integer> neighbors = findNeighbors(position);
				
			while( neighbors.size() != ZERO ) {
				minWave = mapSize;
				position = neighbors.get(ZERO);
				
				if( position == exitPosition) {
					positions.put(position, mapSize);
					break;
				}
	
				ArrayList<Integer> newNeighbors = findNeighbors(position);
		
				for(int neighbor : newNeighbors) {
					if((neighborWave = positions.get(neighbor)) != null) {
						minWave = minWave > neighborWave ? neighborWave : minWave;
					}
					else {
						neighbors.add(neighbor);
					}
				}
				positions.put( neighbors.remove( ZERO ), minWave + 1 );
			}
			
			if(positions.get(exitPosition) == null) {
				return null;
			}
			else {
				int bestWay = exitPosition;
				minWave = mapSize;
				while (positions.get(bestWay) != 1) {
					neighbors = findNeighbors(bestWay);					
					for(int neighbor : neighbors) {
						if((neighborWave = positions.get(neighbor)) != null) {
							if(minWave > neighborWave) {
								minWave = neighborWave;
								bestWay = neighbor; 
							}
						}
					}
					this.map[bestWay/lines][bestWay%lines] = WAY;
				}
				return this.map;
			}
			
		}
		catch (Exception e) {
			return null;
		}
		
	}
	
	private int getNumPosition(int l, int r ) { // преобразование двумерных координат в одномерные
		return (l *lines + r);
	}
	

	private Integer[] findEnterExitCoordinates(char[][] map) throws Exception {
		Integer [] enterExitCoordinates = new Integer[4];//координаты x и y входа и выхода
		int amountEnter = 0; // Флаг поиска входа
		int amountExit = 0; // Флаг поиска выхода
		for(int i = 0; i < rows; i++) {
			for( int j = 0; j < lines; j++) {
				// поиск входа
				if(map[i][j] == ENTER ) { 
					enterExitCoordinates[0]=i;
					enterExitCoordinates[1]=j;
					amountEnter++ ; 
					continue;
				}
				// поиск выхода
				if(map[i][j] == EXIT ) { 
					enterExitCoordinates[2]=i;
					enterExitCoordinates[3]=j;
					amountExit++ ; 
				}
			}
		}
		
		if( amountEnter == 0 ) {
			throw new Exception("Нет ни одного входа");
		}
		if( amountExit == 0 ) {
			throw new Exception("Нет ни одного Выхода");
		}
		
		if( amountEnter > 1 ) { 
			throw new Exception("Входов больше 1");
		}
		if( amountExit > 1 ) { 
			throw new Exception("Выходов больше 1");
		}
		
		return enterExitCoordinates;
	}
	
	private ArrayList<Integer> findNeighbors(int position){
	
		ArrayList<Integer> neighbors = new ArrayList<Integer>(); 
		if( position % lines > ZERO ) {
			if(!isWall(position - 1))	{
				neighbors.add(position - 1);
			}
		}
		
		if( position % lines < (lines - 1) ) {
			if(!isWall(position + 1))	{
				neighbors.add(position + 1);
			}
		}
		
		if( position / lines > ZERO ) {
			if(!isWall(position - lines ))	{
				neighbors.add(position - lines);
			}
		}
		
		if( position / lines < (rows-1) ) {
			if(!isWall(position + lines))	{
				neighbors.add(position + lines);
			}
		}
		
		return neighbors;
	}
	
	
	private boolean isWall(int position ) {
		return ( map[ position / lines ][ position % lines] == WALL);
	}
	
	

}
