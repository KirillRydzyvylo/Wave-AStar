

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Navigation implements Navigator{
	private final static int ZERO = 0 ; 
	final static char ENTER = '@';
	final static char EXIT = 'X';
	private int M; 
	private int N; 
	private char[][] map;
	
	@Override
	public char[][] searchRoute(char[][] map) {
		M = map.length;
		N = map[ZERO].length;
		this.map = map;
		int mapSize = M*N;		
		int minWave;
		Integer neighborWave;
		
		HashMap<Integer,Integer> positions = new HashMap<>();// карта позиция-волна

		BiFunction<Integer, Integer, Integer> numPosition = (i, j) -> (i *N + j);// преобразование двумерного массива в одномерный
		
	
		try {
			Integer [] coordinates = findEnterExitCoordinates(map);
			
			int exitPosition = numPosition.apply(coordinates[2], coordinates[3]);
			int position = numPosition.apply(coordinates[0], coordinates[1]);		
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
					map[bestWay/N][bestWay%N]='+';
				}
				return map;
			}
			
		}
		catch (IllegalArgumentException e) {
			return null;
		}
		
	}
	

	private Integer[] findEnterExitCoordinates(char[][] map) throws IllegalArgumentException {
		Integer [] enterExitCoordinates = new Integer[4];//координаты x и y входа и выхода

		int flagEnter = 0; // Флаг поиска входа
		int flagExit = 0; // Флаг поиска выхода
		for(int i = 0; i < map.length; i++) {
			for( int j = 0; j < map[ZERO].length; j++) {
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
	
	public ArrayList<Integer> findNeighbors(int position){
		
		Function<Integer, Boolean> isWall = (p)->( map[ p / N ][ p % N] == '#');
		
		ArrayList<Integer> neighbors = new ArrayList<Integer>(); 
		if( position % N > ZERO ) {
			if(!isWall.apply(position - 1))	{
				neighbors.add(position - 1);
			}
		}
		
		if( position % N < (N - 1) ) {
			if(!isWall.apply(position + 1))	{
				neighbors.add(position + 1);
			}
		}
		
		if( position / N > ZERO ) {
			if(!isWall.apply(position - N ))	{
				neighbors.add(position - N);
			}
		}
		
		if( position / N < (M-1) ) {
			if(!isWall.apply(position + N))	{
				neighbors.add(position + N);
			}
		}
		
		return neighbors;
	}
	
	
	

}
