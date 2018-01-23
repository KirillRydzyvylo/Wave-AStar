import java.util.Arrays;

public class NavigetionStarter3 {
public static void main(String[] args) {
	//	
		
		char [][] map1= {
		{'.','.','.','.','.'},
		{'.','.','.','.','@'},
		{'.','.','#','#','#'},
		{'.','.','#','#','#'},
		{'.','#','.','.','.'},
		{'.','.','.','X','.'}
		};
		
		char [][] map2= {
				{'.','.','.','.','.'},
				{'.','.','.','.','@'},
				{'.','.','#','#','#'},
				{'.','.','#','#','#'},
				{'.','.','.','.','.'},
				{'#','.','.','X','.'}
				};
		
		char [][] map3= {
				{'.','.','.','.','.'},
				{'.','.','.','.','X'},
				{'.','.','.','.','.'},
				{'.','.','.','.','.'},
				{'.','.','.','.','.'},
				{'.','.','.','@','.'}
				};
		char [][] map4= {
				{'.'}
				};
		char [][] map5= {
				{'.','@','.','.','.','.','X'}
				};
		
		char [][] map6= {
				{'.','.','.','.','.'},
				{'.','.','.','.','#'},
				{'.','.','x','x','x'},
				{'.','.','x','x','x'},
				{'.','x','.','.','.'},
				{'.','.','@','@','.'}
				};
		
		char [][] map7= {
				{'.','.','.','.','.'},
				{'.','.','.','.','.'},
				{'.','.','#','#','#'},
				{'.','.','#','#','#'},
				{'.','#','.','.','.'},
				{'.','.','.','X','.'}
				};
		
		char [][] map8= {
				{'.','.','.','.','.'},
				{'.','.','.','.','.'},
				{'.','.','#','#','#'},
				{'.','.','#','#','#'},
				{'.','#','.','.','.'},
				{'.','.','X','X','.'}
				};
		char [][] map9= {
				{'.','.','.','.','.'},
				{'.','.','.','.','@'},
				{'.','.','#','#','#'},
				{'.','.','#','#','#'},
				{'#','#','.','.','.'},
				{'.','.','.','X','.'}
				};
		char [][] map10= {
				{'.','.','.','.','.','.'},
				{'.','.','#','.','.','.'},
				{'.','.','#','.','.','.'},
				{'.','.','#','.','.','.'},
				{'.','#','.','.','.','.'},
				{'X','#','.','.','.','@'}
				};
		
		char [][] map11= {
				{'.','.','.','.','.','.'},
				{'.','.','#','.','.','.'},
				{'.','.','#','.','.','.'},
				{'.','.','#','.','.','.'},
				{'.','#','.','.','.','.'},
				{'X','#','.','.','.','@'}
				};
		
		
		
		
		Navigation navigation = new Navigation(); 
		long startTime = System.currentTimeMillis();
		char [][] map = navigation.searchRoute(map10);
		long stopTime = System.currentTimeMillis()-startTime;
		System.out.println("start: "+startTime+" stop: "+stopTime);
		
		if( map != null) {
			for(int i = 0 ; i < map.length ; i++) {
				for(int j = 0 ; j < map[0].length ; j++) {
					System.out.print(map[i][j]+" ");
				}
				System.out.println();
			}
		}
		else  System.out.println(map);
		
		
			
		NavigationAStar navigationAStar = new NavigationAStar(); 
		long startTime2 = System.currentTimeMillis();
		char [][] newmap = navigationAStar.searchRoute(map11);
		long stopTime2 = System.currentTimeMillis()-startTime;
		System.out.println("start2: "+startTime2+" stop2: "+stopTime2);
		
		if( newmap != null) {
			for(int i = 0 ; i < newmap.length ; i++) {
				for(int j = 0 ; j < newmap[0].length ; j++) {
					System.out.print(newmap[i][j]+" ");
				}
				System.out.println();
			}
		}
		else  System.out.println(map);
	}

}
