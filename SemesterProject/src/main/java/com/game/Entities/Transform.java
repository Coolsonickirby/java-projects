package com.game.Entities;

public class Transform {
    public double XPos;
    public double YPos;
    public double ZPos;
    public double XRot;
    public double XScale;
    public double YScale;
    public double ZScale;
    public Transform(){
        this.XPos = 0;
        this.YPos = 0;
        this.ZPos = 0;
        this.XRot = 0;
        this.XScale = 1;
        this.YScale = 1;
        this.ZScale = 1;
    }
    public Transform(double XPos, double YPos, double ZPos, double XRot, double XScale, double YScale, double ZScale) {
        this.XPos = XPos;
        this.YPos = YPos;
        this.ZPos = ZPos;
        this.XRot = XRot;
        this.XScale = XScale;
        this.YScale = YScale;
        this.ZScale = ZScale;
    }
}
