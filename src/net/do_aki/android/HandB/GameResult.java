package net.do_aki.android.HandB;


public class GameResult {
    
    final static int STATUS_CONTINUE = 1;
    final static int STATUS_FINISH = 2;
    final static int STATUS_INVALID = 3;
    
    private Integer hit;
    private Integer ball;
    private int status;
    
    public GameResult(int hit, int ball, int status) {
        this.hit = hit;
        this.ball = ball;
        this.status = status;
    }
    
    public Integer getHit() {
        return hit;
    }
    
    public Integer getBall() {
        return ball;
    }
    
    public int getStatus() {
        return status;
    }
    
}
