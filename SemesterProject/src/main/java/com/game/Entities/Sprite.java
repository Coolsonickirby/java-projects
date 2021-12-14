package com.game.Entities;

import com.game.Managers.RenderManager;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite {
    private static final String TAG = "sprite";
    private ImageView spritesheet;
    private Transform transform;
    private double xRect;
    private double yRect;
    private double xSize;
    private double ySize;
    private double width;
    private double height;
    private SpriteData spriteData;
    
    public SpriteData getSpriteData() {
        return spriteData;
    }

    public void setSpriteData(SpriteData spriteData) {
        this.spriteData = spriteData;
    }

    public String getTag() {
        return spriteData.tag;
    }

    public void setTag(String tag) {
        this.spriteData.tag = tag;
    }

    public Sprite(Image spritesheet) {
        this.spritesheet = new ImageView(spritesheet);
        this.transform = new Transform();
        this.spriteData = new SpriteData(Sprite.TAG, 0);
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public ImageView getSpritesheet() {
        return this.spritesheet;
    }

    public void setSpritesheet(ImageView spritesheet) {
        this.spritesheet = spritesheet;
    }

    public double getXSize() {
        return xSize;
    }

    public void setXSize(double xSize) {
        this.xSize = xSize;
    }

    public double getYSize() {
        return ySize;
    }

    public void setYSize(double ySize) {
        this.ySize = ySize;
    }

    public double getXRect() {
        return xRect;
    }

    public void setXRect(double xRect) {
        this.xRect = xRect;
    }

    public double getYRect() {
        return yRect;
    }

    public void setYRect(double yRect) {
        this.yRect = yRect;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getLayer() {
        return spriteData.layer;
    }

    public void setLayer(int layer) {
        this.spriteData.layer = layer;
    }

    public void draw(){
        RenderManager.Draw(this);
    }

    public void onClick(){
        // Do nothing in general
    }

    public void update(){
        // Do nothing in general
    }
    
    public void onHover(){
        // Do nothing in general
    }
}
