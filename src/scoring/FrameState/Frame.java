package scoring.FrameState;

public class Frame {
    private FrameStatus status;
    private int score;

    public Frame(){
        this.status = new Unfinished();
        this.score = 0;
    }

    public int getScore(){
        return this.score;
    }

    public void addBall(int ball){
        status.addBall(ball);
    }

    public void setStatus(FrameStatus status){
        this.status = status;
    }
}
