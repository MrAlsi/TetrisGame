package com.company.Gioco;

public class CadutaBlocco extends Thread {

    private boolean dropBrick = false;
    private Integer delay = Integer.MAX_VALUE;

    public boolean getDropBrick() {
        if(this.dropBrick) {
            this.dropBrick = false;
            return true;
        }
        return this.dropBrick;
    }
    public void setDropBrick(boolean dropBrick) {
        this.dropBrick = dropBrick;
    }

    public Integer getDelay() {
        return delay;
    }
    public void setDelay(Integer delay) {
        this.delay = delay;
    }


    public void run() {
        while(true) {
            try {
                Thread.sleep(this.delay);
            } catch(InterruptedException e) {

                e.printStackTrace();
            }
            this.dropBrick = true;
        }
    }
}
