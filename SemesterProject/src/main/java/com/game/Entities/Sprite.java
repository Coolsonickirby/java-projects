package com.game.Entities;

import com.game.Managers.RenderManager;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite {
    private ImageView spritesheet;
    private Transform transform;
    private double xRect;
    private double yRect;
    private double xSize;
    private double ySize;
    private double width;
    private double height;
    private int layer;

    public Sprite(Image spritesheet) {
        this.spritesheet = new ImageView(spritesheet);
        this.transform = new Transform();
    }

    public void onClick(){
        System.out.println("Sprite clicked!");
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
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public void draw(){
        RenderManager.Draw(this);
    }

    public void update(){
        // Do nothing in general
    }
}
