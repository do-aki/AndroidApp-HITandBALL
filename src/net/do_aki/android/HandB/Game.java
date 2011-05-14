package net.do_aki.android.HandB;

import java.util.*;


public class Game {

    final int FIGURE = 4;

    private int[] target = {0,0,0,0};

    public void initialize() {
        List<Integer> seed = Arrays.asList(0,1,2,3,4,5,6,7,8,9);
        Collections.shuffle(seed);
        
        for(int i=0;i<FIGURE;++i) {
            target[i] = seed.get(i);
        }
        
        android.util.Log.d("answer", Arrays.toString(target));
    }

    public GameResult judge(String input) {
        
        if (input.length() != FIGURE) {
            return new GameResult(0, 0, GameResult.STATUS_INVALID);
        }
        
        int hit = 0;
        int ball = 0;
        
        android.util.Log.d("inupt string",input);
        
        for(int i=0;i<FIGURE;++i) {
            int c = Integer.parseInt(input.substring(i,i+1));
            
            for(int j=0;j<FIGURE;++j) {
                android.util.Log.d("c", String.format("%d (%d) : %d (%d)", i,c,j,target[j]));
                
                if (c == target[j]) {
                    if (i==j) {
                        ++hit;
                    } else {
                        ++ball;
                    }
                    break;
                }
            }
        }
        
        if (hit == FIGURE) {
            return new GameResult(hit, ball, GameResult.STATUS_FINISH);
        } else {
            return new GameResult(hit, ball, GameResult.STATUS_CONTINUE);
        }
    }
    
}
