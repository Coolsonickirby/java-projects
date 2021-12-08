package com.game.Entities;

public class Transform {
    public double XPos;
    public double YPos;
    public double ZPos;
    public double XRot;
    public double YRot;
    public Transform(){
        this.XPos = 0;
        this.YPos = 0;
        this.ZPos = 0;
        this.XRot = 0;
        this.YRot = 0;
    }
    public Transform(double XPos, double YPos, double ZPos, double XRot, double YRot) {
        this.XPos = XPos;
        this.YPos = YPos;
        this.ZPos = ZPos;
        this.XRot = XRot;
        this.YRot = YRot;
    }
}
